import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Login_Wrapper{
    public GUI_Login mainWindow;
    // public JFrame frame;
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
        // frame = new JFrame();
        // frame.setSize(1200, 800);

        panel = new JPanel();
        label = new JLabel("nothing");
        log = new JTextField(16);
        pass = new JTextField(16);
        zaloguj = new JButton("Zaloguj sie");
        zarejestruj = new JButton("Zarejestruj sie");
        

        panel.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
        panel.setLayout(new GridLayout(0,1));
        // panel.setSize(1200, 800);
        panel.add(label);
        label.setVisible(false);
        panel.add(log);
        panel.add(pass);

        panel.add(zaloguj);
        // GUI_Login tmp = this;
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
                // f.registerFrame.setVisible(true);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(f.registerPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Zarejestruj");
                // frame.getContentPane().add(f.registerPanel);
                // f.registerFrame.setVisible(true);
                mainWindow.frame.validate();
            }
        });

        // frame.add(panel, BorderLayout.CENTER);
        // frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // frame.setTitle("BD PROJEKT - Zaloguj");
        // frame.pack();
        // frame.setVisible(true);
    }

    // void setStr(String rec){
    //     label.setText(rec);
    // }

    public void start(){
        mainWindow.frame.getContentPane().removeAll();
        mainWindow.frame.add(panel, BorderLayout.CENTER);
        mainWindow.frame.setTitle("BD PROJEKT - Zaloguj");
        mainWindow.frame.validate();
    }
    
}