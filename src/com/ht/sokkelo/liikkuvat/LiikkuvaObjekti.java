package com.ht.sokkelo.liikkuvat;

import com.ht.sokkelo.*;
/**
 * Abstrakti luokka, josta peritään kaikki sokkelossa esiintyvät liikkuvat
 * objektit. Luokassa esitetään siis attribuutit, jotka sisältyvät kaikille
 * pelissä esiintyville liikkuville objekteille. Näin erotellaan liikkuvat
 * objektit liikkumattomista.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 **/

public abstract class LiikkuvaObjekti extends PeliObjekti implements Comparable<Object> {
    
    /* 
     * Attribuutit
     */
    /** Olion energian määrää kuvaava attribuutti*/
    private int energia; 
    
    /* 
     * Rakentaja
     */
    
    public LiikkuvaObjekti(int riv, int sar, int energia){
        super(riv,sar);
        energia(energia);
    }
    
    /* 
     * Setterit ja getterit 
     */
    
    public void energia(int e){
        if(e > 0)
            energia = e; 
    }
    
    public int energia(){
        return energia;
    }
    
    /* 
     * Korvattavat metodit
     */
    /**
     * Kuormitettu toString-metodi, joka palauttaa olion nimen, rivi-ja sarakeindeksit sekä energian
     * @return olion nimi, rivi-ja sarakeindeksit sekä energia
     */
    public String toString(){
        return super.toString() + energia() +"|"; 
    }

    /**
     * Implimentattu compareTo-metodi, jossa vertaillaan kahden Liikkuvan objektin energioita.
     *
     * @param o olio, jota vertaillaan tähän olioon.
     * @return -1, jos parametrinä saadulla oliolla suurempi energia, 0, jos energiat ovat samansuuruiset, 1 jos tällä
     * oliolla suurempi energia
     */
    public int compareTo(LiikkuvaObjekti o){
        //Tämä olio < parametrinä saatu olio.
        if(energia < o.energia)
            return -1; 
        //tämä olio == parametrinä saatu olio
        if(energia == o.energia)
            return 0; 
        //tämä olio > parametrinä saatu olio
        else
            return 1; 
    }
}
