import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI_Rezerwacja_Result{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel rezerwacjaPanel;
    public JButton menuButton;
    public JLabel statement;

    public GUI_Rezerwacja_Result(Polaczenie p, GUI_Login mainWindow, int id, String st){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        rezerwacjaPanel = new JPanel();
        rezerwacjaPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        rezerwacjaPanel.setLayout(new GridLayout(0,1));

        statement = new JLabel(st);
        statement.setVisible(true);
        rezerwacjaPanel.add(statement);

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
}