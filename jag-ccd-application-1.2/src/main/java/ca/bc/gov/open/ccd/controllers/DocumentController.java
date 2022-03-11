package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.document.secure.DocumentSecureRequest;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecure;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecureResponse;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import ca.bc.gov.open.ccd.models.RequestSuccessLog;
import ca.bc.gov.open.ccd.models.serializers.InstantSerializer;
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
            namespace =
                    "http://reeks.bcgov/CCD.Source.GetDocument.ws:CCD.Source.GetDocument.ws:GetDocumentSecure",
            localPart = "getDocumentSecure")
    @ResponsePayload
    public GetDocumentSecureResponse getDocumentSecure(@RequestPayload GetDocumentSecure document)
            throws JsonProcessingException {

        var inner =
                document.getDocumentSecureRequest() != null
                        ? document.getDocumentSecureRequest()
                        : new DocumentSecureRequest();

        // request getDocument to get url
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "common/document/secure")
                        .queryParam(
                                "requestAgencyIdentifierId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()))
                        .queryParam("applicationCd", inner.getApplicationCd())
                        .queryParam("documentId", inner.getDocumentId())
                        .queryParam("courtDivisionCd", inner.getCourtDivisionCd())
                        .queryParam("physicalFileId", inner.getPhysicalFileId())
                        .queryParam("mdocJustinNo", inner.getMdocJustinNo());

        HttpEntity<Map<String, String>> resp = null;
        try {
            resp =
                    restTemplate.exchange(
                            builder.build().toUri(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getDocumentSecure",
                                    ex.getMessage(),
                                    document)));
            throw new ORDSException();
        }

        if (resp != null && resp.getBody() != null) {
            var body = resp.getBody();
            String resultCd = body.get("resultCd");
            String resultMessage = body.get("resultMessage");
            String status = body.get("status");
            String url = body.get("url");

            if (status == null || !status.equals("1")) {
                // when status is null/or status is not 1, we neither pass the validation nor get a
                // valid url so that just return errors back
                var out = new GetDocumentSecureResponse();
                var documentResult = new ca.bc.gov.open.ccd.common.document.secure.DocumentResult();
                out.setDocumentResponse(documentResult);
                documentResult.setResultCd(resultCd != null ? resultCd : "");
                documentResult.setResultMessage(resultMessage != null ? resultMessage : "");
                return out;
            } else {
                if (resultCd != null && resultMessage != null && url != null) {
                    // process the response's error messages which are return from the ORDS
                    // getDocument API
                    if (!resultCd.equals("1")) {
                        var out = new GetDocumentSecureResponse();
                        var documentResult =
                                new ca.bc.gov.open.ccd.common.document.secure.DocumentResult();
                        out.setDocumentResponse(documentResult);
                        documentResult.setResultCd(resultCd);
                        documentResult.setResultMessage(resultMessage);
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

                        var out = new GetDocumentSecureResponse();
                        var documentResult =
                                new ca.bc.gov.open.ccd.common.document.secure.DocumentResult();
                        documentResult.setB64Content(bs64);
                        out.setDocumentResponse(documentResult);
                        log.info(
                                objectMapper.writeValueAsString(
                                        new RequestSuccessLog(
                                                "Request Success", "getDocumentSecure")));
                        return out;
                    } catch (Exception ex) {
                        log.error(
                                objectMapper.writeValueAsString(
                                        new OrdsErrorLog(
                                                "Error occurred while requesting an uri to get base64 document",
                                                "getDocumentSecure",
                                                ex.getMessage(),
                                                inner)));
                        throw new ORDSException();
                    }
                }
            }
        }

        // the leftover is not invalid scenarios such as, a null response body.
        log.error(
                objectMapper.writeValueAsString(
                        new OrdsErrorLog(
                                "Error received from ORDS",
                                "getDocument",
                                "Either response or its body is null while receiving the request getDocumentSecure's response.",
                                document)));
        throw new ORDSException();
    }
}
