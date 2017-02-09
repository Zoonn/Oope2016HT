package com.ht.sokkelo;

import com.ht.sokkelo.liikkuvat.*;
import apulaiset.*;
import java.io.*;
/**
 * Käsittelijä-luokka, joka vastaa sokkelon sisällä tapahtuvasta
 * logiikasta. Käsittelijä-luokka huolehtii ohjelman toiminnoista
 * ja tapahtumista.
 *
 *<p>
 * Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2016.
 *<p>
 * @author Miikka Mäki mm96932 (maki.miikka.e@student.uta.fi),
 * Informaatiotieteiden yksikkö, Tampereen yliopisto.
 */

public class Kasittelija {

    /*
       tulosteet vakioidaan
     */
    final String VALI = " ";
    final String SEINA = ".";
    final String MONKIJA = "M";
    final String ROBOTTI = "R";
    final String ESINE ="E";
    final String VIRHE ="Virhe!";
    final String KOPS ="Kops!";
    final String VOITTO = "Voitto!";
    final String TAPPIO = "Tappio!";

    final char POHJOINEN = 'p';
    final char ETELA = 'e';
    final char ITA = 'i';
    final char LANSI = 'l';
    final String LOPETUS = "Ohjelma lopetettu.";

    // Kaksiuloitteinen taulukko, jota käytetään sokkelon tietorakenteena
    private PeliObjekti[][] sokkelo;
    //Mönkijän alustus
    private Monkija monkija;
    // robotit-listan alustus
    private OmaLista robotit = new OmaLista();
    // Esineiden lukumäärä, joita peliin luodaan. Alustetaan muuttuja täällä, että sitä voidaan tarkastella liiku-metodissa,
    // jotta voidaan olla perillä siitä, milloin pelin voittoehdot täyttyvät.
    private int esineidenLkm = 0;
    // Säilötään tähän muuttujaan tieto siitä ,kuinka monta esinettä mönkijän varastoon on lisätty.
    private int lisatytEsineet = 0;
    // Peliin asetettava siemen, joka toimii automaatti-luokassa satunanisgeneroijan apuna
    private int siemen = 0;

    /* 
     * Toiminnallisuudesta vastaavat metodit 
     */

    /**
     * Metodi, joka lisää pelin mekkijonoesityksiin ylimääräiset välilyönnit,
     * jotta tehtäväkuvauksen vaatimukset täyttyisivät
     *
     * @param s Merkkijono, johon lisätään tarvittavat välilyönnit ja putkimerkit
     * @return Merkkijono, johon on lisätty tarvittavat välilyönnit ja putkimerkit
     */
    public static String erottelija(String s){
     // Metodi, joka lisää pelin mekkijonoesityksiin ylimääräiset välilyönnit,
     // jotta tehtäväkuvauksen vaatimukset täyttyisivät
        
        //Jaetaan merkkijono osiin split-metodilla 
        String[] osat = s.split("\\|");
        
        for(int i = 0; i < osat.length; i++){
            //Käydään läpi ensimmäinen osa, eli luokan nimi
            if(i == 0){
                // luokan nimen paikassa tulee olla yhdeksän merkkiä. lisätään.
                for(int j = osat[i].length(); j <= 9; j++){
                    //jos välimerkit lisätty, lisätään perään erotin.
                    if(j == 9){
                        osat[i] = osat[i] + "|"; 
                    }
                    // Muuten lisätään lisää välimerkkejä
                    else
                        osat[i] = osat[i] + " "; 
                }
            }
            else
                //Käydään läpi loput paikat. Näissä tulee olla neljä merkkiä. Lisätään...
                for(int j = osat[i].length(); j <= 4; j++){
                    //jos välimerkit lisätty, lisätään perään erotin.
                    if(j == 4){
                        osat[i] = osat[i] + "|"; 
                    }
                    // Muuten lisätään lisää välimerkkejä
                    else
                        osat[i] = osat[i] + " ";
            }
        }
        //Muutetaan taulu takaisin stringiksi ja lähetetään takaisin
        String uusiMerkkijono = ""; 

        for(int i = 0; i < osat.length; i++){
            uusiMerkkijono = uusiMerkkijono + osat[i]; 
        }
        return uusiMerkkijono;
    }

