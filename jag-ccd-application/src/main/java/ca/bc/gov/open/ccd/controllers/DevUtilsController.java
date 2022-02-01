package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.dev.utils.*;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Slf4j
@Endpoint
public class DevUtilsController {

    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public DevUtilsController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(
            namespace = "http://courts.ag.gov.bc.ca/CCD.Source.DevUtil.ws:DevUtils",
            localPart = "clearAppearanceResults")
    @ResponsePayload
    public ClearAppearanceResultsResponse clearAppearanceResults(
            @RequestPayload ClearAppearanceResults clrResults) throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "criminal/clear-appr-results");

        var inner =
                clrResults.getClrResults() != null ? clrResults.getClrResults() : new ClrResults();
        HttpEntity<ClrResults> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ClearAppearanceResultsResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ClearAppearanceResultsResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "clearAppearanceResults",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(
            namespace = "http://courts.ag.gov.bc.ca/CCD.Source.DevUtil.ws:DevUtils",
            localPart = "RecreateCourtList")
    @ResponsePayload
    public RecreateCourtListResponse reCreateCourtList(@RequestPayload RecreateCourtList crtList)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "criminal/recreatecourtlist");

        var inner =
                crtList.getRecreateCourtListDoc() != null
                        ? crtList.getRecreateCourtListDoc()
                        : new RecreateCourtListDoc();
        HttpEntity<RecreateCourtListDoc> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<RecreateCourtListResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            RecreateCourtListResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "reCreateCourtList",
                                    ex.getMessage(),
                                    inner)));
            throw new ORDSException();
        }
    }
}
