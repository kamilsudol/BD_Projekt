import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Login{
    public JFrame frame;
    public JPanel panel;
    // public JLabel label;
    // public JTextField log;
    // public JTextField pass;
    // public JButton zaloguj;
    // public JButton zarejestruj;
    public Polaczenie a;
    public int login_id;
    public GUI_Login(Polaczenie p){
        login_id = -1;
        a = p;
        frame = new JFrame();
        frame.setSize(1200, 800);

        panel = new JPanel();
        // label = new JLabel("nothing");
        // log = new JTextField(16);
        // pass = new JTextField(16);
        // zaloguj = new JButton("Zaloguj sie");
        // zarejestruj = new JButton("Zarejestruj sie");
        

        panel.setBorder(BorderFactory.createEmptyBorder(300,300,300,300));
        panel.setLayout(new GridLayout(0,1));
        // panel.setSize(1200, 800);
        // panel.add(label);
        // label.setVisible(false);
        // panel.add(log);
        // panel.add(pass);

        // panel.add(zaloguj);
        // GUI_Login tmp = this;
        // zaloguj.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e){
        //         String l = log.getText();
        //         String ps = pass.getText();
        //         login_id = a.checkPasswd(l, ps);
        //         if(login_id > -1){
        //             GUI_Menu f = new GUI_Menu(a, tmp, login_id);
        //             frame.getContentPane().removeAll();
        //             frame.getContentPane().add(f.menuPanel);
        //             frame.setTitle("BD PROJEKT - Menu");
        //             frame.validate();
        //         }else{
        //             label.setVisible(true);
        //             label.setText("Bledne dane logowania!");
        //         }
        //     }
        // });

        // panel.add(zarejestruj);

        // zarejestruj.addActionListener(new ActionListener(){
        //     public void actionPerformed(ActionEvent e){
        //         GUI_Register f = new GUI_Register(a, tmp);
        //         // f.registerFrame.setVisible(true);
        //         frame.getContentPane().removeAll();
        //         frame.add(f.registerPanel, BorderLayout.CENTER);
        //         frame.setTitle("BD PROJEKT - Zarejestruj");
        //         // frame.getContentPane().add(f.registerPanel);
        //         // f.registerFrame.setVisible(true);
        //         frame.validate();
        //     }
        // });

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BD PROJEKT - Zaloguj");
        frame.pack();
        frame.setVisible(true);
    }
    
}