package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.ccd.common.code.values.*;
import ca.bc.gov.open.ccd.common.code.values.CodeValue;
import ca.bc.gov.open.ccd.common.code.values.CodeValues;
import ca.bc.gov.open.ccd.common.code.values.secure.*;
import ca.bc.gov.open.ccd.controllers.CodeController;
import ca.bc.gov.open.ccd.models.serializers.InstantDeserializer;
import ca.bc.gov.open.ccd.models.serializers.InstantSerializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
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
        req.setLastRetrievedDate(Instant.now());

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

        // Set up to mock ords response
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
        req.setLastRetrievedDate(Instant.now());
        req.setRequestDtm(Instant.now());
        req.setRequestPartId("A");
        req.setRequestAgencyIdentifierId("A");

        var out = new GetCodeValuesSecureResponse();
        var cvs = new ca.bc.gov.open.ccd.common.code.values.secure.CodeValues();
        out.setCeisCodeValues(cvs);
        out.setJustinCodeValues(cvs);
        out.setResultCd("A");
        out.setResultMessage("A");
        var cv = new ca.bc.gov.open.ccd.common.code.values.secure.CodeValue();
        cvs.setCodeValue(Collections.singletonList(cv));
        cv.setCode("A");
        cv.setCodeType("A");
        cv.setFlex("A");
        cv.setLongDesc("A");
        cv.setShortDesc("A");

        ResponseEntity<GetCodeValuesSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
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

    @Test
    public void testInstantSerializer() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Instant.class, new InstantDeserializer());
        module.addSerializer(Instant.class, new InstantSerializer());
        objectMapper.registerModule(module);

        var time = Instant.now();
        String out = objectMapper.writeValueAsString(time);

        String expected =
                DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")
                        .withZone(ZoneId.of("GMT-7"))
                        .withLocale(Locale.US)
                        .format(time);

        out = out.replace("\"", "");
        Assertions.assertEquals(expected, out);
    }
}
