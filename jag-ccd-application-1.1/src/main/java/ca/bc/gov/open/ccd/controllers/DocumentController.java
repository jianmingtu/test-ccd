package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.document.Document;
import ca.bc.gov.open.ccd.common.document.DocumentResult;
import ca.bc.gov.open.ccd.common.document.GetDocument;
import ca.bc.gov.open.ccd.common.document.GetDocumentResponse;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.Base64Utils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class DocumentController {

    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DocumentController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(
            namespace = "http://courts.gov.bc.ca/CCD.Source.GetDocument.ws:GetDocument",
            localPart = "getDocument")
    @ResponsePayload
    public GetDocumentResponse getDocument(@RequestPayload GetDocument document)
            throws JsonProcessingException {

        var inner =
                document.getDocumentRequest() != null
                        ? document.getDocumentRequest()
                        : new Document();

        // request getDocument to get url
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "common/document")
                        .queryParam("documentId", inner.getDocumentId())
                        .queryParam("courtDivisionCd", inner.getCourtDivisionCd());

        HttpEntity<Map<String, String>> resp = null;
        try {
            resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getDocument",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }

        if (resp != null && resp.getBody() != null) {
            var body = resp.getBody();
            String resultCd = body.get("resultCd");
            String resultMessage = body.get("resultMessage");
            String url = body.get("url");
            if (resultCd != null && resultMessage != null && url != null) {
                // process the response's error messages which are return from the ORDS getDocument
                // API
                if (!resultCd.equals("1")) {
                    var out = new GetDocumentResponse();
                    var one = new DocumentResult();
                    one.setResultCd(resultCd);
                    out.setDocumentResponse(one);
                    one.setResultCd(resultCd);
                    one.setResultMessage(resultMessage);
                    return out;
                }

                // request uri to get base64 document

                try {
                    HttpEntity<byte[]> resp2 =
                            restTemplate.exchange(
                                    new URI(url),
                                    HttpMethod.GET,
                                    new HttpEntity<>(new HttpHeaders()),
                                    byte[].class);

                    String bs64 =
                            resp2.getBody() != null
                                    ? Base64Utils.encodeToString(resp2.getBody())
                                    : "";

                    var out = new GetDocumentResponse();
                    var one = new DocumentResult();
                    one.setB64Content(bs64);
                    out.setDocumentResponse(one);
                    return out;
                } catch (Exception ex) {
                    log.error(
                            objectMapper.writeValueAsString(
                                    new OrdsErrorLog(
                                            "Error occurred while requesting an uri to get base64 document",
                                            "getDocument",
                                            ex.getMessage(),
                                            null)));
                    throw new ORDSException();
                }
            }
        }

        // the leftover is not invalid scenarios such as, a null response body.
        log.error(
                objectMapper.writeValueAsString(
                        new OrdsErrorLog(
                                "Error received from ORDS",
                                "getDocument",
                                "Either response or its body is null while receiving the request getDocument's response.",
                                null)));
        throw new ORDSException();
    }
}
