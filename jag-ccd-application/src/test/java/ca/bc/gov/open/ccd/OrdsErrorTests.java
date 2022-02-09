package ca.bc.gov.open.ccd;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ca.bc.gov.open.ccd.civil.GetCivilFileContent;
import ca.bc.gov.open.ccd.civil.secure.GetCivilFileContentSecure;
import ca.bc.gov.open.ccd.common.code.values.GetCodeValues;
import ca.bc.gov.open.ccd.common.code.values.secure.GetCodeValuesSecure;
import ca.bc.gov.open.ccd.common.criminal.file.content.GetCriminalFileContent;
import ca.bc.gov.open.ccd.common.criminal.file.content.secure.GetCriminalFileContentSecure;
import ca.bc.gov.open.ccd.common.dev.utils.ClearAppearanceResults;
import ca.bc.gov.open.ccd.common.dev.utils.RecreateCourtList;
import ca.bc.gov.open.ccd.common.document.GetDocument;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecure;
import ca.bc.gov.open.ccd.common.process.results.*;
import ca.bc.gov.open.ccd.common.rop.report.GetROPReport;
import ca.bc.gov.open.ccd.common.rop.report.secure.GetROPReportSecure;
import ca.bc.gov.open.ccd.common.user.login.GetUserLogin;
import ca.bc.gov.open.ccd.common.user.mapping.GetParticipantInfo;
import ca.bc.gov.open.ccd.common.user.mapping.MapGuidToParticipant;
import ca.bc.gov.open.ccd.controllers.*;
import ca.bc.gov.open.ccd.court.one.GetCrtList;
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
    public void testPingOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(ORDSException.class, () -> healthController.getPing(new GetPing()));
    }

    @Test
    public void testHealthOrdsFail() {
        HealthController healthController = new HealthController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> healthController.getHealth(new GetHealth()));
    }

    @Test
    public void testProcessVariationOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processVariation(new ProcessVariation()));
    }

    @Test
    public void testProcessSpeakerOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processSpeaker(new ProcessSpeaker()));
    }

    @Test
    public void testProcessCivilResultsOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processCivilResults(new ProcessCivilResults()));
    }

    @Test
    public void testProcessAppearanceMethodOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processAppearanceMethod(new ProcessAppearanceMethod()));
    }

    @Test
    public void testProcessPleaOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processPlea(new ProcessPlea()));
    }

    @Test
    public void testProcessElectionOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processElection(new ProcessElection()));
    }

    @Test
    public void testProcessBailOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processBail(new ProcessBail()));
    }

    @Test
    public void testProcessCriminalResultOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processCriminalResult(new ProcessCriminalResult()));
    }

    @Test
    public void testProcessAgeNoticeOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processAgeNotice(new ProcessAgeNotice()));
    }

    @Test
    public void testProcessMatterCallOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processMatterCall(new ProcessMatterCall()));
    }

    @Test
    public void testProcessSentenceOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processSentence(new ProcessSentence()));
    }

    @Test
    public void testProcessBanOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processBan(new ProcessBan()));
    }

    @Test
    public void testProcessNoteOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processNote(new ProcessNote()));
    }

    @Test
    public void testProcessArraignmentOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processArraignment(new ProcessArraignment()));
    }

    @Test
    public void testProcessMoveOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processMove(new ProcessMove()));
    }

    @Test
    public void testProcessFindingOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processFinding(new ProcessFinding()));
    }

    @Test
    public void testProcessGenericResultOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processGenericResult(new ProcessGenericResult()));
    }

    @Test
    public void testProcessCivilAppearanceMethodOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () ->
                        processController.processCivilAppearanceMethod(
                                new ProcessCivilAppearanceMethod()));
    }

    @Test
    public void testProcessOrderOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processOrder(new ProcessOrder()));
    }

    @Test
    public void testProcessCivilOrderResultOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processCivilOrderResult(new ProcessCivilOrderResult()));
    }

    @Test
    public void testProcessExhibitOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> processController.processExhibit(new ProcessExhibit()));
    }

    @Test
    public void testProcessSpecialCourtOrdsFail() {
        ProcessController processController = new ProcessController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> processController.processSpecialCourt(new ProcessSpecialCourt()));
    }

    @Test
    public void testGetCodeValuesOrdsFail() {
        CodeController codeController = new CodeController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> codeController.getCodeValues(new GetCodeValues()));
    }

    @Test
    public void testGetCodeValuesSecureOrdsFail() {
        CodeController codeController = new CodeController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> codeController.getCodeValuesSecure(new GetCodeValuesSecure()));
    }

    @Test
    public void testGetCrtListOrdsFail() {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> courtController.getCrtList(new GetCrtList()));
    }

    @Test
    public void testGetCrtListSecureOrdsFail() {
        CourtController courtController = new CourtController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> courtController.getCrtListSecure(new GetCrtListSecure()));
    }

    @Test
    public void testGetCriminalFileContentOrdsFail() {
        FileController fileController = new FileController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> fileController.getCriminalFileContent(new GetCriminalFileContent()));
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
    public void testGetCivilFileContentOrdsFail() {
        FileController fileController = new FileController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> fileController.getCivilFileContent(new GetCivilFileContent()));
    }

    @Test
    public void testGetCivilFileContentSecureOrdsFail() {
        FileController fileController = new FileController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> fileController.getCivilFileContentSecure(new GetCivilFileContentSecure()));
    }

    @Test
    public void testGetROPReportOrdsFail() {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> reportController.getRopReport(new GetROPReport()));
    }

    @Test
    public void testGetParticipantInfoOrdsFail() {
        UserController userController = new UserController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> userController.getParticipantInfo(new GetParticipantInfo()));
    }

    @Test
    public void testMapGuidToParticipantOrdsFail() {
        UserController userController = new UserController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> userController.mapGuidToParticipant(new MapGuidToParticipant()));
    }

    @Test
    public void testGetUserLoginOrdsFail() {
        UserController userController = new UserController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> userController.getUserLogin(new GetUserLogin()));
    }

    @Test
    public void testGetROPReportSecureOrdsFail() {
        ReportController reportController = new ReportController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> reportController.getRopReportSecure(new GetROPReportSecure()));
    }

    @Test
    public void testGetClearAppearanceResultsOrdsFail() {
        DevUtilsController devUtilsController = new DevUtilsController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> devUtilsController.clearAppearanceResults(new ClearAppearanceResults()));
    }

    @Test
    public void testGetRecreateCourtListOrdsFail() {
        DevUtilsController devUtilsController = new DevUtilsController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class,
                () -> devUtilsController.reCreateCourtList(new RecreateCourtList()));
    }

    @Test
    public void testGetDocumentOrdsFail() {
        DocumentController documentController = new DocumentController(restTemplate, objectMapper);

        Assertions.assertThrows(
                ORDSException.class, () -> documentController.getDocument(new GetDocument()));
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