    /**
     * Metodi, joka lisää tallennettavaan ensimmäiseen riviin (siemen, sokkelon rivi-ja sarakeindeksit) tarvittavat
     * välimerkit ja putkimerkit.
     *
     * @param s Merkkijono, johon lisätään tarvittavat välilyönnit ja putkimerkit
     * @return Merkkijono, johon on lisätty tarvittavat välilyönnit ja putkimerkit
     */
    public String ekanRivinErottelija(String s){
        //Jaetaan merkkijono osiin split-metodilla
        String[] osat = s.split("\\|");

        for(int i = 0; i < osat.length; i++){
            //Käydään läpi paikat. Näissä tulee olla neljä merkkiä. Lisätään...
            for(int j = osat[i].length(); j <= 4; j++){
                //jos välimerkit lisätty, lisätään perään erotin.
                if(j == 4){
                    osat[i] = osat[i] + "|";
                }
                // Muuten lisätään lisää välimerkkejä
                else
                    osat[i] = osat[i] + " ";
            }
        }
        //Muutetaan taulu takaisin stringiksi ja lähetetään takaisin
        String uusiMerkkijono = "";

        for(int i = 0; i < osat.length; i++){
            uusiMerkkijono = uusiMerkkijono + osat[i];
        }
        return uusiMerkkijono;
    }

    /**
     * Metodi, joka hoitaa pelin lataamisen tiedostosta ja varsinaisen sokkelon alustuksen sisältöineen
     *
     * @throws IOException jos tiedostosta lataamisessa menee jotain pieleen
     */
    public void lataa() throws IOException{

        /*
         * Muuttujien alustus
         */
        String nimi = "";
        int riv;
        int sar;
        int energia = 0;
        char suunta = ' ';

        // Poistetaan aiemmat tiedot taulukoista
        sokkelo = null;

        for(int i = 0; i < robotit.koko();i++){
            robotit.poista(i);
        }

        try{
            // Avataan lukija
            FileInputStream virta = new FileInputStream("sokkelo.txt");
            InputStreamReader lukija = new InputStreamReader(virta);
            BufferedReader puskuroituLukija = new BufferedReader(lukija);

            int riviLkm = 0; // = monesko rivi menossa
            siemen = 0; // = robottien liikutteluu käytettävä siemenluku

            // Luetaan rivit tiedostosta yksi kerrallaan ja tehdään rivin lukumäärän mukaiset toimenpiteet
            while(puskuroituLukija.ready()){
                String rivi = puskuroituLukija.readLine();
                if(!rivi.equals("")) {
                    String osat[] = rivi.split("\\|");
                    riviLkm++;

                    //Poistetaan ylimääräiset merkkivälit osista for-loopilla.
                    for (int i = 0; i < osat.length; i++) {
                        osat[i] = osat[i].replaceAll("\\s", "");
                    }

                    // Asetetaan tässä vaiheessa riveihin ja sarakkeisiin tulevat indeksit,
                    // sillä ne ovat aina samassa paikassa tiedostossa.
                    riv = Integer.parseInt(osat[1]);
                    sar = Integer.parseInt(osat[2]);

                    // Jos tiedoston ensimmäinen rivi, asetetaan siemen ja luodaan kaksiuloitteinen lista sokkelolle
                    if (riviLkm == 1) {
                        siemen = Integer.parseInt(osat[0]);
                        sokkelo = new PeliObjekti[riv + 1][sar + 1];
                        Automaatti.alusta(siemen);

                    }
                    // Muuten asetetaan tiedoston sisältö muuttujiin sen mukaan, kuinka pitkä rivi on rivi riviltä.
                    else {
                        nimi = osat[0];

                        if (osat.length > 3) {
                            energia = Integer.parseInt(osat[3]);
                        }
                        if (osat.length == 5) {
                            suunta = osat[4].charAt(0);
                        }
                    }
                    // Tämän jälkeen voidaan luoda oliot listalle muuttujien avulla, joilla kutsutaan rakentajia
                    // Selvitetään, mikä olio tulee luoda nimen perusteella

                    if (nimi.equals("Seina")) {
                        sokkelo[riv][sar] = new Seina(riv, sar);
                    } else if (nimi.equals("Kaytava")) {
                        sokkelo[riv][sar] = new Kaytava(riv, sar);
                    } else if (nimi.equals("Monkija")) {
                        Kaytava apuKaytava;
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        monkija = new Monkija(riv, sar, energia, suunta);
                        apuKaytava.lista().lisaa(monkija);
                    } else if (nimi.equals("Robotti")) {
                        Kaytava apuKaytava;
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        Robotti uusiRobo = new Robotti(riv, sar, energia, suunta);
                        apuKaytava.lista().lisaa(uusiRobo);

                        robotit.lisaaLoppuun(uusiRobo);

                    } else if (nimi.equals("Esine")) {
                        Kaytava apuKaytava;
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        apuKaytava.lista().lisaa(new Esine(riv, sar, energia));
                        esineidenLkm++;
                    }
                }
            }
            puskuroituLukija.close();
        }
        catch(IOException e){
            System.out.println(VIRHE);
            throw e;
        }

        // Lopuksi tarkistetaan, onko mönkijä alustettu samalle paikalle, missä sijaitsee esineitä. Jos on, lisätään
        // esineet mönkijän varastoon
        Kaytava apuKaytava;
        apuKaytava = (Kaytava)sokkelo[monkija.rivi()][monkija.sarake()];
        int koko = apuKaytava.lista().koko();
        for (int i = koko; i >= 0; i--) {
            LiikkuvaObjekti objekti = (LiikkuvaObjekti) apuKaytava.lista().alkio(i);
            // Jos esine, lisätään se mönkijän varastoon ja poistetaan käytävältä.
            if (objekti instanceof Esine) {
                monkija.varasto().lisaa(objekti);
                apuKaytava.lista().poista(objekti);
                lisatytEsineet++;
            }
        }
    }

