import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Rezerwacja_Result
 * Klasa wykorzystywana jest jako wiadomosc zwrotna dla uzytkownika,
 * czy konkretne operacje zakonczyly sie powodzeniem badz porazka.
 */

public class GUI_Rezerwacja_Result{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel rezerwacjaPanel;
    public JButton menuButton;
    public JLabel statement;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego,
     * id obecnie zalogowanego uzytkownika oraz wiadomosc zwrotna wyslana z bazy.
     * @param p
     * @param mainWindow
     * @param id
     * @param st
     */

    public GUI_Rezerwacja_Result(Polaczenie p, GUI_Login mainWindow, int id, String st){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        rezerwacjaPanel = new JPanel();
        rezerwacjaPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        rezerwacjaPanel.setLayout(new GridLayout(0,1));

        statement = new JLabel(st, SwingConstants.CENTER);
        statement.setFont(new Font("Arial", Font.PLAIN, 30));
        statement.setVisible(true);
        rezerwacjaPanel.add(statement);

        menuButton = new JButton("Powrot do menu"); //Przycisk realizujacy powrot do okna menu.
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