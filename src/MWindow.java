import environmentPack.Environment;
import geneticAlgPack.GeneticAlg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// (M)ain window
public class MWindow extends JFrame{
    //private SWindow simWindow;
    //private EWindow envWindow;

    public MWindow(SWindow s, EWindow e, AWindow a, CWindow c){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(610,220);
        setLocation(4,4);
        //setLayout(new GridLayout(1, 1, 20, 20));
        setLayout(new FlowLayout());

        //simWindow = s;
        //envWindow = e;
        add(new MButtonPanel(s, e, a, c));

        //setResizable(false);
        setVisible(true);
    }
}

class MButtonPanel extends JPanel implements ActionListener{
    private SWindow simWindow;
    private EWindow envWindow;
    private AWindow algWindow;
    private CWindow catWindow;
    private JButton simOneBut;
    private JButton simBut;
    //private JButton w;
    private JButton doubleSpeedBut;
    private JButton quadSpeedBut;
    private JButton halfSpeedBut;
    private JButton envEditBut;
    private JButton algEditBut;
    private JButton loadEnvBut;
    private JButton loadAlgBut;
    private JButton showInvisBut;
    private JButton catBut;
    private JButton quickBut;

    protected MButtonPanel(SWindow simW, EWindow envW, AWindow algW, CWindow catW){
        simWindow = simW;
        envWindow = envW;
        algWindow = algW;
        catWindow = catW;
        //Dimension d = new Dimension(100,100); //niepotrzebne raczej
        //setPreferredSize(d);
        simOneBut = new JButton("Zasymuluj generację");
        simBut = new JButton("Symulacja");
        //w = new JButton("Dodaj");
        doubleSpeedBut = new JButton("2x");
        quadSpeedBut = new JButton("4x");
        halfSpeedBut = new JButton("0.5x");
        envEditBut = new JButton("Edytor środowiska");
        algEditBut = new JButton("Edytor algorytmu");
        loadEnvBut = new JButton("Załaduj środ. z edytora");
        loadAlgBut = new JButton("Załaduj algorytm z edytora");
        showInvisBut = new JButton("Pokaż/ukryj szczegóły");
        catBut = new JButton("Katalog");
        quickBut = new JButton("Szybka symulacja");

        //s.addActionListener(this);
        simOneBut.addActionListener(simW.simPanel);
        simBut.addActionListener(simW);
        //w.addActionListener(this);
        //w.addActionListener((ActionListener)simW.simPanel);
        doubleSpeedBut.addActionListener(simW.simPanel);
        quadSpeedBut.addActionListener(simW.simPanel);
        halfSpeedBut.addActionListener(simW.simPanel);
        envEditBut.addActionListener(envW);
        algEditBut.addActionListener(algW);
        loadEnvBut.addActionListener(this);
        loadAlgBut.addActionListener(this);
        showInvisBut.addActionListener(simW.simPanel);
        catBut.addActionListener(catW);
        quickBut.addActionListener(simW.simPanel);


        setLayout(new GridLayout(4, 3, 20, 20));
        add(envEditBut);
        add(algEditBut);
        add(simBut);
        add(loadEnvBut);
        add(loadAlgBut);
        add(simOneBut);
        //add(w);
        add(quadSpeedBut);
        add(doubleSpeedBut);
        add(halfSpeedBut);
        add(showInvisBut);
        add(catBut);
        add(quickBut);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton) e.getSource()).getText().equals("Załaduj środ. z edytora")){
            Environment environment = envWindow.getEnvironment();
            if(environment != null && !simWindow.simPanel.simulator.isSet()){
                simWindow.setSize(envWindow.overviewWindow.width,envWindow.overviewWindow.height);
                simWindow.simPanel.setEnvironment(environment);
            }
        }
        else if(((JButton) e.getSource()).getText().equals("Załaduj algorytm z edytora")){
            GeneticAlg geneticAlg = algWindow.getAlgorithm();
            if(geneticAlg != null && !simWindow.simPanel.simulator.isSet()){
                simWindow.simPanel.setAlgorithm(geneticAlg);
            }
        }
    }
}