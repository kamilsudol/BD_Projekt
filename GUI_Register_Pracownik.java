import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Register_Pracownik
 * Realizuje ona rejestracje nowych pracownikow (administratorow) hotelu.
 */

public class GUI_Register_Pracownik{
    public int login_id;
    public GUI_Login mainWindow;
    // public JFrame registerFrame;
    public JPanel registerPanel;
    public JButton confirm;
    public JButton menuButton;
    public JTextField imieText;
    public JTextField nazwiskoText;
    public JTextField emailText;
    public JTextField telefonText;
    public JTextField loginText;
    public JTextField hasloText;
    public Polaczenie a;
    public JLabel imieLabel;
    public JLabel nazwiskoLabel;
    public JLabel emailLabel;
    public JLabel telefonLabel;
    public JLabel loginLabel;
    public JLabel hasloLabel;
    public JLabel confirmLabel;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy, odniesienie do okna glownego
     * oraz id obecnie zalogowanego uzytkownika.
     * @param p
     * @param mainWindow
     * @param id
     */

    public GUI_Register_Pracownik(Polaczenie p, GUI_Login mainWindow, int id){
        login_id = id;
        this.mainWindow = mainWindow;
        a = p;
        // registerFrame = new JFrame();
        registerPanel = new JPanel();
        confirm = new JButton("Zatwierdz");
        imieText = new JTextField(32);
        nazwiskoText= new JTextField(32);
        emailText= new JTextField(32);
        telefonText= new JTextField(32);;
        loginText= new JTextField(32);
        hasloText= new JTextField(32);
        imieLabel = new JLabel("Podaj imie pracownika:");
        nazwiskoLabel = new JLabel("Podaj nazwisko pracownika:");
        emailLabel = new JLabel("Podaj e-email pracownika:");
        telefonLabel = new JLabel("Podaj telefon pracownika:");
        loginLabel = new JLabel("Podaj login pracownika:");
        hasloLabel = new JLabel("Podaj haslo pracownika:");
        confirmLabel = new JLabel("", SwingConstants.CENTER);
        confirmLabel.setVisible(false);

        registerPanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        registerPanel.setLayout(new GridLayout(0,1));
        registerPanel.add(imieLabel);
        registerPanel.add(imieText);
        registerPanel.add(nazwiskoLabel);
        registerPanel.add(nazwiskoText);
        registerPanel.add(emailLabel);
        registerPanel.add(emailText);
        registerPanel.add(telefonLabel);
        registerPanel.add(telefonText);
        registerPanel.add(loginLabel);
        registerPanel.add(loginText);
        registerPanel.add(hasloLabel);
        registerPanel.add(hasloText);
        registerPanel.add(confirm);
        registerPanel.add(confirmLabel);

        confirm.addActionListener(new ActionListener(){ //Przycisk realizujacy zatwierdzenie danych i wyslanie informacji do bazy.
            public void actionPerformed(ActionEvent e){
                String im = imieText.getText();
                String nw = nazwiskoText.getText();
                String em = emailText.getText();
                String tel = telefonText.getText();
                String log = loginText.getText();
                String pass = hasloText.getText();

                String flag = a.zarejestruj(im, nw, em, tel, log, pass, "Pracownik");
                confirmLabel.setText(flag);
                confirmLabel.setVisible(true);
            }
        });

        menuButton = new JButton("Powrot do menu"); //Przycisk realizujacy powrot do okna menu.
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu_Admin m = new GUI_Menu_Admin(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        registerPanel.add(menuButton);

    }
}