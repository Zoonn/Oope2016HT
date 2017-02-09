package com.ht.sokkelo;
/**
 * Käytävä-luokka, joka mallintaa aluetta sokkelossa,
 * jota pitkin voi kulkea ja jossa voi olla sokkelossa
 * liikkuvia objekteja; Mönkijä, robotteja tai esineitä.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 **/

public class Kaytava extends PeliObjekti  {
    
    /* 
     * Attribuutit
     */

    /** Omalista-tyyppinen lista, johon säilötään käytävän sisältö */
    private OmaLista lista;
     
    /* 
     * Rakentaja 
     */
     public Kaytava(int riv, int sar){
         super(riv, sar);
         lista = new OmaLista();
    }

    public OmaLista lista(){
        return lista;
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
