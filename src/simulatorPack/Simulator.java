package simulatorPack;

import environment.Circle;
import environment.Particle;
import physicsPack.Vector2D;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Simulator {
    private ArrayList<Particle> pList;
    private long tickTime;
    public Simulator(long tT){
        pList = new ArrayList<Particle>();
        tickTime = tT;
    }
    public void addP(Particle p){
        pList.add(p);
    }
    public void proc(){
        for(Particle p : pList){ //count forces --TODO--
            p.setForce(new Vector2D(0.3F,-0.1F));
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
}
