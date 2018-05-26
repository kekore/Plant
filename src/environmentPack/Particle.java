package environmentPack;
import physicsPack.*;

import java.awt.*;
import java.io.Serializable;

public class Particle implements Serializable{
    protected Physics physics;
    protected Circle shape;
    protected int age; //matters only for fotons

    public enum Type{
        OXYGEN,CARBOXIDE,TOXIC,FOTON,DROP
    }
    protected Type type;

    public Particle(Vector2D p, Vector2D v, Vector2D f, float m, int d, Type t){
        age = 0;
        physics = new Physics(p,v,f,m);
        type = t;
        switch (type){
            case OXYGEN: {
                shape = new Circle(p,d,Color.BLUE,false);
                break;
            }
            case CARBOXIDE: {
                shape = new Circle(p,d,Color.DARK_GRAY,false);
                break;
            }
            case TOXIC: {
                shape = new Circle(p,d,Color.MAGENTA,false);
                break;
            }
            case FOTON: {
                shape = new Circle(p,d,Color.YELLOW,false);
                break;
            }
            case DROP: {
                shape = new Circle(p,d,Color.CYAN,false);
                break;
            }
            default: {
                shape = new Circle(p,d,Color.RED,false);
            }
        }
    }
    public void setForce(Vector2D f){
        physics.setForce(f);
    }
    public void proc(long tickTime){
        physics.proc(tickTime);
        shape.setPos(physics.getPos());
    }

    /*@Override
    public Particle clone(){
        Particle ret;
        try{
            ret = new Particle(physics.getPos().clone(),physics.getVel().clone(),physics.getForce().clone(),physics.getMass(),shape.d,type);
        } catch (CloneNotSupportedException e){
            System.out.println("Particle cloning exception!");
            throw new RuntimeException();
        }
        return ret;
    }*/
}
