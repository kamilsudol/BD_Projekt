import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Raport_Pokoje
 * Klasa realizujaca podglad raportu o pokojach
 */

public class GUI_Raport_Pokoje {
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladUpPanel;
    public JPanel podgladPanel;
    public JPanel podgladButtonPanel;
    public JButton podgladButton;
    public ArrayList<ArrayList<String>> records;
    public ArrayList<String> atrybuty;
    public ArrayList<String> countable;
    public JButton resetujButton;
    public JCheckBox groupCheck;
    public JComboBox<ComboInsert> groupBox;
    public JCheckBox varCheck;
    public JTextField varField;
    public JCheckBox sortCheck;
    public JComboBox<ComboInsert> sortWhatBox;
    public JCheckBox sortHowBox;
    public JButton pokaz1;

    public JLabel searchLabel;
    public JComboBox<ComboInsert> searchBox;
    public JLabel textLabel;
    public JTextField searchField;
    public JCheckBox searchSortCheck;
    public JComboBox<ComboInsert> searchSortBox;
    public JCheckBox searchSortHowBox;
    public JButton pokaz2;

    /**
     * Konstruktor domyslny przyjmujacy polaczenie do bazy, okno glowne aplikacji
     * oraz id aktualnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */

    public GUI_Raport_Pokoje(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        resolve();
        podgladUpPanel = new JPanel();
        wypelnijGornyPanel1();
        wypelnijGornyPanel2();
        wypelnijCentralnyPanel(records);
        wypelnijDolnyPanel();

        initialInserts();
    }

    /**
     * Metoda wypelniajaca srodkowy panel wyswietlajac dane z bazy.
     * @param rec
     */

    public void wypelnij(ArrayList<ArrayList<String>> rec){
        String tmp;
        for(int i = 0; i < rec.size(); i++){
            for(int j = 0; j <rec.get(i).size(); j++){
                tmp = rec.get(i).get(j);
                if(i==0){
                    JLabel t = new JLabel(tmp);
                    t.setFont(new Font("Arial", Font.PLAIN, 17));
                    podgladPanel.add(t);
                }else{
                    podgladPanel.add(new JLabel(tmp));
                }
            }
        }
    }

    /**
     * Metoda wypelniajaca funkcjonalnoscia dolna czesc okna aplikacji.
     */

