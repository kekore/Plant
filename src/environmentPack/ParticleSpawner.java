package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;

public class ParticleSpawner implements Serializable {
    private Particle sample; //includes init position and startV

    private int frequency;

    public ParticleSpawner(Particle sample, int frequency){
        this.sample = sample;
        this.frequency = frequency;
    }

    protected Particle proc(long time){
        if(time % frequency == 0){ //should work with ==
            return sample.clone();
        }
        else return null;
    }
}
