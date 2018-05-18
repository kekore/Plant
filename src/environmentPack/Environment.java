package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Environment implements Serializable {
    //private int width; //TODO mozliwosc roznych wielkosci
    //private int height;
    private ArrayList<Particle> particleList;
    private ArrayList<Factory> factoryList;
    private ArrayList<ParticleSpawner> spawnerList;
    private Tree tree;
    private Sun sun;
    private Rain rain;
    private Wind wind;
    private long time;

    public Environment(){
        //width = canvasWidth;
        //height = canvasHeight;
        particleList = new ArrayList<Particle>();
        factoryList = new ArrayList<Factory>();
        spawnerList = new ArrayList<ParticleSpawner>();
        //time = 0;
    }

    /*public Environment(int canvasWidth, int canvasHeight){
        width = canvasWidth;
        height = canvasHeight;
        pList = new ArrayList<Particle>();
        fList = new ArrayList<Factory>();
    }*/

    public void addParticle(Particle p){
        particleList.add(p);
    }

    public void addFactory(Factory f){
        factoryList.add(f);
    }

    public void addSpawner(ParticleSpawner ps){
        spawnerList.add(ps);
    }

    public void proc(long tickTime){
        //grow tree
        //move sun
        //spawn rain?
        //spawn toxic from factories
        //count forces:
        for(Particle p : particleList){ //TODO count forces (wind)
            p.setForce(new Vector2D(0,-50));
        }
        //proceed particles' physics:
        for(Particle p : particleList){
            p.proc(tickTime);
        }
        //proceed spawners:
        for(ParticleSpawner ps : spawnerList){
            Particle spawnerRet = ps.proc(time);
            if(spawnerRet != null) particleList.add(spawnerRet);
        }
        //Check if particle is out of canvas
        //Check collisions
        time++;
    }

    public ArrayList<Circle> getCircles(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : particleList){
            sList.add(p.getCircle());
        }
        return sList;
    }

    public ArrayList<Line2D> getLines(long tickTime){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        for(Particle p : particleList){
            //lList.add(p.getColLine(tickTime));
            lList.add(p.physics.getColLine(tickTime));
        }
        return lList;
    }

    public ArrayList<Rectangle2D> getRects(){
        ArrayList<Rectangle2D> rList = new ArrayList<Rectangle2D>();
        for(Factory f : factoryList){
            rList.addAll(f.getRects());
        }
        return rList;
    }

    public void insertTree(Tree tree){
        reset();
        this.tree = tree;
    }

    public long getTime(){
        return time;
    }

    public void reset(){

     }
}
