import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Klasa GUI_Login
 * Klasa realizujaca wyswietlanie GUI aplikacji.
 */

public class GUI_Login{
    public JFrame frame;
    public JPanel panel;
    public JLabel label;
    public JLabel autor;
    public JButton start;
    public Polaczenie a;

    /**
     * Konstruktor domyslny, przyjmujacy polaczenie do bazy danych.
     * @param p
     */

    public GUI_Login(Polaczenie p){
        a = p;

        //Segment witajacy

        frame = new JFrame();
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

        start.addActionListener(new ActionListener(){//Przycik realizujacy start aplikacji
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