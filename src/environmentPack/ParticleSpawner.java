package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;

public class ParticleSpawner implements Serializable {
    private Particle sample;
    private Vector2D position;
    private Vector2D startV;
    private int frequency;
    private int cycle;

    protected ParticleSpawner(Vector2D position, Vector2D startV, Particle sample, int frequency){
        this.sample = sample;
        this.position = position;
        this.startV = startV;
        this.frequency = frequency;
        cycle = 0;
    }

    protected Particle proc(){
        if(cycle >= frequency){ //should work with ==
            cycle = 0;
            return sample.clone();
        }
        else{
            cycle++;
            return null;
        }
    }
}
