package environmentPack;


import geneticAlgPack.DNA;
import javafx.util.Pair;
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
    private final int width;
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

    public Environment(int canvasWidth, int canvasHeight, int groundLevel, int seedPosX, int sunTime, boolean sunSide, int rainFreq, int rainInt){
        width = canvasWidth;
        height = canvasHeight;
        particleList = new ArrayList<Particle>();
        factoryList = new ArrayList<Factory>();
        spawnerList = new ArrayList<ParticleSpawner>();

        ground = new Ground(width,height,groundLevel);
        seedPlace = new Vector2D(seedPosX,height-groundLevel);
        seedRect = new Rect(new Vector2D(seedPosX,height-groundLevel),6,6,Color.BLUE,true);

        sun = new Sun(sunTime,sunSide,width,height-groundLevel);
        //spawnerList.addAll(sun.getSpawners());

        if(rainFreq != 0 && rainInt != 0) rain = new Rain(rainFreq,rainInt,width);
        wind = new Wind(Wind.Direction.EAST,Wind.Direction.WEST);

        time = 1;
        isWorking = false;

        //int[20] dnaTest =
        tree = new Tree(new DNA(new int[] {2,8,8,0,4,8,8,2,-2,8,1,0,5,0,0,0,0,0,0,0}),100, seedPosX, height-groundLevel);
        //tree.seed(seedPosX,height-groundLevel);
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
        //sun:
        particleList.addAll(sun.proc(time));
        //count forces:
        for(Particle p : particleList){ //TODO count forces (wind)
            p.setForce(new Vector2D(0,500));
            p.physics.getForce().add(wind.getForce(time));
        }
        /*for(ParticleSpawner ps : spawnerList){ //count sun forces
            if(ps.spawnerType == ParticleSpawner.Type.SUN)ps.updateForce();
        }*/
        //proceed particles' physics and fotons' age:
        for(Particle p : particleList){
            p.proc(tickTime);
            p.age++;
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
        //Check particles out of canvas or old and erase them:
        ArrayList<Particle> toErase = new ArrayList<Particle>();
        for(Particle p : particleList){
            if(p.type == Particle.Type.FOTON && p.age > 1000){ toErase.add(p); }
            else if((p.physics.getPos().getX()<0 || p.physics.getPos().getX() > width) && p.type != Particle.Type.FOTON){
                toErase.add(p);
            } else if ((p.physics.getPos().getY()<0 && p.type != Particle.Type.FOTON) || p.physics.getPos().getY() > height-ground.groundLevel){
                toErase.add(p);
            }
        }
        for(Particle te : toErase){
            particleList.remove(te);
        }
        //proceed tree:
        tree.proc(time);
        //Check collisions:
        toErase.clear();
        ArrayList<Leaf> lList = tree.getLeaves();
        ArrayList<Branch> bList = tree.getBranches();
        for(Particle p : particleList){
            for(Leaf l : lList){
                if(ColChecker.doCollide(p,l)){
                    l.gotParticle(p);
                    toErase.add(p);
                    break;
                }
            }
            if(toErase.contains(p)) continue;
            for(Branch b : bList){
                if(ColChecker.doCollide(p,b)){
                    b.gotParticle(p);
                    toErase.add(p);
                    break;
                }
            }
        }
        for(Particle te : toErase){
            particleList.remove(te);
        }
        //increment time:
        time++;
    }

    public ArrayList<Circle> getCircles(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : particleList){
            sList.add(p.shape);
        }
        sList.addAll(tree.getCircles());
        return sList;
    }

    public ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        /*for(Particle p : particleList){
            lList.add(p.physics.getColLine(tickTime));
        }*/
        //lList.addAll(tree.getLines());
        return lList;
    }

    public ArrayList<Pair<Line2D,Color>> getBranchLines(){
        ArrayList<Pair<Line2D,Color>> array = new ArrayList<Pair<Line2D,Color>>();
        for(Branch b : tree.getBranches()){
            array.add(new Pair<Line2D,Color>(b.line,new Color(0,tree.branchGreen,0)));
        }
        return array;
    }

    public ArrayList<Line2D> getInvisLines(long tickTime){
        ArrayList<Line2D> ilList = new ArrayList<Line2D>();
        for(Particle p : particleList){
            ilList.add(p.physics.getColLine(tickTime));
        }
        ilList.addAll(sun.getLines());
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
        irList.addAll(sun.getRects());
        return irList;
    }

    public void insertTree(Tree tree){
        this.tree = tree;
        reset();
    }

    public long getTime(){
        return time;
    }
    public float getPoints(){
        if(tree == null) return -1;
        else return tree.points;
    }

    private void reset(){
        time = 0;
        particleList.clear();
        //tree.reset();
        //sun.reset();
        //rain.reset();
     }

     public float getSatiety(){
        if(tree==null) return -1;
        ArrayList<Branch> bList = tree.getBranches();
        float satiety=0;
        for(Branch b : bList){
            satiety = satiety + b.satiety;
        }
        return satiety;
     }
}
