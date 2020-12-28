import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Register{
    public GUI_Login mainWindow;
    public JFrame registerFrame;
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


    public GUI_Register(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        a = p;
        registerFrame = new JFrame();
        registerPanel = new JPanel();
        confirm = new JButton("Zatwierdz");
        back = new JButton("Powrot");
        imieText = new JTextField(16);
        nazwiskoText= new JTextField(16);
        emailText= new JTextField(16);
        telefonText= new JTextField(16);;
        loginText= new JTextField(16);
        hasloText= new JTextField(16);
        imieLabel = new JLabel("Podaj imie:");
        nazwiskoLabel = new JLabel("Podaj nazwisko:");
        emailLabel = new JLabel("Podaj e-email:");
        telefonLabel = new JLabel("Podaj telefon:");
        loginLabel = new JLabel("Podaj login:");
        hasloLabel = new JLabel("Podaj haslo:");
        confirmLabel = new JLabel("");
        confirmLabel.setVisible(false);

        registerPanel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
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

        confirm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String im = imieText.getText();
                String nw = nazwiskoText.getText();
                String em = emailText.getText();
                String tel = telefonText.getText();
                String log = loginText.getText();
                String pass = hasloText.getText();

                String flag = a.zarejestruj(im, nw, em, tel, log, pass);
                confirmLabel.setText(flag);
                confirmLabel.setVisible(true);

            }
        });

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(mainWindow.panel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
                mainWindow.frame.validate();
            }
        });


        registerFrame.add(registerPanel, BorderLayout.CENTER);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // registerFrame.setTitle("BD PROJEKT - Zarejestruj");
        registerFrame.pack();
        // registerFrame.setVisible(true);
    }
}