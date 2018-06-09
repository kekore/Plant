import environmentPack.Circle;
import environmentPack.EnvironmentController;
import environmentPack.Rect;
import geneticAlgPack.GeneticAlgController;
import javafx.util.Pair;
import simulatorPack.SimulatorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Klasa okna, która trzyma panel z symulatorem i wyświetla wizualizację postępu aktualnej symulacji.
 */
public class SWindow extends JFrame implements ActionListener{
    /** Panel z symulatorem*/
    protected SimPanel simPanel;

    /**
     * Konstruktor okna symulacji - ustawia nazwę okna, rozmiar, wyłącza działanie przycisku [X], dezaktywuje zmianę wielkości okna, tworzy panel
     * z symulatorem i dodaje go do okna.
     * @param parent Przekaże wskazanie do panelu w celu umożliwienia zmieny tekstu informującego o trybie symulacji.
     */
    public SWindow(MButtonPanel parent){
        super("Podgląd symulacji");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);
        setResizable(false);
        simPanel = new SimPanel(parent);
        add(simPanel);
    }

    /**
     * Obsługuje włączanie i wyłączanie okna oraz jego odświeżania.
     * @param e Zdarzenie
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Symulacja")){
            if(isVisible()){
                setVisible(false);
                simPanel.timer.stop();
            } else {
                setVisible(true);
                simPanel.timer.start();
            }
        }
    }
}

/**
 * Panel wyświetlający wizualizację środowiska oraz informacje w lewym górnym rogu okna.
 */
class SimPanel extends JPanel implements ActionListener{
    /**Odnośnik potrzebny do ustawienia informacji o trybie symulacji.*/
    private MButtonPanel mButtonPanel;
    /**Timer odświeżający obraz w oknie.*/
    protected Timer timer;
    /**Kontroller do komunikacji z symulatorem.*/
    protected SimulatorController simulatorController;
    /**Wyświetla informację o aktualnym czasie(momencie) w środowisku*/
    private JTextField timeText;
    /**Wyświetla liczbę odświeżeń obrazu na sekundę.*/
    private JTextField fpsText;
    /**Wyświetla aktualną punktację drzewa umieszczonego w środowisku.*/
    private JTextField fitText;
    /**Wyświetla aktualne nasycenie drzewa umieszczonego w środowisku.*/
    private JTextField satText;
    /**Wyświetla liczbę operacji wykonywanych przez symulator na sekundę.*/
    private JTextField ppsText;
    /**Wyświetla informację o wykonywaniu szybkiej symulacji.*/
    private JTextField progressText;
    /**Moment rozpoczęcia odliczania 1000ms - dla liczenia fps.*/
    private long secStart;
    /**Liczba klatek naliczona w danej sekundzie.*/
    private int frames;
    /**<i>true</i> - pokazywanie ukrytych szczegółów*/
    private boolean showInvis;

