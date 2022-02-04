package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.open.ccd.common.rop.report.*;
import ca.bc.gov.open.ccd.common.rop.report.RopResult;
import ca.bc.gov.open.ccd.common.rop.report.secure.*;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
import ca.bc.gov.open.ccd.models.serializers.InstantSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

@Endpoint
@Slf4j
public class ReportController {
    @Value("${ccd.host}" + "criminal/")
    private String ordsHost = "https://127.0.0.1/";

    @Value("${ccd.oracle-host}")
    private String oracleHost = "https://127.0.0.1/";

    @Value("${ccd.oracle-name}")
    private String oracleServerName = "A";

    @Value("${ccd.adobe-host}")
    private String adobeServerHost;

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
                UriComponentsBuilder.fromHttpUrl(ordsHost + "ropreport")
                        .queryParam("param1", inner.getParam1())
                        .queryParam("param2", inner.getParam2())
                        .queryParam("formCd", inner.getFormCd());
        try {

            // request url and key from ccd Report EndPoint
            HttpEntity<Map<String, String>> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<>() {});

            String url = resp.getBody() != null ? resp.getBody().get("url") : "";

            String keyValue = resp.getBody() != null ? resp.getBody().get("keyValue") : "";

            String query = "";
            if (url.contains("?")) query = url.split("\\?")[1];

            // build an adobe server uri using its url and parameters being return from ccd and
            // request base64 stream from this adobe server
            query =
                    query.replace("<<FORM>>", inner.getFormCd())
                            .replace("<<APP>>", "justin")
                            .replace("<<TICKET>>", keyValue);

            String rpServerHost = url.length() > 0 ? adobeServerHost : url;
            String rpServerUri = rpServerHost + "?" + query;

            HttpEntity<byte[]> resp2 =
                    restTemplate.exchange(
                            rpServerUri,
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            byte[].class);

            String bs64 =
                    resp2.getBody() != null ? Base64Utils.encodeToString(resp2.getBody()) : "";

            var out = new GetROPReportResponse();
            var one = new RopResult();
            one.setB64Content(bs64);
            one.setResultCd("0");
            out.setROPResponse(one);
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
                UriComponentsBuilder.fromHttpUrl(ordsHost + "ropreport/secure")
                        .queryParam("requestAgencyId", inner.getRequestAgencyIdentifierId())
                        .queryParam("requestPartId", inner.getRequestPartId())
                        .queryParam("requestDtm", InstantSerializer.convert(inner.getRequestDtm()))
                        .queryParam("applicationCd", inner.getApplicationCd())
                        .queryParam("param1", inner.getParam1())
                        .queryParam("param2", inner.getParam2())
                        .queryParam("formCd", inner.getFormCd());

        try {

            // request url and key from ccd Report EndPoint
            HttpEntity<Map<String, String>> resp;
            resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            new ParameterizedTypeReference<Map<String, String>>() {});

            String url = resp.getBody() != null ? resp.getBody().get("url") : "";

            String keyValue = resp.getBody() != null ? resp.getBody().get("keyValue") : "";

            String query = "";
            if (url.contains("?")) query = url.split("\\?")[1];
            query =
                    query.replace("<<FORM>>", inner.getFormCd())
                            .replace("<<APP>>", "justin")
                            .replace("<<TICKET>>", keyValue);

            // build an adobe server uri using its url and parameters being return from ccd and
            // request base64 stream from this adobe server
            String rpServerHost = url.length() > 0 ? adobeServerHost : url;
            String rpServerUri = rpServerHost + "?" + query;

            HttpEntity<byte[]> resp2 =
                    restTemplate.exchange(
                            rpServerUri,
                            HttpMethod.GET,
                            new HttpEntity<>(new HttpHeaders()),
                            byte[].class);

            String bs64 =
                    resp2.getBody() != null ? Base64Utils.encodeToString(resp2.getBody()) : "";

            var out = new GetROPReportSecureResponse();
            var one = new ca.bc.gov.open.ccd.common.rop.report.secure.RopResult();
            one.setB64Content(bs64);
            one.setResultCd("0");
            out.setROPResponse(one);
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
