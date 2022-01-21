package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.civil.*;
import ca.bc.gov.open.ccd.civil.CivilFileContentDoc;
import ca.bc.gov.open.ccd.civil.secure.*;
import ca.bc.gov.open.ccd.common.criminal.file.content.*;
import ca.bc.gov.open.ccd.common.criminal.file.content.FileContentDoc;
import ca.bc.gov.open.ccd.common.criminal.file.content.secure.*;
import ca.bc.gov.open.ccd.configuration.SoapConfig;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import ca.bc.gov.open.ccd.models.serializers.InstantSerializer;
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
public class FileController {

    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public FileController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCriminalFileContent")
    @ResponsePayload
    public GetCriminalFileContentResponse getCriminalFileContent(
            @RequestPayload GetCriminalFileContent getCriminalFileContent)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam(
                                "agencyIdentifierCd",
                                getCriminalFileContent.getAgencyIdentifierCd())
                        .queryParam("roomCd", getCriminalFileContent.getRoomCd())
                        .queryParam("proceedingDate", getCriminalFileContent.getProceedingDate())
                        .queryParam("appearanceID", getCriminalFileContent.getAppearanceID())
                        .queryParam("mdocJustinNo", getCriminalFileContent.getMdocJustinNo());

        try {
            HttpEntity<FileContentDoc> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            FileContentDoc.class);

            var out = new GetCriminalFileContentResponse();
            out.setFileContent(resp.getBody());

            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCriminalFileContent",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCriminalFileContentSecure")
    @ResponsePayload
    public GetCriminalFileContentSecureResponse getCriminalFileContentSecure(
            @RequestPayload GetCriminalFileContentSecure getCriminalFileContent)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam(
                                "agencyIdentifierCd",
                                getCriminalFileContent.getAgencyIdentifierCd())
                        .queryParam("roomCd", getCriminalFileContent.getRoomCd())
                        .queryParam("proceedingDate", getCriminalFileContent.getProceedingDate())
                        .queryParam("appearanceID", getCriminalFileContent.getAppearanceID())
                        .queryParam("mdocJustinNo", getCriminalFileContent.getMdocJustinNo())
                        .queryParam(
                                "requestAgenId",
                                getCriminalFileContent.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", getCriminalFileContent.getRequestPartId())
                        .queryParam(
                                "requestDtm",
                                InstantSerializer.convert(getCriminalFileContent.getRequestDtm()))
                        .queryParam("applicationCd", getCriminalFileContent.getApplicationCd());

        try {
            HttpEntity<GetCriminalFileContentSecureResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            GetCriminalFileContentSecureResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCriminalFileContentSecure",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCivilFileContent")
    @ResponsePayload
    public GetCivilFileContentResponse getCivilFileContent(
            @RequestPayload GetCivilFileContent getCivilFileContent)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("courtLocaCd", getCivilFileContent.getCourtLocaCd())
                        .queryParam("courtRoomCd", getCivilFileContent.getCourtRoomCd())
                        .queryParam(
                                "courtProceedingDate", getCivilFileContent.getCourtProceedingDate())
                        .queryParam("appearanceId", getCivilFileContent.getAppearanceId())
                        .queryParam("physicalFileId", getCivilFileContent.getPhysicalFileId())
                        .queryParam("applicationCd", getCivilFileContent.getApplicationCd());

        try {
            HttpEntity<CivilFileContentDoc> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            CivilFileContentDoc.class);
            var out = new GetCivilFileContentResponse();
            out.setCivilFileContentDoc(resp.getBody());
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCivilFileContent",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "getCivilFileContentSecure")
    @ResponsePayload
    public GetCivilFileContentSecureResponse getCivilFileContentSecure(
            @RequestPayload GetCivilFileContentSecure getCivilFileContent)
            throws JsonProcessingException {

        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("courtLocaCd", getCivilFileContent.getCourtLocaCd())
                        .queryParam("courtRoomCd", getCivilFileContent.getCourtRoomCd())
                        .queryParam(
                                "courtProceedingDate", getCivilFileContent.getCourtProceedingDate())
                        .queryParam("appearanceId", getCivilFileContent.getAppearanceId())
                        .queryParam("physicalFileId", getCivilFileContent.getPhysicalFileId())
                        .queryParam(
                                "requestAgenId", getCivilFileContent.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", getCivilFileContent.getRequestPartId())
                        .queryParam(
                                "requestDtm",
                                InstantSerializer.convert(getCivilFileContent.getRequestDtm()))
                        .queryParam("applicationCd", getCivilFileContent.getApplicationCd());

        try {
            HttpEntity<ca.bc.gov.open.ccd.civil.secure.GetCivilFileContentSecureResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.ccd.civil.secure.GetCivilFileContentSecureResponse
                                    .class);
            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getCivilFileContentSecure",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }
}
