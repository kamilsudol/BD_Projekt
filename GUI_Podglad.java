import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Podglad{
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JButton uzytkownikButton;
    public JButton rezerwacjeButton;
    public JButton uslugiButton;
    public JButton czarna_listaButton;
    public JButton zakwaterowaniButton;
    public JButton pokojeButton;
    public JButton oplataButton;
    public JButton menuButton;

    public GUI_Podglad(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        a = p;
        
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        podgladPanel.setLayout(new GridLayout(0,1));

        uzytkownikButton = new JButton("Tabela \'uzytkownicy\'+\'panel\'");
        uzytkownikButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uzytkownicy m = new GUI_Podglad_Uzytkownicy(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'uzytkownicy + panel\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uzytkownikButton);

        rezerwacjeButton = new JButton("Tabela \'rezerwacje\'");
        rezerwacjeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Rezerwacje m = new GUI_Podglad_Rezerwacje(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'rezerwacje\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(rezerwacjeButton);

        oplataButton = new JButton("Tabela \'oplaty\'");
        oplataButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Oplaty m = new GUI_Podglad_Oplaty(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'oplaty\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(oplataButton);

        uslugiButton = new JButton("Tabela \'dodatkowe uslugi\'");
        uslugiButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad_Uslugi m = new GUI_Podglad_Uslugi(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad - Tabela \'dodatkowe uslugi\'");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(uslugiButton);

        pokojeButton = new JButton("Tabela \'lista pokoi\'");
        pokojeButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
            }
        });
        podgladPanel.add(pokojeButton);

        zakwaterowaniButton = new JButton("Tabela \'zakwaterowani goscie info\'");
        zakwaterowaniButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
            }
        });
        podgladPanel.add(zakwaterowaniButton);

        czarna_listaButton = new JButton("Tabela \'czarna lista\'");
        czarna_listaButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
            }
        });
        podgladPanel.add(czarna_listaButton);

        menuButton = new JButton("Powrot do menu");
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu m = new GUI_Menu(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(menuButton);

    }

}