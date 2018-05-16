import environmentPack.Environment;
import environmentPack.Factory;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

// (O)verview window
public class OWindow extends JFrame{
    private OvrPanel ovrPanel;

    public OWindow(){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);

        ovrPanel = new OvrPanel();
        add(ovrPanel);

        setResizable(false);
    }

    protected OvrPanel getPanel(){
        return ovrPanel;
    }
}

class OvrPanel extends JPanel implements ActionListener, MouseListener{
    private Environment environment;
    private Timer timer;
    private enum Choice{
        NULL, FACTORY
    }
    private Choice choice;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    protected OvrPanel(){
        environment = new Environment();
        choice = Choice.NULL;
        timer = new Timer(15,this);
        timer.start();
        addMouseListener(this);
    }

    @Override
    protected void paintComponent(Graphics g){ //TODO show vectors
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        ArrayList<Rectangle2D> rList = environment.getRects();
        for(Rectangle2D r : rList){
            g2d.setColor(Color.BLACK);
            g2d.draw(r);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
        }
        else if(((JButton)e.getSource()).getText() == "Dodaj fabrykę"){
            System.out.println("fabryka");
            choice = Choice.FACTORY;
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        x1 = e.getX();
        y1 = e.getY();
        System.out.println(x1 + " " + y1);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        x2 = e.getX();
        y2 = e.getY();
        System.out.println(x2 + " " + y2);
        switch (choice){
            case FACTORY: {
                environment.addFactory(new Factory(new Vector2D(x1,y1), new Vector2D(x2-x1,y2-y1)));
                System.out.println("Added factory");
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}
}
