import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Menu{
    public int login_id;
    public Boolean czy_zbanowany;
    public GUI_Login mainWindow;
    public Polaczenie a;
    // public JFrame menuFrame;
    public JPanel menuPanel;
    public JLabel menuLabel;
    public JLabel helloLabel;
    public JLabel infoLabel;
    public JButton rezerwacja;
    public JButton moje_rezerwacje;
    public JButton podglad_tabel;
    public JButton wyloguj;
    
    public GUI_Menu(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        menuPanel.setLayout(new GridLayout(0,1));

        czy_zbanowany = a.czyZbanowany(login_id);

        helloLabel = new JLabel("Witaj "+a.getUserName(login_id)+"!");
        menuPanel.add(helloLabel);

        if(czy_zbanowany){
            infoLabel = new JLabel("Twoje konto zostalo zawieszone na czas nieokreslony. Powod: " + a.getPowod(login_id));
            menuPanel.add(infoLabel);
        }

        menuLabel = new JLabel("Menu:");
        menuPanel.add(menuLabel);

        if(!czy_zbanowany){
            rezerwacja = new JButton("Dokonaj rezerwacji");
            rezerwacja.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    GUI_Rezerwacja p = new GUI_Rezerwacja(a, mainWindow, login_id);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(p.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Dokonaj rezerwacji");
                    mainWindow.frame.validate();
                }
            });
            menuPanel.add(rezerwacja);
        }

        moje_rezerwacje = new JButton("Moje rezerwacje");
        moje_rezerwacje.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Moje_Rezerwacje m = new GUI_Moje_Rezerwacje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.getContentPane().add(m.moje_rezerwacje_Uppanel, BorderLayout.NORTH);
                mainWindow.frame.getContentPane().add(m.moje_rezerwacje_panel, BorderLayout.CENTER);
                mainWindow.frame.getContentPane().add(m.moje_rezerwacje_Buttonpanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Moje rezerwacje");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(moje_rezerwacje);

        podglad_tabel = new JButton("Podglad tabel");
        podglad_tabel.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad p = new GUI_Podglad(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(p.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad tabel");
                mainWindow.frame.validate();
            }
        });
        if(login_id == 0){
            menuPanel.add(podglad_tabel);
        }

        wyloguj = new JButton("Wyloguj");
        wyloguj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Login_Wrapper m = new GUI_Login_Wrapper(a, mainWindow);
                m.label.setVisible(true);
                m.label.setText("Pomyslnie wylogowano!");
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.panel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(wyloguj);
    }

}