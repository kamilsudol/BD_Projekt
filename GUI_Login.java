import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GUI_Login{
    public JFrame frame;
    public JPanel panel;
    public JLabel label;
    public JLabel autor;
    public JButton start;
    public Polaczenie a;
    public int login_id;
    public GUI_Login(Polaczenie p){
        login_id = -1;
        a = p;
        frame = new JFrame();
        // frame.setSize(1200, 800);

        panel = new JPanel();
        label = new JLabel("Baza danych - Hotel", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.PLAIN, 40));
        autor = new JLabel("Wykonal Kamil Sudol", SwingConstants.CENTER);
        start = new JButton("Start");

        panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        panel.setLayout(new GridLayout(0,1));

        panel.add(label);
        panel.add(autor);

        GUI_Login tmp = this;

        start.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Login_Wrapper m = new GUI_Login_Wrapper(a, tmp);
                frame.getContentPane().removeAll();
                frame.add(m.panel, BorderLayout.CENTER);
                frame.setTitle("BD PROJEKT - Zaloguj");
                frame.validate();
            }
        });
        panel.add(start);

        frame.add(panel, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("BD PROJEKT - Start");
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }
    
}