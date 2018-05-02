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
        setSize(600,400);
        setLocation(screenSize.width/4-300,screenSize.height/4-150);
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
    private JButton t;
    private JButton q;
    private JButton h;

    public ButtonPanel(SWindow simW){
        Dimension d = new Dimension(100,100);
        setPreferredSize(d);
        s = new JButton("Symulacja");
        w = new JButton("Dodaj");
        t = new JButton("2x");
        q = new JButton("4x");
        h = new JButton("0.5x");

        //s.addActionListener(this);
        s.addActionListener((ActionListener)simW);
        //w.addActionListener(this);
        w.addActionListener((ActionListener)simW.getPanel());
        t.addActionListener((ActionListener)simW.getPanel());
        q.addActionListener((ActionListener)simW.getPanel());
        h.addActionListener((ActionListener)simW.getPanel());

        setLayout(new GridLayout(1, 2, 20, 20));
        add(s);
        add(w);
        add(t);
        add(q);
        add(h);
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