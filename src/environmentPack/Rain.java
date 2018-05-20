package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Rain implements Serializable{ //TODO wygenerowac wzorzec dla kazdego cyklu
    private int frequency;
    private int intensity;
    protected boolean isRaining;
    private long startTime;
    private int shift;

    private int[] xArray;

    protected Rain(int frequency, int intensity, int width){
        System.out.println(frequency + " " + intensity);
        this.frequency = frequency;
        this.intensity = intensity;
        isRaining = true;
        startTime = 0;
        shift = 0;

        Random generator = new Random();
        xArray = new int[intensity/2];
        for(int i = 0; i < intensity/2; i++){
            xArray[i] = generator.nextInt(width);
        }
    }

    protected boolean proc(long time){
        if(time % (20*frequency) == 0){
            System.out.println("rain: " + time);
            System.out.println(time % (10*frequency));
            System.out.println(frequency);
            //System.out.println("alternate" + time % 10*(long)frequency);
            isRaining = !isRaining;
            startTime = time;
            shift = 0;
        }
        return isRaining;
    }

    protected ArrayList<Particle> getParticles(long time, int width){
        ArrayList<Particle> pList = new ArrayList<Particle>();
        if((time-startTime) % (1000/intensity) != 0) return pList;
        //int n = width*intensity/100;
        for(int i : xArray){
            pList.add(new Particle(new Vector2D((i+shift)%width,0),new Vector2D(),new Vector2D(),10,4,Particle.Type.DROP));
            shift = shift + 10;
        }
        /*for(long i = time % 2; i < intensity/2; i = i + 2){
            pList.add(new Particle(new Vector2D(i*width/intensity,0),new Vector2D(),new Vector2D(),10,4,Particle.Type.DROP));
        }*/
        return pList;
    }
}