    /**Metodi, joka tulostaa mönkijän varaston sisällön pelaajalle. */
    public void inventoi(){
        System.out.println(erottelija(monkija.toString()));
        //Käydään läpi mönkijän varasto for-loopilla ja tulostetaan sisältö jokainen omalle rivilleen
        for(int i = 0; i < monkija.varasto().koko();i++){
            System.out.println(erottelija(monkija.varasto().alkio(i).toString()));
        }
    }

    /** Metodi, joka tulostaa kartan sokkelosta pelaajan näkyville. */
    public void kartta(){
        for(int i = 0; i < sokkelo.length;i++){
            for(int j =0 ; j < sokkelo[i].length;j++){

                // Jos kyseessä seinä, tulostetaan seinän merkki
                if(sokkelo[i][j] instanceof Seina){
                    System.out.print(SEINA);
                }

                // Jos kyseessä käytävä, tulostetaan käytävän merkki
                else if(sokkelo[i][j] instanceof Kaytava){
                    Kaytava apuKaytava;
                    apuKaytava = (Kaytava)sokkelo[i][j];

                    //Jos käytävä on tyhjä, tulostetaan välimerkki
                    if(apuKaytava.lista().onkoTyhja()){
                        System.out.print(VALI);
                    }
                    //Muuten tulostetaan joko mönkijä, robotti tai esine, mikä käytävästä löytyy
                    else{
                        boolean loytyikoLiikkuva = false;
                        for(int z = 0; z < apuKaytava.lista().koko();z++){
                            if(apuKaytava.lista().alkio(z) instanceof Monkija){
                                System.out.print(MONKIJA);
                                loytyikoLiikkuva = true;
                                break;
                            }
                            else if(apuKaytava.lista().alkio(z) instanceof Robotti){
                                System.out.print(ROBOTTI);
                                loytyikoLiikkuva = true;
                                break;
                            }
                        }
                        if(!loytyikoLiikkuva) {
                            System.out.print(ESINE);
                        }
                    }
                }
            }
            if(i < sokkelo.length-1)
             System.out.println();
        }
    }

    /**
     * Metodi, joka tulostaa vieressä olevan käytävän sisällön suunta-parametrin avulla
     *
     * @param suunta mistä suunnasta katsotaan seuraava paikka sokkelossa
     */

    public void katso(char suunta){
        if(suunta == ETELA || suunta == POHJOINEN || suunta == LANSI || suunta == ITA){
            int riv = monkija.rivi();
            int sar = monkija.sarake();

            if(suunta == ETELA){
                riv++;
            }
            else if(suunta == POHJOINEN){
                riv--;
            }
            else if(suunta == LANSI){
                sar--;
            }
            else {
                sar++;
            }
            System.out.println(erottelija(sokkelo[riv][sar].toString()));

            //Jos vieressä on käytävä, tulostetaan käytävän sisältö näkyville.
            if(sokkelo[riv][sar] instanceof Kaytava){

                Kaytava apuKaytava;
                apuKaytava = (Kaytava)sokkelo[riv][sar];
                for(int i = 0; i < apuKaytava.lista().koko();i++) {
                   LiikkuvaObjekti objekti = (LiikkuvaObjekti)apuKaytava.lista().alkio(i);
                    System.out.println(erottelija(objekti.toString()));
                }
            }
        }
        // Virheen sattuessa tulostetaan virheilmotitus
        else{
            System.out.println(VIRHE);
        }
    }

