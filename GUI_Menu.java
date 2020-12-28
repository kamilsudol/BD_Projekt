import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Menu{
    public GUI_Login mainWindow;
    public Polaczenie a;
    // public JFrame menuFrame;
    public JPanel menuPanel;
    public JLabel menuLabel;
    public JButton rezerwacja;
    public JButton moje_rezerwacje;
    public JButton podglad_tabel;
    public JButton wyloguj;
    
    public GUI_Menu(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        a = p;
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        menuPanel.setLayout(new GridLayout(0,1));

        menuLabel = new JLabel("Menu:");
        menuPanel.add(menuLabel);

        rezerwacja = new JButton("Dokonaj rezerwacji");
        rezerwacja.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //
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
                GUI_Podglad p = new GUI_Podglad(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(p.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad tabel");
                mainWindow.frame.validate();
            }
        });
        menuPanel.add(podglad_tabel);

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