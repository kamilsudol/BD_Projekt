import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class GUI_Moje_Rezerwacje{
    public Polaczenie a;
    public GUI_Login mainWindow;
    public int login_id;
    public JPanel moje_rezerwacje_panel;
    public JPanel moje_rezerwacje_Buttonpanel;
    public JPanel moje_rezerwacje_Uppanel;
    public JLabel title;
    public JButton menuButton;
    public ArrayList<GetMojeRezerwacjeInfo> moje_rezerwacje;
    
    public JComboBox<GetMojeRezerwacjeInfo> rezerwacjeComboBox;
    private int chosen_oplata_id;


    public GUI_Moje_Rezerwacje(Polaczenie p, GUI_Login mainWindow, int id){
        a = p;
        this.mainWindow = mainWindow;
        login_id = id;

        moje_rezerwacje_panel = new JPanel();
        moje_rezerwacje_panel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        moje_rezerwacje_panel.setLayout(new GridLayout(0,3));

        moje_rezerwacje_Buttonpanel = new JPanel();
        moje_rezerwacje_Buttonpanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        moje_rezerwacje_Buttonpanel.setLayout(new GridLayout(0,1));

        moje_rezerwacje_Uppanel = new JPanel();
        moje_rezerwacje_Uppanel.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        moje_rezerwacje_Uppanel.setLayout(new GridLayout(0,1));

        title = new JLabel("Prosze wybrac rezerwacje:", SwingConstants.CENTER);
        moje_rezerwacje_Uppanel.add(title);
        // dodajSegment(7);
        
        wypelnij();
        dodajSegment(3);
        JButton infoButton = new JButton("Szczegoly rezerwacji");
        infoButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(chosen_oplata_id != -1){
                    Object item = rezerwacjeComboBox.getSelectedItem();
                    GetMojeRezerwacjeInfo chosen_rezerwacja = (GetMojeRezerwacjeInfo)item;
                    GUI_Moje_Rezerwacje_Szczegoly m = new GUI_Moje_Rezerwacje_Szczegoly(a, mainWindow, login_id, chosen_rezerwacja);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.podgladPanel, BorderLayout.CENTER);
                    mainWindow.frame.add(m.podgladButtonPanel, BorderLayout.SOUTH);
                    mainWindow.frame.setTitle("BD PROJEKT - Moja rezerwacja - Szczegoly");
                    mainWindow.frame.validate();
                }
            }
        });
        moje_rezerwacje_panel.add(infoButton);

        final JButton zaplacButton = new JButton("Zaplac");
        zaplacButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(chosen_oplata_id != -1){
                    String st = a.oplacRezerwacje(chosen_oplata_id);
                    GUI_Rezerwacja_Result m = new GUI_Rezerwacja_Result(a, mainWindow, login_id, st);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Oplata - OK");
                    mainWindow.frame.validate();
                }
            }
        });
        moje_rezerwacje_panel.add(zaplacButton);

        final JButton rezygnujButton = new JButton("Zrezygnuj");
        rezygnujButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(chosen_oplata_id != -1){
                    String st = a.rezygnujRezerwacje(chosen_oplata_id);
                    GUI_Rezerwacja_Result m = new GUI_Rezerwacja_Result(a, mainWindow, login_id, st);
                    mainWindow.frame.getContentPane().removeAll();
                    mainWindow.frame.add(m.rezerwacjaPanel, BorderLayout.CENTER);
                    mainWindow.frame.setTitle("BD PROJEKT - Oplata - Rezygnacja");
                    mainWindow.frame.validate();
                }
            }
        });
        moje_rezerwacje_panel.add(rezygnujButton);
        
        dodajSegment(3);

        menuButton = new JButton("Powrot do menu");
        menuButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                GUI_Menu m = new GUI_Menu(a, mainWindow, login_id);
                mainWindow.frame.getContentPane().removeAll();
                mainWindow.frame.add(m.menuPanel, BorderLayout.CENTER);
                mainWindow.frame.setTitle("BD PROJEKT - Menu");
                mainWindow.frame.validate();
            }
        });
        moje_rezerwacje_Buttonpanel.add(menuButton);

    }

    public void wypelnij(){
        moje_rezerwacje = a.getMojeRezerwacjeInfo(login_id);
        rezerwacjeComboBox = new JComboBox<>();
        if(moje_rezerwacje.size()==0){
            rezerwacjeComboBox.addItem(new GetMojeRezerwacjeInfo(false));
            rezerwacjeComboBox.setEnabled(false);
        }else{
            rezerwacjeComboBox.addItem(new GetMojeRezerwacjeInfo("---------"));
            for(int i = 0; i < moje_rezerwacje.size(); i++){
                rezerwacjeComboBox.addItem(moje_rezerwacje.get(i));
            }
        }
        rezerwacjeComboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                Object item = rezerwacjeComboBox.getSelectedItem();
                chosen_oplata_id = ((GetMojeRezerwacjeInfo)item).oplata_id;
            }
        });
        moje_rezerwacje_Uppanel.add(rezerwacjeComboBox);
        // dodajSegment(3);
    }
    public void dodajSegment(int x){
        for(int i = 0; i < x; i++){
            moje_rezerwacje_panel.add(new JLabel(""));
        }
    }
}