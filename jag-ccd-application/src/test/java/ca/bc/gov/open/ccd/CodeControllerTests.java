package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import bcgov.reeks.ccd_source_codevalues_ws_provider.codevaluessecure.GetCodeValuesSecure;
import bcgov.reeks.ccd_source_codevalues_ws_provider.codevaluessecure.GetCodeValuesSecureResponse;
import brooks.ccd_source_codevalues_ws_provider.codevalues.CodeValue;
import brooks.ccd_source_codevalues_ws_provider.codevalues.CodeValues;
import brooks.ccd_source_codevalues_ws_provider.codevalues.GetCodeValues;
import brooks.ccd_source_codevalues_ws_provider.codevalues.GetCodeValuesResponse;
import ca.bc.gov.open.ccd.controllers.CodeController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.Collections;
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
public class CodeControllerTests {

    @Autowired private ObjectMapper objectMapper;


    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getCodeValuesTest() throws JsonProcessingException {

        var req = new GetCodeValues();
        req.setLastRetrievedDate("A");

        var out = new GetCodeValuesResponse();
        var cvs = new CodeValues();
        out.setCeisCodeValues(cvs);
        out.setJustinCodeValues(cvs);
        var cv = new CodeValue();
        cvs.setCodeValue(Collections.singletonList(cv));
        cv.setCode("A");
        cv.setCodeType("A");
        cv.setFlex("A");
        cv.setLongDesc("A");
        cv.setShortDesc("A");

        ResponseEntity<GetCodeValuesResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCodeValuesResponse>>any()))
                .thenReturn(responseEntity);

        CodeController codeController = new CodeController(restTemplate, objectMapper);
        var resp = codeController.getCodeValues(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getCodeValuesSecureTest() throws JsonProcessingException {
        var req = new GetCodeValuesSecure();
        req.setApplicationCd("A");
        req.setLastRetrievedDate("A");
        req.setRequestDtm(Instant.now());
        req.setRequestPartId("A");
        req.setRequestAgencyIdentifierId("A");

        var out = new GetCodeValuesSecureResponse();
        var cvs = new bcgov.reeks.ccd_source_codevalues_ws_provider.codevaluessecure.CodeValues();
        out.setCeisCodeValues(cvs);
        out.setJustinCodeValues(cvs);
        out.setResultCd("A");
        out.setResultMessage("A");
        var cv = new bcgov.reeks.ccd_source_codevalues_ws_provider.codevaluessecure.CodeValue();
        cvs.setCodeValue(Collections.singletonList(cv));
        cv.setCode("A");
        cv.setCodeType("A");
        cv.setFlex("A");
        cv.setLongDesc("A");
        cv.setShortDesc("A");

        ResponseEntity<GetCodeValuesSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        //     Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCodeValuesSecureResponse>>any()))
                .thenReturn(responseEntity);

        CodeController codeController = new CodeController(restTemplate, objectMapper);
        var resp = codeController.getCodeValuesSecure(req);

        Assertions.assertNotNull(resp);
    }
}
