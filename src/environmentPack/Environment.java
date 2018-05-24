package environmentPack;


import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Environment implements Serializable {
    private int windowWidth;
    private int windowHeight;
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
    private boolean isWorking; //TODO for denying editions

    public Environment(int canvasWidth, int canvasHeight, int groundLevel, int seedPosX, int dayTime, int rainFreq, int rainInt){
        width = canvasWidth;
        height = canvasHeight;
        particleList = new ArrayList<Particle>();
        factoryList = new ArrayList<Factory>();
        spawnerList = new ArrayList<ParticleSpawner>();

        ground = new Ground(width,height,groundLevel);
        seedPlace = new Vector2D(seedPosX,height-groundLevel);
        seedRect = new Rect(new Vector2D(seedPosX,height-groundLevel),6,6,Color.BLUE,true);

        if(rainFreq != 0 && rainInt != 0) rain = new Rain(rainFreq,rainInt,width);

        time = 0;
        isWorking = false;
    }

    public void saveWindowSize(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    public Dimension getWindowSize(){
        return new Dimension(windowWidth,windowHeight);
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
        //rain:
        if(rain != null && rain.proc(time))particleList.addAll(rain.getParticles(time,width));
        //count forces:
        for(Particle p : particleList){ //TODO count forces (wind)
            p.setForce(new Vector2D(0,500));
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
        //Check particles out of canvas and erase them:
        ArrayList<Particle> toErase = new ArrayList<Particle>();
        for(Particle p : particleList){
            if(p.physics.getPos().getX()<0 || p.physics.getPos().getX() > width){
                toErase.add(p);
            } else if (p.physics.getPos().getY()<0 || p.physics.getPos().getY() > height-ground.groundLevel){
                toErase.add(p);
            }
        }
        for(Particle te : toErase){
            particleList.remove(te);
        }
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
        this.tree = tree;
        reset();
    }

    public long getTime(){
        return time;
    }
    public int getPoints(){
        if(tree == null) return -1;
        else return tree.points;
    }

    public void reset(){
        time = 0;
        particleList.clear();
        //tree.reset();
        //sun.reset();
        //rain.reset();
     }
}
