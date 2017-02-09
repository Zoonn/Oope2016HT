package com.ht.sokkelo.liikkuvat;

import apulaiset.Suunnallinen;
import com.ht.sokkelo.*;
/**
 * Mönkijäluokka, joka mallintaa pelaajan ohjaamaa mönkijää sokkelossa.
 * Monkijä voi kerätä sokkelon käytävä-osista itselleen esineitä, jotka
 * varastoidaan OmaLista-tyyppiseen listaan.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 **/

public class Monkija extends LiikkuvaObjekti implements Suunnallinen{
    
    /* 
     * attribuutit
     */
    
    /**OmaLista-tyyppinen lista, johon säilötään mönkijällä olevat esineet */
    private OmaLista varasto;
    /** Attribuutti, jolla kuvataan mönkijän liikkeen suuntaa */
    private char suunta;

    /* 
     * rakentaja
     */
    public Monkija(int riv, int sar, int energia, char s){
        super(riv, sar, energia); 
        suunta(s);
        varasto = new OmaLista();
    }
    
    /*
     * Setterit ja gettterit 
     */
    public void suunta(char s ){
        if(s == 'p' || s == 'e'|| s == 'l' || s == 'i')
            suunta = s;

    }

    public OmaLista varasto(){
        return varasto;
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