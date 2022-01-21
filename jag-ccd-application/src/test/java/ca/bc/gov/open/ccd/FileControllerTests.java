package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.ccd.civil.*;
import ca.bc.gov.open.ccd.civil.CivilFileContentDoc;
import ca.bc.gov.open.ccd.civil.CivilFileType;
import ca.bc.gov.open.ccd.civil.CounselType;
import ca.bc.gov.open.ccd.civil.CourtParticipantType;
import ca.bc.gov.open.ccd.civil.DocumentHearingType;
import ca.bc.gov.open.ccd.civil.FiledByType;
import ca.bc.gov.open.ccd.civil.GeneralAttendeeType;
import ca.bc.gov.open.ccd.civil.IssueType;
import ca.bc.gov.open.ccd.civil.PartyInterestType;
import ca.bc.gov.open.ccd.civil.PreviousAppearanceType;
import ca.bc.gov.open.ccd.civil.ReferenceDocInterest;
import ca.bc.gov.open.ccd.civil.ReferenceDocumentType;
import ca.bc.gov.open.ccd.civil.SpecialProgramType;
import ca.bc.gov.open.ccd.civil.secure.*;
import ca.bc.gov.open.ccd.common.criminal.file.content.*;
import ca.bc.gov.open.ccd.common.criminal.file.content.AccusedFileType;
import ca.bc.gov.open.ccd.common.criminal.file.content.AppearanceCountTypes;
import ca.bc.gov.open.ccd.common.criminal.file.content.AppearanceTypes;
import ca.bc.gov.open.ccd.common.criminal.file.content.ArrestWarrantType;
import ca.bc.gov.open.ccd.common.criminal.file.content.BanTypes;
import ca.bc.gov.open.ccd.common.criminal.file.content.CFCOrderType;
import ca.bc.gov.open.ccd.common.criminal.file.content.DocumentType;
import ca.bc.gov.open.ccd.common.criminal.file.content.FileContentDoc;
import ca.bc.gov.open.ccd.common.criminal.file.content.HearingRestrictionType;
import ca.bc.gov.open.ccd.common.criminal.file.content.PartyAppearanceMethodType;
import ca.bc.gov.open.ccd.common.criminal.file.content.ProtectionOrderType;
import ca.bc.gov.open.ccd.common.criminal.file.content.SentenceType;
import ca.bc.gov.open.ccd.common.criminal.file.content.secure.*;
import ca.bc.gov.open.ccd.controllers.FileController;
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
public class FileControllerTests {
    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getCriminalFileContentTest() throws JsonProcessingException {
        var req = new GetCriminalFileContent();
        req.setAgencyIdentifierCd("A");
        req.setAppearanceID("A");
        req.setRoomCd("A");
        req.setMdocJustinNo("A");
        req.setProceedingDate(Instant.now());

        var out = new FileContentDoc();
        out.setCourtLocaCd("A");
        out.setCourtRoomCd("A");
        out.setCourtProceedingDate(Instant.now());
        out.setAppearanceId(Collections.singletonList("A"));
        out.setMdocJustinNo("A");
        AccusedFileType ac = new AccusedFileType();

        ac.setFileNumber("A");
        ac.setFileLocaAgencyIdentifierCd("A");
        ac.setMdocJustinNo("A");
        ac.setMDocInfoSeqNo("A");
        ac.setPhysTicketSeriesTxt("A");
        ac.setPhyFileFolderNo("A");
        ac.setCourtClassCd("A");
        ac.setCourtLevelCd("A");
        ac.setPartId("A");
        ac.setProfSeqNo("A");
        ac.setSealTypeCd("A");
        ac.setFiledDate(Instant.now());

        ArrestWarrantType wt = new ArrestWarrantType();
        wt.setFileNumberText("A");
        wt.setWarrantDate(Instant.now());
        ac.setArrestWarrant(Collections.singletonList(wt));

        BanTypes bt = new BanTypes();
        bt.setBanTypeCd("A");
        bt.setBanTypeDescription("A");
        bt.setBanTypeAct("A");
        bt.setBanTypeSection("A");
        bt.setBanTypeSubSection("A");
        bt.setBanStatuteId("A");
        bt.setBanCommentText("A");
        bt.setBanOrderedDate(Instant.now());
        bt.setBanSeqNo("A");
        ac.setBan(Collections.singletonList(bt));

        ProtectionOrderType pt = new ProtectionOrderType();
        pt.setPOROrderIssueDate(Instant.now());
        pt.setOrderTypeDsc("A");
        pt.setPORConditionText("A");
        ac.setProtectionOrder(Collections.singletonList(pt));

        CFCOrderType ct = new CFCOrderType();
        ct.setCFCOrderIssueDate(Instant.now());
        ct.setCFCConditionText("A");
        ct.setOrderTypeDsc("A");
        ac.setCFCOrder(Collections.singletonList(ct));

        HearingRestrictionType ht = new HearingRestrictionType();
        ht.setHearingRestrictiontype("A");
        ht.setJudgeName("A");
        ht.setHearingRestrictionDate(Instant.now());
        ac.setHearingRestriction(Collections.singletonList(ht));

        DocumentType dt = new DocumentType();
        dt.setDocmClassification("A");
        dt.setDocmId("A");
        dt.setIssueDate(Instant.now());
        dt.setDocmFormId("A");
        dt.setDocmFormDsc("A");
        dt.setDocmDispositionDsc("A");
        dt.setDocmDispositionDate(Instant.now());
        dt.setImageId("A");
        dt.setDocumentPageCount("A");
        ac.setDocument(Collections.singletonList(dt));

        AppearanceTypes at = new AppearanceTypes();
        at.setAppearanceId("A");
        at.setAppearanceDate(Instant.now());
        at.setCourtAgencyIdentifier("A");
        at.setCourtRoom("A");
        at.setAppearanceNote("A");
        at.setEstimatedTimeHour("A");
        at.setEstimatedTimeMin("A");
        at.setConfirmStatusDsc("A");
        at.setJudgesRecommendation("A");

        AppearanceCountTypes apc = new AppearanceCountTypes();
        apc.setAppcId("A");
        apc.setCountNumber("A");
        apc.setAppearanceReason("A");
        apc.setAppearanceResult("A");
        apc.setFinding("A");
        apc.setSectionTxt("A");
        apc.setSectionDscTxt("A");
        SentenceType st = new SentenceType();
        st.setSntpCd("A");
        st.setSentTermPeriodQty("A");
        st.setSentTermCd("A");
        st.setSentSubtermPeriodQty("A");
        st.setSentSubtermCd("A");
        st.setSentTertiaryTermCd("A");
        st.setSentIntermittentYn("A");
        st.setSentMonetaryAmt("A");
        st.setSentDueTtpDt(Instant.now());
        st.setSentEffectiveDt(Instant.now());
        st.setSentDetailTxt("A");
        st.setSentYcjaAdultYouthCd("A");
        st.setSentCustodySecureYn("A");
        apc.setSentence(Collections.singletonList(st));
        at.setAppearanceCount(Collections.singletonList(apc));

        PartyAppearanceMethodType pmt = new PartyAppearanceMethodType();
        pmt.setPartyName("A");
        pmt.setPartyRole("A");
        pmt.setPartId("A");
        pmt.setPartyAppearanceMethod("A");
        at.setPartyAppearanceMethod(Collections.singletonList(pmt));

        out.setAccusedFile(Collections.singletonList(ac));

        ResponseEntity<FileContentDoc> responseEntity = new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<FileContentDoc>>any()))
                .thenReturn(responseEntity);

        FileController fileController = new FileController(restTemplate, objectMapper);
        var resp = fileController.getCriminalFileContent(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getCriminalFileContentSecureTest() throws JsonProcessingException {
        var req = new GetCriminalFileContentSecure();
        req.setRequestAgencyIdentifierId("A");
        req.setRequestPartId("A");
        req.setRequestDtm(Instant.now());
        req.setApplicationCd("A");
        req.setAgencyIdentifierCd("A");
        req.setRoomCd("A");
        req.setProceedingDate(Instant.now());
        req.setAppearanceID("A");
        req.setMdocJustinNo("A");

        var out = new GetCriminalFileContentSecureResponse();
        out.setResultCd("A");
        out.setResultMessage("A");
        var fc = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.FileContentDoc();

        fc.setCourtLocaCd("A");
        fc.setCourtRoomCd("A");
        fc.setCourtProceedingDate(Instant.now());
        fc.setAppearanceId(Collections.singletonList("A"));
        fc.setMdocJustinNo("A");
        var ac = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.AccusedFileType();

        var ap = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.AppearanceTypes();
        ap.setAppearanceId("A");
        ap.setAppearanceDate(Instant.now());
        ap.setCourtAgencyIdentifier("A");
        ap.setCourtRoom("A");
        ap.setAppearanceNote("A");
        ap.setEstimatedTimeHour("A");
        ap.setEstimatedTimeMin("A");
        ap.setConfirmStatusDsc("A");
        ap.setJudgesRecommendation("A");

        var act = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.AppearanceCountTypes();
        act.setAppcId("A");
        act.setCountNumber("A");
        act.setAppearanceReason("A");
        act.setAppearanceResult("A");
        act.setFinding("A");
        act.setSectionDscTxt("A");
        act.setSectionTxt("A");
        var st = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.SentenceType();

        st.setSntpCd("A");
        st.setSentTermPeriodQty("A");
        st.setSentTermCd("A");
        st.setSentSubtermPeriodQty("A");
        st.setSentSubtermCd("A");
        st.setSentTertiaryTermPeriodQty("A");
        st.setSentTertiaryTermCd("A");
        st.setSentIntermittentYn("A");
        st.setSentMonetaryAmt("A");
        st.setSentDueTtpDt(Instant.now());
        st.setSentEffectiveDt(Instant.now());
        st.setSentDetailTxt("A");
        st.setSentYcjaAdultYouthCd("A");
        st.setSentCustodySecureYn("A");

        act.setSentence(Collections.singletonList(st));

        ap.setAppearanceCount(Collections.singletonList(act));

        ac.setAppearance(Collections.singletonList(ap));

        ac.setFileNumber("A");
        ac.setFileLocaAgencyIdentifierCd("A");
        ac.setMdocJustinNo("A");
        ac.setMDocInfoSeqNo("A");
        ac.setPhysTicketSeriesTxt("A");
        ac.setPhyFileFolderNo("A");
        ac.setCourtClassCd("A");
        ac.setCourtLevelCd("A");
        ac.setPartId("A");
        ac.setProfSeqNo("A");
        ac.setSealTypeCd("A");
        ac.setFiledDate(Instant.now());

        var wt = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.ArrestWarrantType();
        wt.setFileNumberText("A");
        wt.setWarrantDate(Instant.now());
        ac.setArrestWarrant(Collections.singletonList(wt));

        var bt = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.BanTypes();
        bt.setBanTypeCd("A");
        bt.setBanTypeDescription("A");
        bt.setBanTypeAct("A");
        bt.setBanTypeSection("A");
        bt.setBanTypeSubSection("A");
        bt.setBanStatuteId("A");
        bt.setBanCommentText("A");
        bt.setBanOrderedDate(Instant.now());
        bt.setBanSeqNo("A");
        ac.setBan(Collections.singletonList(bt));

        var pt = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.ProtectionOrderType();
        pt.setPOROrderIssueDate(Instant.now());
        pt.setOrderTypeDsc("A");
        pt.setPORConditionText("A");
        ac.setProtectionOrder(Collections.singletonList(pt));

        var ct = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.CFCOrderType();
        ct.setCFCOrderIssueDate(Instant.now());
        ct.setCFCConditionText("A");
        ct.setOrderTypeDsc("A");
        ac.setCFCOrder(Collections.singletonList(ct));

        var ht =
                new ca.bc.gov.open.ccd.common.criminal.file.content.secure.HearingRestrictionType();
        ht.setHearingRestrictiontype("A");
        ht.setJudgeName("A");
        ht.setHearingRestrictionDate(Instant.now());
        ac.setHearingRestriction(Collections.singletonList(ht));

        var dt = new ca.bc.gov.open.ccd.common.criminal.file.content.secure.DocumentType();
        dt.setDocmClassification("A");
        dt.setDocmId("A");
        dt.setIssueDate(Instant.now());
        dt.setDocmFormId("A");
        dt.setDocmFormDsc("A");
        dt.setDocmDispositionDsc("A");
        dt.setDocmDispositionDate(Instant.now());
        dt.setImageId("A");
        dt.setDocumentPageCount("A");
        ac.setDocument(Collections.singletonList(dt));

        AppearanceTypes at = new AppearanceTypes();
        at.setAppearanceId("A");
        at.setAppearanceDate(Instant.now());
        at.setCourtAgencyIdentifier("A");
        at.setCourtRoom("A");
        at.setAppearanceNote("A");
        at.setEstimatedTimeHour("A");
        at.setEstimatedTimeMin("A");
        at.setConfirmStatusDsc("A");
        at.setJudgesRecommendation("A");

        var pa =
                new ca.bc.gov.open.ccd.common.criminal.file.content.secure
                        .PartyAppearanceMethodType();

        pa.setPartyName("A");
        pa.setPartyAppearanceMethod("A");
        pa.setPartyRole("A");
        pa.setPartId("A");

        AppearanceCountTypes apc = new AppearanceCountTypes();
        apc.setAppcId("A");
        apc.setCountNumber("A");
        apc.setAppearanceReason("A");
        apc.setAppearanceResult("A");
        apc.setFinding("A");
        apc.setSectionTxt("A");
        apc.setSectionDscTxt("A");
        SentenceType st2 = new SentenceType();
        st2.setSntpCd("A");
        st2.setSentTermPeriodQty("A");
        st2.setSentTermCd("A");
        st2.setSentSubtermPeriodQty("A");
        st2.setSentSubtermCd("A");
        st2.setSentTertiaryTermCd("A");
        st2.setSentIntermittentYn("A");
        st2.setSentMonetaryAmt("A");
        st2.setSentDueTtpDt(Instant.now());
        st2.setSentEffectiveDt(Instant.now());
        st2.setSentDetailTxt("A");
        st2.setSentYcjaAdultYouthCd("A");
        st2.setSentCustodySecureYn("A");
        apc.setSentence(Collections.singletonList(st2));
        at.setAppearanceCount(Collections.singletonList(apc));

        PartyAppearanceMethodType pmt = new PartyAppearanceMethodType();
        pmt.setPartyName("A");
        pmt.setPartyRole("A");
        pmt.setPartId("A");
        pmt.setPartyAppearanceMethod("A");
        at.setPartyAppearanceMethod(Collections.singletonList(pmt));

        out.setFileContent(fc);

        ResponseEntity<GetCriminalFileContentSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCriminalFileContentSecureResponse>>any()))
                .thenReturn(responseEntity);

        FileController fileController = new FileController(restTemplate, objectMapper);
        var resp = fileController.getCriminalFileContentSecure(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getCivilFileContentTest() throws JsonProcessingException {
        var req = new GetCivilFileContent();
        req.setCourtLocaCd("A");
        req.setCourtRoomCd("A");
        req.setCourtProceedingDate(Instant.now());
        req.setAppearanceId(1L);
        req.setPhysicalFileId("A");
        req.setApplicationCd("A");

        var out = new CivilFileContentDoc();
        out.setCourtLocaCd("A");
        out.setCourtRoomCd("A");
        out.setCourtProceedingDate(Instant.now());
        out.setAppearanceId(Collections.singletonList("A"));
        out.setPhysicalFileId("A");

        var cft = new CivilFileType();
        cft.setPhysicalFileID("A");
        cft.setFileNumber("A");
        cft.setCourtLevelCd("A");
        cft.setCourtClassCd("A");
        cft.setIndigencyGrantedYN("A");
        cft.setInfantFileYN("A");
        cft.setCFCSAFileYN("A");
        cft.setMarriageDate(Instant.now());
        cft.setMarriageDate(Instant.now());
        cft.setDivorceDate(Instant.now());
        cft.setFederalClearanceDate(Instant.now());
        cft.setCentralRegNumber("A");
        cft.setCaveatExpiryDate(Instant.now());
        cft.setProbateFileNumber("A");
        cft.setCaveatCancelledYN("A");
        cft.setAssetDeclaredAmt("A");
        cft.setProbateFeeAmt("A");
        cft.setProbateEnteredDate(Instant.now());
        cft.setAdditionalAssetDeclaredAmt("A");
        cft.setAdditionalProbateFeeAmt("A");
        cft.setEstateValueAmt("A");
        cft.setOutsideBCAssetAmt("A");
        cft.setAssetCommentText("A");
        cft.setFileCommentText("A");
        cft.setSealedYN("A");
        cft.setSheriffCommentText("A");
        cft.setFileLocaAgencyIdentifierCd("A");

        var sp = new SpecialProgramType();
        sp.setSpecialProgramDsc("A");
        sp.setProgramEntryDate(Instant.now());
        sp.setProgramEntryReasonDsc("A");
        sp.setProgramExitDate(Instant.now());
        sp.setProgramExitReasonDsc("A");

        cft.setSpecialProgram(Collections.singletonList(sp));

        var pt = new PreviousAppearanceType();
        pt.setAppearanceId("A");
        pt.setAppearanceDate(Instant.now());
        pt.setCourtAgencyIdentifier("A");
        pt.setCourtRoom("A");
        pt.setAdjudicatorName("A");
        pt.setAdjudicatorComment("A");
        pt.setAdjudicatorAppearanceMethod("A");
        cft.setPreviousAppearance(Collections.singletonList(pt));

        var dh = new DocumentHearingType();
        dh.setDocumentId("A");
        dh.setFileSeqNumber("A");
        dh.setDocumentTypeCd("A");
        dh.setDocumentTypeDescription("A");
        dh.setOrderDocumentYN("A");
        dh.setAppearanceReasonCode("A");
        dh.setAppearanceResultCode("A");
        var is = new IssueType();
        is.setIssueDsc("A");
        is.setIssueNumber("A");
        is.setConcludedYN("A");
        dh.setIssue(Collections.singletonList(is));

        var fb = new FiledByType();
        fb.setFiledByName("A");
        fb.setRoleTypeCode("A");
        dh.setFiledBy(Collections.singletonList(fb));

        var cp = new CourtParticipantType();
        cp.setPartyName("A");
        cp.setPartyRole("A");
        cp.setPartId("A");
        cp.setPartyAppearanceMethod("A");
        cp.setLeftRightParty("A");
        var ct = new CounselType();
        ct.setCounselName("A");
        ct.setCounselAppearanceMethod("A");
        cp.setCounsel(Collections.singletonList(ct));

        pt.setCourtParticipant(Collections.singletonList(cp));
        pt.setDocumentHearing(Collections.singletonList(dh));

        var gt = new GeneralAttendeeType();
        gt.setAttendeeName("A");
        gt.setCounselName("A");

        pt.setGeneralAttendee(Collections.singletonList(gt));

        var dt = new ca.bc.gov.open.ccd.civil.DocumentType();
        dt.setDocumentId("A");
        dt.setFileSeqNumber("A");
        dt.setDocumentTypeCd("A");
        dt.setDocumentTypeDescription("A");
        dt.setDocumentAccessLevelCd("A");
        dt.setDocumentSealEndDate(Instant.now());
        dt.setDocumentSealStartDate(Instant.now());
        dt.setDateGranted("A");
        dt.setEstimatedDocumentMinutes("A");
        dt.setEndedDate(Instant.now());
        dt.setDateVaried("A");
        dt.setCancelledDate(Instant.now());
        dt.setDocumentCommentText("A");
        dt.setOrderDocumentYN("A");
        dt.setPOROrderYN("A");
        dt.setCFCOrderYN("A");
        dt.setImageId("A");
        dt.setSealedYN("A");
        dt.setDocumentPageCount("A");
        dt.setSwornByNm("A");
        dt.setAffidavitNo("A");

        var is2 = new IssueType();
        is2.setIssueDsc("A");
        is2.setConcludedYN("A");
        is2.setIssueNumber("A");
        dt.setIssue(Collections.singletonList(is2));

        var pit = new PartyInterestType();
        pit.setOrderRoleTypeDsc("A");
        pit.setPartyName("A");
        pit.setPartyBirthDate(Instant.now());
        dt.setPartyInterest(Collections.singletonList(pit));

        cft.setDocument(Collections.singletonList(dt));

        var rd = new ReferenceDocumentType();
        rd.setReferenceDocumentId("A");
        rd.setReferenceDocumentNo("A");
        rd.setAppearanceId("A");
        rd.setAppearanceDate(Instant.now());
        rd.setObjectGuid("A");
        rd.setDescriptionText("A");
        rd.setEnterDtm("A");
        rd.setReferenceDocumentTypeCd("A");
        rd.setReferenceDocumentTypeDsc("A");
        var rdi = new ReferenceDocInterest();
        rdi.setPartyId("A");
        rdi.setPartyName("A");
        rdi.setNonPartyName("A");
        rd.setReferenceDocumentInterest(Collections.singletonList(rdi));

        out.setCivilFile(Collections.singletonList(cft));

        ResponseEntity<CivilFileContentDoc> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<CivilFileContentDoc>>any()))
                .thenReturn(responseEntity);

        FileController fileController = new FileController(restTemplate, objectMapper);
        var resp = fileController.getCivilFileContent(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getCivilFileContentSecureTest() throws JsonProcessingException {
        var req = new GetCivilFileContentSecure();
        req.setRequestAgencyIdentifierId("A");
        req.setRequestPartId("A");
        req.setRequestDtm(Instant.now());
        req.setAppearanceId(1L);
        req.setCourtLocaCd("A");
        req.setCourtRoomCd("A");
        req.setCourtProceedingDate(Instant.now());
        req.setAppearanceId(1L);
        req.setPhysicalFileId("A");

        var out = new GetCivilFileContentSecureResponse();
        out.setResultCd("A");
        out.setResultMessage("A");

        var doc = new ca.bc.gov.open.ccd.civil.secure.CivilFileContentDoc();
        doc.setCourtLocaCd("A");
        doc.setCourtRoomCd("A");
        doc.setCourtProceedingDate(Instant.now());
        doc.setAppearanceId(Collections.singletonList("A"));
        doc.setPhysicalFileId("A");

        out.setCivilFileContentDoc(doc);

        ResponseEntity<GetCivilFileContentSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCivilFileContentSecureResponse>>any()))
                .thenReturn(responseEntity);

        FileController fileController = new FileController(restTemplate, objectMapper);
        var resp = fileController.getCivilFileContentSecure(req);

        Assertions.assertNotNull(resp);
    }
}
