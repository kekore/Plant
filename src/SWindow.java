import environment.Circle;
import environment.Particle;
import physicsPack.Vector2D;
import simulatorPack.Simulator;

import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.random;

public class SWindow extends JFrame implements ActionListener{

    public SWindow(){
        super("Simulation");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //do zmiany
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
        if(((JButton)e.getSource()).getText() == "Symulacja"){
            if(!siming) {
                setVisible(true);
                siming = true;
                simPanel.start();
            }
            else{
                setVisible(true);
                siming = false;
                simPanel.pause();
            }
        }
    }
    protected SimPanel getPanel(){
        return simPanel;
    }

    private SimPanel simPanel;
    private boolean siming;
}

class SimPanel extends JPanel implements ActionListener{
    private Timer timer;
    private Simulator simulator;
    Random generator;

    SimPanel(){
        simulator = new Simulator(100);
        timer = new Timer(1,this);
        generator = new Random();
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        ArrayList<Circle> sList = simulator.getShapes();
        //ArrayList<Line2D> lList = simulator.getLines();
        for(Circle s : sList){
            g2d.setColor(s.getColor());
            g2d.draw(s.getEllipse());
        }
        /*for(Line2D l : lList){
            g2d.setColor(Color.RED);
            g2d.draw(l);
        }*/
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
        } else if (((JButton) e.getSource()).getText() == "Dodaj") {
            for(int i = 0; i < 1; i++) {
                Vector2D p = new Vector2D((float)generator.nextInt(400) + 50, (float)generator.nextInt(600) + 50);
                Vector2D v = new Vector2D((float)generator.nextInt(50) / 10+10, (float)generator.nextInt(50) / 10+10);
                simulator.addP(p, v, 1, generator.nextInt(3)+9, Particle.Type.OXYGEN);
            }
        } else if (((JButton) e.getSource()).getText() == "2x"){
            simulator.setSpeed(2);
        } else if (((JButton) e.getSource()).getText() == "4x"){
            simulator.setSpeed(1);
        } else if (((JButton) e.getSource()).getText() == "0.5x"){
            simulator.setSpeed(8);
        }
    }
    protected void start(){simulator.startSimulation();}
    protected void pause(){simulator.pauseSimulation();}
}