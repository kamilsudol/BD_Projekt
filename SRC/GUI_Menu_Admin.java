import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Menu_Admin
 * Klasa wyswietlajaca panel administratora.
 * Funckjonalosc ta jest dostepna tylko dla
 * administratorow hotelu.
 */

public class GUI_Menu_Admin{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel menuPanel;
    public JLabel menuLabel;
    public JButton zablokuj;
    public JButton usun;
    public JButton podglad_tabel;
    public JButton podglad_raportow;
    public JButton menuButton;
    public JButton dodajPracownika;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */
    
    public GUI_Menu_Admin(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        menuPanel.setLayout(new GridLayout(0,1));

        menuLabel = new JLabel("Menu administratora:", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        menuPanel.add(menuLabel);

        dodajPracownika = new JButton("Dodaj nowego pracownika"); //Przycisk realizujacy mozliwosc dodania nowego pracownika hotelu
        dodajPracownika.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Register_Pracownik f = new GUI_Register_Pracownik(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(f.registerPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Dodaj pracownika");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(dodajPracownika);
        
        zablokuj = new JButton("Zablokuj uzytkownika");//Przycisk realizujacy mozliwosc zablokowania danego uzytkownika
        zablokuj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Zablokuj m = new GUI_Zablokuj(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.panelUp, BorderLayout.NORTH);
                mainWindow.frame.add(m.panelCenter, BorderLayout.CENTER);
                mainWindow.frame.add(m.panelDown, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Zablokuj uzytkownika");
                mainWindow.frame.validate();
            }
        });

        menuPanel.add(zablokuj);

        if(a.HeadAdmin(id)){//funkcjonalnosc dostepna tylko dla HeadAdmina
            usun = new JButton("Usun uzytkownika");//Przycisk realizujacy mozliwosc usuniecia konkretnego uzytkownika
            usun.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    GUI_Usun m = new GUI_Usun(a, mainWindow, login_id);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.panelUp, BorderLayout.NORTH);
                    mainWindow.frame.add(m.panelCenter, BorderLayout.CENTER);
                    mainWindow.frame.add(m.panelDown, BorderLayout.SOUTH);
                    mainWindow.frame.setTitle("BD PROJEKT - Usun uzytkownika");
                    mainWindow.frame.validate();
                }
            });

            menuPanel.add(usun);
        }

        podglad_tabel = new JButton("Podglad tabel");//Przycisk realuzujacy mozliwosc podlgadu tabel bazy danych
        podglad_tabel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad p = new GUI_Podglad(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(p.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad tabel");
                mainWindow.frame.validate();
            }
        });

        menuPanel.add(podglad_tabel);

        podglad_raportow = new JButton("Podglad raportow");//Przycisk realuzujacy mozliwosc podlgadu raportow
        podglad_raportow.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Raport p = new GUI_Raport(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(p.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad raportow");
                mainWindow.frame.validate();
            }
        });

        menuPanel.add(podglad_raportow);
        
        menuButton = new JButton("Powrot do menu");//Przycisk realizujacy powrot do okna menu.
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu m = new GUI_Menu(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(menuButton);
    }

}