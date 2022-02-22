package ca.bc.gov.open.ccd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.ccd.civil.secure.GetCivilFileContentSecure;
import ca.bc.gov.open.ccd.common.code.values.secure.GetCodeValuesSecure;
import ca.bc.gov.open.ccd.common.criminal.file.content.secure.GetCriminalFileContentSecure;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecure;
import ca.bc.gov.open.ccd.common.process.results.*;
import ca.bc.gov.open.ccd.common.rop.report.secure.GetROPReportSecure;
import ca.bc.gov.open.ccd.controllers.*;
import ca.bc.gov.open.ccd.court.secure.one.GetCrtListSecure;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class OrdsErrorTests {
    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate;

    @Test
    public void testGetCodeValuesSecureOrdsFail() {
        CodeController codeController = new CodeController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> codeController.getCodeValuesSecure(new GetCodeValuesSecure()));
    }

    @Test
    public void testGetCrtListSecureOrdsFail() {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> courtController.getCrtListSecure(new GetCrtListSecure()));
    }

    @Test
    public void testGetCriminalFileContentSecureOrdsFail() {
        FileController fileController = new FileController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () ->
                        fileController.getCriminalFileContentSecure(
                                new GetCriminalFileContentSecure()));
    }

    @Test
    public void testGetCivilFileContentSecureOrdsFail() {
        FileController fileController = new FileController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> fileController.getCivilFileContentSecure(new GetCivilFileContentSecure()));
    }

    @Test
    public void testGetROPReportSecureOrdsFail() {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> reportController.getRopReportSecure(new GetROPReportSecure()));
    }

    @Test
    public void testGetDocumentSecureOrdsFail() {
        DocumentController documentController = new DocumentController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> documentController.getDocumentSecure(new GetDocumentSecure()));
    }

    @Test
    public void securityTestFail_Then401() throws Exception {
        var response =
                mockMvc.perform(post("/ws").contentType(MediaType.TEXT_XML))
                        .andExpect(status().is4xxClientError())
                        .andReturn();
        Assertions.assertEquals(
                HttpStatus.UNAUTHORIZED.value(), response.getResponse().getStatus());
    }
}
