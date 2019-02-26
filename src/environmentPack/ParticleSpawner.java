package environmentPack;

import physicsPack.Physics;
import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;

/**
 * Klasa źródła cząsteczek.
 */
public class ParticleSpawner implements Serializable {
    /**Rodaj cząsteczki która jest generowana.*/
    private Particle.Type partType;
    /**Początkowy wektor prędkości cząsteczki która jest generowana.*/
    private Vector2D partStartV;
    /*Średnica cząsteczki która jest generowana.*/
    private int partD;
    /*Masa cząsteczki która jest generowana.*/
    private float partMass;
    /*Reprezentacja graficzna źródła*/
    protected Rect rectangle;
    /** <i>true</i> - na obiekt działa fizyka, <i>false</i> - na obiekt nie działa fizyka.*/
    protected boolean isStatic;
    /** Obiekt fizyki źródła cząsteczek.*/
    protected Physics physics;
    /**Odwrotność częstotliwości generowania cząsteczek.*/
    private int frequency;

    public ParticleSpawner(Particle sample, boolean isStatic, int frequency, Vector2D p, Vector2D v, Vector2D f, float m){
        partType = sample.type;
        partStartV = sample.physics.getVel();
        partD = sample.shape.d;
        partMass = sample.physics.getMass();
        this.isStatic = isStatic;
        this.frequency = frequency;
        rectangle = new Rect(new Vector2D(sample.physics.getPos()),6,6,Color.RED,false);
        physics = new Physics(p,v,f,m);
    }

    /**
     * Wykonuje krok fizyki źródła cząsteczek o ile {@link #isStatic} jest true.
     * @param time Czas, na podstawie którego obiekt ma zdecydować czy wygenerować cząsteczki.
     * @param tickTime Relatywna długość tiku zegara - ten parametr jest przekazywany do wykonania kroku w fizyce cząstek - {@link physicsPack.Physics#proc(long)}.
     * @return Zwraca cząsteczkę jeśli był czas na generację lub <i>null</i> jeśli nie.
     */
    protected Particle proc(long time,long tickTime){
        if(!isStatic){
            physics.proc(tickTime);
            rectangle.setPos(physics.getPos());
        }
        if(time % frequency == 0){
            return new Particle(new Vector2D(physics.getPos()),new Vector2D(partStartV),new Vector2D(),partMass,partD,partType);
        }
        else return null;
    }
}
