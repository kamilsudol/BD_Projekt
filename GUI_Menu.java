import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Menu{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    // public JFrame menuFrame;
    public JPanel menuPanel;
    public JLabel menuLabel;
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

        menuLabel = new JLabel("Menu:");
        menuPanel.add(menuLabel);

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

        moje_rezerwacje = new JButton("Moje rezerwacje");
        moje_rezerwacje.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
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
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(mainWindow.panel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(wyloguj);
    }

}