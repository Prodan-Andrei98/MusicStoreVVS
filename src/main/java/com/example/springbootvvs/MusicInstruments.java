package com.example.springbootvvs;

import net.bytebuddy.implementation.bytecode.constant.NullConstant;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class MusicInstruments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String instrumentName;
    private String Company;
    private double priceInstrument;
    private int nrProd;
    public MusicInstruments(){

    }
    public MusicInstruments(String instrumentName,String Company,double priceInstrument,int nrProd){
        this.instrumentName = instrumentName;
        this.Company = Company;
        this.priceInstrument = priceInstrument;
        this.nrProd = nrProd;
    }
    public MusicInstruments(Long id,String instrumentName,String Company, double priceInstrument,int nrProd){

        this.id=id;
        this.instrumentName = instrumentName;
        this.Company = Company;
        this.priceInstrument = priceInstrument;
        this.nrProd= nrProd;
    }

    public Long getId() {
        return  id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInstrumentName() {
        return instrumentName;
    }

    public void setInstrumentName(String instrumentName) {
        this.instrumentName = instrumentName;
    }

    public double getPriceInstrument() {
        return priceInstrument;
    }

    public void setPriceInstrument(double priceInstrument) {
        this.priceInstrument = priceInstrument;
    }

    public String getCompany() {
        return Company;
    }

    public void setCompany(String Company) {
        this.Company = Company;
    }

    public int getNrProd() {
        return nrProd;
    }

    public void setNrProd(int nrProd) {
        this.nrProd = nrProd;
    }


   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MusicInstruments instruments = (MusicInstruments) o;

       return   id!=null &&  Double.compare( instruments.priceInstrument,priceInstrument) == 0
               && Objects.equals(id, instruments.id) && Objects.equals(instrumentName, instruments.instrumentName)
               && Objects.equals(nrProd, instruments.nrProd);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, instrumentName, nrProd, priceInstrument);
    }

}
