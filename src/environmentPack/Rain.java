package environmentPack;

import javafx.util.Pair;
import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Rain implements Serializable{
    private int frequency;
    private int intensity;
    protected boolean isRaining;
    private long startTime;
    private int shift;

    private ArrayList<Pair<Integer,Integer>> xmArray;

    protected Rain(int frequency, int intensity, int width){

        this.frequency = frequency;
        this.intensity = intensity;
        isRaining = false;
        startTime = 0;
        shift = 0;

        Random generator = new Random();
        xmArray = new ArrayList<Pair<Integer,Integer>>();
        for(int i = 0; i < intensity/2; i++){
            xmArray.add(new Pair<Integer, Integer>(new Integer(generator.nextInt(width)),new Integer(generator.nextInt(3)+3)));
        }
    }

    protected boolean proc(long time){
        if(time % (10*frequency) == 0){
            isRaining = !isRaining;
            startTime = time;
            shift = 0;
        }
        return isRaining;
    }

    protected ArrayList<Particle> getParticles(long time, int width){
        ArrayList<Particle> pList = new ArrayList<Particle>();
        if((time-startTime) % (1000/intensity) != 0) return pList; // not needed??
        for(Pair<Integer,Integer> p : xmArray){
            pList.add(new Particle(new Vector2D((p.getKey()+shift)%width,0),new Vector2D(0,20),new Vector2D(),p.getValue()-2,p.getValue(),Particle.Type.DROP));
            shift = shift + 10;
        }
        return pList;
    }

    protected void reset(){
        isRaining = false;
        startTime = 0;

        shift = 0;
    }
}
