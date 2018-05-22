package environmentPack;

import physicsPack.Physics;
import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class ParticleSpawner implements Serializable {
    //protected Particle sample; //includes init position and startV
    private Particle.Type partType;
    private Vector2D partStartV;
    private int partD;
    private float partMass;
    protected Rect rectangle;
    public enum Type{
        STATIC, RAIN, SUN
    }
    private Type spawnerType;
    private Physics physics;
    private int frequency;

    public ParticleSpawner(Particle sample, Type spawnerType, int frequency, Vector2D p, Vector2D v, Vector2D f, float m){
        partType = sample.type;
        partStartV = sample.physics.getVel();
        partD = sample.shape.d;
        partMass = sample.physics.getMass();
        this.spawnerType = spawnerType;
        this.frequency = frequency;
        rectangle = new Rect(new Vector2D(sample.physics.getPos()),6,6,Color.RED,false);
        physics = new Physics(p,v,f,m);
    }

    protected Particle proc(long time,long tickTime){
        if(spawnerType != Type.STATIC)physics.proc(tickTime);
        if(time % frequency == 0){ //should work with == !
            return new Particle(new Vector2D(physics.getPos()),new Vector2D(partStartV),new Vector2D(),partMass,partD,partType);
        }
        else return null;
    }
}
