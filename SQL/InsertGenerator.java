import java.util.*;
import java.io.*;

public class InsertGenerator {
    private final String file;
    private ArrayList<String> inserts;
    private final ArrayList<String> imiona = new ArrayList<>(List.of("Kamil", "Jan", "Sylwia", "Agnieszka", "Anna", "Terasa", "Tomasz"));
    private final ArrayList<String> nazwiska = new ArrayList<>(List.of("Kowalski", "Wisniewski", "Kutyla", "Abacki", "Babacki", "Kabacki", "Leszcz", "Okon"));
    private final ArrayList<String> uslugi = new ArrayList<>(List.of("Kregielnia", "Basen", "Bilard", "Silownia", "Brak"));
    private final HashMap<String, Double> uslugi_cennik = new HashMap<>(){{
        put("Kregielnia", 25.0);
        put("Silownia", 5.0);
        put("Basen", 15.0);
        put("Bilard", 10.0);
        put("Brak", 0.0);
    }};
    private final int pokoje_max = 5;
    InsertGenerator(String file_path){
        file = file_path;
        inserts = new ArrayList<>();
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public String getRandomName(){
        return imiona.get(getRandomNumber(0, imiona.size()));
    }

    public String getRandomSurname(){
        return nazwiska.get(getRandomNumber(0, nazwiska.size()));
    }

    public String getRandomUsluga(){
        return uslugi.get(getRandomNumber(0, uslugi.size()));
    }

    void generujRezerwacje(int x){
        for(int i = 0; i < x; i++){
            int adult = getRandomNumber(0, pokoje_max+1);
            inserts.add("BEGIN;\n");
            inserts.add("INSERT INTO projekt.rezerwacje(\"uzytkownik_id\",\"pokoj_id\",\"data_rezerwacji\",\"od_kiedy\",\"do_kiedy\",\"liczba_dzieci\",\"liczba_doroslych\") VALUES("+getRandomNumber(0,20)+", "+getRandomNumber(0,20)+",NOW(),"+resolveDate()+", "+adult+","+getRandomNumber(0, pokoje_max - adult + 1)+");\n");
            inserts.add("INSERT INTO projekt.dodatkowe_uslugi(\"nazwa_uslugi\", \"cena_od_osoby\", \"rezerwacja_id\") VALUES("+resolveUslugi()+", latest_rezerwacja_id());\n");
            inserts.add("COMMIT;\n");
            inserts.add("\n");
        }
    }

    void generujUzytkownikow(int x){
        for(int i = 0; i < x; i++){
            inserts.add("INSERT INTO \"uzytkownik\"(\"imie\",\"nazwisko\",\"e_mail\",\"numer\",\"typ\") VALUES ('"+getRandomName()+"', '"+getRandomSurname()+"', 'mail"+i+"@gmail.COM', "+getRandomNumber(500000000,999999999)+", 'Klient');\n");
            inserts.add("INSERT INTO \"panel\" VALUES (latest_uzytkownik_id(), 'uzytkownik"+i+"', 'haslo"+i+"');\n");
            inserts.add("\n");
        }
    }

    void save(){
        try{
            File f1 = new File(file);
            try {
                f1.createNewFile();
            } catch (IOException e) {
                System.out.println("Blad podczas tworzenia nowego pliku.");
            }
            PrintWriter zapis = new PrintWriter(file);
            for(int i = 0; i < inserts.size(); i++){
                zapis.print(inserts.get(i));
            }
            zapis.close();
        }catch(FileNotFoundException e){
            System.out.println("Wczytywany plik o podanej nazwie nie istnieje!");
            System.exit(1);
        }
    }

    String resolveUslugi(){
        String what = getRandomUsluga();
        return "\'"+what+"\', " + uslugi_cennik.get(what);
    }

    String resolveDate(){
        int year_start = getRandomNumber(2020, 2022);
        int month_start = getRandomNumber(1,13);
        int day_start = getRandomNumber(1, resolveDays(month_start, year_start));

        int year_end = getRandomNumber(year_start, year_start+2);
        int month_end= getRandomNumber(month_start,13);
        int day_end = getRandomNumber(day_start, resolveDays(month_end, year_end));
        String start = year_start+"-"+month_start+"-"+day_start;
        String end = year_end+"-"+month_end+"-"+day_end;
        return "CAST('"+start+"' AS DATE),CAST('"+end+"' AS DATE)";
    }

    public int resolveDays(int i, int rok){
        int x = 0;
        switch(i){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                x = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                x = 30;
                break;
            case 2:
                if(((rok % 4 == 0) && !(rok % 100 == 0)) || (rok % 400 == 0)){
                    x = 29;
                }else{
                    x = 28;
                }
                break;
        }
        return x;
    }

    public static void main(String[] args){
//        InsertGenerator uzyt = new InsertGenerator("uzytkownicy.sql");
//        uzyt.generujUzytkownikow(20);
//        uzyt.save();

        InsertGenerator rez = new InsertGenerator("rezerwacje.sql");
        rez.generujRezerwacje(30);
        rez.save();
    }
}
