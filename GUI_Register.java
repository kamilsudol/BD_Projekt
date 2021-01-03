import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Register{
    public GUI_Login mainWindow;
    // public JFrame registerFrame;
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
        // registerFrame = new JFrame();
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
        emailLabel = new JLabel("Podaj e-email:");
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

        confirm.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String im = imieText.getText();
                String nw = nazwiskoText.getText();
                String em = emailText.getText();
                String tel = telefonText.getText();
                String log = loginText.getText();
                String pass = hasloText.getText();

                if(!im.equals("") && !nw.equals("") && !em.equals("") && !tel.equals("") && !log.equals("") && !pass.equals("")){
                    String flag = a.zarejestruj(im, nw, em, tel, log, pass);
                    confirmLabel.setText(flag);
                    confirmLabel.setVisible(true);
                }else{
                    confirmLabel.setText("Prosze wypelnic wszystkie pola!");
                    confirmLabel.setVisible(true);
                }
            }
        });

        back.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Login_Wrapper m = new GUI_Login_Wrapper(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.panel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
                mainWindow.frame.validate();
            }
        });


        // registerFrame.add(registerPanel, BorderLayout.CENTER);
        // registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // // registerFrame.setTitle("BD PROJEKT - Zarejestruj");
        // registerFrame.pack();
        // // registerFrame.setVisible(true);
    }
}