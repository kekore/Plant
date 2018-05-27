package environmentPack;

import javafx.util.Pair;
import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Rain implements Serializable{ //TODO wygenerowac wzorzec dla kazdego cyklu (cycle)
    private int frequency;                  //TODO naprawic czestotliwosc
    private int intensity;
    protected boolean isRaining;
    private long startTime;
    private int cycle;
    private int shift;

    //private int[] xArray;
    private ArrayList<Pair<Integer,Integer>> xmArray;

    protected Rain(int frequency, int intensity, int width){
        //System.out.println(frequency + " " + intensity);
        this.frequency = frequency;
        this.intensity = intensity;
        isRaining = false;
        startTime = 0;
        cycle = 0;
        shift = 0;

        Random generator = new Random();
        //xArray = new int[intensity/2];
        xmArray = new ArrayList<Pair<Integer,Integer>>();
        for(int i = 0; i < intensity/2; i++){
            //xArray[i] = generator.nextInt(width);

            xmArray.add(new Pair<Integer, Integer>(new Integer(generator.nextInt(width)),new Integer(generator.nextInt(3)+3)));
        }
    }

    protected boolean proc(long time){
        if(time % (10*frequency) == 0){
            /*System.out.println("rain: " + time);
            System.out.println(time % (10*frequency));
            System.out.println(frequency);*/
            //System.out.println("alternate" + time % 10*(long)frequency);
            isRaining = !isRaining;
            startTime = time;
            shift = 0;
        }
        return isRaining;
    }

    protected ArrayList<Particle> getParticles(long time, int width){
        ArrayList<Particle> pList = new ArrayList<Particle>();
        if((time-startTime) % (1000/intensity) != 0) return pList; // not needed??
        //int n = width*intensity/100;
        for(Pair<Integer,Integer> p : xmArray){
            pList.add(new Particle(new Vector2D((p.getKey()+shift)%width,0),new Vector2D(0,20),new Vector2D(),p.getValue()-2,p.getValue(),Particle.Type.DROP));
            cycle++;
            shift = shift + 10;
        }
        /*for(long i = time % 2; i < intensity/2; i = i + 2){
            pList.add(new Particle(new Vector2D(i*width/intensity,0),new Vector2D(),new Vector2D(),10,4,Particle.Type.DROP));
        }*/
        return pList;
    }

    protected void reset(){
        isRaining = false;
        startTime = 0;
        cycle = 0;
        shift = 0;
    }
}
