import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Register
 * Jest ona odpowiedzialna za procedure rejestracji nowych
 * uzytkownikow.
 */

public class GUI_Register{
    public GUI_Login mainWindow;
    public JPanel registerPanel;
    public JButton confirm;
    public JButton back;
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
     * Kontstruktor domyslny, przyjmujacy polaczenie do bazy danych oraz odniesienie do
     * okna glownego.
     * @param p
     * @param mainWindow
     */
    public GUI_Register(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        a = p;
        registerPanel = new JPanel();
        confirm = new JButton("Zatwierdz");
        back = new JButton("Powrot");
        imieText = new JTextField(32);
        nazwiskoText= new JTextField(32);
        emailText= new JTextField(32);
        telefonText= new JTextField(32);;
        loginText= new JTextField(32);
        hasloText= new JTextField(32);
        imieLabel = new JLabel("Podaj imie:");
        nazwiskoLabel = new JLabel("Podaj nazwisko:");
        emailLabel = new JLabel("Podaj e-mail:");
        telefonLabel = new JLabel("Podaj telefon:");
        loginLabel = new JLabel("Podaj login:");
        hasloLabel = new JLabel("Podaj haslo:");
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
        registerPanel.add(back);

        confirm.addActionListener(new ActionListener(){  // Przycisk zatwierdzajacy rejestracje, wysylajacy informacje do bazy.
            public void actionPerformed(ActionEvent e){
                String im = imieText.getText();
                String nw = nazwiskoText.getText();
                String em = emailText.getText();
                String tel = telefonText.getText();
                String log = loginText.getText();
                String pass = hasloText.getText();

                String flag = a.zarejestruj(im, nw, em, tel, log, pass, "Klient");
                confirmLabel.setText(flag);
                confirmLabel.setVisible(true);
            }
        });

        back.addActionListener(new ActionListener(){  //Przycisk realizujacy powrot do okna logowania.
            public void actionPerformed(ActionEvent e){
                GUI_Login_Wrapper m = new GUI_Login_Wrapper(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.panel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
                mainWindow.frame.validate();
            }
        });


    }
}