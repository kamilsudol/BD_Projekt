import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GetMojeRezerwacjeInfo
 * Pomocnicza klasa przechowujaca wszystkie
 * informacje na temat danej rezerwacji.
 */

public class GetMojeRezerwacjeInfo{
    public  int rezerwacja_id;
    public  int uzytkownik_id;
    public  int pokoj_id;
    public  String od_kiedy;
    public  String do_kiedy;
    public  String data_rezerwacji;
    public  int liczba_dzieci;
    public  int liczba_doroslych;
    public  int uslugi_id;
    public  String uslugi_nazwa;
    public  double uslugi_cena;
    public  int oplata_id;
    public  String status;
    public  double kwota;
    public  int numer_pokoju;
    public  int pietro;
    public  int liczba_miejsc;
    public  String kategoria;
    public  int pokoj_cena;
    public  Boolean flag;

    /**
     * Konstruktor ustawiajacy odpowiednie wartosci.
     * @param r_id
     * @param u_id
     * @param p_id
     * @param start
     * @param stop
     * @param when
     * @param dzieci
     * @param dorosli
     * @param us_id
     * @param u_nazw
     * @param u_cena
     * @param o_id
     * @param stat
     * @param cena_calosc
     * @param p_nr
     * @param p_p
     * @param p_lm
     * @param kat
     * @param p_cen
     */

    public GetMojeRezerwacjeInfo(int r_id, int u_id, int p_id, String start, String stop, String when, int dzieci, int dorosli, int us_id, String u_nazw, double u_cena, int o_id, String stat, double cena_calosc, int p_nr, int p_p, int p_lm, String kat, int p_cen){
        rezerwacja_id = r_id;
        uzytkownik_id = u_id;
        pokoj_id = p_id;
        od_kiedy = start;
        do_kiedy = stop;
        data_rezerwacji = when;
        liczba_dzieci = dzieci;
        liczba_doroslych = dorosli;
        uslugi_id = us_id;
        uslugi_nazwa = u_nazw;
        uslugi_cena = u_cena;
        oplata_id = o_id;
        status = stat;
        kwota = cena_calosc;
        numer_pokoju = p_nr;
        pietro = p_p;
        liczba_miejsc = p_lm;
        kategoria = kat;
        pokoj_cena = p_cen;
        flag = true;
    }

    /**
     * Pomocniczy konstruktor.
     * @param f
     */

    public GetMojeRezerwacjeInfo(Boolean f){
        flag = f;
        rezerwacja_id = 0;
        uzytkownik_id = 0;
        pokoj_id = 0;
        od_kiedy = "";
        do_kiedy = "";
        data_rezerwacji = "";
        liczba_dzieci = 0;
        liczba_doroslych = 0;
        uslugi_id = 0;
        uslugi_nazwa = "";
        uslugi_cena = 0;
        oplata_id = -1;
        status = "Brak rezerwacji.";
        kwota = 0;
        numer_pokoju = 0;
        pietro = 0;
        liczba_miejsc = 0;
        kategoria = "";
        pokoj_cena = 0;
    }

    /**
     * Konstruktor dla "pustego" obiektu.
     * @param s
     */

    public GetMojeRezerwacjeInfo(String s){
        this(false);
        status = s;
        oplata_id = -1;
    }

    public String toString(){
        if(flag){
            return "Data zakwaterowania:   " + od_kiedy + "   Data wykwaterowania:   " + do_kiedy + "   Kwota:   " + kwota + "   Status:   "+ status;
        }else{
            return status;
        }
    }

    /**
     * Metoda ralizujaca wyswietlenie wszystkich informacji.
     * @param panel
     */

    public void info(JPanel panel){
        panel.add(new JLabel("Informacje o rezerwacji:", SwingConstants.CENTER));
        panel.add(new JLabel("Data zlozenia rezerwacji:   "+data_rezerwacji+"   Data zakwaterowania:   "+od_kiedy+"   Data wykwaterowania:   "+do_kiedy+"   Liczba doroslych:   "+String.valueOf(liczba_doroslych)+"   Liczba dzieci:   "+String.valueOf(liczba_dzieci)));

        panel.add(new JLabel("Informacje o wybranym pokoju:", SwingConstants.CENTER));
        panel.add(new JLabel("Nazwa kategorii:   "+kategoria+"   Pietro:   "+String.valueOf(pietro)+"   Numer pokoju:   "+String.valueOf(numer_pokoju)+"   Maksymalna liczba miejsc:   "+String.valueOf(liczba_miejsc)+"   Cena od osoby:   "+String.valueOf(pokoj_cena)+ " zl"));

        panel.add(new JLabel("Informacje o dodatkowych uslugach:", SwingConstants.CENTER));
        panel.add(new JLabel("Nazwy uslug:   "+uslugi_nazwa+"   Cena od osoby:   "+String.valueOf(uslugi_cena)+ " zl"));

        panel.add(new JLabel("Informacje o platnosci:", SwingConstants.CENTER));
        panel.add(new JLabel("Status:   "+status+"   Kwota:   "+String.valueOf(kwota) + " zl"));

    }
}