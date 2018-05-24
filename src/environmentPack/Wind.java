package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Wind implements Serializable{
    private ArrayList<Vector2D> noise;
    private ArrayList<Vector2D> template;
    protected static final int noiseAmount = 10;
    protected static final int noiseStrength = 10;
    protected static final int templateAmount = 10;
    public enum Direction{
        NORTH,EAST,SOUTH,WEST
    }

    protected Wind(Direction dir1, Direction dir2){
        Random generator = new Random();
        noise = new ArrayList<Vector2D>();
        for(int i = 0; i < noiseAmount; i++){
            noise.add(new Vector2D(generator.nextFloat()*2*noiseStrength-noiseStrength,generator.nextFloat()*2*noiseStrength-noiseStrength));
        }
        template = new ArrayList<Vector2D>();
        for(int i = 0; i < templateAmount; i++){
            //TODO template.add(new Vector2D())
        }
    }
    //TODO getForce
}
