import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Podglad_Kategoria
 * Klasa realizujaca podglad zawartosci tabeli kategoria
 */

public class GUI_Podglad_Kategoria{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JPanel podgladButtonPanel;
    public JButton podgladButton;
    public ArrayList<ArrayList<String>> records;

    public GUI_Podglad_Kategoria(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        records =  a.getTableKategoria();
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,records.get(0).size()));
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
        podgladButtonPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladButtonPanel.setLayout(new GridLayout(0,1));
        podgladButtonPanel.add(podgladButton);
    }

    public void wypelnij(){
        String tmp;
        for(int i = 0; i < records.size(); i++){
            for(int j = 0; j <records.get(i).size(); j++){
                tmp = records.get(i).get(j);
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
}