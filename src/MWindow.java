import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// (M)ain window
public class MWindow extends JFrame{
    private SWindow simWindow;
    private EWindow envWindow;

    public MWindow(SWindow s, EWindow e){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,300);
        setLocation(screenSize.width/4-300,screenSize.height/4-150);
        setLayout(new GridLayout(1, 1, 20, 20));
        //setLayout(new FlowLayout(FlowLayout.CENTER));

        simWindow = s;
        envWindow = e;
        JPanel bP = new ButtonPanel(simWindow, envWindow);
        add(bP);

        setResizable(false);
        setVisible(true);
    }
}

class ButtonPanel extends JPanel{
    private JButton s;
    private JButton w;
    private JButton t;
    private JButton q;
    private JButton h;
    private JButton envEditBut;

    public ButtonPanel(SWindow simW, EWindow envW){
        Dimension d = new Dimension(100,100);
        setPreferredSize(d);
        s = new JButton("Symulacja");
        w = new JButton("Dodaj");
        t = new JButton("2x");
        q = new JButton("4x");
        h = new JButton("0.5x");
        envEditBut = new JButton("Edytor środowiska");

        //s.addActionListener(this);
        s.addActionListener((ActionListener)simW);
        //w.addActionListener(this);
        w.addActionListener((ActionListener)simW.getPanel());
        t.addActionListener((ActionListener)simW.getPanel());
        q.addActionListener((ActionListener)simW.getPanel());
        h.addActionListener((ActionListener)simW.getPanel());
        envEditBut.addActionListener((ActionListener)envW);

        setLayout(new GridLayout(2, 2, 20, 20));
        add(s);
        add(w);
        add(t);
        add(q);
        add(h);
        add(envEditBut);
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