    /**
     * Metodi, jolla liikutetaan mönkijää pitkin sokkeloa suuntaparametrin mukaan.
     *
     * @param suunta mihin suuntaan mönkijää liikutetaan sokkelossa
     */
    public void liiku(char suunta){

        if(suunta == ETELA || suunta == POHJOINEN || suunta == LANSI || suunta == ITA) {
            boolean sijaintiOK = false;
            int riv = monkija.rivi();
            int sar = monkija.sarake();

            // Tarkistetaan uusi sijainti: jos sijainti on seinä, mönkijä ei liiku ja tulostetaan "Kops!"
            // Jos uusi sijainti on käytävä, mönkijä liikkuu sinne. Jos käytävällä on esineitä, liitetään esineet
            // mönkijän varastoon ja poistetaan käytävän listalta. Jos käytävällä on robotti, taistellaan robottia
            // vastaan
            try {
                if (suunta == ETELA) {
                    riv = monkija.rivi() + 1;
                } else if (suunta == POHJOINEN) {
                    riv = monkija.rivi() - 1;
                } else if (suunta == LANSI) {
                    sar = monkija.sarake() - 1;
                } else {
                    sar = monkija.sarake() + 1;
                }

                //kohdattiin seinä. Kopsahdus.
                if (sokkelo[riv][sar] instanceof Seina) {
                    System.out.println(KOPS);
                    roboLiiku(robotit);
                }
                //Kohdattiin käytävä
                else {
                    Kaytava apuKaytava;
                    apuKaytava = (Kaytava) sokkelo[riv][sar];

                    //Tarkistetaan, onko käytävä tyhjä
                    if(apuKaytava.lista().koko() == 0){
                        sijaintiOK = true;
                    }
                    // Käytävässä on esine tai robotti. Jos esine, lisätään se mönkijän varastoon
                    else {
                        for (int i = apuKaytava.lista().koko()-1; i >= 0; i--) {
                            LiikkuvaObjekti objekti = (LiikkuvaObjekti) apuKaytava.lista().alkio(i);

                            // Jos esine, lisätään se mönkijän varastoon ja poistetaan käytävältä.
                            if (objekti instanceof Esine) {
                                monkija.varasto().lisaa(objekti);
                                apuKaytava.lista().poista(objekti);
                                sijaintiOK = true;
                                lisatytEsineet++;
                            }
                            // Kohdattiin robotti
                            else {
                                // Kutsutaan taistele-metodia, joka selvittää, voittaako mönkijä vai robotti
                                LiikkuvaObjekti voittaja = taistele(monkija, objekti);

                                // Jos voittaja on mönkijä, poistetaan robotti
                                if (voittaja instanceof Monkija) {
                                    sijaintiOK = true;
                                    apuKaytava.lista().poista(objekti);
                                    robotit.poista(objekti);
                                    System.out.println(VOITTO);
                                }

                                //Voittajaksi selvisi robotti. siirretään robotti mönkijän paikalle, tuhotaan
                                // mönkijä ja lopetetaan peli
                                else {
                                    sijaintiOK = false;
                                    apuKaytava.lista().lisaa(objekti);
                                    apuKaytava = (Kaytava) sokkelo[monkija.rivi()][monkija.sarake()];
                                    apuKaytava.lista().poista(monkija);
                                    System.out.println(TAPPIO);
                                    lopeta();
                                }
                            }
                        }
                    }
                }
                // Jos sijainti on kunnossa, voidaan liikuttaa mönkijää
                if (sijaintiOK) {
                    Kaytava apuKaytava;
                    // Liikutetaan mönkijää apuolion avulla siihen suuntaan, mihin suuntaparametri ilmoittaa
                    if (suunta == ETELA) {
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        monkija.rivi(riv);
                        monkija.suunta(suunta);
                        apuKaytava.lista().lisaa(monkija);
                        apuKaytava = (Kaytava)sokkelo[riv-1][sar];
                        apuKaytava.lista().poista(monkija);

                    } else if (suunta == POHJOINEN) {
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        monkija.rivi(riv);
                        monkija.suunta(suunta);
                        apuKaytava.lista().lisaa(monkija);
                        apuKaytava = (Kaytava)sokkelo[riv+1][sar];
                        apuKaytava.lista().poista(monkija);

                    } else if (suunta == LANSI) {
                        apuKaytava = (Kaytava) sokkelo[riv][sar];
                        monkija.sarake(sar);
                        monkija.suunta(suunta);
                        apuKaytava.lista().lisaa(monkija);
                        apuKaytava = (Kaytava)sokkelo[riv][sar+1];
                        apuKaytava.lista().poista(monkija);

                    } else {
                        apuKaytava = (Kaytava)sokkelo[riv][sar];
                        monkija.sarake(sar);
                        monkija.suunta(suunta);
                        apuKaytava.lista().lisaa(monkija);
                        apuKaytava = (Kaytava)sokkelo[riv][sar-1];
                        apuKaytava.lista().poista(monkija);
                    }

                    // for-looppi, millä päivitetään mahdolliset mönkijän varastossa olevien
                    // esineiden sijainnit vastaamaan mönkijän uutta sijaintia.
                    for(int j = 0; j <monkija.varasto().koko();j++){
                        Esine esine = (Esine)monkija.varasto().alkio(j);
                        esine.rivi(monkija.rivi());
                        esine.sarake(monkija.sarake());
                    }

                    // Tarkistetaan voittoehdot. Mikäli liikkeen jälkeen on lisätty viimeinen esine, eli mönkijän
                    // varastoon on lisätty yhteensä yhtä monta esinettä kuin mitä peliin alunperin ladattiin, tarkoittaa tämä
                    // pelaajan voittoa. Tässä tapauksessa tulostetaan loppunäkymä ja voittoteksti ja lopetetaan peli.
                    if(lisatytEsineet == esineidenLkm){
                        lopeta();
                    }

                    // Mönkijän liikuttamisen jälkeen siirretään vuoro pelille, milloin robottien
                    // paikat päivitetään

                    roboLiiku(robotit);
                }
            }
            // Jos virheellinen syöte, tulostetaan virheilmoitus.
            catch(NullPointerException e){
                System.out.println(VIRHE);
            }
        }
        // Virheen sattuessa tulostetaan virheilmoitus
        else{
            System.out.println(VIRHE);
        }
    }

