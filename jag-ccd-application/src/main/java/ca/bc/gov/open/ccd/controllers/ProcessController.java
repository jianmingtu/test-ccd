package ca.bc.gov.open.ccd.controllers;

import ca.bc.gov.ag.court.ccd_source_processresults_ws_provider.processresults.*;
import ca.bc.gov.open.ccd.configuration.SoapConfig;
import ca.bc.gov.open.ccd.exceptions.ORDSException;
import ca.bc.gov.open.ccd.models.OrdsErrorLog;
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

@Slf4j
@Endpoint
public class ProcessController {

    @Value("${ccd.host}")
    private String host = "https://127.0.0.1/";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public ProcessController(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processVariation")
    @ResponsePayload
    public ProcessVariationResponse processVariation(@RequestPayload ProcessVariation process)
            throws JsonProcessingException {

        var inner = process.getVariation() != null ? process.getVariation() : new VariationType();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<VariationType> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessVariationResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessVariationResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processVariation",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processSpeaker")
    @ResponsePayload
    public ProcessSpeakerResponse processSpeaker(@RequestPayload ProcessSpeaker process)
            throws JsonProcessingException {

        var inner = process.getSpeaker() != null ? process.getSpeaker() : new Speaker();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Speaker> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessSpeakerResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessSpeakerResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processSpeaker",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processCivilResults")
    @ResponsePayload
    public ProcessCivilResultsResponse processCivilResults(
            @RequestPayload ProcessCivilResults process) throws JsonProcessingException {

        var inner =
                process.getCivilResult() != null ? process.getCivilResult() : new CivilResultType();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<CivilResultType> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessCivilResultsResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessCivilResultsResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processCivilResults",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processAppearanceMethod")
    @ResponsePayload
    public ProcessAppearanceMethodResponse processAppearanceMethod(
            @RequestPayload ProcessAppearanceMethod process) throws JsonProcessingException {

        var inner =
                process.getAppearanceMethod() != null
                        ? process.getAppearanceMethod()
                        : new AppearanceMethod();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<AppearanceMethod> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessAppearanceMethodResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessAppearanceMethodResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processAppearanceMethod",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processPlea")
    @ResponsePayload
    public ProcessPleaResponse processPlea(@RequestPayload ProcessPlea process)
            throws JsonProcessingException {

        var inner = process.getPlea() != null ? process.getPlea() : new Plea();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Plea> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessPleaResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessPleaResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processPlea",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processElection")
    @ResponsePayload
    public ProcessElectionResponse processElection(@RequestPayload ProcessElection process)
            throws JsonProcessingException {

        var inner = process.getElection() != null ? process.getElection() : new Election();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Election> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessElectionResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessElectionResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processElection",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processBail")
    @ResponsePayload
    public ProcessBailResponse processBail(@RequestPayload ProcessBail process)
            throws JsonProcessingException {

        var inner = process.getBailDocInput() != null ? process.getBailDocInput() : new BailDoc();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<BailDoc> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessBailResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessBailResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processBail",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processCriminalResult")
    @ResponsePayload
    public ProcessCriminalResultResponse processCriminalResult(
            @RequestPayload ProcessCriminalResult process) throws JsonProcessingException {

        var inner =
                process.getCriminalResult() != null
                        ? process.getCriminalResult()
                        : new CriminalResult();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<CriminalResult> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessCriminalResultResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessCriminalResultResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processCriminalResult",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processAgeNotice")
    @ResponsePayload
    public ProcessAgeNoticeResponse processAgeNotice(@RequestPayload ProcessAgeNotice process)
            throws JsonProcessingException {

        var inner = process.getAgeNotice() != null ? process.getAgeNotice() : new AgeNotice();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<AgeNotice> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessAgeNoticeResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessAgeNoticeResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processAgeNotice",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processMatterCall")
    @ResponsePayload
    public ProcessMatterCallResponse processMatterCall(@RequestPayload ProcessMatterCall process)
            throws JsonProcessingException {

        var inner = process.getMatterCall() != null ? process.getMatterCall() : new MatterCall();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<MatterCall> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessMatterCallResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessMatterCallResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processMatterCall",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processSentence")
    @ResponsePayload
    public ProcessSentenceResponse processSentence(@RequestPayload ProcessSentence process)
            throws JsonProcessingException {

        var inner = process.getSentence() != null ? process.getSentence() : new Sentence();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Sentence> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessSentenceResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessSentenceResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processSentence",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processBan")
    @ResponsePayload
    public ProcessBanResponse processBan(@RequestPayload ProcessBan process)
            throws JsonProcessingException {

        var inner = process.getBan() != null ? process.getBan() : new Ban();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Ban> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessBanResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessBanResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processBan",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processNote")
    @ResponsePayload
    public ProcessNoteResponse processNote(@RequestPayload ProcessNote process)
            throws JsonProcessingException {

        var inner = process.getNote() != null ? process.getNote() : new NoteType();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<NoteType> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessNoteResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessNoteResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processNote",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processArraignment")
    @ResponsePayload
    public ProcessArraignmentResponse processArraignment(@RequestPayload ProcessArraignment process)
            throws JsonProcessingException {

        var inner = process.getArraignment() != null ? process.getArraignment() : new Arraignment();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Arraignment> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessArraignmentResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessArraignmentResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processArraignment",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processMove")
    @ResponsePayload
    public ProcessMoveResponse processMove(@RequestPayload ProcessMove process)
            throws JsonProcessingException {

        var inner = process.getMove() != null ? process.getMove() : new Move();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Move> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessMoveResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessMoveResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processMove",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processFinding")
    @ResponsePayload
    public ProcessFindingResponse processFinding(@RequestPayload ProcessFinding process)
            throws JsonProcessingException {

        var inner = process.getFinding() != null ? process.getFinding() : new Finding();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Finding> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessFindingResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessFindingResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processFinding",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processGenericResult")
    @ResponsePayload
    public ProcessGenericResultResponse processGenericResult(
            @RequestPayload ProcessGenericResult process) throws JsonProcessingException {

        var inner =
                process.getGenericResult() != null
                        ? process.getGenericResult()
                        : new GenericResult();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<GenericResult> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessGenericResultResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessGenericResultResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processGenericResult",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "ProcessCivilAppearanceMethod")
    @ResponsePayload
    public ProcessCivilAppearanceMethodResponse processCivilAppearanceMethod(
            @RequestPayload ProcessCivilAppearanceMethod process) throws JsonProcessingException {

        var inner =
                process.getCivilAppearanceMethod() != null
                        ? process.getCivilAppearanceMethod()
                        : new CivilAppearanceMethod();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<CivilAppearanceMethod> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessCivilAppearanceMethodResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessCivilAppearanceMethodResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "ProcessCivilAppearanceMethod",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processOrder")
    @ResponsePayload
    public ProcessOrderResponse processOrder(@RequestPayload ProcessOrder process)
            throws JsonProcessingException {

        var inner = process.getOrder() != null ? process.getOrder() : new OrderType();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<OrderType> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessOrderResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessOrderResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processOrder",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processCivilOrderResult")
    @ResponsePayload
    public ProcessCivilOrderResultResponse processCivilOrderResult(
            @RequestPayload ProcessCivilOrderResult process) throws JsonProcessingException {

        var inner =
                process.getCivilOrderResult() != null
                        ? process.getCivilOrderResult()
                        : new CivilOrderType();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<CivilOrderType> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessCivilOrderResultResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessCivilOrderResultResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processCivilOrderResult",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processExhibit")
    @ResponsePayload
    public ProcessExhibitResponse processExhibit(@RequestPayload ProcessExhibit process)
            throws JsonProcessingException {

        var inner =
                process.getExhibitRequest() != null ? process.getExhibitRequest() : new Exhibit();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<Exhibit> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessExhibitResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessExhibitResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processExhibit",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }

    @PayloadRoot(namespace = SoapConfig.SOAP_NAMESPACE, localPart = "processSpecialCourt")
    @ResponsePayload
    public ProcessSpecialCourtResponse processSpecialCourt(
            @RequestPayload ProcessSpecialCourt process) throws JsonProcessingException {

        var inner =
                process.getSpecialCourt() != null ? process.getSpecialCourt() : new SpecialCourt();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(host + "appearance");

        HttpEntity<SpecialCourt> payload = new HttpEntity<>(inner, new HttpHeaders());

        try {
            HttpEntity<ProcessSpecialCourtResponse> resp =
                    restTemplate.exchange(
                            builder.toUriString(),
                            HttpMethod.POST,
                            payload,
                            ProcessSpecialCourtResponse.class);

            return resp.getBody();
        } catch (Exception ex) {
            log.error(
                    objectMapper.writeValueAsString(
                            new OrdsErrorLog(
                                    "Error received from ORDS",
                                    "processSpecialCourt",
                                    ex.getMessage(),
                                    null)));
            throw new ORDSException();
        }
    }
}
