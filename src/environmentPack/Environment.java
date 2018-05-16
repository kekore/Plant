package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Environment {
    //private int width; //TODO mozliwosc roznych wielkosci
    //private int height;
    private ArrayList<Particle> pList;
    private ArrayList<Factory> fList;
    private Tree tree;
    private Sun sun;
    private Rain rain;
    private Wind wind;

    public Environment(){
        //width = canvasWidth;
        //height = canvasHeight;
        pList = new ArrayList<Particle>();
        fList = new ArrayList<Factory>();
    }

    /*public Environment(int canvasWidth, int canvasHeight){
        width = canvasWidth;
        height = canvasHeight;
        pList = new ArrayList<Particle>();
        fList = new ArrayList<Factory>();
    }*/

    public void addParticle(Particle p){
        pList.add(p);
    }

    public void addFactory(Factory f){
        fList.add(f);
    }

    public void proc(long tickTime){
        //grow tree
        //move sun
        //spawn rain?
        //spawn toxic from factories
        for(Particle p : pList){ //TODO count forces (wind)
            p.setForce(new Vector2D(0,-50));
        }
        for(Particle p : pList){
            p.proc(tickTime);
        }
        //Check if particle is out of canvas
        //Check collisions
    }

    public ArrayList<Circle> getCircles(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : pList){
            sList.add(p.getCircle());
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

    public ArrayList<Rectangle2D> getRects(){
        ArrayList<Rectangle2D> rList = new ArrayList<Rectangle2D>();
        for(Factory f : fList){
            rList.addAll(f.getRects());
        }
        return rList;
    }

    public void insertTree(Tree tree){
        reset();
        this.tree = tree;
    }

    public void reset(){

     }
}
