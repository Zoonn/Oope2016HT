package com.ht.sokkelo.liikkuvat;
/**
 * Esine-luokka, joka mallintaa sokkelon käytäville ilmaantuvia esineitä.
 * Pelaajan ohjaama mönkijä kerää esineet omaan listaansa satuttuaan
 * niiden kanssa samalle käytävälle. Mönkijä voi muuntaa esineen
 * energiaksi, jota käytetään robotti-vihollisten kanssa taistelemiseen.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 **/

public class Esine extends LiikkuvaObjekti{
    
    /*
     * Rakentaja 
     */
    public Esine(int riv, int sar, int ener){
        super(riv,sar,ener);
    }
    
    /* 
     * Korvattavat metodit
     */

    /**
     * Kuormitettu toString-metodi, joka palauttaa olion nimen, rivi-ja sarakeindeksit sekä energian
     * @return olion nimi, rivi-ja sarakeindeksit sekä energia
     */
    public String toString(){
        return super.toString(); 
    }

    /**
     * Implimentattu compareTo-metodi, jossa vertaillaan kahden Liikkuvan objektin energioita.
     *
     * @param o olio, jota vertaillaan tähän olioon.
     * @return -1, jos parametrinä saadulla oliolla suurempi energia, 0, jos energiat ovat samansuuruiset, 1 jos tällä
     * oliolla suurempi energia
     */
    public int compareTo(Object o){
        //Tämä olio < parametrinä saatu olio.
        if(energia() < ((LiikkuvaObjekti) o).energia())
            return -1; 
        //tämä olio == parametrinä saatu olio
        if(energia() == ((LiikkuvaObjekti) o).energia())
            return 0; 
        //tämä olio > parametrinä saatu olio
        else
            return 1; 
    }
}
