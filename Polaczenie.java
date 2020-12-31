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

  int checkPasswd(String login, String password){
    try { 
      //  Wykonanie zapytania SELSECT do bazy danych
      //  Wykorzystane elementy: prepareStatement(), executeQuery()
            //wydruk rekordow zawartych w zwroconym kursorze  ( zbior rekordow - ResultSet )
       PreparedStatement pst = c.prepareStatement("SELECT uzyt_id, haslo FROM projekt.panel WHERE login = \'" + login+"\'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
       String haslo;
      //  while (rs.next())  {
            rs.next();//
            haslo = rs.getString("haslo") ;
            int id = Integer.parseInt(rs.getString("uzyt_id"));
            // String nazwisko    = rs.getString("lname") ;
            // System.out.print("Zwrocone kolumny  ");
            // System.out.println(imie+" "+nazwisko) ;
            // System.out.println(imie) ;
            // my_record = "Zwrocone kolumny  " + imie;
          //  }
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

  public String zarejestruj(String imie, String nazwisko, String email, String telefon, String login, String haslo){
      try { 
        PreparedStatement exist_prepare = c.prepareStatement("SELECT COUNT(*) AS exist FROM projekt.panel WHERE login = \'"+login+"\'",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
        ResultSet exist_result = exist_prepare.executeQuery();
        exist_result.next();
        int exist = Integer.parseInt(exist_result.getString("exist"));
        exist_result.close();
        exist_prepare.close();
        if(exist==0){
          PreparedStatement counted_id = c.prepareStatement("SELECT COUNT(uzyt_id) AS id FROM projekt.panel",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
          ResultSet result_count_id = counted_id.executeQuery();
          result_count_id.next();
          String string_id = result_count_id.getString("id");
          int id = Integer.parseInt(string_id);
          id++;
          result_count_id.close();
          counted_id.close();

          // PreparedStatement pst1 = c.prepareStatement("INSERT INTO projekt.uzytkownik VALUES("+id+",\'"+imie+"\',\'"+nazwisko+"\',\'"+email+"\',"+Integer.parseInt(telefon)+" )");
          PreparedStatement pst1 = c.prepareStatement("INSERT INTO projekt.uzytkownik VALUES("+id+",\'"+imie+"\',\'"+nazwisko+"\',\'"+email+"\',\'"+telefon+"\' )");
          int ok = pst1.executeUpdate();
          pst1.close();    
          if(ok != 0){
            PreparedStatement pst2 = c.prepareStatement("INSERT INTO projekt.panel VALUES("+id+", \'"+login+"\',\'"+haslo+"\')");

            pst2.executeUpdate();
            pst2.close();
            return "Pomyslnie zarejestrowano uzytkownika!";
          }else{
            return "Prosze wprowadzic poprawne dane!";
          }
        }else{
          return "Uzytkownik o podanym loginie juz istnieje!";
        }

      }
     catch(SQLException e)  {
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return "";
        }
  }
  public ArrayList<ArrayList<String>> getTableUzytkownicy(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.uzytkownicy",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID:");
      tmp.add("LOGIN:");
      tmp.add("HASLO:");
      tmp.add("IMIE:");
      tmp.add("NAZWISKO:");
      tmp.add("E-MAIL:");
      tmp.add("TELEFON:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("uzyt_id"));
            tmp.add(rs.getString("login"));
            tmp.add(rs.getString("haslo"));
            tmp.add(rs.getString("imie"));
            tmp.add(rs.getString("nazwisko"));
            tmp.add(rs.getString("e_mail"));
            tmp.add(rs.getString("numer"));
            records.add(tmp);
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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

  public ArrayList<ArrayList<String>> getTablePokoje(){
      ArrayList<ArrayList<String>> records = new ArrayList<>();
      ArrayList<String> tmp = new ArrayList<>();
      try { 

       PreparedStatement pst = c.prepareStatement("SELECT * FROM projekt.pokojeView",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
       ResultSet rs = pst.executeQuery();
      tmp.add("ID POKOJU:");
      tmp.add("NUMER POKOJU:");
      tmp.add("PIETRO:");
      tmp.add("LICZBA MIEJSC:");
      tmp.add("ID KATEGORII:");
      tmp.add("NAZWA KATEGORII:");
      tmp.add("CENA OD OSOBY:");
      records.add(tmp);

      while (rs.next())  {
            tmp = new ArrayList<>();
            tmp.add(rs.getString("pokoj_id"));
            tmp.add(rs.getString("numer_pokoju"));
            tmp.add(rs.getString("pietro"));
            tmp.add(rs.getString("liczba_miejsc"));
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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }
  
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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }
 
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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return records;
      }
  }

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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return -1;
      }
  }

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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return lista_pokoi;
      }
  }

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
          System.out.println("Blad podczas przetwarzania danych:"+e) ;
          return info;
      }
  }
}