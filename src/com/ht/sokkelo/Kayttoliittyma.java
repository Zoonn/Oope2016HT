package com.ht.sokkelo;

import apulaiset.*;
import java.io.IOException;
/**
 *  
 * Käyttöliittymä-luokka huolehtii pelaajan ja tietokoneen välisestä
 * vuorovaikutuksesta. Käyttöliittymä tulostaa tietoja pelaajalle
 * ja lukee pelaajan syötteet, sekä kutsuu niitä vastaavia toimintoja.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 */

public class Kayttoliittyma {
    
    /* 
     * Alustetaan kiinnitetyt vakiomuuttujat, kuten ilmoitukset, 
     * virheilmoitukset, objektien liikutteluun ja toimintoihin
     * käytettävät syötteet.
     */
    
    //Aloitusviesti
    final String ALOITUS = "***********\n* SOKKELO *\n***********";
    //Syötepyyntö
    final String ANNASYOTE = "Kirjoita komento:";
    //Virheilmoitus
    final String VIRHE = "Virhe!";
    //Lopetusilmoitus
    final String LOPETUS = "Ohjelma lopetettu."; 
    
    //suunnat, joihin mönkijä voi liikkua.
    final char POHJOINEN = 'p', ETELA = 'e', ITA = 'i', LANSI = 'l';
    //tietojen lukeminen tiedostosta 
    final String LATAA = "lataa"; 
    //Mönkijän tietojen tulostaminen
    final String INVENTOI = "inventoi";
    //Sokkelon tulostaminen
    final String KARTTA = "kartta"; 
    // naapuripaikan tulostaminen 
    final String KATSO = "katso";
    // Mönkijän liikuttaminen
    final String LIIKU = "liiku"; 
    //esineiden muuntaminen
    final String MUUNNA = "muunna"; 
    //vuoron ohittaminen
    final String ODOTA = "odota"; 
    //tietojen tallentaminen tiedostoon
    final String TALLENNA = "tallenna"; 
    //Ohjelman lopettaminen
    final String LOPETA = "lopeta";

    /*
     * Alustetaan muuttujia
     */
    private String toiminto = "";
    private Kasittelija kasittelija = new Kasittelija();
    private String toimintoRivi[];
    
    /*
     * Suoritusmetodi, missä käyttöliittymä pyörii.
     */
    /**
     * Metodi, joka tulostaa pelaajalle aloitusviestin, lataa pelin tilanteen tekstitiedostosta ja toimii peliä
     * pyörittävänä do-while-looppina. Metodi myös kutsuu lue-metodia, jossa luetaan pelaana syöte ja tarkistetaan se
     *
     * @throws IOException jos loopissa menee jokin vikaan.
     */
    public void suorita() throws IOException{
        boolean loppu = false;

        System.out.println(ALOITUS);
        kasittelija.lataa();

        do{
            toiminto = lue();

            // jos toiminto tunnistetaan vakioiduksi toimenpiteeksi, tehdään sitävastaava toimenpide
            if(toiminto != null){
                toimintoRivi = toiminto.split("\\s");


                if(toiminto.equals(LATAA)){
                    kasittelija.lataa();
                }
                else if(toiminto.equals(INVENTOI)){
                    kasittelija.inventoi();
                }
                else if(toiminto.equals(LOPETA)){
                    kasittelija.lopeta();
                }
                else if(toiminto.equals(KATSO)){
                    String apu = toimintoRivi[1];
                    char suunta = apu.charAt(0);
                    kasittelija.katso(suunta);
                }
                else if(toiminto.equals(ODOTA)){
                    kasittelija.odota();

                }
                else if(toiminto.equals(TALLENNA)){
                    kasittelija.tallenna();
                }
                else if(toiminto.equals(KARTTA)){
                    kasittelija.kartta();
                }



                else if(toimintoRivi[0].equals(LIIKU) || toimintoRivi[0].equals(KATSO)){

                    toimintoRivi[1] = toimintoRivi[1].replaceAll("\\s","");
                    String apu = toimintoRivi[1];
                    char suunta = apu.charAt(0);

                    if(toimintoRivi[0].equals(LIIKU)){
                        kasittelija.liiku(suunta);
                        kasittelija.kartta();
                    }
                    else if(toimintoRivi[0].equals(KATSO)){
                        kasittelija.katso(suunta);
                    }
                }
                else if(toimintoRivi[0].equals(MUUNNA)) {
                    if(toimintoRivi.length == 2 && toimintoRivi[1].length() == 1) {
                        toimintoRivi[1] = toimintoRivi[1].replaceAll("\\s","");
                        int luku = Integer.parseInt(toimintoRivi[1]);
                        kasittelija.muunna(luku);
                    }
                    else
                        System.out.println(VIRHE);
                }
            }
            else
                System.out.println(VIRHE);
        }
        while(!loppu);

    }

    /**
     * Metodi, joka tulostaa pelaajalle syötepyynnön ja lukee pelaajan antaman syötteen. Metodi myös tarkistaa,
     * onko pelaajan antama syöte oikeellinen. Jos syöte on kunnossa, palautetaan syöte.
     *
     * @return pelaajan antama syöte, mikäli se on oikeellinen
     */
    public String lue(){

        //alustetaan apumuuttujat
        boolean ok = false;
        String syotto;

        //Virheiden tarkistus
        do{
            System.out.println(ANNASYOTE);

            syotto = null;
            syotto = In.readString();

            //Tarkistetaan, että syöte on oikeellinen
            if(syotto.equals(KARTTA)||syotto.equals(LATAA) ||syotto.equals(TALLENNA)||syotto.equals(LOPETA)||
                    syotto.equals(ODOTA)||syotto.equals(INVENTOI)){
                ok = true;
            }
            else if(!ok ){
                //Luetaan kaksiosaiset komennot
                String[] tuplaKomento = syotto.split("\\s");
                if(tuplaKomento == null || tuplaKomento.length > 2){
                    System.out.println(VIRHE);
                }
                // Varaudutaan virheellisiin syötteisiin
                else if(tuplaKomento.length == 2 && tuplaKomento[1].length() == 1) {
                    String apu2 = tuplaKomento[1];
                    // Jos syötteenä vastaanotettiin liiku tai katso, tarkistetaan onko suunta kunnossa.
                    if (tuplaKomento[0].equals(LIIKU) || tuplaKomento[0].equals(KATSO)) {
                        char suunta2 = apu2.charAt(0);
                        if (suunta2 == POHJOINEN || suunta2 == ETELA ||
                                suunta2 == LANSI || suunta2 == ITA) {
                            ok = true;
                        } else {
                            System.out.println(VIRHE);
                        }
                    }
                    // Jos syöte on muunna, muunnetaan syötteen toinen osa int-muotoiseksi
                    else if (tuplaKomento[0].equals(MUUNNA)) {
                        try {
                            int muunnos = Integer.parseInt(apu2);
                            if(muunnos ==(int)muunnos)
                                ok = true;
                        }
                        catch (Exception e) {
                            System.out.println(VIRHE);
                        }
                    }
                    else
                        System.out.println(VIRHE);
                }
                else {
                    System.out.println(VIRHE);
                }
            }
        }
        while(!ok);
        return syotto;
    }
}
