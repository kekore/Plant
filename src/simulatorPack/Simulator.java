package simulatorPack;

import environmentPack.Circle;
import environmentPack.Environment;
import environmentPack.Particle;
import environmentPack.Rect;
import geneticAlgPack.GeneticAlg;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Simulator implements ActionListener{
    private GeneticAlg geneticAlg;
    private Environment environment;
    private boolean isSet;
    private long tickTime;
    //private boolean quickSim;
    private Timer timer;
    private int speed;
    private int cycle;

    public Simulator(long tT, Environment environment){ //TODO algorythm as parameter
        if(environment != null){                        //TODO add quick simulation thread class
            this.environment = environment;
            isSet = true;
        } else isSet = false;
        //else this.environment = new Environment(600,700,100,20,12,50,50);
        tickTime = tT;
        //quickSim = false;
        timer = new Timer(1,this);
        speed = 4;
        cycle = 0;
    }
    private void addP(Particle p){
        environment.addParticle(p);
    }
    public void addP(Vector2D p, Vector2D v, Vector2D f, float m, int r, Particle.Type t){
        addP(new Particle(p,v,f,m,r,t));
    }
    private void proc(){
        //timer.stop(); //stop and start zeby wykonywal raz w jednym momencie
        environment.proc(tickTime);
        //TODO have to check if simulation is finished and then do some genetic alg stuff
        //timer.start();
    }
    public ArrayList<Circle> getCircles(){
        return environment.getCircles();
    }
    public ArrayList<Line2D> getLines(){
        return environment.getLines();
    }
    public ArrayList<Line2D> getInvisLines(){
        return environment.getInvisLines(tickTime);
    }
    public ArrayList<Rect> getRects(){
        return environment.getRects();
    }
    public ArrayList<Rect> getInvisRects() { return environment.getInvisRects(); }
    public void startSimulation(){
        timer.start();
    }
    public void pauseSimulation(){
        timer.stop();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(cycle >= speed){
            proc();
            cycle = 0;
        }
        else{
            cycle++;
        }
    }
    public void setSpeed(int a){speed = a;}
    public void setEnvironment(Environment environment){
        this.environment = environment;
        isSet = true;
    }
    public void setAlgorithm(GeneticAlg geneticAlg){
        this.geneticAlg = geneticAlg;
        //isSet...
    }
    public long getTime(){
        if(!isSet) return 0;
        return environment.getTime();
    }
    public int getPoints(){
        if(!isSet) return -2;
        return environment.getPoints();
    }
    public boolean isSet() { return isSet; }
}
