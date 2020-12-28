import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Rezerwacja{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel rezerwacjaPanel;
    public JButton zatwierdzButton;
    public JButton menuButton;
    public JLabel pokojLabel;
    public JLabel liczbaDoroslychLabel;
    public JLabel liczbaDzieciLabel;
    public JLabel kwotaLabel;
    public JLabel uslugiLabel;
    public JLabel odKiedyLabel;
    public JLabel doKiedyLabel;
    public double kwota_z_pokoi;
    public double kwota_z_uslug;
    public JComboBox day_odKiedyDropList;
    public JComboBox month_odKiedyDropList;
    public JComboBox year_odKiedyDropList;
    public JComboBox day_doKiedyDropList;
    public JComboBox month_doKiedyDropList;
    public JComboBox year_doKiedyDropList;
    public Container pane;


    public GUI_Rezerwacja(Polaczenie p, GUI_Login mainWindow, int id){

        kwota_z_pokoi = 0;
        kwota_z_uslug = 0;

        login_id = id;
        this.mainWindow = mainWindow;
        a = p;

        rezerwacjaPanel = new JPanel();
        rezerwacjaPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        rezerwacjaPanel.setLayout(new GridLayout(0,1));

        odKiedyLabel = new JLabel("Prosze wybrac termin zakwaterowania:");
        rezerwacjaPanel.add(odKiedyLabel);

        day_odKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(day_odKiedyDropList);

        month_odKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(month_odKiedyDropList);

        year_odKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(year_odKiedyDropList);

        doKiedyLabel = new JLabel("Prosze wybrac termin wykwaterowania:");
        rezerwacjaPanel.add(doKiedyLabel);

        day_doKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(day_doKiedyDropList);

        month_doKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(month_doKiedyDropList);

        year_doKiedyDropList = new JComboBox();
        rezerwacjaPanel.add(year_doKiedyDropList);

        liczbaDoroslychLabel = new JLabel("Prosze podac liczbe doroslych:");
        rezerwacjaPanel.add(liczbaDoroslychLabel);

        liczbaDzieciLabel = new JLabel("Prosze podac liczbe dzieci:");
        rezerwacjaPanel.add(liczbaDzieciLabel);

        pokojLabel = new JLabel("Prosze wybrac pokoj/pokoje:");
        rezerwacjaPanel.add(pokojLabel);

        uslugiLabel = new JLabel("Prosze wybrac dodatkowe uslugi (opcjonalnie):");
        rezerwacjaPanel.add(uslugiLabel);

        kwotaLabel = new JLabel("Calkowity koszt: "+(kwota_z_uslug+kwota_z_pokoi) + " zl");
        rezerwacjaPanel.add(kwotaLabel);

        zatwierdzButton = new JButton("Potwierdz rezerwacje");
        zatwierdzButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
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

    public void kwotaUslugiCompute(){
        //
    }

    public void kwotaPokojeCompute(){
        //
    }
}