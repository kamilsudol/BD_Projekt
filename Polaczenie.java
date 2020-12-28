import java.sql.*;
import java.util.*;

public class Polaczenie {
    public String my_record;
    public Connection c;
    public Polaczenie(){

  //   public static void main(String[] argv) {
    /*
    System.out.println("Sprawdzenie czy sterownik jest zarejestrowany w menadzerze");
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException cnfe) {
      System.out.println("Nie znaleziono sterownika!");
      System.out.println("Wyduk sledzenia bledu i zakonczenie.");
      cnfe.printStackTrace();
      System.exit(1);
    }
    System.out.println("Zarejstrowano sterownik - OK, kolejny krok nawiazanie polaczenia z baza danych.");
    */
    c = null;
      
    try {
      // Wymagane parametry polaczenia z baza danych:
      // Pierwszy - URL do bazy danych:
      //        jdbc:dialekt SQL:serwer(adres + port)/baza w naszym przypadku:
      //        jdbc:postgres://pascal.fis.agh.edu.pl:5432/baza
      // Drugi i trzeci parametr: uzytkownik bazy i haslo do bazy 
      c = DriverManager.getConnection("jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u8sudol",
                                      "u8sudol", "8sudol");
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

  String getStr(){
      return my_record;
  }

  Boolean checkPasswd(String login, String password){
    try { 
      //  Wykonanie zapytania SELSECT do bazy danych
      //  Wykorzystane elementy: prepareStatement(), executeQuery()
            //wydruk rekordow zawartych w zwroconym kursorze  ( zbior rekordow - ResultSet )
       PreparedStatement pst = c.prepareStatement("SELECT haslo FROM projekt.panel WHERE login = \'" + login+"\'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       String haslo;
      //  while (rs.next())  {
            rs.next();//
            haslo = rs.getString("haslo") ;
            // String nazwisko    = rs.getString("lname") ;
            // System.out.print("Zwrocone kolumny  ");
            // System.out.println(imie+" "+nazwisko) ;
            // System.out.println(imie) ;
            // my_record = "Zwrocone kolumny  " + imie;
          //  }
       rs.close();
       pst.close();    
      return password.equals(haslo);
      }
     catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return false;
        }
  }

  public String zarejestruj(String imie, String nazwisko, String email, String telefon, String login, String haslo){
      try { 
        PreparedStatement exist_prepare = c.prepareStatement("SELECT COUNT(*) AS exist FROM projekt.panel WHERE login = \'"+login+"\'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet exist_result = exist_prepare.executeQuery();
        exist_result.next();
        int exist = Integer.parseInt(exist_result.getString("exist"));
        exist_result.close();
        exist_prepare.close();
        if(exist==0){
          PreparedStatement counted_id = c.prepareStatement("SELECT MAX(uzyt_id) AS id FROM projekt.panel",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
          ResultSet result_count_id = counted_id.executeQuery();
          result_count_id.next();
          String string_id = result_count_id.getString("id");
          int id = Integer.parseInt(string_id);
          id++;
          result_count_id.close();
          counted_id.close();

          PreparedStatement pst1 = c.prepareStatement("INSERT INTO projekt.uzytkownik VALUES("+id+",\'"+imie+"\',\'"+nazwisko+"\',\'"+email+"\',"+Integer.parseInt(telefon)+" )");
          pst1.executeUpdate();
          pst1.close();    
          PreparedStatement pst2 = c.prepareStatement("INSERT INTO projekt.panel VALUES("+id+", \'"+login+"\',\'"+haslo+"\')");

          pst2.executeUpdate();
          pst2.close();
          return "Pomyslnie zarejestrowano uzytkownika!";
        }else{
          return "Uzytkownik o podanym loginie juz istnieje!";
        }

      }
     catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return "";
        }
  }
  public ArrayList<String> getTableUzytkownicy(){
      ArrayList<String> uzytkownicy_records = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.uzytkownik u JOIN projekt.panel p ON u.uzytkownik_id = p.uzyt_id",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       uzytkownicy_records.add("ID  |  LOGIN  |  HASLO  |  IMIE  |  NAZWISKO  |  E-MAIL  |  TELEFON");
      while (rs.next())  {
            uzytkownicy_records.add(rs.getString("uzyt_id")+"   "+rs.getString("login")+"   "+rs.getString("haslo")+"   "+rs.getString("imie")+"   "+rs.getString("nazwisko")+"   "+rs.getString("e_mail")+"   "+rs.getString("numer"));
      }
       rs.close();
       pst.close();    
      return uzytkownicy_records;
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return uzytkownicy_records;
      }
  }

  public ArrayList<String> getTableRezerwacje(){
      ArrayList<String> rezerwacje_records = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.rezerwacje",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       rezerwacje_records.add("ID REZERWACJI  |  ID UZYTKOWNIKA  |  ID POKOJU  |  DATA REZERWACJI  |  OD KIEDY  |  DO KIEDY  |  LICZBA DZIECI   |   LICZBA DOROSLYCH");
      while (rs.next())  {
            rezerwacje_records.add(rs.getString("rezerwacja_id")+"   "+rs.getString("uzytkownik_id")+"   "+rs.getString("pokoj_id")+"   "+rs.getString("data_rezerwacji")+"   "+rs.getString("od_kiedy")+"   "+rs.getString("do_kiedy")+"   "+rs.getString("liczba_dzieci")+"   "+rs.getString("liczba_doroslych"));
      }
       rs.close();
       pst.close();    
      return rezerwacje_records;
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return rezerwacje_records;
      }
  }

  public ArrayList<String> getTableOplaty(){
      ArrayList<String> records = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.oplata",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       records.add("ID OPLATA  |  ID REZERWACJI  |  STATUS  |  KWOTA");
      while (rs.next())  {
            records.add(rs.getString("oplata_id")+"   "+rs.getString("rezerwacja_id")+"   "+rs.getString("status_czy_oplacone")+"   "+rs.getString("kwota"));
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

  public ArrayList<String> getTableUslugi(){
      ArrayList<String> records = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.dodatkowe_uslugi",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       records.add("ID USLUGI  |  NAZWA USLUGI  |  CENA OD OSOBY  |  ID REZERWACJI");
      while (rs.next())  {
            records.add(rs.getString("dodatkowe_uslugi_id")+"   "+rs.getString("nazwa_uslugi")+"   "+rs.getString("cena_od_osoby")+"   "+rs.getString("rezerwacja_id"));
      }
       rs.close();
       pst.close();    
      return records;
      }
      catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }
}