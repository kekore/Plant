package environmentPack;
import physicsPack.*;

import java.awt.*;
import java.io.Serializable;

/**
 * Klasa cząsteczki.
 */
public class Particle implements Serializable{
    /** Fizyka cząsteczki - zobacz {@link Physics}*/
    protected Physics physics;
    /** Reprezentacja graficzna cząsteczki.*/
    protected Circle shape;
    /** Wiek cząsteczki. */
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
                shape = new Circle(p,d,Color.DARK_GRAY,true);
                break;
            }
            case TOXIC: {
                shape = new Circle(p,d,Color.MAGENTA,false);
                break;
            }
            case FOTON: {
                shape = new Circle(p,d,Color.ORANGE,true);
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

    /**
     * Ustawienie siły jaka działa na cząsteczkę.
     * @param f Siła do ustawienia.
     */
    public void setForce(Vector2D f){
        physics.setForce(new Vector2D(f));
    }

    /**
     * Wykonanie kroku w fizyce cząsteczki i przesunięcie jej kształtu.
     * @param tickTime Relatywna długość tiku zegara - ten parametr jest przekazywany do wykonania kroku w fizyce cząstek - {@link physicsPack.Physics#proc(long)}.
     */
    public void proc(long tickTime){
        physics.proc(tickTime);
        shape.setPos(physics.getPos());
    }
}