    /**
     * Metodi, joka muuttaa mönkijän varastossa olevat esineet mönkijän energiaksi järjestyksessä pienimmästä suurimpaan.
     *
     * @param maara kuina monta esinettä mönkijän varastosta muunnetaan mönkijälle energiaksi
     */
    public void muunna(int maara){
        if(maara > 0 && maara <= monkija.varasto().koko()){
            // Käydään for-loopilla kaikki varaston sisältö läpi, ja ynnätään apuolion avulla energiat yhteen
            // sekä lopuksi sijoitetaan mönkijän energiaksi
            for(int i = 0;i < maara;i++){
                if(monkija.varasto().alkio(0) != null) {
                    Esine esine = (Esine) monkija.varasto().alkio(0);
                    int energia = esine.energia();
                    int monkijanEnergia = monkija.energia();
                    energia = energia + monkijanEnergia;
                    monkija.energia(energia);
                    monkija.varasto().poista(0);
                }
                else
                    break;
            }
        }
        else
            System.out.println(VIRHE);
    }

    /** Metodi, jolla hypätään yli pelaajan vuoro tekemättä mitään ja annetaan vuoro roboteille. */
    public void odota(){
        //  siirretään vuoro pelille, milloin robottien paikat päivitetään
        roboLiiku(robotit);
        kartta();
    }

