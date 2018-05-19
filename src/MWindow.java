import environmentPack.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// (M)ain window
public class MWindow extends JFrame{
    //private SWindow simWindow;
    //private EWindow envWindow;

    public MWindow(SWindow s, EWindow e){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //do zmiany... albo nie?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,300);
        setLocation(screenSize.width/4-300,screenSize.height/4-150);
        setLayout(new GridLayout(1, 1, 20, 20));
        //setLayout(new FlowLayout(FlowLayout.CENTER));

        //simWindow = s;
        //envWindow = e;
        add(new MButtonPanel(s, e));

        setResizable(false);
        setVisible(true);
    }
}

class MButtonPanel extends JPanel implements ActionListener{
    private SWindow simWindow;
    private EWindow envWindow;
    private JButton s;
    private JButton w;
    private JButton t;
    private JButton q;
    private JButton h;
    private JButton envEditBut;
    private JButton loadEnvBut;
    private JButton showInvisBut;

    protected MButtonPanel(SWindow simW, EWindow envW){
        simWindow = simW;
        envWindow = envW;
        //Dimension d = new Dimension(100,100); //niepotrzebne raczej
        //setPreferredSize(d);
        s = new JButton("Symulacja");
        w = new JButton("Dodaj");
        t = new JButton("2x");
        q = new JButton("4x");
        h = new JButton("0.5x");
        envEditBut = new JButton("Edytor środowiska");
        loadEnvBut = new JButton("Załaduj środowisko z edytora");
        showInvisBut = new JButton("Pokaż/ukryj");

        //s.addActionListener(this);
        s.addActionListener((ActionListener)simW);
        //w.addActionListener(this);
        w.addActionListener((ActionListener)simW.simPanel);
        t.addActionListener((ActionListener)simW.simPanel);
        q.addActionListener((ActionListener)simW.simPanel);
        h.addActionListener((ActionListener)simW.simPanel);
        envEditBut.addActionListener((ActionListener)envW);
        loadEnvBut.addActionListener((ActionListener)this);
        showInvisBut.addActionListener((ActionListener)simW.simPanel);


        setLayout(new GridLayout(2, 2, 20, 20));
        add(s);
        add(w);
        add(t);
        add(q);
        add(h);
        add(envEditBut);
        add(loadEnvBut);
        add(showInvisBut);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton) e.getSource()).getText() == "Załaduj środowisko z edytora"){
            Environment environment = envWindow.getEnvironment();
            if(environment != null){
                simWindow.setSize(envWindow.overviewWindow.width,envWindow.overviewWindow.height);
                simWindow.simPanel.setEnvironment(environment);
            }
        }
    }
}