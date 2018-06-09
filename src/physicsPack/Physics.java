package physicsPack;

import java.awt.geom.Line2D;
import java.io.Serializable;

/**
 * Klasa obsługująca fizykę - położenie, prędkość, przyspieszenie, siłę przyłożoną do ciała, masę.
 */
public class Physics implements Serializable{
    /**Wektor położenia.*/
    private Vector2D pos;
    /**Wektor prędkości.*/
    private Vector2D vel;
    /**Wektor przyspieszenia*/
    private Vector2D acc;
    /**Wektor siły*/
    private Vector2D force;
    /**Masa ciała.*/
    private float mass;

    public Physics(Vector2D p, Vector2D v, Vector2D f, float m){
        pos = p;
        vel = v;
        acc = new Vector2D();
        force = f;
        mass = m;
    }

    /**
     * Wykonuje krok czasowy - aktualizuje wartość przyspieszenia, prędkości i pozycji zgodnie z wzorami fizycznymi.
     * @param tickTime Określa skokowość zmian wektorów - duża wartość to mniejsze skoki, ale dłuższe pokonywanie drogi lub dłuższa symulacja,
     *                 mała wartość to większe skoki, ale szybsze pokonywanie drogi lub krótsza symulacja.
     */
    public void proc(long tickTime)
    {
        if(mass != 0)acc.set(force.scaleNC((float)1/mass));
        vel.add(acc.scaleNC((float)1/tickTime));
        pos.add(vel.scaleNC((float)1/tickTime));
    }

    public Vector2D getPos() {
        return pos;
    }
    public void setPos(Vector2D pos) {
        this.pos = pos;
    }
    public Vector2D getVel() {
        return vel;
    }
    public void setVel(Vector2D vel) {
        this.vel = vel;
    }
    public Vector2D getAcc() {
        return acc;
    }
    public void setAcc(Vector2D acc) {
        this.acc = acc;
    }
    public Vector2D getForce() {
        return force;
    }
    public void setForce(Vector2D force) {
        this.force = force;
    }
    public float getMass() {
        return mass;
    }
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     *
     * @param tickTime zobacz {@link #proc(long)}
     * @return Zwraca linię kolizji, czyli linię między aktualną pozycją ciała a przewidywaną pozycją ciała w kolejnym kroku czasowym.
     */
    public Line2D getColLine(long tickTime){
        Vector2D predictedA;
        if(mass != 0)predictedA = force.scaleNC((float)1/mass);
        else predictedA = new Vector2D();
        Vector2D predictedV = vel.addNC(predictedA.scaleNC((float)1/tickTime));
        Vector2D predictedP = pos.addNC(predictedV.scaleNC((float)1/tickTime));
        return new Line2D.Float(pos.getX(),pos.getY(),predictedP.getX(),predictedP.getY());
    }
}
