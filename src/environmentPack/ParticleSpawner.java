package environmentPack;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class ParticleSpawner implements Serializable {
    protected Particle sample; //includes init position and startV
    protected Rect rectangle;

    private int frequency;

    public ParticleSpawner(Particle sample, int frequency){
        this.sample = sample;
        this.frequency = frequency;
        rectangle = new Rect(new Rectangle2D.Float(sample.physics.getPos().getX()-3,sample.physics.getPos().getY()-3,6,6), Color.RED);
    }

    protected Particle proc(long time){
        if(time % frequency == 0){ //should work with == !
            return sample.clone();
        }
        else return null;
    }
}
