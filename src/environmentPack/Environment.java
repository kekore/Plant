package environmentPack;

import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.util.ArrayList;

public class Environment {
    private ArrayList<Particle> pList;

    public Environment(){
        pList = new ArrayList<Particle>();
    }

    public void addParticle(Particle p){
        pList.add(p);
    }

    public void proc(long tickTime){
        //grow tree
        //move sun
        //spawn rain?
        //spawn toxic from factories
        for(Particle p : pList){ //count forces (wind)
            p.setForce(new Vector2D(0,-50));
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

    public ArrayList<Line2D> getLines(long tickTime){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        for(Particle p : pList){
            //lList.add(p.getColLine(tickTime));
            lList.add(p.physics.getColLine(tickTime));
        }
        return lList;
    }
    /* public void reset(){

     }
     */
}
