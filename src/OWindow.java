import environmentPack.*;
import physicsPack.Vector2D;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;

// (O)verview window
public class OWindow extends JFrame implements ChangeListener, ActionListener{
    protected OvrPanel ovrPanel;
    private int width;
    private int height;
    private boolean isApplied;

    public OWindow(){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);
        width = 600;
        height = 700;
        isApplied = false;

        ovrPanel = new OvrPanel();
        add(ovrPanel);

        setResizable(false);
    }

    @Override
    public void stateChanged (ChangeEvent e){
        //System.out.println(((JSlider)e.getSource()).getName());
        if(!isApplied) {
            if (((JSlider) e.getSource()).getName() == "widthSlider") {
                width = ((JSlider) e.getSource()).getValue();
                setSize(width, height);
            } else if (((JSlider) e.getSource()).getName() == "heightSlider") {
                height = ((JSlider) e.getSource()).getValue();
                setSize(width, height);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText() == "Zainicjuj"){
            ovrPanel.initEnv(width,height);
            isApplied = true;
        }
    }
}

class OvrPanel extends JPanel implements ActionListener, MouseListener{
    protected Environment environment;
    private boolean isInitialized;
    private Timer timer;
    private enum Choice{
        NULL, FACTORY, SPAWNER
    }
    private Choice choice;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    protected OvrPanel(){
        environment = new Environment(0,0);
        choice = Choice.NULL;
        timer = new Timer(15,this);
        timer.start();
        setBackground(Color.WHITE);
        addMouseListener(this);
    }
    protected void initEnv(int canvasWidth, int canvasHeight){
        environment = new Environment(canvasWidth,canvasHeight);
        isInitialized = true;
    }
    //TODO check if works properly - if all fields are loaded
    private int saveFile(){
        int ret = 0;
        try {
            FileOutputStream fos = new FileOutputStream(new File("environment.env"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            System.out.println("oos:");
            oos.writeObject(environment);
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            ret = 1;
        } catch (IOException e) {
            System.out.println("Error initializing stream!");
            ret = 2;
        } catch (Exception e) {
            System.out.println("Unexpected exception!");
            ret = 3;
        }
        return ret;
    }

    private int loadFile(){
        int ret = 0;
        try {
            FileInputStream fis = new FileInputStream(new File("environment.env"));
            System.out.println("fis");
            ObjectInputStream ois = new ObjectInputStream(fis);
            System.out.println("ois");
            environment = (Environment) ois.readObject();
            //environment = read;
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            ret = 1;
        } catch (IOException e) {
            System.out.println("Error initializing stream!");
            ret = 2;
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found exception!");
            ret = 3;
        } catch (Exception e) {
            System.out.println("Unexpected exception!");
            ret = 4;
        }
        return ret;
    }

    @Override
    protected void paintComponent(Graphics g){ //TODO show vectors
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        ArrayList<Rect> rList = environment.getRects();
        rList.addAll(environment.getInvisRects());
        for(Rect r : rList){
            g2d.setColor(r.color);
            g2d.draw(r.rectangle);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
        } else if(((JButton)e.getSource()).getText() == "Dodaj fabrykę"){
            System.out.println("fabryka"); //TODO erase it
            choice = Choice.FACTORY;
        } else if(((JButton)e.getSource()).getText() == "Dodaj spawner"){
            choice = Choice.SPAWNER;
        } else if(((JButton)e.getSource()).getText() == "Zapisz do pliku"){
            System.out.println("Save file returned: " + saveFile()); //TODO change this
        } else if (((JButton)e.getSource()).getText() == "Wczytaj z pliku"){
            System.out.println(environment);
            System.out.println("Load file returned: " + loadFile()); //TODO change this
            System.out.println(environment);
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
        if(isInitialized) {
            x2 = e.getX();
            y2 = e.getY();
            System.out.println(x2 + " " + y2);
            switch (choice) {
                case FACTORY: {
                    environment.addFactory(new Factory(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1)));
                    System.out.println("Added factory");
                    break;
                }
                case SPAWNER: {
                    Particle p = new Particle(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), new Vector2D(), 1, 10, Particle.Type.TOXIC);
                    environment.addSpawner(new ParticleSpawner(p, 20));
                    break;
                }
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
