package simulatorPack;

import environmentPack.Circle;
import environmentPack.Environment;
import environmentPack.Particle;
import environmentPack.Rect;
import geneticAlgPack.GeneticAlg;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Simulator implements ActionListener{
    //private ArrayList<Particle> pList;
    private GeneticAlg geneticAlg;
    private Environment environment;
    private long tickTime;
    //private boolean quickSim;
    private Timer timer;
    //private Time time;
    //private long time;
    private int speed;
    private int cycle;

    public Simulator(long tT, Environment environment){
        //pList = new ArrayList<Particle>();
        if(environment != null) this.environment = environment;
        else this.environment = new Environment(600,700,100);
        tickTime = tT;
        //quickSim = false;
        timer = new Timer(1,this);
        //time = new Time();
        speed = 4;
        cycle = 1;
        //time = 0;
    }
    private void addP(Particle p){
        environment.addParticle(p);
    }
    public void addP(Vector2D p, Vector2D v, Vector2D f, float m, int r, Particle.Type t){
        addP(new Particle(p,v,f,m,r,t));
    }
    private void proc(){
        environment.proc(tickTime);
        //time++;
    }
    public ArrayList<Circle> getCircles(){
        return environment.getCircles();
    }
    public ArrayList<Line2D> getLines(){
        return environment.getLines(tickTime);
    }
    public ArrayList<Rect> getRects(){
        return environment.getRects();
    }
    public ArrayList<Rect> getInvisRects() { return environment.getInvisRects(); }
    public void startSimulation(){
        timer.start();
        //time.unpause();
    }
    public void pauseSimulation(){
        timer.stop();
        //time.pause();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(cycle >= speed){
            proc();
            cycle = 1;
        }
        else{
            cycle++;
        }
    }
    public void setSpeed(int a){speed = a;}
    public void setEnvironment(Environment environment){
        this.environment = environment;
    }
    public long getTime(){return environment.getTime();}
}
