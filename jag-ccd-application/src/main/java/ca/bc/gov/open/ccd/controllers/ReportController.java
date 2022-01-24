package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.rop.report.*;
import ca.bc.gov.open.ccd.common.rop.report.RopResult;
import ca.bc.gov.open.ccd.common.rop.report.secure.*;
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

@Endpoint
@Slf4j
public class ReportController {
    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ReportController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(
            namespace = "http://brooks.ag.gov.bc.ca/CCD.Source.GetROPReport.ws:GetROPReport",
            localPart = "getROPReport")
    @ResponsePayload
    public GetROPReportResponse getRopReport(@RequestPayload GetROPReport getROPReport)
            throws JsonProcessingException {

        var inner =
                getROPReport != null && getROPReport.getROPRequest() != null
                        ? getROPReport.getROPRequest()
                        : new Rop();
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("param1", inner.getParam1())
                        .queryParam("param2", inner.getParam2())
                        .queryParam("formCd", inner.getFormCd());

        try {
            HttpEntity<RopResult> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            RopResult.class);
            var out = new GetROPReportResponse();
            out.setROPResponse(resp.getBody());
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getROPReport",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(
            namespace = "http://reeks.bcgov/CCD.Source.GetROPReport.ws:GetROPReportSecure",
            localPart = "getROPReportSecure")
    @ResponsePayload
    public GetROPReportSecureResponse getRopReportSecure(
            @RequestPayload GetROPReportSecure getROPReport) throws JsonProcessingException {

        var inner =
                getROPReport != null && getROPReport.getROPSecureRequest() != null
                        ? getROPReport.getROPSecureRequest()
                        : new RopSecureRequest();
        UriComponentsBuilder builder =
                UriComponentsBuilder.fromHttpUrl(host + "appearance")
                        .queryParam("param1", inner.getParam1())
                        .queryParam("param2", inner.getParam2())
                        .queryParam("formCd", inner.getFormCd())
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()));

        try {
            HttpEntity<ca.bc.gov.open.ccd.common.rop.report.secure.RopResult> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            ca.bc.gov.open.ccd.common.rop.report.secure.RopResult.class);
            var out = new GetROPReportSecureResponse();
            out.setROPResponse(resp.getBody());
            return out;
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "getROPReportSecure",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }
}
