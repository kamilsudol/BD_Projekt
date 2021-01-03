import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Login_Wrapper{
    public GUI_Login mainWindow;
    public JPanel panel;
    public JLabel label;
    public JTextField log;
    public JTextField pass;
    public JButton zaloguj;
    public JButton zarejestruj;
    public Polaczenie a;
    public int login_id;
    public GUI_Login_Wrapper(Polaczenie p, GUI_Login mainWindow){
        this.mainWindow = mainWindow;
        login_id = -1;
        a = p;
        panel = new JPanel();
        label = new JLabel("nothing", SwingConstants.CENTER);
        log = new JTextField(32);
        pass = new JTextField(32);
        zaloguj = new JButton("Zaloguj sie");
        zarejestruj = new JButton("Zarejestruj sie");
        

        panel.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
        panel.setLayout(new GridLayout(0,1));
        panel.add(label);
        label.setVisible(false);
        panel.add(log);
        panel.add(pass);

        panel.add(zaloguj);
        zaloguj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String l = log.getText();
                String ps = pass.getText();
                login_id = a.checkPasswd(l, ps);
                if(login_id > -1){
                    GUI_Menu f = new GUI_Menu(a, mainWindow, login_id);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.getContentPane().add(f.menuPanel);
                    mainWindow.frame.setTitle("BD PROJEKT - Menu");
                    mainWindow.frame.validate();
                }else{
                    label.setVisible(true);
                    label.setText("Bledne dane logowania!");
                }
            }
        });

        panel.add(zarejestruj);

        zarejestruj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Register f = new GUI_Register(a, mainWindow);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(f.registerPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zarejestruj");
                mainWindow.frame.validate();
            }
        });
    }
    
}