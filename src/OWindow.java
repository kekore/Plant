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
public class OWindow extends JFrame implements ChangeListener, ActionListener{
    protected OvrPanel ovrPanel;
    private EWindow eWindow;
    protected int width;
    protected int height;

    public OWindow(EWindow e){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        width = 600;
        height = 700;
        setSize(width,height);
        setLocation(screenSize.width/2,6);

        eWindow = e;
        ovrPanel = new OvrPanel();
        add(ovrPanel);

        setResizable(false);
    }

    @Override
    public void stateChanged (ChangeEvent e){
        if (((JSlider) e.getSource()).getName().equals("widthSlider")) {
            width = ((JSlider) e.getSource()).getValue();
            setSize(width, height);
            ovrPanel.noInit();
            ovrPanel.updateSize();
            if(ovrPanel.seedPosX > ovrPanel.getSize().getWidth()-10) ovrPanel.seedPosX = (int)ovrPanel.getSize().getWidth()-20;
        } else if (((JSlider) e.getSource()).getName().equals("heightSlider")) {
            height = ((JSlider) e.getSource()).getValue();
            setSize(width, height);
            ovrPanel.noInit();
            ovrPanel.updateSize();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Zapisz do pliku")){
            ovrPanel.saveFile(width,height);
        } else if(((JButton)e.getSource()).getText().equals("Wczytaj z pliku")){
            if(ovrPanel.loadFile() == 0) eWindow.alterPage();
            Dimension size = ovrPanel.environment.getWindowSize();
            width = size.width;
            height = size.height;
            setSize(size);
        } else if (((JButton)e.getSource()).getText().equals("Utwórz inne")){
            setSize(600,700);
            ovrPanel.updateSize();
        }
    }
}

class OvrPanel extends JPanel implements ActionListener, MouseListener, ChangeListener{
    protected int canvasWidth;
    protected int canvasHeight;
    protected Environment environment;
    protected boolean isInitialized;
    protected Timer timer;
    private enum Choice{
        NULL, FACTORY, SPAWNER, SEED, NUCLEAR
    }
    private Choice choice;
    private int x1;
    private int y1;
    private int x2;
    private int y2;
    private boolean mousePressed;

    private int groundLevel;
    protected int seedPosX;
    private int sunTime;
    private int rainFrequency;
    private int rainIntensity;
    private Wind.Direction dir1;
    private Wind.Direction dir2;

    protected OvrPanel(){
        canvasWidth = (int)getSize().getWidth();
        canvasHeight = (int)getSize().getHeight();
        isInitialized = false;
        choice = Choice.SEED;
        timer = new Timer(15,this);
        setBackground(Color.WHITE);
        addMouseListener(this);
        groundLevel = 100;
        seedPosX = 20;
        sunTime = 12;
        rainFrequency = 50;
        rainIntensity = 50;
        dir1 = Wind.Direction.EAST;
        dir2 = Wind.Direction.WEST;
    }
    protected void updateSize(){
        canvasWidth = (int)getSize().getWidth();
        canvasHeight = (int)getSize().getHeight();
    }
    protected void initEnv(){
        environment = new Environment(canvasWidth,canvasHeight,groundLevel,seedPosX,Math.abs(sunTime*100/24),sunTime>0,rainFrequency,rainIntensity,dir1,dir2);
        isInitialized = true;
    }
    protected void noInit(){
        isInitialized = false;
        environment = null;
    }

