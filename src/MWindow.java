import environmentPack.EnvironmentController;
import geneticAlgPack.GeneticAlgController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
// (M)enu window
public class MWindow extends JFrame{

    public MWindow(){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(610,220);
        setLocation(4,4);
        setLayout(new FlowLayout());

        add(new MButtonPanel());

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
    private JButton startPauseSimBut;
    protected JTextField simState;
    private JButton doubleSpeedBut;
    private JButton quadSpeedBut;
    private JButton halfSpeedBut;
    private JButton envEditBut;
    private JButton algEditBut;
    private JButton showInvisBut;
    private JButton catBut;
    private JButton quickBut;

    protected Timer simulationProcingTimer;

    protected MButtonPanel(){
        simWindow = new SWindow(this);
        envWindow = new EWindow();
        algWindow = new AWindow();
        catWindow = new CWindow(simWindow);
        simOneBut = new JButton("Zasymuluj generację");
        simBut = new JButton("Symulacja");
        startPauseSimBut = new JButton("Start/pauza symulacji");
        simState = new JTextField("Symulacja zapauzowana");
        simState.setEditable(false);
        doubleSpeedBut = new JButton("Szybkość symulacji: 2x");
        quadSpeedBut = new JButton("Szybkość symulacji: 4x");
        halfSpeedBut = new JButton("Szybkość symulacji: 0.5x");
        envEditBut = new JButton("Edytor środowiska");
        algEditBut = new JButton("Edytor algorytmu");
        showInvisBut = new JButton("Pokaż/ukryj szczegóły");
        catBut = new JButton("Katalog");
        quickBut = new JButton("Szybka symulacja");

        simOneBut.addActionListener(simWindow.simPanel);
        simBut.addActionListener(simWindow);
        startPauseSimBut.addActionListener(this);
        doubleSpeedBut.addActionListener(simWindow.simPanel);
        quadSpeedBut.addActionListener(simWindow.simPanel);
        halfSpeedBut.addActionListener(simWindow.simPanel);
        envEditBut.addActionListener(envWindow);
        envEditBut.addActionListener(this);
        algEditBut.addActionListener(algWindow);
        algEditBut.addActionListener(this);
        showInvisBut.addActionListener(simWindow.simPanel);
        catBut.addActionListener(catWindow);
        quickBut.addActionListener(simWindow.simPanel);
        quickBut.addActionListener(this);

        setLayout(new GridLayout(4, 3, 20, 20));
        add(envEditBut);
        add(algEditBut);
        add(simBut);
        add(startPauseSimBut);
        add(simState);
        add(simOneBut);
        add(quadSpeedBut);
        add(doubleSpeedBut);
        add(halfSpeedBut);
        add(showInvisBut);
        add(catBut);
        add(quickBut);

        simulationProcingTimer = new Timer(1,simWindow.simPanel.simulatorController);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == envEditBut){
            EnvironmentController environmentController = envWindow.getEnvironmentController();
            if(environmentController != null){
                simWindow.setSize(envWindow.overviewWindow.width,envWindow.overviewWindow.height);
                simWindow.simPanel.setEnvironmentController(environmentController);
            }
        }
        else if(e.getSource() == algEditBut){
            GeneticAlgController geneticAlgController = algWindow.getAlgorithmController();
            if(geneticAlgController != null){
                simWindow.simPanel.setAlgorithmController(geneticAlgController);
            }
        }
        else if(e.getSource() == startPauseSimBut){
            if(simulationProcingTimer.isRunning()){
                simulationProcingTimer.stop();
                if(!simState.getText().equals("Szybka symulacja"))simState.setText("Symulacja zapauzowana");
            }
            else{
                simulationProcingTimer.start();
                if(!simState.getText().equals("Szybka symulacja"))simState.setText("Symulacja włączona");
            }
        }
    }
}