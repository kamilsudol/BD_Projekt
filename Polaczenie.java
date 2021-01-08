import java.sql.*;
import java.util.*;

/**
 * Klasa Polaczenie
 * Klasa ta realizuje wszelkie polaczenia z baza danych.
 */

public class Polaczenie {
    public String my_record;
    public Connection c;

    /**
     * Konstruktor domyslny laczacy sie z baza.
     */

    public Polaczenie(){
    c = null;
    try {
      c = DriverManager.getConnection("jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u8sudol", "u8sudol", "8sudol");
    } catch (SQLException se) {
      System.out.println("Brak polaczenia z baza danych, wydruk logu sledzenia i koniec.");
      se.printStackTrace();
      System.exit(1);
    }
    if (c != null) {
      System.out.println("Polaczenie z baza danych OK ! ");
    }else{
        System.out.println("Brak polaczenia z baza, dalsza czesc aplikacji nie jest wykonywana.");   
    }
      
  }

    /**
     * Metoda sprawdzajaca dane logowania, w przypadku powodzenia zwracajaca id uzytkownika.
     * @param login
     * @param password
     * @return
     */

  int checkPasswd(String login, String password){
    try {
       PreparedStatement pst = c.prepareStatement("SELECT uzyt_id, haslo FROM projekt.panel WHERE login = \'" + login+"\'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       String haslo;
            rs.next();
            haslo = rs.getString("haslo") ;
            int id = Integer.parseInt(rs.getString("uzyt_id"));
       rs.close();
       pst.close();    
      if(password.equals(haslo)){
        return id;
      }else{
        return -1;
      }
      }
     catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return -1;
        }
  }

    /**
     * Metoda realizujaca procedure rejestracji uzytkownika jako transakcje. W przypadku niepowowodzenia
     * wywolywany jest ROLLBACK oraz wypisywany jest powod niepowodzenia.
     * @param imie
     * @param nazwisko
     * @param email
     * @param telefon
     * @param login
     * @param haslo
     * @param typ
     * @return
     */

  public String zarejestruj(String imie, String nazwisko, String email, String telefon, String login, String haslo, String typ){
    try {
//      PreparedStatement counted_id = c.prepareStatement("SELECT COUNT(uzyt_id) AS id FROM projekt.panel",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
//      ResultSet result_count_id = counted_id.executeQuery();
//      result_count_id.next();
//      String string_id = result_count_id.getString("id");
//      int id = Integer.parseInt(string_id);
//      id++;
//      result_count_id.close();
//      counted_id.close();

      PreparedStatement pst1 = c.prepareStatement("BEGIN");
      PreparedStatement pst2 = c.prepareStatement("INSERT INTO projekt.uzytkownik(\"imie\",\"nazwisko\",\"e_mail\",\"numer\",\"typ\") VALUES(\'"+imie+"\',\'"+nazwisko+"\',\'"+email+"\',\'"+telefon+"\',\'"+typ+"\' )");
      PreparedStatement pst3 = c.prepareStatement("INSERT INTO projekt.panel VALUES(projekt.latest_uzytkownik_id(), \'"+login+"\',\'"+haslo+"\')");
      PreparedStatement pst4 = c.prepareStatement("COMMIT");

      pst1.executeUpdate();
      pst2.executeUpdate();
      pst3.executeUpdate();
      pst4.executeUpdate();
      
      pst1.close();  
      pst2.close();  
      pst3.close();  
      pst4.close();

      return "Pomyslnie dodano nowego uzytkownika!";
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          try{
            PreparedStatement pst5 = c.prepareStatement("ROLLBACK");
            pst5.executeUpdate();
            pst5.close();
          }catch(SQLException d){
            System.out.println("Blad podczas przetwarzania danych:"+d) ;
            return "Blad przy dodawaniu nowego uzytkownika!";
          }
          String powod = e.getMessage();
          String[] powod_tab = powod.split("[||]");
          return "Blad przy dodawaniu nowego uzytkownika! " + powod_tab[2];
      }
  }

  /**
     * Metoda zwracajaca zawartosc tabeli panel
     * @return
     */

