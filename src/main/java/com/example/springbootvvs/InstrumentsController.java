package com.example.springbootvvs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping
public class InstrumentsController {
    private List<MusicInstruments> musicInstruments;
    private InstrumentstsRepository instrumentstsRepository;
   @Autowired
   public InstrumentsController(InstrumentstsRepository instrumentstsRepository){
       this.instrumentstsRepository = instrumentstsRepository;
   }


    @RequestMapping(value="/allInstr",method = RequestMethod.GET)
    public List<MusicInstruments> getAll()
    {
        return instrumentstsRepository.findAll();
    }


    @RequestMapping(value= "/affordable/{price}",method = RequestMethod.GET)
    public List<MusicInstruments> getAffordable(@PathVariable double price) {
       return instrumentstsRepository.findByPriceInstrumentLessThan(price);

//        List<MusicInstruments> musicInstrumentsList = instrumentstsRepository.findByPriceInstrumentLessThan(price);
//
//        if(musicInstrumentsList.isEmpty()){
//            throw new ResponseStatusException(HttpStatus.GONE, "Nu s-au gasit instrumente muzicale cu pretul selectat");
//        }
//
//        return musicInstrumentsList;
    }



    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public List<MusicInstruments> deleteInstruments(@PathVariable Long id){
        instrumentstsRepository.deleteById(id);
        return instrumentstsRepository.findAll();}



}
