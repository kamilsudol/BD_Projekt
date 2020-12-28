// import javax.swing.BorderFactory;
// import javax.swing.JFrame;
// import javax.swing.JPanel;
// import javax.swing.JLabel;
// import java.awt.BorderLayout;
// import java.awt.GridLayout;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Login{
    public JFrame frame;
    public JPanel panel;
    public JLabel label;
    public JTextField log;
    public JTextField pass;
    public JButton zaloguj;
    public JButton zarejestruj;
    public Polaczenie a;
    public GUI_Login(Polaczenie p){
        a = p;
        frame = new JFrame();
        panel = new JPanel();
        label = new JLabel("nothing");
        log = new JTextField(16);
        pass = new JTextField(16);
        zaloguj = new JButton("Zaloguj sie");
        zarejestruj = new JButton("Zarejestruj sie");
        

        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,10));
        panel.setLayout(new GridLayout(0,1));
        panel.add(label);
        label.setVisible(false);
        panel.add(log);
        panel.add(pass);

        panel.add(zaloguj);
        GUI_Login tmp = this;
        zaloguj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String l = log.getText();
                String ps = pass.getText();
                if(a.checkPasswd(l, ps)){
                    GUI_Menu f = new GUI_Menu(a, tmp);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(f.menuPanel);
                    frame.setTitle("BD PROJEKT - Menu");
                    frame.validate();
                }else{
                    label.setVisible(true);
                    label.setText("Bledne dane logowania!");
                }
            }
        });

        panel.add(zarejestruj);

        zarejestruj.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Register f = new GUI_Register(a, tmp);
                // f.registerFrame.setVisible(true);
                frame.getContentPane().removeAll();
                frame.add(f.registerPanel, BorderLayout.CENTER);
                frame.setTitle("BD PROJEKT - Zarejestruj");
                // frame.getContentPane().add(f.registerPanel);
                // f.registerFrame.setVisible(true);
                frame.validate();
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BD PROJEKT - Zaloguj");
        frame.pack();
        frame.setVisible(true);
    }

    void setStr(String rec){
        label.setText(rec);
    }
    
}