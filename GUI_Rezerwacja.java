import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class GUI_Rezerwacja{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel rezerwacjaPanel;
    public JButton zatwierdzButton;
    public JButton menuButton;
    public JLabel pokojLabel1;
    public JLabel pokojLabel2;
    public JLabel liczbaDoroslychLabel;
    public JLabel liczbaDzieciLabel;
    public JLabel kwotaLabel;
    public JLabel uslugiLabel;
    public JLabel odKiedyLabel;
    public JLabel doKiedyLabel;
    public double kwota_z_pokoi;
    public double kwota_z_uslug;

    public JComboBox<ComboInsert> day_odKiedyDropList;
    public JComboBox<ComboInsert> month_odKiedyDropList;
    public JComboBox<ComboInsert> year_odKiedyDropList;
    public JComboBox<ComboInsert> day_doKiedyDropList;
    public JComboBox<ComboInsert> month_doKiedyDropList;
    public JComboBox<ComboInsert> year_doKiedyDropList;

    private int current_day;
    private int current_month;
    private int current_year;

    private int chosen_start_year;
    private int chosen_start_month;
    private int chosen_start_day;

    private int chosen_end_year;
    private int chosen_end_month;
    private int chosen_end_day;

    static public GetMiesiac miesiac;

    public JComboBox<ComboInsert> dorosliDropList;
    public JComboBox<ComboInsert> dzieciDropList;
    private int chosen_adult_people;
    private int chosen_kiddo_people;

    private int max_people;

    public JCheckBox uslugaBasen;
    public JCheckBox uslugaKregielnia;
    public JCheckBox uslugaSilownia;
    public JCheckBox uslugaBilard;

    public JComboBox<ComboRoomInsert> pokojeDropList;

    private int chosen_pokoj_liczba_miejsc;
    private int chosen_pokoj_id;
    private int chosen_pokoj_cena;

    Boolean data_flag;
    Boolean osoby_flag;
    Boolean pokoj_flag;

    private String start_rezerwacji;
    private String stop_rezerwacji;

    public GetUslugi silownia;
    public GetUslugi basen;
    public GetUslugi bilard;
    public GetUslugi kregielnia;
    public GetUslugi calosc;

    public GUI_Rezerwacja(Polaczenie p, GUI_Login mainWindow, int id){
        silownia = new GetUslugi("Silownia ", 5);
        kregielnia = new GetUslugi("Kregielnia ", 25);
        bilard = new GetUslugi("Bilard ", 10);
        basen = new GetUslugi("Basen ", 15);

        data_flag = false;
        osoby_flag = false;
        pokoj_flag = false;

        miesiac = new GetMiesiac();
        ustal_aktualna_date();

        kwota_z_pokoi = 0;
        kwota_z_uslug = 0;

        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        max_people = a.getMaxPeople();

        rezerwacjaPanel = new JPanel();
        rezerwacjaPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        rezerwacjaPanel.setLayout(new GridLayout(0,1));

        odKiedyLabel = new JLabel("Prosze wybrac termin zakwaterowania (nie wczesniej, niz 7 dni od dnia dzisiejszego):");
        rezerwacjaPanel.add(odKiedyLabel);

        year_odKiedyDropList = new JComboBox<>();
        year_odKiedyDropList.addItem(new ComboInsert("Prosze wybrac rok", -1));
        yearDropListFill(year_odKiedyDropList, current_year);
        year_odKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = year_odKiedyDropList.getSelectedItem();
                chosen_start_year = ((ComboInsert)item).getVal();
                if(chosen_start_year != -1){
                    month_odKiedyDropList.setEnabled(true);
                    myClear(month_odKiedyDropList);
                    myClear(day_odKiedyDropList);
                    monthDropListFill(month_odKiedyDropList, current_month, chosen_start_year, current_year);
                }else{
                    myClear(month_odKiedyDropList);
                    month_odKiedyDropList.setEnabled(false);
                    myClear(day_odKiedyDropList);
                    day_odKiedyDropList.setEnabled(false);
                }
            }
        });
        rezerwacjaPanel.add(year_odKiedyDropList);

        month_odKiedyDropList = new JComboBox<>();
        month_odKiedyDropList.addItem(new ComboInsert("Prosze wybrac miesiac", -1));
        month_odKiedyDropList.setEnabled(false);
        month_odKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = month_odKiedyDropList.getSelectedItem();
                chosen_start_month = ((ComboInsert)item).getVal();
                if(chosen_start_month != -1){
                    day_odKiedyDropList.setEnabled(true);
                    myClear(day_odKiedyDropList);
                    dayDropListFill(day_odKiedyDropList, current_day, current_month, chosen_start_month, current_year, chosen_start_year);
                }else{
                    myClear(day_odKiedyDropList);
                    day_odKiedyDropList.setEnabled(false);
                }
            }
        });
        rezerwacjaPanel.add(month_odKiedyDropList);

        day_odKiedyDropList = new JComboBox<>();
        day_odKiedyDropList.addItem(new ComboInsert("Prosze wybrac dzien", -1));
        day_odKiedyDropList.setEnabled(false);
        day_odKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = day_odKiedyDropList.getSelectedItem();
                chosen_start_day = ((ComboInsert)item).getVal();
                if(chosen_start_day != -1){
                    myClear(year_doKiedyDropList);
                    year_doKiedyDropList.setEnabled(true);
                    yearDropListFill(year_doKiedyDropList, chosen_start_year);
                }else{
                    myClear(year_doKiedyDropList);
                    myClear(month_doKiedyDropList);
                    myClear(day_doKiedyDropList);
                    year_doKiedyDropList.setEnabled(false);
                    month_doKiedyDropList.setEnabled(false);
                    day_doKiedyDropList.setEnabled(false);
                }

            }
        });
        rezerwacjaPanel.add(day_odKiedyDropList);

        doKiedyLabel = new JLabel("Prosze wybrac termin wykwaterowania:");
        rezerwacjaPanel.add(doKiedyLabel);

        year_doKiedyDropList = new JComboBox<>();
        year_doKiedyDropList.addItem(new ComboInsert("Prosze wybrac rok", -1));
        year_doKiedyDropList.setEnabled(false);
        year_doKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = year_doKiedyDropList.getSelectedItem();
                chosen_end_year = ((ComboInsert)item).getVal();
                if(chosen_end_year != -1){
                    month_doKiedyDropList.setEnabled(true);
                    myClear(month_doKiedyDropList);
                    myClear(day_doKiedyDropList);
                    monthDropListFill(month_doKiedyDropList, chosen_start_month, chosen_start_year, chosen_end_year);
                }else{
                    myClear(month_doKiedyDropList);
                    month_doKiedyDropList.setEnabled(false);
                    myClear(day_doKiedyDropList);
                    day_doKiedyDropList.setEnabled(false);
                }
            }
        });
        rezerwacjaPanel.add(year_doKiedyDropList);

        month_doKiedyDropList = new JComboBox<>();
        month_doKiedyDropList.addItem(new ComboInsert("Prosze wybrac miesiac", -1));
        month_doKiedyDropList.setEnabled(false);
        month_doKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = month_doKiedyDropList.getSelectedItem();
                chosen_end_month = ((ComboInsert)item).getVal();
                if(chosen_end_month != -1){
                    day_doKiedyDropList.setEnabled(true);
                    myClear(day_doKiedyDropList);
                    dayDropListFill(day_doKiedyDropList, chosen_start_day, chosen_start_month,chosen_end_month,chosen_start_year, chosen_end_year);
                }else{
                    myClear(day_doKiedyDropList);
                    day_doKiedyDropList.setEnabled(false);
                }
            }
        });
        rezerwacjaPanel.add(month_doKiedyDropList);

        day_doKiedyDropList = new JComboBox<>();
        day_doKiedyDropList.addItem(new ComboInsert("Prosze wybrac dzien", -1));
        day_doKiedyDropList.setEnabled(false);
        day_doKiedyDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = day_doKiedyDropList.getSelectedItem();
                chosen_end_day = ((ComboInsert)item).getVal();
                data_flag = chosen_end_day != -1;
                if(data_flag){
                    przedzialRezerwacjiCompute();
                    pokojeDropListCheck();
                }else{
                    przedzialRezerwacjiReset();
                    pokojeDropListCheck();
                }
            }
        });
        rezerwacjaPanel.add(day_doKiedyDropList);

        liczbaDoroslychLabel = new JLabel("Prosze podac liczbe doroslych (100% ceny od osoby):");
        rezerwacjaPanel.add(liczbaDoroslychLabel);

        dorosliDropList = new JComboBox<>();
        dorosliDropList.addItem(new ComboInsert("Maksymalna dostepna liczba osob na pokoj: " + String.valueOf(max_people), -1));
        fillPeopleDropList(dorosliDropList, 0);
        dorosliDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = dorosliDropList.getSelectedItem();
                chosen_adult_people = ((ComboInsert)item).getVal();
                if(chosen_adult_people != -1){
                    dzieciDropList.setEnabled(true);
                    dzieciDropList.insertItemAt(new ComboInsert("Pozostala dostepna liczba osob na pokoj: " + String.valueOf(max_people - chosen_adult_people), -1), 0);
                    myClear(dzieciDropList);
                    fillPeopleDropList(dzieciDropList, chosen_adult_people);
                    uslugiReset();
                    kwotaReset();
                }else{
                    myClear(dzieciDropList);
                    dzieciDropList.setEnabled(false);
                    uslugiReset();
                    kwotaReset();
                }
            }
        });
        rezerwacjaPanel.add(dorosliDropList);

        liczbaDzieciLabel = new JLabel("Prosze podac liczbe dzieci (50% ceny od osoby):");
        rezerwacjaPanel.add(liczbaDzieciLabel);

        dzieciDropList = new JComboBox<>();
        dzieciDropList.addItem(new ComboInsert("Pozostala dostepna liczba osob na pokoj: " + String.valueOf(max_people - chosen_adult_people), -1));
        dzieciDropList.setEnabled(false);
        dzieciDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = dzieciDropList.getSelectedItem();
                chosen_kiddo_people = ((ComboInsert)item).getVal();
                uslugiReset();
                kwotaReset();
                osoby_flag = chosen_kiddo_people != -1;
                if(osoby_flag){
                    przedzialRezerwacjiCompute();
                    pokojeDropListCheck();
                }else{
                    przedzialRezerwacjiReset();
                    pokojeDropListCheck();
                }
                
            }
        });
        rezerwacjaPanel.add(dzieciDropList);

        pokojLabel1 = new JLabel("Prosze wybrac jeden z dostepnych pokoi (w przypadku wyboru pokoju z wieksza liczba miejsc,");
        pokojLabel2 = new JLabel("niz zadeklarowana, wowczas pobrana bedzie oplata wysokosci 25% kwoty za pokoj od kazdego nadmiarowego miejsca):");
        rezerwacjaPanel.add(pokojLabel1);
        rezerwacjaPanel.add(pokojLabel2);

        pokojeDropList = new JComboBox<>();
        pokojeDropList.addItem(new ComboRoomInsert("-----"));
        pokojeDropList.setEnabled(false);
        pokojeDropList.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = pokojeDropList.getSelectedItem();
                chosen_pokoj_id = ((ComboRoomInsert)item).getId();
                chosen_pokoj_liczba_miejsc = ((ComboRoomInsert)item).getMiejsca();
                chosen_pokoj_cena = ((ComboRoomInsert)item).getCena();
                if(chosen_pokoj_id != -1){
                    pokoj_flag = true;
                    kwotaPokojeCompute();
                    kwotaUpdate();
                }else{
                    pokoj_flag = false;
                    kwotaReset();
                }
            }
        });
        rezerwacjaPanel.add(pokojeDropList);

        uslugiLabel = new JLabel("Prosze wybrac dodatkowe uslugi (opcjonalnie):");
        rezerwacjaPanel.add(uslugiLabel);

        uslugaSilownia = new JCheckBox("Dostep do silowni - koszt 5 zl od osoby (w przypadku dzieci obowiazuje ulga 50%)");
        uslugaSilownia.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(uslugaSilownia.isSelected() && chosen_adult_people != -1){
                    kwota_z_uslug+= chosen_adult_people * 5 + chosen_kiddo_people * 2.5;
                    silownia.setVis(true);
                    kwotaUpdate();
                }else{
                    kwota_z_uslug-= chosen_adult_people * 5 + chosen_kiddo_people * 2.5;
                    silownia.setVis(false);
                    kwotaUpdate();
                }
            }
        });
        rezerwacjaPanel.add(uslugaSilownia);

        uslugaBilard = new JCheckBox("Dostep do bilardu - koszt 10 zl od osoby (w przypadku dzieci obowiazuje ulga 50%)");
        uslugaBilard.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(uslugaBilard.isSelected() && chosen_adult_people != -1 && chosen_kiddo_people != -1){
                    kwota_z_uslug+= chosen_adult_people * 10 + chosen_kiddo_people * 5;
                    bilard.setVis(true);
                    kwotaUpdate();
                }else{
                    kwota_z_uslug-= chosen_adult_people * 10 + chosen_kiddo_people * 5;
                    bilard.setVis(false);
                    kwotaUpdate();
                }
            }
        });
        rezerwacjaPanel.add(uslugaBilard);

        uslugaBasen = new JCheckBox("Dostep do basenu - koszt 15 zl od osoby (w przypadku dzieci obowiazuje ulga 50%)");
        uslugaBasen.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(uslugaBasen.isSelected() && chosen_adult_people != -1 && chosen_kiddo_people != -1){
                    kwota_z_uslug+= chosen_adult_people * 15 + chosen_kiddo_people * 7.5;
                    basen.setVis(true);
                    kwotaUpdate();
                }else{
                    kwota_z_uslug-= chosen_adult_people * 15 + chosen_kiddo_people * 7.5;
                    basen.setVis(false);
                    kwotaUpdate();
                }
            }
        });
        rezerwacjaPanel.add(uslugaBasen);

        uslugaKregielnia = new JCheckBox("Dostep do kregielni - koszt 25 zl od osoby (w przypadku dzieci obowiazuje ulga 50%)");
        uslugaKregielnia.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(uslugaKregielnia.isSelected() && chosen_adult_people != -1 && chosen_kiddo_people != -1){
                    kwota_z_uslug+= chosen_adult_people * 25 + chosen_kiddo_people * 12.5;
                    kregielnia.setVis(true);
                    kwotaUpdate();
                }else{
                    kwota_z_uslug-= chosen_adult_people * 25 + chosen_kiddo_people * 12.5;
                    kregielnia.setVis(false);
                    kwotaUpdate();
                }
            }
        });
        rezerwacjaPanel.add(uslugaKregielnia);

        kwotaLabel = new JLabel("Calkowity koszt: "+(kwota_z_uslug+kwota_z_pokoi) + " zl");
        rezerwacjaPanel.add(kwotaLabel);

        zatwierdzButton = new JButton("Potwierdz rezerwacje");
        zatwierdzButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(data_flag && osoby_flag && pokoj_flag){
                    computeUslugi();
                    String st = a.dokonaj_rezerwacji(login_id, chosen_pokoj_id, chosen_adult_people, chosen_kiddo_people, start_rezerwacji, stop_rezerwacji, calosc);
                    GUI_Rezerwacja_Result m = new GUI_Rezerwacja_Result(a, mainWindow, login_id, st);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Rezerwacja");
                    mainWindow.frame.validate();
                }else{
                    System.out.println("Prosze uzupelnic wszystkie dane!");
                }
            }
        });
        rezerwacjaPanel.add(zatwierdzButton);

        menuButton = new JButton("Powrot do menu");
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu m = new GUI_Menu(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        rezerwacjaPanel.add(menuButton);
    }

    public void kwotaUpdate(){
        kwotaLabel.setText("Calkowity koszt: " + (kwota_z_uslug+kwota_z_pokoi) + " zl");
    }

    public void kwotaPokojeCompute(){
        int liczba_dni = okresPobytu();
        // System.out.println("Liczba dni:" + liczba_dni);
        if(chosen_adult_people == 0 && chosen_kiddo_people == 0){
            kwota_z_pokoi = 0;
        }else{
            kwota_z_pokoi = liczba_dni*chosen_pokoj_cena*(chosen_adult_people + 0.5*chosen_kiddo_people + 0.25*(chosen_pokoj_liczba_miejsc - chosen_adult_people - chosen_kiddo_people));
        }
    }

    public void dayDropListFill(JComboBox<ComboInsert> x, int day, int month_start, int month_stop, int year_start, int year_stop){
        int days_per_month = miesiac.resolveDays(month_stop, year_stop);
        if(year_start == year_stop && month_start == month_stop){
            for(int i = day; i < days_per_month+1; i++){
                x.addItem(new ComboInsert(String.valueOf(i),i));
            }
        }else{
            for(int i = 1; i < days_per_month+1; i++){
                x.addItem(new ComboInsert(String.valueOf(i),i));
            }
        }
    }

    public void monthDropListFill(JComboBox<ComboInsert> x, int month, int year_start, int year_stop){
        if(year_start == year_stop){
            for(int i = month; i < 13; i++){
                x.addItem(new ComboInsert(miesiac.nazwa(i),i));
            }
        }else{
            for(int i = 1; i < 13; i++){
                x.addItem(new ComboInsert(miesiac.nazwa(i),i));
            }
        }
    }
    public void yearDropListFill(JComboBox<ComboInsert> x, int year){
        for(int i = year; i < year + 3; i++){
            x.addItem(new ComboInsert(String.valueOf(i),i));
        }
    }

    private void ustal_aktualna_date(){
        Date d = new Date();
        SimpleDateFormat dzien = new SimpleDateFormat("dd");
        SimpleDateFormat miesiac = new SimpleDateFormat("MM");
        SimpleDateFormat rok = new SimpleDateFormat("yyyy");

        current_day = Integer.parseInt(dzien.format(d));
        current_month = Integer.parseInt(miesiac.format(d));
        current_year = Integer.parseInt(rok.format(d));
    }

    protected void myClear(JComboBox<ComboInsert> x){
        if(x.getItemCount()>1){
            for(int i=x.getItemCount()-1;i>0;i--){
                x.removeItemAt(i);
            }
        }
    }

    public void fillPeopleDropList(JComboBox<ComboInsert> x, int left){
        for(int i = 0; i < max_people - left + 1; i++){
            x.addItem(new ComboInsert(String.valueOf(i),i));
        }
    }

    public void kwotaReset(){
        kwota_z_pokoi = 0;
        kwota_z_uslug = 0;
        kwotaUpdate();
    }

    public void uslugiReset(){
        uslugaBasen.setSelected(false);
        uslugaBilard.setSelected(false);
        uslugaKregielnia.setSelected(false);
        uslugaSilownia.setSelected(false);
    }

    public void przedzialRezerwacjiCompute(){
        start_rezerwacji = chosen_start_year+"-"+chosen_start_month+"-"+chosen_start_day;
        stop_rezerwacji = chosen_end_year+"-"+chosen_end_month+"-"+chosen_end_day;
    }

    public void przedzialRezerwacjiReset(){
        start_rezerwacji = "";
        stop_rezerwacji = "";
    }

    public void pokojeDropListCheck(){
        if(osoby_flag && data_flag){
            pokojeDropList.setEnabled(true);
            wyczyscPokojeDropList();
            wypelnijPokojeDropList();
        }else{
            pokojeDropList.setEnabled(false);
            wyczyscPokojeDropList();
        }
    }

    public void wypelnijPokojeDropList(){
        GetWolnePokoje pokoje = new GetWolnePokoje(a);
        pokoje.dostepnePokoje(chosen_adult_people+chosen_kiddo_people, start_rezerwacji, stop_rezerwacji);
        ArrayList<ComboRoomInsert> tmp = pokoje.dostepnePokojeInfo();
        for(int i = 0; i < tmp.size(); i++){
            pokojeDropList.addItem(tmp.get(i));
        } 
    }

    public void wyczyscPokojeDropList(){
        for(int i=pokojeDropList.getItemCount()-1;i>0;i--){
                pokojeDropList.removeItemAt(i);
        }
    }

    public int okresPobytu(){
        int count = 0;
        // int count_months = 0;

        // for(int i = chosen_start_year; i <= chosen_end_year; i++){
        //     if(i == chosen_start_year){
        //         count_months += 12 - chosen_start_month + 1;
        //     }else if(i == chosen_end_year){
        //         count_months += chosen_end_month;
        //     }else{
        //         count_months +=12;
        //     }
        // }

        // int k = 0;
        // int month_start_copy = chosen_start_month;

        // for(int i = 1; i <= count_months; i++){
        //     if(month_start_copy == chosen_start_month && i == 1){
        //         count += miesiac.resolveDays(month_start_copy, chosen_start_year + k) - chosen_start_day + 1;
        //     }else if(i == count_months){
        //         count += chosen_end_day;
        //         System.out.println(chosen_end_day);
        //     }else{
        //         count += miesiac.resolveDays(month_start_copy, chosen_start_year + k);
        //     }
        //     if(month_start_copy == 12){
        //         k++;
        //         month_start_copy = 1;
        //     }else{
        //         month_start_copy++;
        //     }
        // }

        count = a.okres_zakwaterowania(start_rezerwacji, stop_rezerwacji);
        // System.out.println(count);
        return count;
    }

    public void computeUslugi(){
        String koncowy_opis = "";
        koncowy_opis += silownia.toString() + bilard.toString() + basen.toString() + kregielnia.toString();
        if(koncowy_opis.equals("")){
            koncowy_opis = "Brak";
        }
        int koncowa_cena = silownia.getCena() + bilard.getCena() + basen.getCena() + kregielnia.getCena();
        calosc = new GetUslugi(koncowy_opis, koncowa_cena);
        calosc.setVis(true);
    }
}