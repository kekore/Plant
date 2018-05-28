package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Wind implements Serializable{
    private ArrayList<Vector2D> noise;
    private ArrayList<Vector2D> template;
    protected static final int noiseAmount = 10;
    protected static final int noiseStrength = 50;
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
        float x;
        float y;
        for(int i = 0; i < templateAmount; i++){
            x=(generator.nextFloat()+1)*50;
            y=(generator.nextFloat()+1)*50;
            if(i<templateAmount/(generator.nextInt(3)+2)){
                switch (dir1){
                    case EAST:{
                        x=x*(generator.nextInt(4)+1);
                        break;
                    }
                    case WEST:{
                        x=-x*(generator.nextInt(4)+1);
                        break;
                    }
                    case NORTH:{
                        y=-y*(generator.nextInt(4)+1);
                    }
                    case SOUTH:{
                        y=y*(generator.nextInt(4)+1);
                    }
                }
            } else{
                switch (dir2){
                    case EAST:{
                        x=x*(generator.nextInt(4)+1);
                        break;
                    }
                    case WEST:{
                        x=-x*(generator.nextInt(4)+1);
                        break;
                    }
                    case NORTH:{
                        y=-y*(generator.nextInt(4)+1);
                    }
                    case SOUTH:{
                        y=y*(generator.nextInt(4)+1);
                    }
                }
            }
            template.add(new Vector2D(x,y));
        }
    }

    protected Vector2D getForce(long time){
        Vector2D force = (template.get((int)(Math.floor(time/200)%templateAmount))).addNC(noise.get((int)(Math.floor(time/200)%templateAmount)));
        return force;
    }
}