    /**
     * Metodi, jolla tallennetaan nykyinen pelitilanne sokkelo.txt-tiedostoon.
     *
     * @throws IOException, jos tiedostoon tallentamisessa menee jokin vikaan
     */
    public void tallenna() throws IOException {
        try {
            // Avataan kirjoittaja
            File tiedosto = new File("sokkelo.txt");
            FileOutputStream tulostusVirta = new FileOutputStream(tiedosto);
            PrintWriter kirjoittaja = new PrintWriter(tulostusVirta, true);

            //aluksi tulostetaan tiedoston ensimmäiselle riville siemenluku ja sokkelon rivi-ja sarakeindeksit
            kirjoittaja.println(ekanRivinErottelija(Integer.toString(siemen)+"|"+Integer.toString(sokkelo.length-1)
                    +"|"+Integer.toString(sokkelo[0].length-1)));

            // Käydään läpi sokkelon jokainen kohta ja sijoitetaan ne apuolioon. sokkelon indeksien avulla selvitetään, mikä
            // olio on kyseessä tietyssä paikkaa sokkeloa. Kirjoitetaan tostringin ja erottelija-metodin avulla
            // olion merkkijonoesitys tiedostoon vaaditun formaatin mukaisesti
            for (int i = 0; i < sokkelo.length-1; i++) {
                for (int j = 0; j < sokkelo[i].length-1; j++) {

                    PeliObjekti apuOlio;
                    Kaytava apuKaytava;
                    apuOlio = sokkelo[i][j];
                    // Jos kyseessä seinä, tallennetaan seinä
                    if(sokkelo[i][j] instanceof Seina){
                        kirjoittaja.print(erottelija(apuOlio.toString()));
                    }

                    // Jos kyseessä käytävä, tallennetaan käytävä
                    else if(sokkelo[i][j] instanceof Kaytava) {
                        apuKaytava = (Kaytava) sokkelo[i][j];
                        kirjoittaja.print(erottelija(apuOlio.toString()));
                        //Jos käytävä on ei ole tyhjä, tallennetaan myös käytävän sisältö
                        if (!apuKaytava.lista().onkoTyhja()) {
                                kirjoittaja.println();
                            for (int z = 0; z < apuKaytava.lista().koko(); z++) {
                                 kirjoittaja.print(erottelija(apuKaytava.lista().alkio(z).toString()));
                                // Jos kohdataan mönkijä, tallennetaan tässä vaiheessa mönkijän varaston mahdollinen sisältö
                                if(apuKaytava.lista().alkio(z) instanceof Monkija){
                                    if(monkija.varasto().koko() != 0) {
                                        kirjoittaja.println();
                                        for (int x = 0; x < monkija.varasto().koko(); x++) {
                                            kirjoittaja.print(erottelija(monkija.varasto().alkio(x).toString()));
                                            if(x < monkija.varasto().koko()-1){
                                                kirjoittaja.println();
                                            }
                                        }
                                    }
                                }
                                if(z < apuKaytava.lista().koko()-1)
                                     kirjoittaja.println();
                            }
                        }
                    }
                    // Jos viimeisen rivin viimeinen sarake, suljetaan kirjoittaja
                    if(i == sokkelo.length-1 && j == sokkelo.length-1){
                        kirjoittaja.close();
                    }
                    // muuten tulostetaan rivinvaihto
                    else {
                        kirjoittaja.println();
                    }
                }
            }
            kirjoittaja.close();
        }
            // Napataan poikkeus, jos jotain menee pieleen teidostoon tallentamisessa
            catch(IOException e){
                System.out.println(e);
                System.out.println(VIRHE);
            }

    }

    /** Metodi, jolla keskeytetään peliä pyörittävä while-operaatio ja lopetetaan peli. */
    public void lopeta(){
        kartta();
        System.out.println(LOPETUS);
        System.exit(0);
    }

    /**
     * Metodi, jossa selvitetään nokkimisjärjestys kahden liikkuvan objektin välillä,
     * jotka annetaan metodille parametreinä. Objektit laitetaan paremmuusjärjestykseen niiden energian perusteella.
     *
     * @param a mönkijä, joka verrataan robottiin
     * @param b robotti, jota verrataan mönkijään
     * @return vertailussa suuremman energian omaava olio, eli taistelun voittaja
     */
    public LiikkuvaObjekti taistele(LiikkuvaObjekti a, LiikkuvaObjekti b){
        if(a.energia() >= b.energia()){
            // Miinustetaan robotin sisältämä energia mönkijän energiasta, jos mönkijä voittaa.
            a.energia(a.energia() - b.energia());
            return a;
        }
        else
            return b;
    }

