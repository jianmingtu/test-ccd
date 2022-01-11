package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.user.login.*;
import ca.bc.gov.open.ccd.common.user.mapping.*;
import ca.bc.gov.open.ccd.configuration.SoapConfig;
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

@Endpoint
@Slf4j
public class UserController {
    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getParticipantInfo")
    @ResponsePayload
    public GetParticipantInfoResponse getParticipantInfo(
            @RequestPayload GetParticipantInfo getParticipantInfo) throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("guid", getParticipantInfo.getGuid());

        try {
            HttpEntity<GetParticipantInfoResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetParticipantInfoResponse.class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getParticipantInfo",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "mapGuidToParticipant")
    @ResponsePayload
    public MapGuidToParticipantResponse mapGuidToParticipant(
            @RequestPayload MapGuidToParticipant mapGuidToParticipant)
            throws JsonProcessingException {
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("guid", mapGuidToParticipant.getGuid())
                        .queryParam("partId", mapGuidToParticipant.getPartId())
                        .queryParam("idirId", mapGuidToParticipant.getIdirId());

        try {
            HttpEntity<MapGuidToParticipantResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            new HttpEntity<>(new HttpHeaders()),
                            MapGuidToParticipantResponse.class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "mapGuidToParticipant",
                                    ex.getMessage(),
                                    mapGuidToParticipant)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getUserLogin")
    @ResponsePayload
    public GetUserLoginResponse getUserLogin(@RequestPayload GetUserLogin getUserLogin)
            throws JsonProcessingException {
        var inner =
                getUserLogin.getRequest() != null
                        ? getUserLogin.getRequest()
                        : new GetUserLoginRequestType();

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("temporaryAccessGuid", inner.getTemporaryAccessGuid())
                        .queryParam("domainUserGuid", inner.getDomainUserGuid())
                        .queryParam("domainUserId", inner.getDomainUserId());

        try {
            HttpEntity<GetUserLoginResponseType> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetUserLoginResponseType.class);
            var out = new GetUserLoginResponse();
            out.setResponse(resp.getBody());
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getUserLogin",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }
}
