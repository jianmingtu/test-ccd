package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import bcgov.reeks.ccd_source_getropreport_ws.getropreportsecure.GetROPReportSecure;
import bcgov.reeks.ccd_source_getropreport_ws.getropreportsecure.RopSecureRequest;
import ca.bc.gov.ag.brooks.ccd_source_getropreport_ws.getropreport.GetROPReport;
import ca.bc.gov.ag.brooks.ccd_source_getropreport_ws.getropreport.Rop;
import ca.bc.gov.ag.brooks.ccd_source_getropreport_ws.getropreport.RopResult;
import ca.bc.gov.open.ccd.controllers.ReportController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ReportControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getRopReportTest() throws JsonProcessingException {
        var req = new GetROPReport();
        var one = new Rop();

        one.setParam1("A");
        one.setParam2("A");
        one.setFormCd("A");

        req.setROPRequest(one);

        var out = new RopResult();
        out.setB64Content("A");
        out.setResultCd("A");
        out.setResultMessage("A");

        ResponseEntity<RopResult> responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<RopResult>>any()))
                .thenReturn(responseEntity);

        ReportController reportController = new ReportController(restTemplate, objectMapper);
        var resp = reportController.getRopReport(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getRopReportSecureTest() throws JsonProcessingException {
        var req = new GetROPReportSecure();
        var one = new RopSecureRequest();

        one.setParam1("A");
        one.setParam2("A");
        one.setFormCd("A");
        one.setRequestAgencyIdentifierId("A");
        one.setRequestPartId("A");
        one.setRequestDtm(Instant.now());
        one.setApplicationCd("A");

        req.setROPSecureRequest(one);

        var out = new bcgov.reeks.ccd_source_getropreport_ws.getropreportsecure.RopResult();
        out.setB64Content("A");
        out.setResultCd("A");
        out.setResultMessage("A");

        ResponseEntity<bcgov.reeks.ccd_source_getropreport_ws.getropreportsecure.RopResult>
                responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito
                                .<Class<
                                                bcgov.reeks.ccd_source_getropreport_ws
                                                        .getropreportsecure.RopResult>>
                                        any()))
                .thenReturn(responseEntity);

        ReportController reportController = new ReportController(restTemplate, objectMapper);
        var resp = reportController.getRopReportSecure(req);

        Assertions.assertNotNull(resp);
    }
}
