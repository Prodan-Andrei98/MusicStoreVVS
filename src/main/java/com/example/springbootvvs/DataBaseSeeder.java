package com.example.springbootvvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataBaseSeeder implements CommandLineRunner {
    private final InstrumentstsRepository instrumentstsRepository;
    @Autowired
    public DataBaseSeeder(InstrumentstsRepository instrumentstsRepository){
        this.instrumentstsRepository = instrumentstsRepository;
    }
    @Override
    public void run(String... args) throws Exception {
        List<MusicInstruments> instruments= new ArrayList<>();

        instruments.add(new MusicInstruments("Chitara","Fender",1600,4));
        instruments.add(new MusicInstruments("Pian","Yamaha",3500,3));
        instruments.add(new MusicInstruments("Saxofon","Yamaha",500,5));
        instruments.add(new MusicInstruments("Vioara","Ibanez",1234,5));
        instrumentstsRepository.saveAll(instruments);
    }
}
