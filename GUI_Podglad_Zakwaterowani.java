import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI_Podglad_Zakwaterowani{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JPanel podgladButtonPanel;
    public JButton podgladButton;
    public ArrayList<ArrayList<String>> records;

    public GUI_Podglad_Zakwaterowani(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,3));
        records = new ArrayList<>();
        wypelnij();
        podgladButton = new JButton("Powrot do podgladu tabel");
        podgladButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Podglad m = new GUI_Podglad(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Podglad tabel");
                mainWindow.frame.validate();
            }
        });
        podgladButtonPanel = new JPanel();
        podgladButtonPanel.setLayout(new GridLayout(0,1));
        podgladButtonPanel.add(podgladButton);
    }

    public void wypelnij(){
        records =  a.getTableZakwaterowani();
        String tmp;
        for(int i = 0; i < records.size(); i++){
            for(int j = 0; j <records.get(i).size(); j++){
                tmp = records.get(i).get(j);
                podgladPanel.add(new JLabel(tmp));
            }
        }
    }
}