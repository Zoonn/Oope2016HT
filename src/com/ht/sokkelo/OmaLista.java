package com.ht.sokkelo;
import com.ht.sokkelo.liikkuvat.Robotti;
import fi.uta.csjola.oope.lista.*;
/**
 * Luokka, jota käytetään sokkelossa esiintyvien objektien sisältöjen
 * säilömiseen ja mallintamiseen. Luokka toimii linkitetyn listan tavoin.
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 */

public class OmaLista extends LinkitettyLista {

   /*
    * Uudet listaoperaatiot.
    *
    */

   /** Listan alkiot säilyttävät kasvavan suuruusjärjestyksen,
    * jos lisäys tehdään tällä operaatiolla.
    *
    * @param alkio viite olioon, jonka esivanhempi tai luokka
    * on toteuttanut Comparable-rajapinnan.
    * @throws IllegalArgumentException, jos oliolla ei ole
    * Comparable-rajapinnan toteutusta.
    */
   @SuppressWarnings("unchecked") // Estetään kääntäjän varoitus.
   public void lisaa(Object alkio){

       for (int i = 0; i <= this.koko(); i++) {
           boolean lisatty = false;
           if (paikkaOK(i) || (koko == 0 && i == 0)) {
               // Pään paikka tai listassa tasan yksi alkio.
               if (koko == 0 && i == 0) {
                   lisaaAlkuun(alkio);
                   lisatty = true;
               } else if (((Comparable) alkio).compareTo(this.alkio(i)) == -1) {
                   lisaa(i, alkio);
                   lisatty = true;
                   break;
               } else if (i == this.koko() - 1 && !lisatty) {
                   lisaaLoppuun(alkio);
                   break;
               }
           }
       }
   }

      /** Poistaa annettuun viitteeseen liittyvän alkion listalta.
        *
        * @param alkio viite poistettavaan tietoalkioon. Paluuarvo on null,
        * jos parametri on null-arvoinen tai poistettavaa alkiota ei löytynyt.
        */
   public Object poista(Object alkio) {
      // Apuviite, joka alustetaan aluksi virhekoodilla.
      Object poistettava = null;
      
      // Kääntyy todeksi, jos löydetään poistettava alkio.
      boolean loydetty = false;
      
      // Käydään listaa läpi alusta loppuun niin pitkään kuin alkioita on
      // saatavilla tai poistettavaa ei ole löydetty.
      int i = 0;
      while (i < koko && !loydetty) {
         // Löydettiin tietoalkio, johon liittyy parametri ja listan solmu.
         if (alkio == alkio(i)) {
            // Asetetaan poistettavaan alkioon apuviite, jotta alkiota ei hukata.
            poistettava = poista(i);
            
            // Löydettiin mitä haettiin.
            loydetty = true;
         }
         
         // Siirrytään seuraavaan paikkaan.
         else
            i++;
      }
      
      // Palalutetaan viite mahdollisesti poistettuun alkioon.
      return poistettava;
   }

   /** Poistaa annetun nimisen luokan tietoalkiot listalta ja palauttaa
     * viitteet niihin listalla.
     * 
     * @param luokanNimi listalta poistettavien alkioiden luokan nimi.
     * @return lista, jossa viitteet poistettuihin alkoihin. Lista on tyhjä,
     * jos listalla ei ollut luokan olioita.
     */
   public OmaLista poista(String luokanNimi) {
      // Tehdään palautettava lista.
      OmaLista poistetut = new OmaLista();

      // Yritetään poistaa, jos alkioita on vähintään yksi.
      if (koko > 0) {
         // Silmukoidaan lista läpi alusta loppuun.
         int i = 0;
         while (i < koko) {
            // Selvitetään metaolion avulla nykyisen tietoalkion luokan nimi.
            String alkionLuokanNimi = alkio(i).getClass().getSimpleName();

            // Poistetaan alkio, jos luokan nimi on parametrina annettu
            // ja lisätään viite tuloslistan loppuun.
            if (alkionLuokanNimi.equals(luokanNimi))
               poistetut.lisaaLoppuun(poista(i));
            // Kasvatetaan laskuria vain, kun ei poisteta, jotta alkioita
            // ei jäisi väliin.
            else
               i++;
         }
      }
      
      // Palautetaan viite tuloslistaan.
      return poistetut;
   }
   
   /*
    * Object-luokan metodin korvaus.
    *
    */

   public String toString() {
      // Listan alkioittainen merkkijonoesitys tänne.
      String alkiot = "[";

      // Tarkistetaan, että listalla on alkioita.
      if (!onkoTyhja()) {
         // Aloitetaan parametrina saadun listan päästä.
         Solmu paikassa = paa();

         // Edetään solmu kerrallaan, kunnes löydetään alkio tai lista loppuu.
         while (paikassa != null) {
            // Liitetään apuviite paikassa-viitteeseen liittyvän solmun alkioon.
            Object paikanAlkio = paikassa.alkio();

            // Siirrytään seuraavaan solmuun. Seuraava-aksessori palauttaa
            // viitteen paikassa-viitteeseen liittyvää solmua _seuraavaan_
            // solmuun. Sijoituksen jälkeen paikassa-viite liittyy tähän solmuun.
            paikassa = paikassa.seuraava();

            // Lisätään alkio ja erotin merkkijonoon.
            alkiot += paikanAlkio;
            if (paikassa != null)
               alkiot += ", ";
         }

      }

      // Viimeistellään esitys.
      alkiot += "]";

      // Palautetaan oma lista merkkijonona.
      return alkiot;
   }
}
