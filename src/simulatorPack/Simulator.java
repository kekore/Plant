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
    protected Environment environment;
    //flagi:
    private boolean isSet;
    private boolean isWorking;
    private boolean isWorkingForAlgorithm;
    private boolean isOverviewing;
    private boolean isResimulating;
    protected boolean quickSim;
    //stałe:
    private final long tickTime;
    protected final long simulationTime;
    //zarządzanie czasem:
    private Timer timer;
    private int speed;
    private int cycle;
    //obliczanie akcji na sekunde:
    private long secStart;
    private int actions;
    private int PPS;
    //wątek:
    private QuickSimThread thread;
    //pola dotyczące wymianyinformacji z algorytmem:
    private Individual currentIndividual;

    public Simulator(long tT, long simulationTime){
        isSet = false;
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
        isWorkingForAlgorithm=false;
        isOverviewing=false;
        isResimulating=false;
    }
    private void addP(Particle p){
        environment.addParticle(p);
    }
    public void addP(Vector2D p, Vector2D v, Vector2D f, float m, int r, Particle.Type t){
        addP(new Particle(p,v,f,m,r,t));
    }
    synchronized protected void proc(){
        if(System.currentTimeMillis() - secStart < 1000){
            actions++;
        } else{
            PPS = actions;
            actions = 0;
            secStart = System.currentTimeMillis();
        }
        environment.proc(tickTime);
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
    @Override
    public void actionPerformed(ActionEvent e){
        timer.stop();
        if(quickSim) return;
        if(cycle >= speed){
            proc();
            cycle = 0;
        }
        else{
            cycle++;
        }
        if(environment.getTime() == GeneticAlg.simulationTime) endSimulation();
        else timer.start();
    }
    protected void endSimulation(){
        cycle=0;
        if(isResimulating){
            currentIndividual = null;
            isResimulating = false;
            isWorking = false;
            if(quickSim) quickSim = !quickSim;
        }
        else if(isWorkingForAlgorithm) {
            if (!geneticAlg.signalTested(currentIndividual, environment.getTree(), environment.getPoints()))
                startNewTestingSimulation(geneticAlg.getNextIndividual());
            else {
                currentIndividual = null;
                isWorkingForAlgorithm = false;
                isWorking = false;
                if (quickSim) quickSim = !quickSim;
            }
        }
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
    public float getPoints(){ //should be synchronized instead of this condition (?)
        if(!isSet || quickSim) return -2;
        return environment.getPoints();
    }
    public float getSatiety(){ //should be synchronized instead of this condition (?)
        if(!isSet || quickSim) return -2;
        return environment.getSatiety();
    }
    public int getPPS(){
        return PPS;
    }
    public boolean isSet() { return isSet; }
    public void alterQuickSim(){
        if(!isWorking || environment==null) return;
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
        isWorkingForAlgorithm = true;
        startNewTestingSimulation(geneticAlg.getNextIndividual());
    }
    private void startNewTestingSimulation(Individual i){
        currentIndividual = i;
        System.out.println("STARTING SIMULATION OF INDIVIDUAL WITH DNA: "+i.getDna().getString());
        environment.insertTreeToTest(i.getDna(),GeneticAlg.startSatiety);
        timer.start();
    }

    public void overviewIndividual(Individual i){
        if(isWorking) return;
        if(isOverviewing && i==currentIndividual){
            resimulate(i);
            return;
        }
        isOverviewing = true;
        currentIndividual=i;
        environment.insertTestedTree(i.getTree());
    }
    private void resimulate(Individual i){
        isOverviewing = false;
        isWorking = true;
        isResimulating = true;
        environment.insertTreeToTest(i.getDna(),GeneticAlg.startSatiety);
        timer.start();
    }

    //dla katalogu:
    public int getPopSize(){
        return geneticAlg.getPopSize();
    }
    public ArrayList<Individual> getSortedList(int generation){
        return geneticAlg.getSortedList(generation);
    }
    public int getCurrentGen(){return geneticAlg.getCurrentGen();}
    public int getLastTestedGen(){
        if(geneticAlg == null) return -1;
        return geneticAlg.getLastTestedGen();
    }
}
