package ca.bc.gov.open.ccd;

import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.when;

import ca.bc.gov.open.ccd.common.rop.report.secure.GetROPReportSecure;
import ca.bc.gov.open.ccd.common.rop.report.secure.RopSecureRequest;
import ca.bc.gov.open.ccd.controllers.ReportController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
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

        Map<String, String> m = new HashMap<>();
        m.put("url", "http://127.0.0.1?param1=<<FORM>>&&param3=<<TICKET>>");
        m.put("keyValue", "tokenvalue");
        ResponseEntity<Map<String, String>> responseEntity2 =
                new ResponseEntity<>(m, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        AdditionalMatchers.not(contains("tokenvalue")),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<ParameterizedTypeReference<Map<String, String>>>any()))
                .thenReturn(responseEntity2);

        var out = "A";
        ResponseEntity<byte[]> responseEntity =
                new ResponseEntity<>(out.getBytes(StandardCharsets.UTF_8), HttpStatus.OK);

        // Set up to adobe response
        when(restTemplate.exchange(
                        Mockito.matches("tokenvalue"),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<byte[]>>any()))
                .thenReturn(responseEntity);

        ReportController reportController = new ReportController(restTemplate, objectMapper);
        var resp = reportController.getRopReportSecure(req);

        Assertions.assertNotNull(resp);
    }
}
