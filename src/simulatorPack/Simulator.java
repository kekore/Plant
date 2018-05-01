package simulatorPack;

import environment.Circle;
import environment.Particle;
import physicsPack.Time;
import physicsPack.Vector2D;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Simulator implements ActionListener{
    private ArrayList<Particle> pList;
    private long tickTime;
    private boolean quickSim;
    private Timer timer;
    private Time time;

    public Simulator(long tT){
        pList = new ArrayList<Particle>();
        tickTime = tT;
        quickSim = false;
        timer = new Timer(1,this);
        time = new Time();
    }
    private void addP(Particle p){
        pList.add(p);
    }
    public void addP(Vector2D p, Vector2D v, float m, int r, Particle.Type t){
        pList.add(new Particle(p,v,m,r,t,time.time()));
    }
    public void proc(){
        for(Particle p : pList){ //count forces --TODO--
            p.setForce(new Vector2D(0.3F,-0.1F));
        }
        for(Particle p : pList){
            p.proc(time.time(), tickTime);
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
    public void startSimulation(){
        timer.start();
        time.unpause();
    }
    public void pauseSimulation(){
        timer.stop();
        time.pause();
    }
    @Override
    public void actionPerformed(ActionEvent e){
        proc();
    }
}
