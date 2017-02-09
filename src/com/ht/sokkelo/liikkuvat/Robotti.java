package com.ht.sokkelo.liikkuvat;

import apulaiset.Suunnallinen;
/**
 * Robotti-luokka, joka toimii pelaajan ohjaamaa mönkijää
 * piinaavana vihollisena. Robotti liikkuu sokkelossa apulaiset-
 * pakkauksessa sijaitsevien apumetodien avulla ja haastaa mönkijän
 * taisteluun, mikäli sattuu sen kanssa samalle käytävälle.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 **/

public class Robotti extends LiikkuvaObjekti implements Suunnallinen{
    
    /*
     * Attribuutit
     */

    /** Attribuutti, jolla kuvataan obotin liikkeen suuntaa */
    private char suunta; 
    
   /* 
    * Rakentaja
    */
    public Robotti(int riv, int sar, int ener, char s){
        super(riv, sar, ener); 
        suunta(s); 
    }
    
    /*
     * Setterit ja getterit 
     */
    public void suunta(char s ){
        if(s == 'p' || s == 'e'|| s == 'l' || s == 'i')
            suunta = s; 
    }
    
    public char suunta(){
        return suunta; 
    }

    /*
     * Korvattavat metodit
     */

    /**
     * Kuormitettu toString-metodi, joka palauttaa olion nimen, rivi-ja sarakeindeksit sekä energian
     * @return olion nimi, rivi-ja sarakeindeksit sekä energia
     */
    public String toString(){
        return super.toString() + suunta() +"|";
    }

    /**
     * Implimentattu compareTo-metodi, jossa vertaillaan kahden Liikkuvan objektin energioita.
     *
     * @param o olio, jota vertaillaan tähän olioon.
     * @return -1, jos parametrinä saadulla oliolla suurempi energia, 0, jos energiat ovat samansuuruiset, 1 jos tällä
     * oliolla suurempi energia
     */
    public int compareTo(Object o){
        //T�m� olio < parametrin� saatu olio.
        if(energia() < ((LiikkuvaObjekti) o).energia())
            return -1; 
        //t�m� olio == parametrin� saatu olio
        if(energia() == ((LiikkuvaObjekti) o).energia())
            return 0; 
        //t�m� olio > parametrin� saatu olio
        else
            return 1; 
    }
}
