package com.example.springbootvvs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RestControllerTest {

    @Autowired
    private InstrumentstsRepository instrumentstsRepository;

    @LocalServerPort
    private int port;

    private String serverUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @BeforeEach
    public void initServerURL() {
        instrumentstsRepository.deleteAll();
        this.serverUrl = "http://localhost:" + port;
    }
    private ResponseEntity<List<InstrumentstsRepository>> executeInstrumentRequest(String url) {
        return restTemplate.exchange(serverUrl + url, HttpMethod.GET, null, new ParameterizedTypeReference<List<InstrumentstsRepository>>() {
        });
    }
    @Test
    public void whenGetAllInstrumentsWithEmptyDB_thenReturn200AndCorrectResponse() {
        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/allProd");
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        assertEquals(0, response.getBody().size());

    }
    @Test
    public void whenGetAllinstruments_thenReturn200AndCorrectResponse() {
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",200,5));

        instrumentstsRepository.saveAll(instruments);

        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/allInstr");
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<InstrumentstsRepository> responseinstrumentsList = response.getBody();
        assertTrue((responseinstrumentsList.containsAll(instruments) && instruments.containsAll(responseinstrumentsList)));

    }
    @Test
    public void whenGetFindinstrumentsWithPriceLessThanAll_thenReturn200And0Elements() {
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/affordable/1700");
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<InstrumentstsRepository> responseinstrumentsList = response.getBody();
        assertEquals(0,responseinstrumentsList.size());

    }
    @Test
    public void whenGetFindinstrumentsWithPriceInDataBase_thenReturn200AndCorrectResponseBody() {
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
       new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/affordable/1500");
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());
        List<InstrumentstsRepository> responseinstrumentsList = response.getBody();

        List<MusicInstruments> expectedList = new ArrayList<>();
        expectedList.add(instruments.get(1));
        assertTrue((responseinstrumentsList.containsAll(expectedList) && expectedList.containsAll(responseinstrumentsList)));


    }
    @Test
    public void whenGetFindinstrumentsWithPriceBigThanAll_thenReturn200AndCorrectResponseBody() {
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
       new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/affordable/4000");
        assertEquals(HttpStatus.valueOf(200), response.getStatusCode());

        List<InstrumentstsRepository> responseinstrumentsList = response.getBody();
        assertTrue((responseinstrumentsList.containsAll(instruments) && instruments.containsAll(responseinstrumentsList)));


    }

    @Test
    public void whenGetFindinstrumentsWithoutPathVariable_thenReturn404() {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeInstrumentRequest("/affordable"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    public void whenDeleteInstrumentWithIdInDB_thenReturn200AndCorrectResponseBody() {

        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        ResponseEntity<List<InstrumentstsRepository>> response = executeInstrumentRequest("/delete/" + instruments.get(0).getId());
        List<MusicInstruments> expectedList = new ArrayList<>();
        expectedList.add(instruments.get(1));
        expectedList.add(instruments.get(2));

        List<InstrumentstsRepository> responseinstrumentsList = response.getBody();
        assertTrue((responseinstrumentsList.containsAll(expectedList) && expectedList.containsAll(responseinstrumentsList)));

    }

    @Test
    public void whenRemoveInstrumentWithoutPathVariable_thenReturn404() throws Exception {
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeInstrumentRequest("/delete"));
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }
    @Test
    public void whenRemoveInstrumentWithIdNotInDB_then100() throws Exception {
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
       new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        HttpServerErrorException response = assertThrows(HttpServerErrorException.class, () -> executeInstrumentRequest("/delete/100"));
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

    }
    @Test
    public void whenRemoveInstrumentWithIdInDBGetMethod_then405(){
        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);

        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeInstrumentRequest("/delete/"+instruments.get(0).getId()));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }
    @Test
    public void whenRemoveInstrumentWithNotInDBGetMethod_then404(){

        List<MusicInstruments> instruments = Arrays.asList(new MusicInstruments("Orga","Yamaha",2500,5),
        new MusicInstruments("Chitara","Fender",1600,4),
        new MusicInstruments("Pian","Yamaha",3500,3),
        new MusicInstruments("Saxofon","Yamaha",500,5));

        instrumentstsRepository.saveAll(instruments);
        HttpClientErrorException response = assertThrows(HttpClientErrorException.class, () -> executeInstrumentRequest("/delete/500"));
        assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());

    }


}