    public ArrayList<ArrayList<String>> getTablePanel(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.panel",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID:");
      tmp.add("LOGIN:");
      tmp.add("HASLO:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("uzyt_id"));
            tmp.add(rs.getString("login"));
            tmp.add(rs.getString("haslo"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli uzytkownik
     * @return
     */

  public ArrayList<ArrayList<String>> getTableUzytkownicy(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.uzytkownik",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID:");
      tmp.add("IMIE:");
      tmp.add("NAZWISKO:");
      tmp.add("E-MAIL:");
      tmp.add("TELEFON:");
      tmp.add("TYP KONTA:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("uzytkownik_id"));
            tmp.add(rs.getString("imie"));
            tmp.add(rs.getString("nazwisko"));
            tmp.add(rs.getString("e_mail"));
            tmp.add(rs.getString("numer"));
            tmp.add(rs.getString("typ"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli rezerwacje.
     * @return
     */

  public ArrayList<ArrayList<String>> getTableRezerwacje(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.rezerwacje",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID REZERWACJI:");
      tmp.add("ID UZYTKOWNIKA:");
      tmp.add("ID POKOJU:");
      tmp.add("DATA REZERWACJI:");
      tmp.add("OD KIEDY:");
      tmp.add("DO KIEDY:");
      tmp.add("LICZBA DZIECI:");
      tmp.add("LICZBA DOROSLYCH:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("rezerwacja_id"));
            tmp.add(rs.getString("uzytkownik_id"));
            tmp.add(rs.getString("pokoj_id"));
            tmp.add(rs.getString("data_rezerwacji"));
            tmp.add(rs.getString("od_kiedy"));
            tmp.add(rs.getString("do_kiedy"));
            tmp.add(rs.getString("liczba_dzieci"));
            tmp.add(rs.getString("liczba_doroslych"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli oplaty.
     * @return
     */

  public ArrayList<ArrayList<String>> getTableOplaty(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.oplata",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID OPLATY:");
      tmp.add("ID REZERWACJI:");
      tmp.add("STATUS:");
      tmp.add("KWOTA:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("oplata_id"));
            tmp.add(rs.getString("rezerwacja_id"));
            tmp.add(rs.getString("status_czy_oplacone"));
            tmp.add(rs.getString("kwota"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli dodatkowe_uslugi_info
     * @return
     */

  public ArrayList<ArrayList<String>> getTableUslugi(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.dodatkowe_uslugi",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID USLUGI:");
      tmp.add("ID REZERWACJI:");
      tmp.add("NAZWA USLUGI:");
      tmp.add("CENA OD OSOBY:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("dodatkowe_uslugi_id"));
            tmp.add(rs.getString("rezerwacja_id"));
            tmp.add(rs.getString("nazwa_uslugi"));
            tmp.add(rs.getString("cena_od_osoby"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }


    /**
     * Metoda zwracajaca zawartosc tabeli kategoria.
     * @return
     */

    public ArrayList<ArrayList<String>> getTableKategoria(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.kategoria",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID KATEGORII:");
      tmp.add("NAZWA KATEGORII:");
      tmp.add("CENA OD OSOBY:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("kategoria_id"));
            tmp.add(rs.getString("nazwa_kategorii"));
            tmp.add(rs.getString("cena_od_osoby"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli pokoje.
     * @return
     */

  public ArrayList<ArrayList<String>> getTablePokoje(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.pokoj",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID POKOJU:");
      tmp.add("NUMER POKOJU:");
      tmp.add("PIETRO:");
      tmp.add("LICZBA MIEJSC:");
      tmp.add("ID KATEGORII:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("pokoj_id"));
            tmp.add(rs.getString("numer_pokoju"));
            tmp.add(rs.getString("pietro"));
            tmp.add(rs.getString("liczba_miejsc"));
            tmp.add(rs.getString("kategoria_id"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

  /**
     * Metoda zwracajaca zawartosc tabeli rezygnacja_z_rezerwacji_info.
     * @return
     */
  
    public ArrayList<ArrayList<String>> getTableRezygnacja(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.rezygnacja_z_rezerwacji_info",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID INFORMACJI:");
      tmp.add("ID REZERWACJI:");
      tmp.add("ID UZYTKOWNIKA:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("info_id"));
            tmp.add(rs.getString("rezerwacja_id"));
            tmp.add(rs.getString("uzytkownik_id"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli zakwaterowani_goscie_info.
     * @return
     */
  
  public ArrayList<ArrayList<String>> getTableZakwaterowani(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.zakwaterowani_goscie_info",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID INFORMACJI:");
      tmp.add("ID REZERWACJI:");
      tmp.add("STATUS:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("info_id"));
            tmp.add(rs.getString("rezerwacja_id"));
            tmp.add(rs.getString("status_czy_zakwaterowany"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca zawartosc tabeli czarna_lista.
     * @return
     */
 
  public ArrayList<ArrayList<String>> getTableBlacklist(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.czarna_lista",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID INFORMACJI:");
      tmp.add("ID UZYTKOWNIKA:");
      tmp.add("POWOD:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("info_id"));
            tmp.add(rs.getString("uzytkownik_id"));
            tmp.add(rs.getString("powod"));
            records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

    /**
     * Metoda zwracajaca maksymalna liczbe miejsc sposrod wszystkich pokoi.
     * @return
     */

  public int getMaxPeople(){
    try { 
      PreparedStatement pst = c.prepareStatement("SELECT MAX(liczba_miejsc) AS max FROM projekt.pokoj",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      int max = -1;
      while (rs.next())  {
            max = Integer.parseInt(rs.getString("max"));;
      }
      rs.close();
      pst.close();    
      return max;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return -1;
      }
  }

    /**
     * Metoda zwracajaca informacje na temat wolnych pokoi w podanym terminie.
     * @param liczba_osob
     * @param od_kiedy
     * @param do_kiedy
     * @return
     */

  public ArrayList<Integer> getWolnePokoje(int liczba_osob, String od_kiedy, String do_kiedy){
    ArrayList<Integer> lista_pokoi = new ArrayList<>();
    try { 
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.get_pokoje(" + liczba_osob+",'"+od_kiedy+"','"+do_kiedy+"')",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      while (rs.next())  {
            lista_pokoi.add(Integer.parseInt(rs.getString("id_pokoju")));
      }
      rs.close();
      pst.close();    
      return lista_pokoi;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return lista_pokoi;
      }
  }

    /**
     * Metoda zwracajaca informacje pokoju o zadanym id.
     * @param id
     * @return
     */

  public ComboRoomInsert getPokojInfo(int id){
    ComboRoomInsert info = new ComboRoomInsert();
    try { 
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.pokojeView WHERE pokoj_id = " + id,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();

      while (rs.next())  {
            int pokoj_id = Integer.parseInt(rs.getString("pokoj_id"));
            int cena = Integer.parseInt(rs.getString("cena_od_osoby"));
            String kategoria = rs.getString("nazwa_kategorii");
            int numer_pokoju = Integer.parseInt(rs.getString("numer_pokoju"));
            int pietro = Integer.parseInt(rs.getString("pietro"));
            int miejsca = Integer.parseInt(rs.getString("liczba_miejsc"));
            info.setNew(pokoj_id, pietro, numer_pokoju, kategoria, cena, miejsca);
      }
      rs.close();
      pst.close();    
      return info;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return info;
      }
  }

    /**
     * Metoda wysylajaca zapytanie do bazy w celu ustalenia dlugosci okresu pobytu w hotelu.
     * @param od_kiedy
     * @param do_kiedy
     * @return
     */

  public int okres_zakwaterowania(String od_kiedy, String do_kiedy){
    int a = -1;
    try { 
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.get_liczba_dni('"+od_kiedy+"','"+do_kiedy+"')",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      while (rs.next())  {
            a = Integer.parseInt(rs.getString("get_liczba_dni"));
      }
      rs.close();
      pst.close();    
      return a;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return a;
      }
  }

    /**
     * Metoda realizujaca proces zatwierdzenia rezerwacji jako transakcja. W przypadku niepowodzenia wyswietlany jest powod
     * oraz wywolywany jest ROLLBACK.
     * @param uzyt_id
     * @param pokoj_id
     * @param adult_people
     * @param kiddo_people
     * @param start
     * @param stop
     * @param calosc
     * @return
     */

  public String dokonaj_rezerwacji(int uzyt_id, int pokoj_id, int adult_people, int kiddo_people, String start, String stop, GetUslugi calosc){
    try { 
      PreparedStatement pst1 = c.prepareStatement("BEGIN");
      PreparedStatement pst2 = c.prepareStatement("INSERT INTO projekt.rezerwacje(\"uzytkownik_id\",\"pokoj_id\",\"data_rezerwacji\",\"od_kiedy\",\"do_kiedy\",\"liczba_dzieci\",\"liczba_doroslych\") VALUES("+uzyt_id+","+pokoj_id+",NOW(),\'"+start+"\',\'"+stop+"\',"+kiddo_people+","+adult_people+")");
      PreparedStatement pst3 = c.prepareStatement("INSERT INTO projekt.dodatkowe_uslugi(\"nazwa_uslugi\", \"cena_od_osoby\", \"rezerwacja_id\") VALUES(\'"+calosc.toString()+"\',"+calosc.getCena()+", projekt.latest_rezerwacja_id())");
      PreparedStatement pst4 = c.prepareStatement("COMMIT");

      pst1.executeUpdate();
      pst2.executeUpdate();
      pst3.executeUpdate();
      pst4.executeUpdate();
      
      pst1.close();  
      pst2.close();  
      pst3.close();  
      pst4.close();
      return "Pomyslnie dodano rezerwacje!";
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          try{
            PreparedStatement pst5 = c.prepareStatement("ROLLBACK");
            pst5.executeUpdate();
            pst5.close();
          }catch(SQLException d){
           System.out.println("Blad podczas przetwarzania danych:"+d) ;
            return "Blad przy dodawaniu rezerwacji!";
          }
          String powod = e.getMessage();
          String[] powod_tab = powod.split("[||]");
          return "Blad przy dodawaniu rezerwacji! " + powod_tab[2];
      }
  }

    /**
     * Metoda zwracajaca informacje na temat wszystkich rezerwacji zlozonych przez uzytkownika o podanym id.
     * @param id
     * @return
     */

  public ArrayList<GetMojeRezerwacjeInfo> getMojeRezerwacjeInfo(int id){
      try { 
        ArrayList<GetMojeRezerwacjeInfo> a = new ArrayList<>();
        PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.RezerwacjeInfoView WHERE uzytkownik_id = " + id,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = pst.executeQuery();
        while (rs.next())  {
            int rezerwacja_id = Integer.parseInt(rs.getString("rezerwacja_id"));
            int uzytkownik_id = Integer.parseInt(rs.getString("uzytkownik_id"));
            int pokoj_id = Integer.parseInt(rs.getString("pokoj_id"));
            String od_kiedy = rs.getString("od_kiedy");
            String do_kiedy = rs.getString("do_kiedy");
            String data_rezerwacji = rs.getString("data_rezerwacji");
            int liczba_dzieci = Integer.parseInt(rs.getString("liczba_dzieci"));
            int liczba_doroslych = Integer.parseInt(rs.getString("liczba_doroslych"));
            int uslugi_id = Integer.parseInt(rs.getString("dodatkowe_uslugi_id"));
            String uslugi_nazwa = rs.getString("nazwa_uslugi");
            double uslugi_cena = Double.parseDouble(rs.getString("cena_uslugi"));
            int oplata_id = Integer.parseInt(rs.getString("oplata_id"));
            String status = rs.getString("status_czy_oplacone");
            double kwota = Double.parseDouble(rs.getString("kwota"));
            int numer_pokoju = Integer.parseInt(rs.getString("numer_pokoju"));
            int pietro = Integer.parseInt(rs.getString("pietro"));
            int liczba_miejsc = Integer.parseInt(rs.getString("liczba_miejsc"));
            String kategoria = rs.getString("nazwa_kategorii");
            int pokoj_cena = Integer.parseInt(rs.getString("cena_od_osoby"));
            a.add(new GetMojeRezerwacjeInfo(rezerwacja_id,uzytkownik_id,pokoj_id,od_kiedy,do_kiedy,data_rezerwacji,liczba_dzieci,liczba_doroslych, uslugi_id, uslugi_nazwa, uslugi_cena,oplata_id,status, kwota, numer_pokoju, pietro,liczba_miejsc, kategoria, pokoj_cena));
        }
        rs.close();
        pst.close();    
        return a;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return new ArrayList<GetMojeRezerwacjeInfo>();
      }
  }

    /**
     * Metoda realizujaca oplate rezerwacji.
     * @param id
     * @return
     */

  public String oplacRezerwacje(int id){
    try{
      PreparedStatement pst1 = c.prepareStatement("SELECT projekt.oplataZaplac("+id+")");
      // pst1.executeUpdate();
      pst1.executeQuery();
      pst1.close();  
      
      return "Pomyslnie oplacono rezerwacje!";
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          String powod = e.getMessage();
          String[] powod_tab = powod.split("[||]");
          return "Blad przy oplacaniu rezerwacji! " +powod_tab[2];
      }
  }

    /**
     * Metoda realizujaca rezygnacje z rezerwacji.
     * @param id
     * @return
     */

  public String rezygnujRezerwacje(int id){
    try{
      PreparedStatement pst1 = c.prepareStatement("SELECT projekt.oplataRezygnuj("+id+")");
      pst1.executeQuery();
      pst1.close();  
      
      return "Pomyslnie zrezygnowano z rezerwacji!";
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          String powod = e.getMessage();
          String[] powod_tab = powod.split("[||]");
          return "Blad przy rezygnacji z rezerwacji! " +powod_tab[2];
      }
  }

    /**
     * Metoda wysylajaca zapytanie do bazy odnosnie danych personalnych aktualnie zalogowanego uzytkownika.
     * @param id
     * @return
     */

  public String getUserName(int id){
    try { 
      PreparedStatement pst = c.prepareStatement("SELECT imie ||\' \'|| nazwisko AS result FROM projekt.uzytkownik WHERE uzytkownik_id="+id,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      String dane = "";
      while (rs.next())  {
            dane = rs.getString("result");
      }
      rs.close();
      pst.close();    
      return dane;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return "";
      }
  }

    /**
     * Metoda sprawdzajaca, czy uzytkownik o podanym id zostal zablokowany.
     * @param id
     * @return
     */

  public Boolean czyZbanowany(int id){
    try { 
      int a = -1;
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.ZbanowanyCheck("+id+")",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      while (rs.next())  {
            a = Integer.parseInt(rs.getString("ZbanowanyCheck"));
      }
      rs.close();
      pst.close();    
      return a != 0;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return false;
      }
  }

    /**
     * Metoda zwracajaca powod zablokowania uzytkownika o podanym id.
     * @param id
     * @return
     */

  public String getPowod(int id){
    try { 
      String powod = "";
      PreparedStatement pst = c.prepareStatement("SELECT powod FROM projekt.czarna_lista WHERE uzytkownik_id = "+id,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      while (rs.next())  {
            powod = rs.getString("powod");
      }
      rs.close();
      pst.close();    
      return powod;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return "";
      }
  }

    /**
     * Metoda wywolujaca aktualizacje bazy przy starcie aplikacji.
     */

  public void BazaUpdate(){
    try { 
      PreparedStatement pst1 = c.prepareStatement("SELECT projekt.StartUpdate()");
      pst1.executeQuery();
      pst1.close();      
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
      }
  }

    /**
     * Metoda realizujaca procez blokady uzytkownika.
     * @param id
     * @param reason
     * @return
     */

  public String Zbanuj(int id, String reason){
    try { 
      PreparedStatement pst = c.prepareStatement("INSERT INTO projekt.czarna_lista(\"uzytkownik_id\",\"powod\") VALUES("+id+",\'"+reason+"\')");
      pst.executeUpdate();
      pst.close();  
      return "Pomyslnie zablokowano uzytkownika!";
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          String powod = e.getMessage();
          String[] powod_tab = powod.split("[||]");
          return "Blad przy probie zablokowania uzytkownika! " + powod_tab[2];
      }
  }

    /**
     * Metoda sprawdzajaca, czy uzytkownik o padanym id jest administratorem hotelu.
     * @param id
     * @return
     */

  public int czyAdmin(int id){
    int a = -1;
    try { 
    PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.Autoryzacja("+id+")",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      while (rs.next())  {
            a = Integer.parseInt(rs.getString("Autoryzacja"));
      }
      rs.close();
      pst.close();    
      return a;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return -1;
      }
  }

    /**
     * Metoda zwracajaca informacje o uzytkownikach, ktorzy nie zostali zablokowani.
     * @return
     */

  public ArrayList<ComboUserInsert> getUsers(){
    try {
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.uzytkownik u WHERE (SELECT uzytkownik_id FROM projekt.czarna_lista WHERE uzytkownik_id = u.uzytkownik_id) IS NULL",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      ArrayList<ComboUserInsert> records = new ArrayList<>();

      while (rs.next())  {
            String id = rs.getString("uzytkownik_id");
            String im = rs.getString("imie");
            String naz = rs.getString("nazwisko");
            String typ = rs.getString("typ");
            records.add(new ComboUserInsert(Integer.parseInt(id), im, naz, typ));
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
//          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return new ArrayList<ComboUserInsert>();
      }
  }

    /**
     * Metoda zwracajaca wartosci dla podlgadu raportu o uzytkownikach.
     * @return
     */

  public RaportWrapper getRaportUzytkownicy(){
    try {
      PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.uzytkownicy",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp;
      ArrayList<String> atrybuty = new ArrayList<>(Arrays.asList("uzyt_id","login","haslo", "imie", "nazwisko","e_mail","numer","typ"));
      ArrayList<String> grupowalne = new ArrayList<>(Arrays.asList("imie", "nazwisko", "typ"));

      records.add(atrybuty);

      while (rs.next())  {
          tmp = new ArrayList<>();
          tmp.add(rs.getString("uzyt_id"));
          tmp.add(rs.getString("login"));
          tmp.add(rs.getString("haslo"));
          tmp.add(rs.getString("imie"));
          tmp.add(rs.getString("nazwisko"));
          tmp.add(rs.getString("e_mail"));
          tmp.add(rs.getString("numer"));
          tmp.add(rs.getString("typ"));
          records.add(tmp);
      }
       rs.close();
       pst.close();    
      return new RaportWrapper(records, atrybuty, grupowalne, null, null);
      }
      catch(SQLException e)  {
         System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return new RaportWrapper();
      }
  }

    /**
     * Metoda egzekwujaca pierwsze zapytanie z raportu o uzytkownikach.
     * @param query
     * @param nam
     * @return
     */

  ArrayList<ArrayList<String>> uzytkownicyExecuteRaportQuery1(String query, String nam){
    try {
      PreparedStatement pst = c.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp;
      ArrayList<String> atrybuty = new ArrayList<>(Arrays.asList(nam.toUpperCase(), "ZLICZ"));

      records.add(atrybuty);

      while (rs.next())  {
          tmp = new ArrayList<>();
          tmp.add(rs.getString(nam));
          tmp.add(rs.getString("zlicz"));
          records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
         System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return new ArrayList<ArrayList<String>>();
      }
  }

    /**
     * Metoda egzekwujaca drugie zapytanie z raportu o uzytkownikach.
     * @param query
     * @param nam
     * @return
     */

  ArrayList<ArrayList<String>> uzytkownicyExecuteRaportQuery2(String query){
    try {
      PreparedStatement pst = c.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
      ResultSet rs = pst.executeQuery();
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp;
      ArrayList<String> atrybuty = new ArrayList<>(Arrays.asList("UZYT_ID","LOGIN","HASLO", "IMIE", "NAZWISKO","E_MAIL","NUMER","TYP"));

      records.add(atrybuty);

      while (rs.next())  {
          tmp = new ArrayList<>();
          tmp.add(rs.getString("uzyt_id"));
          tmp.add(rs.getString("login"));
          tmp.add(rs.getString("haslo"));
          tmp.add(rs.getString("imie"));
          tmp.add(rs.getString("nazwisko"));
          tmp.add(rs.getString("e_mail"));
          tmp.add(rs.getString("numer"));
          tmp.add(rs.getString("typ"));
          records.add(tmp);
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
         System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return new ArrayList<ArrayList<String>>();
      }
  }
}