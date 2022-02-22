package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.ccd.controllers.CourtController;
import ca.bc.gov.open.ccd.court.one.*;
import ca.bc.gov.open.ccd.court.secure.one.GetCrtListSecure;
import ca.bc.gov.open.ccd.court.secure.one.GetCrtListSecureResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
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
public class CourtControllerTests {

    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getCrtListSecureTest() throws JsonProcessingException {
        var req = new GetCrtListSecure();
        req.setRequestAgencyIdentifierId("A");
        req.setRequestPartId("A");
        req.setRequestDtm(Instant.now());
        req.setApplicationCd("A");
        req.setAgencyIdentifierCd("A");
        req.setRoomCd("A");
        req.setProceedingDate(Instant.now());
        req.setDivisionCd("A");
        req.setFileNumber("A");

        var out = new GetCrtListSecureResponse();
        out.setResultCd("A");
        out.setResultMessage("A");

        var clt = new ca.bc.gov.open.ccd.court.secure.one.CourtListTypes();

        clt.setCourtLocationName("A");
        clt.setCourtRoomCode("A");
        clt.setCourtProceedingsDate(Instant.now());
        var fs = new ca.bc.gov.open.ccd.court.secure.one.FileSearchParameter();
        fs.setFileNumber("A");
        fs.setCourtDivisionCd("A");

        clt.setFileSearchParameter(fs);

        var crl = new ca.bc.gov.open.ccd.court.secure.one.CriminalCourtList();
        crl.setCriminalAppearanceID("A");
        crl.setCourtListTypeCd("A");
        crl.setAppearanceSequenceNumber("A");
        crl.setAppearanceTime(Instant.now());
        var fi = new ca.bc.gov.open.ccd.court.secure.one.FileInformationType();
        fi.setFileLocaAgencyIdentifierCd("A");
        fi.setPhyFileFolderNo("A");
        fi.setPhysTicketSeriesTxt("A");
        fi.setMdocInfoSeqNo("A");
        fi.setPartId("A");
        fi.setProfSeqNo("A");
        fi.setMdocJustinNo("A");
        fi.setCourtLevelCd("A");
        fi.setCourtClassCd("A");
        fi.setMdocTypeCd("A");
        fi.setMdocTypeDsc("A");
        fi.setMdocImageId("A");
        fi.setMdocAmendedYN("A");
        fi.setMdocAmendedText("A");
        var is = new ca.bc.gov.open.ccd.court.secure.one.Issue();
        is.setCountPrntSeqNo("A");
        is.setStatuteActCd("A");
        is.setStatuteSectionCd("A");
        is.setStatuteSectionDsc("A");
        fi.setIssue(Collections.singletonList(is));

        crl.setFileInformation(fi);

        crl.setFileNumberText("A");
        crl.setFileHomeLocationName("A");
        crl.setSealTypeCd("A");
        crl.setSealTypeDsc("A");
        crl.setOtherFileInformationText("A");
        crl.setAccusedFullName("A");

        var fn = new ca.bc.gov.open.ccd.court.secure.one.AccusedFormattedName();
        fn.setLastName("A");
        fn.setGiven1Name("A");
        fn.setGiven2Name("A");
        fn.setGiven3Name("A");
        fn.setOrgName("A");

        crl.setAccusedFormattedName(fn);

        crl.setAccusedBirthDate(Instant.now());
        crl.setAccusedCurrentBailProcessText("A");
        crl.setAccusedInCustodyFlag("A");
        crl.setCounselFullName("A");
        crl.setCounselDesignationYN("A");
        crl.setCounselPartId("A");
        crl.setCaseAgeDaysNumber("A");
        crl.setCrownTypeCd("A");
        crl.setCrownLocationCd("A");
        crl.setParticipantRoleCd("A");
        crl.setParticipantRoleDsc("A");

        var at = new ca.bc.gov.open.ccd.court.secure.one.AttendanceMethod();
        at.setApprId("A");
        at.setAssetUsageSeqNo("A");
        at.setRoleType("A");
        at.setAttendanceMethodCd("A");
        at.setPhoneNumber("A");
        at.setInstruction("A");
        at.setOtherRoleName("A");
        crl.setAttendanceMethod(Collections.singletonList(at));

        var ac = new ca.bc.gov.open.ccd.court.secure.one.AppearanceCount();
        ac.setAppearanceCountId("A");
        ac.setCountPrintSequenceNumber("A");
        ac.setChargeStatuteCode("A");
        ac.setChargeStatuteDescription("A");
        ac.setLesserIncludedChargeStatuteCode("A");
        ac.setAppearanceCountCancelledYN("A");
        ac.setLesserIncludedChargeStatuteDescription("A");
        ac.setAppearanceReasonCode("A");
        ac.setPleaCode("A");
        ac.setPleaDate(Instant.now());
        ac.setElectionCode("A");
        ac.setElectionDate(Instant.now());
        ac.setMdctSeqNo("A");
        ac.setOffenceAgeDaysNumber("A");
        ac.setIssuingOfficerPoliceForceCode("A");
        ac.setIssuingOfficerPINText("A");
        ac.setIssuingOfficerSurnameName("A");
        ac.setFindingCode("A");
        ac.setFindingDate(Instant.now());

        crl.setAppearanceCount(Collections.singletonList(ac));

        var bn = new ca.bc.gov.open.ccd.court.secure.one.Bans();
        bn.setBanTypeCd("A");
        bn.setBanTypeDescription("A");
        bn.setBanTypeAct("A");
        bn.setBanTypeSection("A");
        bn.setBanTypeSubSection("A");
        bn.setBanStatuteId("A");
        bn.setBanCommentText("A");
        bn.setBanAcprId("A");
        crl.setBans(Collections.singletonList(bn));

        var bov = new ca.bc.gov.open.ccd.court.secure.one.BailOrderToVary();
        bov.setFormTypeCd("A");
        bov.setDocmTypeDsc("A");
        bov.setDocmId("A");
        bov.setDocmIssueDate(Instant.now());
        bov.setDocmImageId("A");
        bov.setDocmStatus("A");
        crl.setBailOrderToVary(Collections.singletonList(bov));

        var sov = new ca.bc.gov.open.ccd.court.secure.one.SentOrderToVary();
        sov.setFormTypeCd("A");
        sov.setDocmTypeDsc("A");
        sov.setDocmId("A");
        sov.setDocmIssueDate(Instant.now());
        sov.setDocmImageId("A");
        sov.setDocmStatus("A");
        crl.setSentOrderToVary(Collections.singletonList(sov));

        var an = new ca.bc.gov.open.ccd.court.secure.one.AgeNotice();
        an.setEventDate(Instant.now());
        an.setEventTypeDsc("A");
        an.setDetailText("A");
        an.setDOB(Instant.now());
        an.setRelationship("A");
        an.setProvenBy("A");
        an.setNoticeTo("A");

        crl.setAgeNotice(Collections.singletonList(an));

        var st = new ca.bc.gov.open.ccd.court.secure.one.SpeakerType();
        st.setSpeakerId("A");
        st.setSpeakerTypeCd("A");
        st.setSpeakerSeqNo("A");
        st.setVoirDireSeqNo("A");
        st.setSpeakerName("A");
        st.setSpeakerStatusCd("A");
        st.setSpeakerStatusDsc("A");
        var se = new ca.bc.gov.open.ccd.court.secure.one.SpeakerEvent();
        se.setSpeakerEventDate(Instant.now());
        se.setSpeakerEventTime(Instant.now());
        se.setSpeakerEventText("A");

        st.setSpeakerEvent(Collections.singletonList(se));
        crl.setSpeaker(Collections.singletonList(st));

        var aw = new ca.bc.gov.open.ccd.court.secure.one.ArrestWarrant();
        aw.setFileNumberText("A");
        aw.setWarrantDate(Instant.now());
        crl.setArrestWarrant(Collections.singletonList(aw));

        var crp = new ca.bc.gov.open.ccd.court.secure.one.CRProtectionOrderType();
        crp.setPORConditionText("A");
        crp.setOrderTypeDsc("A");
        crp.setPOROrderIssueDate(Instant.now());
        crl.setProtectionOrder(Collections.singletonList(crp));

        var sa = new ca.bc.gov.open.ccd.court.secure.one.ScheduledAppearance();
        sa.setAppearanceId("A");
        sa.setCourtAgencyIdentifier("A");
        sa.setCourtRoom("A");
        sa.setAppearanceDate(Instant.now());
        sa.setAppearanceTime(Instant.now());
        sa.setAppearanceReasonCd("A");
        sa.setEstDurationHours("A");
        sa.setEstDurationMins("A");

        var hr = new ca.bc.gov.open.ccd.court.secure.one.HearingRestrictionType();
        hr.setHearingRestrictiontype("A");
        hr.setJudgeName("A");
        hr.setHearingRestrictionDate(Instant.now());

        crl.setHearingRestriction(Collections.singletonList(hr));
        crl.setScheduledAppearance(Collections.singletonList(sa));

        var co = new ca.bc.gov.open.ccd.court.secure.one.CFCOrderType();
        co.setCFCOrderIssueDate(Instant.now());
        co.setOrderTypeDsc("A");
        co.setCFCConditionText("A");

        crl.setCFCOrder(Collections.singletonList(co));

        var crl2 = new ca.bc.gov.open.ccd.court.secure.one.CivilCourtListType();
        crl2.setAppearanceId("A");
        crl2.setAppearanceTime(Instant.now());
        crl2.setBinderText("A");
        crl2.setCourtListPrintSortNumber("A");
        crl2.setCivilDocumentsAvailable("A");
        crl2.setAppearanceDate(Instant.now());
        crl2.setExternalFileNumberText("A");
        crl2.setCourtListTypeCd("A");
        crl2.setCourtRoomCd("A");
        crl2.setCourtClassCd("A");
        crl2.setFileAccessLevelCd("A");
        crl2.setSealStartDate(Instant.now());
        crl2.setSealEndDate(Instant.now());
        crl2.setSheriffCommentText("A");
        crl2.setSealFileSOCText("A");
        crl2.setEstimatedAppearanceMinutes("A");

        var py = new ca.bc.gov.open.ccd.court.secure.one.PhysicalFileType();
        py.setPhysicalFileID("A");
        py.setFileAccessLevelCd("A");
        py.setFileNumber("A");
        py.setStyleOfCause("A");
        py.setLeftPartyLastName("A");
        py.setLeftPartyGivenName("A");
        py.setLeftPartyOtherCount("A");
        py.setRightPartyGivenName("A");
        py.setRightPartyLastName("A");
        py.setRightPartyOtherCount("A");
        py.setThirdPartyGivenName("A");
        py.setThirdPartyLastName("A");
        py.setThirdPartyOtherCount("A");
        py.setHomeAgencyCd("A");
        py.setCivilAgencyCd("A");
        crl2.setPhysicalFile(py);

        var as = new ca.bc.gov.open.ccd.court.secure.one.AssetType();
        as.setAssetTypeDescription("A");
        crl2.setAsset(Collections.singletonList(as));

        var dt = new ca.bc.gov.open.ccd.court.secure.one.DocumentType();
        dt.setAppearanceID("A");
        dt.setDocumentId("A");
        dt.setFileSeqNumber("A");
        dt.setRoleTypeCode("A");
        dt.setDocumentTypeCd("A");
        dt.setDocumentTypeDescription("A");
        dt.setDocumentAccessLevelCd("A");
        dt.setDocumentSealEndDate(Instant.now());
        dt.setDocumentSealStartDate(Instant.now());
        dt.setDateGranted("A");
        dt.setEstimatedDocumentMinutes("A");
        dt.setDateVaried("A");
        dt.setCancelledDate(Instant.now());
        dt.setOrderDocumentYN("A");
        dt.setAdjudicatorPartId("A");
        dt.setAdjudicatorName("A");
        dt.setOrderDocumentYN("A");
        dt.setAppearanceReasonCode("A");

        var is2 = new ca.bc.gov.open.ccd.court.secure.one.IssueType();
        is2.setIssueDescription("A");
        is2.setIssueType("A");
        is2.setIssueNumber("A");
        dt.setIssue(Collections.singletonList(is2));

        var ft = new ca.bc.gov.open.ccd.court.secure.one.FiledByType();
        ft.setFiledByName("A");
        ft.setRoleTypeCode("A");
        dt.setFiledBy(Collections.singletonList(ft));
        crl2.setDocument(Collections.singletonList(dt));

        var pt = new ca.bc.gov.open.ccd.court.secure.one.PartyType();
        pt.setPartyId("A");
        pt.setPartyScheduled("A");
        pt.setPartyRoleType("A");
        pt.setPartyFullName("A");
        pt.setAttendanceMethodCd("A");
        pt.setPartyTypeCd("A");
        pt.setLeftRightParty("A");
        pt.setBirthDate(Instant.now());
        pt.setPhoneNumber("A");
        pt.setInstruction("A");
        pt.setPartyFullAddressText("A");
        pt.setWarrantIssueDate("A");
        pt.setActiveYN("A");
        var ct = new ca.bc.gov.open.ccd.court.secure.one.CounselType();
        ct.setCounselFullName("A");
        ct.setPhoneNumber("A");
        ct.setCounselId("A");

        pt.setCounsel(Collections.singletonList(ct));

        var rt = new ca.bc.gov.open.ccd.court.secure.one.RepresentativeType();
        rt.setInstruction("A");
        rt.setAttendanceMethodCd("A");
        rt.setPhoneNumber("A");
        rt.setRepFullName("A");
        pt.setRepresentative(Collections.singletonList(rt));

        var lrt = new ca.bc.gov.open.ccd.court.secure.one.LegalRepresentativeType();
        lrt.setLegalRepFullName("A");
        lrt.setLegalRepTypeDsc("A");
        pt.setLegalRepresentative(Collections.singletonList(lrt));

        var pr2 = new ca.bc.gov.open.ccd.court.secure.one.PartyRoleType2();
        pr2.setRoleTypeCd("A");
        pr2.setRoleTypeDsc("A");

        pt.setPartyRole(Collections.singletonList(pr2));

        var pnt = new ca.bc.gov.open.ccd.court.secure.one.PartyNameType();
        pnt.setFirstGivenNm("A");
        pnt.setNameTypeCd("A");
        pnt.setNameTypeDsc("A");
        pnt.setSurnameNm("A");
        pnt.setFirstGivenNm("A");
        pnt.setSecondGivenNm("A");
        pnt.setThirdGivenNm("A");
        pnt.setOrganizationNm("A");
        pt.setPartyName(Collections.singletonList(pnt));

        crl2.setParties(Collections.singletonList(pt));

        var st2 = new ca.bc.gov.open.ccd.court.secure.one.SpeakerType();
        st2.setSpeakerId("A");
        st2.setSpeakerStatusCd("A");
        st2.setSpeakerSeqNo("A");
        st2.setVoirDireSeqNo("A");
        st2.setSpeakerName("A");
        st2.setSpeakerStatusCd("A");
        st2.setSpeakerStatusDsc("A");
        var se2 = new ca.bc.gov.open.ccd.court.secure.one.SpeakerEvent();
        se2.setSpeakerEventText("A");
        se2.setSpeakerEventDate(Instant.now());
        se2.setSpeakerEventTime(Instant.now());
        st2.setSpeakerEvent(Collections.singletonList(se2));

        var pot = new ca.bc.gov.open.ccd.court.secure.one.ProtectionOrderType();
        pot.setPOROrderIssueDate(Instant.now());
        pot.setOrderTypeDsc("A");
        pot.setPORConditionText("A");
        pot.setPOROrderExpiryDate(Instant.now());
        var rpt = new ca.bc.gov.open.ccd.court.secure.one.RestrainingPartyNameType();
        rpt.setRestrainingPartyName("A");
        var ppt = new ca.bc.gov.open.ccd.court.secure.one.ProtectedPartyNameType();
        ppt.setProtectedPartyName("A");
        pot.setRestrainingPartyName(Collections.singletonList(rpt));
        pot.setProtectedPartyName(Collections.singletonList(ppt));

        crl2.setProtectionOrder(Collections.singletonList(pot));

        var sat = new ca.bc.gov.open.ccd.court.secure.one.ScheduledAppearanceType();
        sat.setAppearanceId("A");
        sat.setCourtAgencyIdentifier("A");
        sat.setCourtRoom("A");
        sat.setAppearanceDate(Instant.now());
        sat.setAppearanceTime(Instant.now());
        sat.setAppearanceReasonCd("A");
        sat.setEstDurationHours("A");
        sat.setEstDurationMins("A");

        crl2.setScheduledAppearance(Collections.singletonList(sat));

        var hr2 = new ca.bc.gov.open.ccd.court.secure.one.HearingRestrictionType();
        hr2.setHearingRestrictionDate(Instant.now());
        hr2.setJudgeName("A");
        hr2.setHearingRestrictionDate(Instant.now());
        crl2.setHearingRestriction(Collections.singletonList(hr2));

        var caw = new ca.bc.gov.open.ccd.court.secure.one.CivilArrestWarrantType();
        caw.setWarrantDate(Instant.now());
        caw.setWarrantTypeCd("A");
        caw.setWarrantTypeDsc("A");
        crl2.setArrestWarrant(Collections.singletonList(caw));

        var ud = new ca.bc.gov.open.ccd.court.secure.one.UnscheduledDocumentType();
        ud.setDocumentId("A");
        ud.setFileSeqNumber("A");
        ud.setDocumentTypeDsc("A");
        var fb = new ca.bc.gov.open.ccd.court.secure.one.FiledByType();
        fb.setRoleTypeCode("A");
        fb.setRoleTypeCode("A");
        ud.setFiledBy(Collections.singletonList(fb));

        crl2.setUnscheduledDocument(Collections.singletonList(ud));

        var ov = new ca.bc.gov.open.ccd.court.secure.one.OrderToVaryType();
        ov.setAdjudicatorName("A");
        ov.setDateGranted("A");
        ov.setDocumentId("A");
        ov.setDocumentTypeDsc("A");

        crl2.setOrderToVary(Collections.singletonList(ov));

        clt.setCivilCourtList(Collections.singletonList(crl2));
        clt.setCriminalCourtList(Collections.singletonList(crl));
        out.setCourtLists(clt);

        ResponseEntity<GetCrtListSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(URI.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetCrtListSecureResponse>>any()))
                .thenReturn(responseEntity);

        CourtController courtController = new CourtController(restTemplate, objectMapper);
        var resp = courtController.getCrtListSecure(req);

        Assertions.assertNotNull(resp);
    }
}
