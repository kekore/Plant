package environmentPack;

import physicsPack.Physics;
import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class ParticleSpawner implements Serializable { //TODO wywalic sun i przywrocic isStatic
    //protected Particle sample; //includes init position and startV
    private Particle.Type partType;
    private Vector2D partStartV;
    private int partD;
    private float partMass;
    protected Rect rectangle;
    protected boolean isStatic;
    /*public enum Type{
        STATIC,MOVING,SUN
    }
    protected Type spawnerType;*/
    protected Physics physics;
    private int frequency;
    protected boolean working;

    public ParticleSpawner(Particle sample, boolean isStatic, int frequency, Vector2D p, Vector2D v, Vector2D f, float m){
        partType = sample.type;
        partStartV = sample.physics.getVel();
        partD = sample.shape.d;
        partMass = sample.physics.getMass();
        this.isStatic = isStatic;
        //this.spawnerType = spawnerType;
        this.frequency = frequency;
        rectangle = new Rect(new Vector2D(sample.physics.getPos()),6,6,Color.RED,false);
        //this.isStatic = isStatic;
        physics = new Physics(p,v,f,m);
        working = true;
    }

    protected Particle proc(long time,long tickTime){
        if(!isStatic){
            physics.proc(tickTime);
            rectangle.setPos(physics.getPos());
        }
        if(time % frequency == 0){ //should work with == !
            //if(spawnerType == Type.SUN && !working) return null;
            return new Particle(new Vector2D(physics.getPos()),new Vector2D(partStartV),new Vector2D(),partMass,partD,partType);
        }
        else return null;
    }
}
