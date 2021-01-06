import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * Klasa GUI_Moje_Rezerwacje_Szczegoly
 * Klasa realizujaca podglad do wszystkich informacji
 * dotyczacych wybranej rezerwacji.
 */

public class GUI_Moje_Rezerwacje_Szczegoly{
    public int login_id;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel podgladPanel;
    public JPanel podgladButtonPanel;
    public JButton podgladButton;
    public GetMojeRezerwacjeInfo info;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego,
     * id obecnie zalogowanego uzytkownika oraz zestaw informacji dla konkretnej rezerwacji.
     * @param p
     * @param mainWindow
     * @param id
     * @param i
     */

    public GUI_Moje_Rezerwacje_Szczegoly(Polaczenie p, GUI_Login mainWindow, int id, GetMojeRezerwacjeInfo i){
        info = i;
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        podgladPanel = new JPanel();
        podgladPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        podgladPanel.setLayout(new GridLayout(0,1));

        //Segment wyswietlajacy informacje.
        wypelnij();

        podgladButton = new JButton("Powrot do moich rezerwacji"); //Przycisk realizujacy powrot do okna moich rezerwacji
        podgladButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Moje_Rezerwacje m = new GUI_Moje_Rezerwacje(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.moje_rezerwacje_Uppanel, BorderLayout.NORTH);
                mainWindow.frame.add(m.moje_rezerwacje_panel, BorderLayout.CENTER);
                mainWindow.frame.add(m.moje_rezerwacje_Buttonpanel, BorderLayout.SOUTH);
                mainWindow.frame.setTitle("BD PROJEKT - Moje rezerwacje");
                mainWindow.frame.validate();
            }
        });
        podgladButtonPanel = new JPanel();
        podgladButtonPanel.setLayout(new GridLayout(0,1));
        podgladButtonPanel.add(podgladButton);
    }

    /**
     * Prosta metoda wywolujaca metode wypelnienia okna danymi.
     */

    public void wypelnij(){
        info.info(podgladPanel);
    }
}