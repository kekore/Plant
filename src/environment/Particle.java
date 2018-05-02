package environment;
import physicsPack.*;

import java.awt.*;
import java.awt.geom.Line2D;

public class Particle {
    private Physics physics;
    //private Color color;
    private Circle shape;

    public enum Type{
        OXYGEN,CARBOXIDE,TOXIC
    }
    private Type type;

    public Particle(Vector2D p, Vector2D v, float m, int r, Type t){
        physics = new Physics(p,v,m);
        type = t;
        switch (type){
            case OXYGEN: {
                shape = new Circle(p,r,Color.BLUE);
                break;
            }
            case CARBOXIDE: {
                shape = new Circle(p,r,Color.DARK_GRAY);
                break;
            }
            case TOXIC: {
                shape = new Circle(p,r,Color.MAGENTA);
                break;
            }
            default: {
                shape = new Circle(p,r,Color.RED);
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
    public Circle getShape(){
        return shape;
    }
    public Line2D getColLine(long tickTime){
        return physics.getColLine(tickTime);
    }
}