    public void wypelnijDolnyPanel(){
        podgladButtonPanel = new JPanel();
        podgladButtonPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        podgladButtonPanel.setLayout(new GridLayout(0,1));

        resetujButton = new JButton("Resetuj podglad"); //Przycisk realizujacy przywrocenie wygladu domyslnego
        resetujButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                reset();
            }
        });
        podgladButtonPanel.add(resetujButton);

        podgladButton = new JButton("Powrot do podgladu raportow");//Przycisk realizujacy powrot do menu podlgadu.
        podgladButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Raport m = new GUI_Raport(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow");
                mainWindow.frame.validate();
            }
        });
        podgladButtonPanel.add(podgladButton);
    }

    /**
     * Metoda wypelniajaca funkcjonalnoscia gorna czesc okna aplikacji.
     */

    public void wypelnijGornyPanel1(){//Sekcja realizujaca zapytanie GROUP BY
        podgladUpPanel.removeAll();
        podgladUpPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        podgladUpPanel.setLayout(new GridLayout(0,8));

        groupCheck = new JCheckBox("Zlicz elementy po: ");
        groupCheck.setSelected(false);
        groupCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(groupCheck.isSelected()){
                    groupBox.setEnabled(true);
                }else{
                    groupBox.setEnabled(false);
                }
            }
        });
        podgladUpPanel.add(groupCheck);

        groupBox = new JComboBox<>();
        groupBox.setEnabled(false);
        groupBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(groupBox.getItemCount() != 0){
                    Object item = groupBox.getSelectedItem();
                    clearDropList(sortWhatBox);
                    updateDropList(((ComboInsert)item).getName());
                }
            }
        });
        podgladUpPanel.add(groupBox);

        varCheck = new JCheckBox("po konkretnej wartosci: ");
        varCheck.setSelected(false);
        varCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(varCheck.isSelected()){
                    varField.setEnabled(true);
                }else{
                    varField.setEnabled(false);
                }
            }
        });
        podgladUpPanel.add(varCheck);

        varField = new JTextField(255);
        varField.setEnabled(false);
        podgladUpPanel.add(varField);

        sortCheck = new JCheckBox("Sortuj po: ");
        sortCheck.setSelected(false);
        sortCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(sortCheck.isSelected()){
                    sortWhatBox.setEnabled(true);
                    sortHowBox.setEnabled(true);
                }else{
                    sortWhatBox.setEnabled(false);
                    sortHowBox.setEnabled(false);
                }
            }
        });
        podgladUpPanel.add(sortCheck);

        sortWhatBox = new JComboBox<>();
        sortWhatBox.setEnabled(false);
        podgladUpPanel.add(sortWhatBox);

        sortHowBox = new JCheckBox("malejaco");
        sortHowBox.setSelected(false);
        podgladUpPanel.add(sortHowBox);

        pokaz1 = new JButton("Pokaz");
        pokaz1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(groupCheck.isSelected()){
                    resloveQuery1();
                }
            }
        });
        podgladUpPanel.add(pokaz1);
    }

    public void wypelnijGornyPanel2(){//Selcka realizujaca zapytanie wyszukujace.
        for(int i = 0; i < 8; i++){
            podgladUpPanel.add(new JLabel(""));
        }
        searchLabel = new JLabel("Wyszukaj rekord dla: ");
        podgladUpPanel.add(searchLabel);

        searchBox = new JComboBox<>();
        podgladUpPanel.add(searchBox);

        textLabel = new JLabel("   o wartosci: ");
        podgladUpPanel.add(textLabel);

        searchField = new JTextField(255);
        podgladUpPanel.add(searchField);

        searchSortCheck = new JCheckBox("Sortuj po: ");
        searchSortCheck.setSelected(false);
        searchSortCheck.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(searchSortCheck.isSelected()){
                    searchSortBox.setEnabled(true);
                    searchSortHowBox.setEnabled(true);
                }else{
                    searchSortBox.setEnabled(false);
                    searchSortHowBox.setEnabled(false);
                }
            }
        });
        podgladUpPanel.add(searchSortCheck);

        searchSortBox = new JComboBox<>();
        podgladUpPanel.add(searchSortBox);

        searchSortHowBox = new JCheckBox("malejaco");
        searchSortHowBox.setSelected(false);
        podgladUpPanel.add(searchSortHowBox);

        pokaz2 = new JButton("Pokaz");
        pokaz2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(searchField.getText() != ""){
                    resloveQuery2();
                }
            }
        });
        podgladUpPanel.add(pokaz2);

    }

    /**
     * Metoda inicjalizujaca srodkowy panel.
     * @param rec
     */

    public void wypelnijCentralnyPanel(ArrayList<ArrayList<String>> rec){
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,rec.get(0).size()));
        wypelnij(rec);
    }

    /**
     * Metoda wyznaczajaca potrzebne dla raportu elementy.
     */

    public void resolve(){
        RaportWrapper wartosci = a.getRaportPokoje();
        records = wartosci.calosc;
        atrybuty = wartosci.atrybuty;
        countable = wartosci.countable;
    }

    /**
     * Metoda wypelniajaca wskazany JComboBox
     * @param droplista
     * @param elementy
     */

    public void wypelnijDropList(JComboBox<ComboInsert> droplista, ArrayList<String> elementy){
        for(int i = 0; i < elementy.size(); i++){
            droplista.addItem(new ComboInsert(elementy.get(i), i));
        }
    }

    /**
     * Pomocnicza metoda wypelniajaca wszystkie droplisty.
     */

    public void initialInserts(){
        clearDropList(sortWhatBox);
        clearDropList(groupBox);
        clearDropList(searchBox);
        clearDropList(searchSortBox);
        wypelnijDropList(groupBox, countable);
        wypelnijDropList(searchBox, atrybuty);
        wypelnijDropList(searchSortBox, atrybuty);
    }

    /**
     * Metoda czyszczaca zawartosc JComboBox
     * @param x
     */

    public void clearDropList(JComboBox<ComboInsert> x){
        if(x.getItemCount()!=0){
            for(int i=x.getItemCount()-1;i>-1;i--){
                x.removeItemAt(i);
            }
        }
    }

    /**
     * Metoda wypelniajaca dropliste dla sortowania przy GROUP BY
     * @param s
     */

    public void updateDropList(String s){
        sortWhatBox.addItem(new ComboInsert(s, 0));
        sortWhatBox.addItem(new ComboInsert("zlicz", 1));
    }

    /**
     * Metoda tworzaca zapytanie GROUP BY
     */

    public void resloveQuery1(){
        Object item = groupBox.getSelectedItem();
        String nam = ((ComboInsert)item).getName();

        String query = "SELECT "+nam+", COUNT("+nam+") AS zlicz FROM projekt.pokojeView GROUP BY " + nam;
        if(varCheck.isSelected()){
            query+=" HAVING CAST(" + nam +" AS TEXT) LIKE \'%"+varField.getText() +"%\'";
        }
        if(sortCheck.isSelected()){
            item = sortWhatBox.getSelectedItem();
            String what = ((ComboInsert)item).getName();
            query += " ORDER BY "+what;
        }
        if(sortHowBox.isSelected()){
            query += " DESC";
        }
        aktualizujCentralnyPanel(a.executeRaportQuery1(query, nam));
    }

    /**
     * Metoda tworzaca zapytanie wyszukujace
     */

    public void resloveQuery2(){
        Object item = searchBox.getSelectedItem();
        String nam = ((ComboInsert)item).getName();

        String what = searchField.getText();

        String query = "SELECT * FROM projekt.pokojeView WHERE "+nam+" = \'" + what + "\'";

        if(searchSortCheck.isSelected()){
            item = searchSortBox.getSelectedItem();
            query += " ORDER BY " + ((ComboInsert)item).getName();
        }

        if(searchSortHowBox.isSelected()){
            query += " DESC";
        }
        aktualizujCentralnyPanel(a.pokojeExecuteRaportQuery2(query));
    }

    /**
     * Metoda przywracajaca domyslne wartosci.
     */

    public void reset(){
        wypelnijGornyPanel1();
        wypelnijGornyPanel2();
        initialInserts();
        podgladUpPanel.validate();
        aktualizujCentralnyPanel(records);
    }

    /**
     * Pomocnicza metoda wypelniajaca srodkowy panel podanymi danymi.
     * @param rec
     */

    public void aktualizujCentralnyPanel(ArrayList<ArrayList<String>> rec){
        podgladPanel.removeAll();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,rec.get(0).size()));
        wypelnij(rec);
        podgladPanel.validate();
    }
}