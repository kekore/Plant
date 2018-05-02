import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class MWindow extends JFrame{

    public MWindow(SWindow s){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,500);
        setLocation(screenSize.width/4-200,screenSize.height/4-150);
        setLayout(new GridLayout(2, 1, 20, 20));
        //setLayout(new FlowLayout(FlowLayout.CENTER));

        simWindow = s;
        JPanel bP = new ButtonPanel(simWindow);
        add(bP);

        setResizable(false);
        setVisible(true);
    }

    private SWindow simWindow;
}

class ButtonPanel extends JPanel{
    private JButton s;
    private JButton w;

    public ButtonPanel(SWindow simW){
        Dimension d = new Dimension(100,100);
        setPreferredSize(d);
        s = new JButton("Symulacja");
        w = new JButton("Dodaj");

        //s.addActionListener(this);
        s.addActionListener((ActionListener)simW);
        //w.addActionListener(this);
        w.addActionListener((ActionListener)simW.getPanel());

        setLayout(new GridLayout(1, 2, 20, 20));
        add(s);
        add(w);
        //simV = false;
    }

    /*@Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == s && !simV){
            simV = true;
        }
    }
    private boolean simV;*/
}