import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI_Podglad_Uzytkownicy{
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JButton podgladButton;
    public ArrayList<String> uzytkownicy_records;

    public GUI_Podglad_Uzytkownicy(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        a = p;
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        podgladPanel.setLayout(new GridLayout(0,1));
        uzytkownicy_records = new ArrayList<>();
        wypelnij();
        podgladButton = new JButton("Powrot do podgladu tabel");
        podgladButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad m = new GUI_Podglad(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad tabel");
                mainWindow.frame.validate();
            }
        });
        podgladPanel.add(podgladButton);
    }

    public void wypelnij(){
        uzytkownicy_records =  a.getTableUzytkownicy();
        String tmp;
        for(int i = 0; i < uzytkownicy_records.size(); i++){
            tmp = uzytkownicy_records.get(i);
            podgladPanel.add(new JLabel(tmp));
        }
    }
}