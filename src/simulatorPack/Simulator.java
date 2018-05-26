package simulatorPack;

import environmentPack.*;
import geneticAlgPack.GeneticAlg;
import geneticAlgPack.Individual;
import javafx.util.Pair;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Simulator implements ActionListener{
    private GeneticAlg geneticAlg;
    private Environment environment;
    private boolean isSet;
    private long tickTime;
    protected boolean quickSim;
    private Timer timer;
    private int speed;
    private int cycle;
    protected final long simulationTime;
    private long secStart;
    private int actions;
    private int PPS;
    private QuickSimThread thread;
    private boolean isWorking;
    private Individual currentIndividual;

    public Simulator(long tT, long simulationTime){ //TODO algorythm as parameter
        isSet = false;
        //else this.environment = new Environment(600,700,100,20,12,50,50);
        tickTime = tT;
        quickSim = false;
        timer = new Timer(1,this);
        speed = 4;
        cycle = 0;
        this.simulationTime = simulationTime;
        secStart = System.currentTimeMillis();
        actions=0;
        thread = new QuickSimThread(this);
        isWorking=false;
    }
    private void addP(Particle p){
        environment.addParticle(p);
    }
    public void addP(Vector2D p, Vector2D v, Vector2D f, float m, int r, Particle.Type t){
        addP(new Particle(p,v,f,m,r,t));
    }
    protected void proc(){
        if(System.currentTimeMillis() - secStart < 1000){
            actions++;
        } else{
            PPS = actions;
            actions = 0;
            secStart = System.currentTimeMillis();
        }
        //timer.stop(); //stop and start zeby wykonywal raz w jednym momencie
        environment.proc(tickTime);
        //TODO have to check if simulation is finished and then do some genetic alg stuff
        //timer.start();
    }

    public ArrayList<Circle> getCircles(){
        if(environment == null) return new ArrayList<Circle>();
        return environment.getCircles();
    }
    public ArrayList<Line2D> getLines(){
        if(environment == null) return new ArrayList<Line2D>();
        return environment.getLines();
    }
    public ArrayList<Line2D> getInvisLines(){
        if(environment == null) return new ArrayList<Line2D>();
        return environment.getInvisLines(tickTime);
    }
    public ArrayList<Rect> getRects(){
        if(environment == null) return new ArrayList<Rect>();
        return environment.getRects();
    }
    public ArrayList<Rect> getInvisRects() {
        if(environment == null) return new ArrayList<Rect>();
        return environment.getInvisRects();
    }
    public ArrayList<Pair<Line2D,Color>> getBranchLines(){
        if(environment == null) return new ArrayList<Pair<Line2D,Color>>();
        return environment.getBranchLines();
    }
    public void startSimulation(){
        timer.start();
    }
    public void pauseSimulation(){
        timer.stop();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        timer.stop();
        if(cycle >= speed){
            if(quickSim) return;
            proc();
            cycle = 0;
        }
        else{
            cycle++;
        }
        if(environment.getTime() == GeneticAlg.simulationTime) endSimulation();
        timer.start();
    }
    private void endSimulation(){

    }

    public void setSpeed(int a){speed = a;}
    public void setEnvironment(Environment environment){
        if(isSet) return;
        this.environment = environment;
        if(geneticAlg != null)isSet = true;
    }
    public void setAlgorithm(GeneticAlg geneticAlg){
        if(isSet) return;
        this.geneticAlg = geneticAlg;
        if(environment != null)isSet = true;
    }
    public long getTime(){
        if(!isSet) return 0;
        return environment.getTime();
    }
    public float getPoints(){
        if(!isSet) return -2;
        return environment.getPoints();
    }
    public float getSatiety(){
        if(!isSet) return -2;
        return environment.getSatiety();
    }
    public int getPPS(){
        return PPS;
    }
    public boolean isSet() { return isSet; }
    public void alterQuickSim(){
        if(environment==null) return;
        if(!quickSim) {
            quickSim = true;
            timer.stop();
            thread = new QuickSimThread(this);
            thread.start();
        } else{
            quickSim = false;
            try{
                thread.join();
            } catch (InterruptedException e){
                throw new RuntimeException();
            }
            timer.start();
        }
    }
    public boolean isQuickSim(){
        return quickSim;
    }

    public void simulateGeneration(){
        if(isWorking || !isSet) return;
        isWorking = true;
        currentIndividual = geneticAlg.getNextIndividual();
        environment.insertTree(currentIndividual.getDna(),GeneticAlg.startSatiety);
        startSimulation();
    }
}
