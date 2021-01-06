import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Menu
 * Klasa realizujaca udostepnienie funkcjonalnosci
 * projektu dla kazdego uzytkownika.
 */

public class GUI_Menu{
    public int login_id;
    public Boolean czy_zbanowany;
    public GUI_Login mainWindow;
    public Polaczenie a;
    public JPanel menuPanel;
    public JLabel menuLabel;
    public JLabel helloLabel;
    public JLabel infoLabel;
    public JButton rezerwacja;
    public JButton moje_rezerwacje;
    public JButton adminMenu;
    public JButton wyloguj;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */
    
    public GUI_Menu(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        menuPanel = new JPanel();
        menuPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        menuPanel.setLayout(new GridLayout(0,1));

        czy_zbanowany = a.czyZbanowany(login_id);

        //Segment witajacy uzytkownika

        helloLabel = new JLabel("Witaj "+a.getUserName(login_id)+"!", SwingConstants.CENTER);
        helloLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        menuPanel.add(helloLabel);

        //Segment sprawdzajacy, czy uzytkownik zostal zablokowany

        if(czy_zbanowany){
            infoLabel = new JLabel("Twoje konto zostalo zawieszone na czas nieokreslony. Powod: " + a.getPowod(login_id), SwingConstants.CENTER);
            infoLabel.setFont(new Font("Arial", Font.PLAIN, 17));
            menuPanel.add(infoLabel);
        }

        menuLabel = new JLabel("Menu:", SwingConstants.CENTER);
        menuLabel.setFont(new Font("Arial", Font.PLAIN, 17));
        menuPanel.add(menuLabel);

        //Segment dedykowany dla administratorow hotelu

        if(a.czyAdmin(login_id) == 1){
            adminMenu = new JButton("Panel administratora");
            adminMenu.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    GUI_Menu_Admin m = new GUI_Menu_Admin(a, mainWindow, login_id);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Menu - Administrator");
                    mainWindow.frame.validate();
                }
            });

            menuPanel.add(adminMenu);
        }



        if(!czy_zbanowany){//Sprawdzenie, czy aktualnie zalogowany uzytkownik moze dokonac rezerwacji
            rezerwacja = new JButton("Dokonaj rezerwacji"); //Przycisk realizujacy udostepnienie mozliwosci dodokania rezerwacji
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

        moje_rezerwacje = new JButton("Moje rezerwacje");//Przycik realizujacy udostepnienie mozliwosci podgladu swoich rezerwacji.
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

        wyloguj = new JButton("Wyloguj");//Przycisk realizujacy wylogowanie sie ze swojego konta.
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