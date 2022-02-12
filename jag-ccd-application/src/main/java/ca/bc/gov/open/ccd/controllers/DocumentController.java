package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.document.Document;
import ca.bc.gov.open.ccd.common.document.DocumentResult;
import ca.bc.gov.open.ccd.common.document.GetDocument;
import ca.bc.gov.open.ccd.common.document.GetDocumentResponse;
import ca.bc.gov.open.ccd.common.document.secure.DocumentSecureRequest;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecure;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecureResponse;
import ca.bc.gov.open.ccd.common.rop.report.GetROPReportResponse;
import ca.bc.gov.open.ccd.common.rop.report.RopResult;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.Map;

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
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "common/getdocument").
                queryParam("documentId", inner.getDocumentId())
                .queryParam("courtDivisionCd", inner.getCourtDivisionCd());

        HttpEntity<Map<String, String>> resp = null;
        try {
            resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<>() {
                            });
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

        if (resp != null &&  resp.getBody() != null) {
            var body = resp.getBody();
            String resultCd = body.get("resultCd");
            String resultMessage = body.get("resultMessage");
            if (resultCd != null && resultMessage != null) {
                // process the response's error messages or proactively detect potential errors
                if(!resultCd.equals("1")) {
                    var out = new GetDocumentResponse();
                    var one = new DocumentResult();
                    one.setResultCd(resultCd);
                    out.setDocumentResponse(one);
                    one.setResultCd(resultCd);
                    one.setResultMessage(resultMessage);
                    return out;
                }

                String uri = "https://www.google.ca/";
                // request uri to get base64 document
                try {
                    HttpEntity<byte[]> resp2 =
                            restTemplate.exchange(
                                    uri,
                                    HttpMethod.GET,
                                    new HttpEntity<>(new HttpHeaders()),
                                    byte[].class);

                    String bs64 =
                            resp2.getBody() != null ? Base64Utils.encodeToString(resp2.getBody()) : "";

                    var out = new GetDocumentResponse();
                    var one = new DocumentResult();
                    one.setB64Content(bs64);
                    out.setDocumentResponse(one);
                    return out;
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
            }
        }

        log.error(
                objectMapper.writeValueAsString(
                        new OrdsErrorLog(
                                "Error received from ORDS",
                                "getDocument",
                                "Either response or its body is null while receiving the request getDocument's response.",
                                null)));
        throw new ORDSException();
    }

    @PayloadRoot(
            namespace =
                    "http://reeks.bcgov/CCD.Source.GetDocument.ws:CCD.Source.GetDocument.ws:GetDocumentSecure",
            localPart = "getDocumentSecure")
    @ResponsePayload
    public GetDocumentSecureResponse getDocumentSecure(@RequestPayload GetDocumentSecure document)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "common/secure/document");

        var inner =
                document.getDocumentSecureRequest() != null
                        ? document.getDocumentSecureRequest()
                        : new DocumentSecureRequest();
        HttpEntity<DocumentSecureRequest> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<GetDocumentSecureResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            payload,
                            GetDocumentSecureResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getDocumentSecure",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
