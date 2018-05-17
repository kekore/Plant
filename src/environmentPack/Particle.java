package environmentPack;
import physicsPack.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class Particle implements Serializable{
    protected Physics physics;
    //private Color color;
    private Circle shape;

    public enum Type{
        OXYGEN,CARBOXIDE,TOXIC,FOTON,DROP
    }
    private Type type;

    public Particle(Vector2D p, Vector2D v, Vector2D f, float m, int d, Type t){
        physics = new Physics(p,v,f,m);
        type = t;
        switch (type){
            case OXYGEN: {
                shape = new Circle(p,d,Color.BLUE);
                break;
            }
            case CARBOXIDE: {
                shape = new Circle(p,d,Color.DARK_GRAY);
                break;
            }
            case TOXIC: {
                shape = new Circle(p,d,Color.MAGENTA);
                break;
            }
            case FOTON: {
                shape = new Circle(p,d,Color.YELLOW);
                break;
            }
            case DROP: {
                shape = new Circle(p,d,Color.CYAN);
                break;
            }
            default: {
                shape = new Circle(p,d,Color.RED);
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
    public Circle getCircle(){
        return shape;
    }
    /*public Line2D getColLine(long tickTime){
        return physics.getColLine(tickTime);
    }*/
}
