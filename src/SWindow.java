import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;

public class SWindow extends JFrame implements ActionListener{

    public SWindow(){
        super("Simulation");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,400);
        setLocation(screenSize.width/2+250,screenSize.height/2+100);
        setResizable(false);
        c = new Cir();
        add(c);
        //setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Scatch");
        setVisible(true);
        c.sT();
        /*Object source = e.getSource();
        if(source.toString() == "Symulacja"){
            setVisible(true);
        }*/
    }
    protected void update(long n){
        c.setT(n);
    }
    private Cir c;
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
