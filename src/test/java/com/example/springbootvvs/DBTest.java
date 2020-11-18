package com.example.springbootvvs;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
@SpringBootTest
public class DBTest {

    @Autowired
    private InstrumentstsRepository instrumentstsRepository;

    @BeforeEach
    public void initDataBase(){
        instrumentstsRepository.deleteAll();

    }

    @Test
    public void testFindAllInstrumentsWhenDBIsEmpty(){
        List<MusicInstruments> musicInstruments = instrumentstsRepository.findAll();
        assertEquals(0,musicInstruments.size());

    }
    @Test
    public void testFindAllInstrumentsWhenDBIsNotEmpty(){
        List<MusicInstruments> instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);

        List<MusicInstruments> musicInstruments = instrumentstsRepository.findAll();
        assertTrue((instruments.containsAll(musicInstruments) && musicInstruments.containsAll(instruments)));

    }

    @Test
    public void testSaveInstrumentWhenDBIsEmpty(){
        MusicInstruments instruments = new MusicInstruments("Xilofon","Scarlet",57 ,2);
        MusicInstruments addedInstruments;
        addedInstruments = instrumentstsRepository.save(instruments);
        List<MusicInstruments> musicInstruments1 = instrumentstsRepository.findAll();
        assertNotNull(addedInstruments);
        assertEquals(1,musicInstruments1.size());

    }
    @Test
    public void testSaveProductWhenDBIsNotEmpty() {
        List<MusicInstruments> instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);

        MusicInstruments instruments1 = new MusicInstruments("Acordeon","Boban",2410,7);
        instruments.add(instruments1);
        instrumentstsRepository.save(instruments1);
        List<MusicInstruments> musicInstruments = instrumentstsRepository.findAll();
        assertTrue((instruments.containsAll(musicInstruments) && musicInstruments.containsAll(instruments)));

    }
    @Test
    public void testFindByPriceLessThanWhenPriceIsLessThanAll(){
        List<MusicInstruments> instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        //instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);

        double price = 1600;
        List<MusicInstruments> musicInstruments = null;
        musicInstruments = instrumentstsRepository.findByPriceInstrumentLessThan(price);

        assertEquals(2,musicInstruments.size());

    }
    @Test
    public void testFindByPriceLessThanWhenPriceIsBigThanAll(){

        List<MusicInstruments>instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);

        double price = 4157.21;
        List<MusicInstruments> musicInstruments = null;
        musicInstruments = instrumentstsRepository.findByPriceInstrumentLessThan(price);
        assertEquals(4,musicInstruments.size());

    }

    @Test
    public void testFindByPriceLessThanWhenPriceIsInDataBase(){
        List<MusicInstruments> instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);


        double price = 1600;
        List<MusicInstruments> musicInstruments= null;
        musicInstruments = instrumentstsRepository.findByPriceInstrumentLessThan(price);

        instruments.remove(0);
        instruments.remove(0);
        assertTrue((instruments.containsAll(musicInstruments) && musicInstruments.containsAll(instruments)));

    }
    @Test
    public void testDeleteProductByIdWhenIdExist(){

        List<MusicInstruments> instruments = new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);

        Long id = instruments.get(0).getId();
        instrumentstsRepository.deleteById(id);
        instruments.remove(0);
        List<MusicInstruments> makeupProducts = instrumentstsRepository.findAll();
        assertTrue((instruments.containsAll(makeupProducts) && makeupProducts.containsAll(instruments)));

    }

}
