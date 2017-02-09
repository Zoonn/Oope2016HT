package com.ht.sokkelo;
/**
 * Seinä-luokka, joka mallintaa sokkelon seiniä. Seinän läpi
 * ei voi kulkea, eikä siihen voi säilöä liikkuvia objekteja.
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 */

public class Seina extends PeliObjekti{
    
    /*
     * Rakentajat
     */
    public Seina(int riv, int sar){
        super(riv, sar);  
    }
    
    /*
     * Korvattavat metodit 
     */

    /**
     * {@inheritDoc}
     *
     * @return {@inheritDoc}
     */
    public String toString(){
        return super.toString(); 
    }
}
