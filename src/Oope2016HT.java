/** -----------------------
 *  Tampereen Yliopisto
 *  OOPE2016
 *  Miikka Mäki
 *  mm96932
 *  Oope2016HT
 *  9.5.2016
 *  -----------------------
 *  
 * Java-kielellä toimiva tekstipohjainen sokkelopeli.
 * 
 * Toteutetaan javalla peli, missä pelaajan ohjaama mönkijä
 * seikkailee pitkin sokkeloa. Tehtävänä on kerätä mönkijän varastoon
 * kaikki sokkelossa olevat esineet samalla vältellen robotteja,
 * jotka toimivat pelin vihollisina. Joskus pelaajan ohjaama mönkijä
 * joutuu myös taistelemaan robotteja vastaan. Sokkelo koostuu seinistä
 * ja käytävistä. Mönkijä ja robotit voivat liikkua käytäviä pitkin.
 * Myös esineet voivat ilmestyä käytävälle.
 * 
 * Peli on toteutettu luokkahierarkialla, jossa peliobjektit perivät
 * yläluokkansa attribuutteja riippuen niiden tehtävistä ja toiminnoista.
 * Tietorakenteena käytetään LinkitettyLista-luokasta perittyä OmaLista-luokkaa.
 * 
 * Oope2016HT-luokka toimii pelin pääluokkana, johon sisältyy pelin main-
 * metodi ja itse pelin pyöritys. Peli loppuu, kun pelaaja on kerännyt kaikki
 * esineet sokkelosta, häviää taistelussa robotille tai lopettaa pelin
 * erillisellä komennolla.
 *
 */
import com.ht.sokkelo.*;
import java.io.IOException;

public class Oope2016HT {
    public static void main(String[] args) throws IOException {
        Kayttoliittyma kayttoliittyma = new Kayttoliittyma();
        kayttoliittyma.suorita();
    }
}