    /**
     * Metodi, jossa liikutetaan robottia Automaaatti-apuluokan avulla. Robotti-listalla säilytetään pelin kaikkia
     * robotteja. Metodi liikuttaa niistä jokaista pelin vuorolla. Jos satutaan samaan paikkaan mönkijän kanssa,
     * taistellaan sitä vastaan.
     *
     * @param robotit lista- jossa säilötään tiedot kaikista pelissä aktiivista roboteista
     */
    public void roboLiiku(OmaLista robotit) {

        // lippumuuttuja, joka on true, jos lisäämiseen vaaditut ehdot täyttyvät
        boolean voiLisata = true;
        // lippumuuttuja, jos robotti voitti mahdollisen taistelun mönkijää vastaan
        boolean robottiVoitti = false;

        // For-looppi, joka käy läpi robotit- listan kaikki alkiot, eli pelissä aktiiviset robotit ja poistaa ne sokkelosta
        for (int i = 0; i < robotit.koko(); i++) {

            // Asetetaan aluksi käsiteltävä robotti apuolioon, joka tullaan poistamaan listalta ennen liikuttelua
            Robotti poistettavaRobotti = (Robotti) robotit.alkio(i);

            int riv = poistettavaRobotti.rivi();
            int sar = poistettavaRobotti.sarake();

            Kaytava apuKaytava;
            apuKaytava = (Kaytava) sokkelo[riv][sar];
            apuKaytava.lista().poista(poistettavaRobotti);
        }
            // Päivitetään robottien paikat looppien välissä
            Automaatti.paivitaPaikat(robotit, sokkelo);

        // for-looppi, joka käy läpi robotit-listan kaikki alkiot ja lisää ne takaisin sokkeloon päivitettyjen paikkojen mukaan
        // Ennen lisäystä tarkistetaan, onko tulevassa paikassa mönkijä. Jos on, taistellaan. Mikäli usempi robotti
        // On liikkumassa mönkijän sijaintiin ,taistelevat robotit mönkijää vastaan yksi kerrallaan toistensa jälkeen.
        for (int i = 0; i < robotit.koko(); i++) {
            // Lippumuuttujat takaisin alkuperäiseen tilaan uutta luuppia varten
            voiLisata = true;
            robottiVoitti = false;

            Kaytava apuKaytava;
            Robotti uusiRobotti = (Robotti) robotit.alkio(i);
            int uusiRiv = uusiRobotti.rivi();
            int uusiSar = uusiRobotti.sarake();

            apuKaytava = (Kaytava) sokkelo[uusiRiv][uusiSar];

            // Tarksitetaan tässä vaiheessa robotin tuleva paikka. Jos paikalla on mönkijä, robotti taistelee
            // sitä vastaan.
            try{
                for (int j = 0; j < apuKaytava.lista().koko(); j++) {
                    LiikkuvaObjekti objekti = (LiikkuvaObjekti) apuKaytava.lista().alkio(j);

                    if (objekti instanceof Monkija) {

                        // Kutsutaan taistele-metodia, joka selvittää, voittaako mönkijä vai robotti
                        LiikkuvaObjekti voittaja = taistele(objekti, uusiRobotti);

                        // Jos voittaja on mönkijä, asetetaan lippumuttuja, eikä lisätä uutta robottia listalle.
                        // poistetaan samalla robotti aktiivisten robottien listalta.
                        if (voittaja instanceof Monkija) {
                            voiLisata = false;
                            robotit.poista(uusiRobotti);
                            System.out.println(VOITTO);
                            i--;
                        }

                        //Voittajaksi selvisi robotti.Asetetaan lippumuttuja, että mönkijää voi liikuttaa.
                        // tuhotaan mönkijä ja lopetetaan peli.
                        else {
                            apuKaytava = (Kaytava) sokkelo[monkija.rivi()][monkija.sarake()];
                            apuKaytava.lista().poista(monkija);
                            robottiVoitti = true;
                            voiLisata = true;
                        }
                    }
                    // Ei tarvittu taistella. voidaan liikkua.
                    else
                        voiLisata = true;
                }
            }
            // Napataan virhe, mikäli jotain odottamatonta sattuu.
            catch(Exception e){
                System.out.println(VIRHE);
            }

            // Paikka on kunnossa, eli robotti voidaan lisätä uudelle paikalle. Mikäli ollaan taisteltu mönkijää vastaan
            // ja lisättävä robotti voitti taistelun, siirretään robotti mönkijän paikalle ja lopetetaan peli.
            if( voiLisata) {
                apuKaytava.lista().lisaa(uusiRobotti);

                if(robottiVoitti){
                    System.out.println(TAPPIO);
                    lopeta();
                }
            }
        }
    }
}
