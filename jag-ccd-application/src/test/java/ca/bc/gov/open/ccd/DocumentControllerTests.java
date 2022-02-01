package ca.bc.gov.open.ccd;

import static org.mockito.Mockito.when;

import ca.bc.gov.open.ccd.common.document.DocumentResult;
import ca.bc.gov.open.ccd.common.document.GetDocument;
import ca.bc.gov.open.ccd.common.document.GetDocumentResponse;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecure;
import ca.bc.gov.open.ccd.common.document.secure.GetDocumentSecureResponse;
import ca.bc.gov.open.ccd.controllers.DocumentController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class DocumentControllerTests {
    @Autowired private ObjectMapper objectMapper;

    @Mock private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void getDocumentTest() throws JsonProcessingException {

        var req = new GetDocument();
        var out = new GetDocumentResponse();
        var docR = new DocumentResult();
        out.setDocumentResponse(docR);
        ResponseEntity<GetDocumentResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetDocumentResponse>>any()))
                .thenReturn(responseEntity);

        DocumentController documentController = new DocumentController(restTemplate, objectMapper);
        var resp = documentController.getDocument(req);

        Assertions.assertNotNull(resp);
    }

    @Test
    public void getDocumentSecureTest() throws JsonProcessingException {

        var req = new GetDocumentSecure();
        var out = new GetDocumentSecureResponse();
        var docR = new ca.bc.gov.open.ccd.common.document.secure.DocumentResult();
        out.setDocumentResponse(docR);
        ResponseEntity<GetDocumentSecureResponse> responseEntity =
                new ResponseEntity<>(out, HttpStatus.OK);

        // Set up to mock ords response
        when(restTemplate.exchange(
                        Mockito.any(String.class),
                        Mockito.eq(HttpMethod.GET),
                        Mockito.<HttpEntity<String>>any(),
                        Mockito.<Class<GetDocumentSecureResponse>>any()))
                .thenReturn(responseEntity);

        DocumentController documentController = new DocumentController(restTemplate, objectMapper);
        var resp = documentController.getDocumentSecure(req);

        Assertions.assertNotNull(resp);
    }
}
