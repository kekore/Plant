import environmentPack.Circle;
import environmentPack.Particle;
import physicsPack.Vector2D;
import simulatorPack.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
// (S)imulation window
public class SWindow extends JFrame implements ActionListener{

    public SWindow(){
        super("Podgląd symulacji");
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
    private JTextField timeText;
    private JTextField fpsText;
    private long secStart;
    private int frames;

    protected SimPanel(){
        simulator = new Simulator(100,null);
        timer = new Timer(15,this);
        generator = new Random();
        timer.start();
        setLayout(null);

        timeText = new JTextField();
        timeText.setBounds(1,0,100,20);
        timeText.setEditable(false);
        timeText.setBorder(null);
        add(timeText);

        fpsText = new JTextField();
        fpsText.setBounds(1, 18, 60, 20);
        fpsText.setEditable(false);
        fpsText.setBorder(null);
        add(fpsText);

        frames = 0;
        secStart = System.currentTimeMillis();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        ArrayList<Circle> cList = simulator.getShapes();
        ArrayList<Line2D> lList = simulator.getLines();
        ArrayList<Rectangle2D> rList = simulator.getRects();
        for(Circle c : cList){
            g2d.setColor(c.getColor());
            g2d.draw(c.getEllipse());
        }
        for(Line2D l : lList){
            g2d.setColor(Color.RED);
            g2d.draw(l);
        }
        for(Rectangle2D r : rList){
            g2d.setColor(Color.BLACK);
            g2d.draw(r);
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
                fpsText.setText("FPS: " + new Integer(frames).toString());
                frames = 0;
                secStart = System.currentTimeMillis();
            }
            timeText.setText("Time: " + simulator.getTime().toString());
        } else if (((JButton) e.getSource()).getText() == "Dodaj") {
            for(int i = 0; i < 1; i++) {
                /*Vector2D p = new Vector2D((float)generator.nextInt(400) + 50, (float)generator.nextInt(600) + 50);
                Vector2D v = new Vector2D((float)generator.nextInt(50) / 10+10, (float)generator.nextInt(50) / 10+10);*/
                Vector2D p = new Vector2D(50,50);
                Vector2D v = new Vector2D(30,200);
                Vector2D f = new Vector2D(0,-50);
                simulator.addP(p, v, f, 1, 10, Particle.Type.OXYGEN);
            }
        } else if (((JButton) e.getSource()).getText() == "2x"){
            simulator.setSpeed(2);
        } else if (((JButton) e.getSource()).getText() == "4x"){
            simulator.setSpeed(1);
        } else if (((JButton) e.getSource()).getText() == "0.5x"){
            simulator.setSpeed(64);
        }
    }
    protected void start(){simulator.startSimulation();}
    protected void pause(){simulator.pauseSimulation();}
}