    protected int saveFile(int windowWidth, int windowHeight){
        if(!isInitialized) return 4;
        int ret = 0;
        environment.saveWindowSize(windowWidth, windowHeight);
        try {
            FileOutputStream fos = new FileOutputStream(new File("environment.env"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
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

    protected int loadFile(){
        int ret = 0;
        try {
            FileInputStream fis = new FileInputStream(new File("environment.env"));
            ObjectInputStream ois = new ObjectInputStream(fis);
            environment = (Environment) ois.readObject();
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
            if(!r.isFilled())g2d.draw(r.rectangle);
            else g2d.fill(r.rectangle);
        }

        if(mousePressed){
            Line2D.Float l = new Line2D.Float();
            if(getMousePosition() != null)l = new Line2D.Float(x1,y1,(float)(getMousePosition().getX()),((float)getMousePosition().getY()));
            g2d.setColor(Color.RED);
            g2d.draw(l);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == timer){
            repaint();
        } else if(((JButton)e.getSource()).getText().equals("Dodaj fabrykę")){
            choice = Choice.FACTORY;
        } else if(((JButton)e.getSource()).getText().equals("Dodaj elektrownię nuklearną")){
            choice = Choice.NUCLEAR;
        } else if(((JButton)e.getSource()).getText().equals("Dodaj źródło tlenu")){
            choice = Choice.SPAWNER;
        } else if (((JButton)e.getSource()).getText().equals("Posadź ziarno")){
            choice = Choice.SEED;
        } else if (((JButton)e.getSource()).getText().equals("Zainicjuj")){
            choice = Choice.NULL;
            initEnv();
        } else if(((JButton)e.getSource()).getText().equals("Utwórz inne")){
            noInit();
            choice = Choice.SEED;
            groundLevel = 100;
            seedPosX = 20;
            sunTime = 12;
            rainFrequency = 50;
            rainIntensity = 50;
            dir1 = Wind.Direction.EAST;
            dir2 = Wind.Direction.WEST;
        }
    }

    @Override
    public void mousePressed(MouseEvent e){
        x1 = e.getX();
        y1 = e.getY();
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e){
        mousePressed = false;
        x2 = e.getX();
        y2 = e.getY();
        switch (choice) {
            case FACTORY: {
                if (isInitialized && y1 + 36 < canvasHeight - groundLevel) {
                    environment.addFactory(new Factory(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), 30,Factory.Type.NORMAL));
                }
                break;
            }
            case NUCLEAR:{
                if (isInitialized && y1 + 36 < canvasHeight - groundLevel) {
                    environment.addFactory(new Factory(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), 35,Factory.Type.TOXIC));
                }
                break;
            }
            case SPAWNER: {
                if (isInitialized && y1 + 3 < canvasHeight - groundLevel) {
                    Vector2D pos = new Vector2D(x1, y1);
                    Particle p = new Particle(new Vector2D(x1, y1), new Vector2D(x2 - x1, y2 - y1), new Vector2D(), 1, 5, Particle.Type.OXYGEN);
                    environment.addSpawner(new ParticleSpawner(p,true,15,pos,new Vector2D(),new Vector2D(),10));
                }
                break;
            }
            case SEED: {
                noInit();
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
        if(((JSlider) e.getSource()).getName().equals("groundSlider")){
            noInit();
            groundLevel = ((JSlider) e.getSource()).getValue();
        } else if(((JSlider) e.getSource()).getName().equals("sunSlider")){
            noInit();
            sunTime = ((JSlider) e.getSource()).getValue();
        } else if(((JSlider) e.getSource()).getName().equals("rainFreqSlider")){
            noInit();
            rainFrequency = ((JSlider) e.getSource()).getValue();
        } else if(((JSlider) e.getSource()).getName().equals("rainIntSlider")){
            noInit();
            rainIntensity = ((JSlider) e.getSource()).getValue();
        } else if(((JSlider) e.getSource()).getName().equals("firstWindSlider")){
            noInit();
            switch (((JSlider) e.getSource()).getValue()){
                case 0: { dir1 = Wind.Direction.NORTH; }
                case 1: { dir1 = Wind.Direction.EAST; }
                case 2: { dir1 = Wind.Direction.SOUTH; }
                case 3: { dir1 = Wind.Direction.WEST; }
            }
        } else if(((JSlider) e.getSource()).getName().equals("secondWindSlider")){
            noInit();
            switch (((JSlider) e.getSource()).getValue()){
                case 0: { dir2 = Wind.Direction.NORTH; }
                case 1: { dir2 = Wind.Direction.EAST; }
                case 2: { dir2 = Wind.Direction.SOUTH; }
                case 3: { dir2 = Wind.Direction.WEST; }
            }
        }
    }
}
