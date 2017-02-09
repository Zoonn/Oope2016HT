package com.ht.sokkelo;

import apulaiset.Paikallinen;
/**
 * Abstrakti juuriluokka, josta peritään kaikki sokkelossa esiintyvät
 * objektit. Luokassa esitetään siis attribuutit, jotka sisältyvät kaikille
 * pelissä esiintyville objekteille.
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 */

public abstract class PeliObjekti implements Paikallinen{
    
    /* Attribuutit 
     * 
     * Objektien paikkaa esitt�v�t rivi-ja sarakeindeksit.
     */
    /** Peliobjektin rivi-indeksi sokkelossa */
    private int rivInd;
    /** Peliobjektin sarake-indeksi sokkelossa */
    private int sarInd;
    
    /*
     * Rakentaja 
     */
    public PeliObjekti(int i, int j){
        rivi(i);
        sarake(j);
    }
    
    /* 
     * Setterit ja getterit
     */
    
    public void rivi(int i){
        if(i >= 0)
            rivInd = i;
    }
    
    public void sarake(int j){
        if(j >= 0)
            sarInd = j; 
    }
    
    public int rivi(){
        return rivInd; 
    }
    
    public int sarake(){
        return sarInd; 
    }

    /**
     * Metodi, millä tarkastetaan, onko kyseessä oleva olio Käytävä-luokkaa.
     *
     * @return 1, jos olio on käytävä, 0, jos olio ei ole käytävä
     */
    public boolean sallittu(){
        if (this instanceof Kaytava){
            return true;
        }
        else
            return false;
    }
    
    /*
     * Korvattavat metodit 
     */

    /**
     * Kuormitettu toString-metodi, joka palauttaa olion tiedot merrkijonona
     * @return Merkkijono, jossa olion luokan nimi, rivi-indeksi ja sarakeindeksi.
     */
    public String toString(){

        return getClass().getSimpleName() +"|"+ rivi() +"|"+ sarake()+"|";
    }
}
