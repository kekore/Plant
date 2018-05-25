import environmentPack.Circle;
import environmentPack.Environment;
import environmentPack.Particle;
import environmentPack.Rect;
import geneticAlgPack.GeneticAlg;
import javafx.util.Pair;
import physicsPack.Vector2D;
import simulatorPack.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
// (S)imulation window
public class SWindow extends JFrame implements ActionListener{

    protected SimPanel simPanel;
    private boolean siming;

    public SWindow(){
        super("Podgląd symulacji");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);
        setResizable(false);
        simPanel = new SimPanel();
        add(simPanel);
        siming = false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Symulacja")){
            if(!siming) {
                if(simPanel.simulator.isSet()) {
                    setVisible(true);
                    siming = true;
                    simPanel.start();
                }
            }
            else{
                setVisible(true);
                siming = false;
                simPanel.pause();
            }
        }
    }
}

class SimPanel extends JPanel implements ActionListener{

    private Timer timer;
    protected Simulator simulator;
    //Random generator;
    private JTextField timeText;
    private JTextField fpsText;
    private JTextField fitText;
    private long secStart;
    private int frames;
    private boolean showInvis;

    protected SimPanel(){
        simulator = new Simulator(200,null);
        timer = new Timer(1,this);
        //generator = new Random();
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

        frames = 0;
        secStart = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        ArrayList<Circle> cList = simulator.getCircles();
        ArrayList<Line2D> lList = simulator.getLines();
        ArrayList<Rect> rList = simulator.getRects();
        ArrayList<Pair<Line2D,Color>> bList = simulator.getBranchLines();
        if(showInvis){
            lList.addAll(simulator.getInvisLines());
            rList.addAll(simulator.getInvisRects());
        }
        for(Circle c : cList){
            g2d.setColor(c.color);
            g2d.draw(c.ellipse);
        }
        for(Line2D l : lList){
            g2d.setColor(Color.GREEN);
            g2d.draw(l);
        }
        for(Rect r : rList){
            g2d.setColor(r.color);
            if(!r.isFilled)g2d.draw(r.rectangle);
            else g2d.fill(r.rectangle);
        }
        for(Pair<Line2D,Color> pair : bList){
            g2d.setColor(pair.getValue());
            g2d.draw(pair.getKey());
        }
    }

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
            timeText.setText("Time: " + simulator.getTime());
            int fit = (int)simulator.getPoints();
            if(fit >= 0) fitText.setText("Fitness: " + fit);
            else fitText.setText("Fitness: -");
        } else if (((JButton) e.getSource()).getText().equals("Dodaj")) {
            for(int i = 0; i < 1; i++) {
                /*Vector2D p = new Vector2D((float)generator.nextInt(400) + 50, (float)generator.nextInt(600) + 50);
                Vector2D v = new Vector2D((float)generator.nextInt(50) / 10+10, (float)generator.nextInt(50) / 10+10);*/
                Vector2D p = new Vector2D(50,50);
                Vector2D v = new Vector2D(30,200);
                Vector2D f = new Vector2D(0,-50);
                simulator.addP(p, v, f, 1, 10, Particle.Type.OXYGEN);
            }
        } else if (((JButton) e.getSource()).getText().equals("2x")){
            simulator.setSpeed(2);
        } else if (((JButton) e.getSource()).getText().equals("4x")){
            simulator.setSpeed(1);
        } else if (((JButton) e.getSource()).getText().equals("0.5x")){
            simulator.setSpeed(64);
        } else if (((JButton) e.getSource()).getText().equals("Pokaż/ukryj")){
            showInvis = !showInvis;
        }
    }
    protected void setEnvironment(Environment environment){
        simulator.setEnvironment(environment);
    }
    protected void setAlgorithm(GeneticAlg geneticAlg) { simulator.setAlgorithm(geneticAlg); }
    protected void start(){simulator.startSimulation();}
    protected void pause(){simulator.pauseSimulation();}
}