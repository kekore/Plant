package simulatorPack;

import environment.Circle;
import environment.Particle;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Simulator implements ActionListener{
    private ArrayList<Particle> pList;
    private long tickTime;
    //private boolean quickSim;
    private Timer timer;
    //private Time time;
    private int speed;
    private int cycle;

    public Simulator(long tT){
        pList = new ArrayList<Particle>();
        tickTime = tT;
        //quickSim = false;
        timer = new Timer(1,this);
        //time = new Time();
        speed = 4;
        cycle = 1;
    }
    private void addP(Particle p){
        pList.add(p);
    }
    public void addP(Vector2D p, Vector2D v, float m, int r, Particle.Type t){
        pList.add(new Particle(p,v,m,r,t));
    }
    public void proc(){
        for(Particle p : pList){ //count forces --TODO--
            p.setForce(new Vector2D(3,-7));
        }
        for(Particle p : pList){
            p.proc(tickTime);
        }
        //Check if particle is out of canvas
        //Check collisions
    }
    public ArrayList<Circle> getShapes(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : pList){
            sList.add(p.getShape());
        }
        return sList;
    }
    public ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        for(Particle p : pList){
            lList.add(p.getColLine(tickTime));
        }
        return lList;
    }
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
}
