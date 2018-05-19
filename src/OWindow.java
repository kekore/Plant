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
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.ArrayList;

// (O)verview window
public class OWindow extends JFrame implements ChangeListener{
    protected OvrPanel ovrPanel;
    protected int width;
    protected int height;

    public OWindow(){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = 600;
        height = 700;
        setSize(width,height);
        setLocation(screenSize.width/2,screenSize.height/2-350);

        ovrPanel = new OvrPanel();
        add(ovrPanel);

        setResizable(false);
    }

    @Override
    public void stateChanged (ChangeEvent e){
        if (((JSlider) e.getSource()).getName() == "widthSlider") {
            width = ((JSlider) e.getSource()).getValue();
            setSize(width, height);
            ovrPanel.isInitialized=false;
            ovrPanel.environment=null;
            ovrPanel.updateSize();
        } else if (((JSlider) e.getSource()).getName() == "heightSlider") {
            height = ((JSlider) e.getSource()).getValue();
            setSize(width, height);
            ovrPanel.isInitialized=false;
            ovrPanel.environment=null;
            ovrPanel.updateSize();
        }
    }
}

class OvrPanel extends JPanel implements ActionListener, MouseListener, ChangeListener{
    protected int canvasWidth;
    protected int canvasHeight;
    protected Environment environment;
    protected boolean isInitialized;
    private Timer timer;
    private enum Choice{
        NULL, FACTORY, SPAWNER, SEED
    }
    private Choice choice;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private int groundLevel;
    private int seedPosX;
    private boolean mousePressed;

    protected OvrPanel(){
        canvasWidth = (int)getSize().getWidth();
        canvasHeight = (int)getSize().getHeight();
        //environment = new Environment(canvasWidth,canvasHeight,0);
        isInitialized = false;
        choice = Choice.NULL;
        timer = new Timer(15,this);
        timer.start();
        setBackground(Color.WHITE);
        addMouseListener(this);
        groundLevel = 100;
    }
    protected void updateSize(){
        canvasWidth = (int)getSize().getWidth();
        canvasHeight = (int)getSize().getHeight();
    }
    protected void initEnv(){
        environment = new Environment(canvasWidth,canvasHeight,groundLevel,seedPosX);
        isInitialized = true;
    }
    //TODO check if works properly - if all fields are loaded
    private int saveFile(){
        if(!isInitialized) return 4;
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
        if(ret == 0)isInitialized = true;
        return ret;
    }

    @Override
    protected void paintComponent(Graphics g){ //TODO show vectors
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Rectangle2D.Float ground;
        Rectangle2D.Float seed;
        ArrayList<Rect> rList = new ArrayList<Rect>();
        if(!isInitialized){
            updateSize();
            ground = new Rectangle2D.Float(0,canvasHeight-groundLevel,canvasWidth,groundLevel);
            g2d.setColor(new Color(139,69,19));
            g2d.fill(ground);
            seed = new Rectangle2D.Float(seedPosX-3,canvasHeight-groundLevel-3,6,6);
            g2d.setColor(Color.BLUE);
            g2d.fill(seed);
        } else {
            rList.addAll(environment.getRects());
            rList.addAll(environment.getInvisRects());
        }
        for(Rect r : rList){
            g2d.setColor(r.color);
            if(!r.isFilled)g2d.draw(r.rectangle);
            else g2d.fill(r.rectangle);
        }

        if(mousePressed){
            Line2D.Float l = new Line2D.Float(x1,y1,(float)getMousePosition().getX(),(float)getMousePosition().getY());
            g2d.setColor(Color.RED);
            g2d.draw(l);
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
        } else if (((JButton)e.getSource()).getText() == "Posadź ziarno"){
            choice = Choice.SEED;
        } else if (((JButton)e.getSource()).getText() == "Zainicjuj"){
            initEnv();
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        mousePressed = true;
        x1 = e.getX();
        y1 = e.getY();
        System.out.println(x1 + " " + y1);
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mousePressed = false;
        x2 = e.getX();
        y2 = e.getY();
        System.out.println(x2 + " " + y2);
        switch (choice) {
            case FACTORY: {
                if (isInitialized && y1 + 36 < canvasHeight - groundLevel) {
                    environment.addFactory(new Factory(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), 30));
                    System.out.println("Added factory");
                    }
                    break;
                }
            case SPAWNER: {
                if (isInitialized && y1 + 3 < canvasHeight - groundLevel) {
                    Vector2D pos = new Vector2D(x1, y1);
                    Particle p = new Particle(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), new Vector2D(), 1, 10, Particle.Type.TOXIC);
                    environment.addSpawner(new ParticleSpawner(p,ParticleSpawner.Type.STATIC,20,pos,new Vector2D(),new Vector2D(),10));
                }
                break;
            }
            case SEED: {
                isInitialized = false;
                environment = null;
                seedPosX = x1;
                break;
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}

    @Override
    public void stateChanged (ChangeEvent e){
        if(((JSlider) e.getSource()).getName() == "groundSlider"){
            isInitialized = false;
            environment = null;
            groundLevel = ((JSlider) e.getSource()).getValue();
        }
    }
}
