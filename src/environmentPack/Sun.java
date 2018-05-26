package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;

public class Sun implements Serializable{ //TODO nie uzywac spawnerow
    private final Vector2D center;
    private float distance;
    private Vector2D spinningVector;
    ArrayList<ParticleSpawner> sList;
    private Vector2D roofCenter;
    private float roofLength;
    protected Sun(int dayTime, boolean sunSide, int width, int height){
        center = new Vector2D(width/2,height);
        distance = (float)Math.sqrt((width/2)*(width/2)+height*height);
        if(sunSide) spinningVector = new Vector2D(distance,0);
        else spinningVector = new Vector2D(-distance,0);
        roofCenter = center.addNC(spinningVector);

        if(height/2 > width){
            roofLength = height*2;
        } else{
            roofLength = width;
        }
        int spawnersAmount = (int)(roofLength/5);
        sList = new ArrayList<ParticleSpawner>();
    }

    protected ArrayList<ParticleSpawner> getSpawners(){

    }

    protected void proc(){
        for(ParticleSpawner ps : sList){
            ps.physics.setPos();
        }
    }
}
