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
        s = new SimPanel();
        add(s);
        //setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText() == "Symulacja"){
            setVisible(true);
            s.sT();
        }
    }
    protected SimPanel getPanel(){
        return s;
    }

    private SimPanel s;
}

class SimPanel extends JPanel implements ActionListener{
    private Timer timer;
    private Simulator simulator;

    SimPanel(){
        simulator = new Simulator();
        timer = new Timer(10,this);
        generator = new Random();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        ArrayList<Circle> sList = simulator.getShapes();
        for(Circle s : sList){
            g2d.setColor(s.getColor());
            g2d.draw(s.getEllipse());
        }

    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            //System.out.println(System.currentTimeMillis());
            if(System.currentTimeMillis()-t>100){ //zawsze proc, ale mnozyc przez część czasu!! czyli zmienic proc w physics --TODO--
                t = System.currentTimeMillis(); //procować powinien tylko simulator(mierzyc czas etc.) a repaint panel
                simulator.proc();
            }
            repaint();
        } else if (((JButton) e.getSource()).getText() == "Dodaj") {
            Vector2D p = new Vector2D(generator.nextInt(400)+50,generator.nextInt(600)+50);
            Vector2D v = new Vector2D(generator.nextInt(50)/10,generator.nextInt(50)/10);
            simulator.addP(new Particle(p,v,1,10,Particle.Type.OXYGEN));
        }
    }
    protected void sT(){
        timer.start();
    }
    private long t;
    Random generator;
}

class Cir extends JPanel implements MouseMotionListener, ActionListener{
    Cir(){
        x=10;
        y=20;
        r=8;
        d=0;
        timer = new Timer(100, this);
        addMouseMotionListener(this);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D)g;
        g2d.setColor(Color.darkGray);
        c = new Ellipse2D.Float(x,y+d,r,r);
        g2d.draw(c);
    }
    @Override
    public void mouseMoved(MouseEvent e){
        x = e.getX();
        y = e.getY();
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){

    }
    public void setT(long n){
        t = n;
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            d++;
            repaint();
        }
    }
    protected void sT(){
        timer.start();
    }

    private int x,y,r,d;
    private long t;
    private Graphics2D g2d;
    private Ellipse2D c;
    private Timer timer;
}
