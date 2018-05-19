package environmentPack;


import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Environment implements Serializable {
    private final int width; //TODO mozliwosc roznych wielkosci
    private final int height;
    private ArrayList<Particle> particleList;
    private ArrayList<Factory> factoryList;
    private ArrayList<ParticleSpawner> spawnerList;
    private final Ground ground;
    private Tree tree;
    private final Vector2D seedPlace;
    private final Rect seedRect;
    private Sun sun;
    private Rain rain;
    private Wind wind;
    private long time;

    public Environment(int canvasWidth, int canvasHeight, int groundLevel, int seedPosX){
        width = canvasWidth;
        height = canvasHeight;
        particleList = new ArrayList<Particle>();
        factoryList = new ArrayList<Factory>();
        spawnerList = new ArrayList<ParticleSpawner>();

        ground = new Ground(width,height,groundLevel);
        seedPlace = new Vector2D(seedPosX,height-groundLevel);
        seedRect = new Rect(new Vector2D(seedPosX,height-groundLevel),6,6,Color.BLUE,true);

        time = 0;
    }

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
        //proceed factories:
        for(Factory f : factoryList){
            Particle factoryRet = f.proc(time);
            if(factoryRet != null) particleList.add(factoryRet);
        }
        //proceed spawners:
        for(ParticleSpawner ps : spawnerList){
            Particle spawnerRet = ps.proc(time,tickTime);
            if(spawnerRet != null) particleList.add(spawnerRet);
        }
        //Check if particle is out of canvas
        //Check collisions
        time++;
    }

    public ArrayList<Circle> getCircles(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : particleList){
            sList.add(p.shape);
        }
        return sList;
    }

    public ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        /*for(Particle p : particleList){
            lList.add(p.physics.getColLine(tickTime));
        }*/
        return lList;
    }

    public ArrayList<Line2D> getInvisLines(long tickTime){
        ArrayList<Line2D> ilList = new ArrayList<Line2D>();
        for(Particle p : particleList){
            ilList.add(p.physics.getColLine(tickTime));
        }
        return ilList;
    }

    public ArrayList<Rect> getRects(){
        ArrayList<Rect> rList = new ArrayList<Rect>();
        for(Factory f : factoryList){
            rList.addAll(f.getRects());
        }
        rList.add(ground.rectangle);
        return rList;
    }

    public ArrayList<Rect> getInvisRects(){
        ArrayList<Rect> irList = new ArrayList<Rect>();
        irList.add(seedRect);
        for(ParticleSpawner ps : spawnerList){
            irList.add(ps.rectangle);
        }
        return irList;
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