    /**
     * Tworzy obiekt {@link SimulatorController}, ustawia tło i layout panelu oraz dodaje elementy tekstowe.
     * @param parent Odnośnik potrzebny do ustawienia informacji o trybie symulacji.
     */
    protected SimPanel(MButtonPanel parent){
        mButtonPanel = parent;
        simulatorController = new SimulatorController(200);
        timer = new Timer(1,this);
        showInvis = false;
        timer.start();
        setLayout(null);
        setBackground(Color.WHITE);

        timeText = new JTextField();
        timeText.setBounds(1,0,100,20);
        timeText.setEditable(false);
        timeText.setBorder(null);
        timeText.setBackground(Color.WHITE);
        add(timeText);

        fpsText = new JTextField();
        fpsText.setBounds(1, 18, 60, 20);
        fpsText.setEditable(false);
        fpsText.setBorder(null);
        fpsText.setBackground(Color.WHITE);
        add(fpsText);

        fitText = new JTextField();
        fitText.setBounds(1,36,100,20);
        fitText.setEditable(false);
        fitText.setBorder(null);
        fitText.setBackground(Color.WHITE);
        add(fitText);

        satText = new JTextField();
        satText.setBounds(1,54,100,20);
        satText.setEditable(false);
        satText.setBorder(null);
        satText.setBackground(Color.WHITE);
        add(satText);

        ppsText = new JTextField();
        ppsText.setBounds(1,72,60,20);
        ppsText.setEditable(false);
        ppsText.setBorder(null);
        ppsText.setBackground(Color.WHITE);
        add(ppsText);

        progressText = new JTextField();
        progressText.setBounds(1,90,100,20);
        progressText.setEditable(false);
        progressText.setBorder(null);
        progressText.setBackground(Color.WHITE);
        add(progressText);
        progressText.setVisible(false);

        frames = 0;
        secStart = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        if(simulatorController.isQuickSim())return;
        ArrayList<Circle> cList = simulatorController.getCircles();
        ArrayList<Line2D> lList = simulatorController.getLines();
        ArrayList<Rect> rList = simulatorController.getRects();
        ArrayList<Pair<Line2D,Color>> bList = simulatorController.getBranchLines();
        if(showInvis){
            lList.addAll(simulatorController.getInvisLines());
            rList.addAll(simulatorController.getInvisRects());
        }
        for(Circle c : cList){
            g2d.setColor(c.color);
            if(!c.isFilled()) g2d.draw(c.ellipse);
            else g2d.fill(c.ellipse);
        }
        for(Line2D l : lList){
            g2d.setColor(Color.RED);
            g2d.draw(l);
        }
        for(Rect r : rList){
            g2d.setColor(r.color);
            if(!r.isFilled())g2d.draw(r.rectangle);
            else g2d.fill(r.rectangle);
        }
        for(Pair<Line2D,Color> pair : bList){
            g2d.setColor(pair.getValue());
            g2d.draw(pair.getKey());
        }
    }

    /**
     * Jeśli nadeszło zdarzenie {@link #timer}a to aktualizuje napisy. Jeśli nadeszło zdarzenie przycisku to wywołuje odpowiednią metodę.
     * @param e Zdarzenie
     */
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
            if(System.currentTimeMillis()-secStart < 1000){
                frames++;
            }
            else{
                fpsText.setText("FPS: " + frames);
                frames = 0;
                secStart = System.currentTimeMillis();
            }
            timeText.setText("Time: " + simulatorController.getTime());
            int fit = (int)simulatorController.getPoints();
            fitText.setText("Fitness: " + fit);
            int sat = (int)simulatorController.getSatiety();
            if(sat >= 0) satText.setText("Satiety: " + sat);
            else satText.setText("Satiety: -");
            ppsText.setText("PPS: "+simulatorController.getPPS());
            if(simulatorController.isQuickSim()){
                progressText.setVisible(true);
                progressText.setText("Szybka symulacja");
            } else progressText.setVisible(false);
        } else if (((JButton) e.getSource()).getText().equals("Szybkość symulacji: 2x")){
            simulatorController.setSpeed(2);
        } else if (((JButton) e.getSource()).getText().equals("Szybkość symulacji: 4x")){
            simulatorController.setSpeed(1);
        } else if (((JButton) e.getSource()).getText().equals("Szybkość symulacji: 0.5x")){
            simulatorController.setSpeed(8);
        } else if (((JButton) e.getSource()).getText().equals("Pokaż/ukryj szczegóły")){
            showInvis = !showInvis;
        } else if (((JButton) e.getSource()).getText().equals("Szybka symulacja")){
            if(!simulatorController.isQuickSim()) mButtonPanel.simState.setText("Szybka symulacja");
            else if(mButtonPanel.simulationProcingTimer.isRunning()) mButtonPanel.simState.setText("Symulacja włączona");
            else mButtonPanel.simState.setText("Symulacja zapauzowana");
            simulatorController.alterQuickSim();
        } else if (((JButton) e.getSource()).getText().equals("Zasymuluj generację")){
            simulatorController.simulateGeneration();
        }
    }
    protected void setEnvironmentController(EnvironmentController environmentController){
        simulatorController.setEnvironmentController(environmentController);
    }
    protected void setAlgorithmController(GeneticAlgController geneticAlgController) {
        simulatorController.setAlgorithmController(geneticAlgController);
    }
}