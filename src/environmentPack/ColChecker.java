package environmentPack;

import java.awt.geom.Rectangle2D;

/**
 * Klasa sprawdzająca kolizje
 */
public class ColChecker {
    /**
     * Sprawdza kolizję cząsteczki z gałęzią - zobacz {@link Environment#proc(long)}.
     * @param p Sprawdzana cząsteczka.
     * @param b Sprawdzana gałąź.
     * @return <i>true</i> jeśli obiekty kolidują, <i>false</i> jeśli nie kolidują.
     */
    static protected boolean doCollide(Particle p, Branch b){
        if(b.line.intersects(new Rectangle2D.Float(p.shape.position.getX()-p.shape.d/2,p.shape.position.getY()-p.shape.d/2,p.shape.d,p.shape.d))){
            return true;
        }
        else return false;
    }

    /**
     * Sprawdza kolizję cząsteczki z liściem - zobacz {@link Environment#proc(long)}.
     * @param p Sprawdzana cząsteczka.
     * @param l Sprawdzany liść.
     * @return <i>true</i> jeśli obiekty kolidują, <i>false</i> jeśli nie kolidują.
     */
    static protected boolean doCollide(Particle p, Leaf l){
        if(l.d < 1) return false;
        Rectangle2D.Float pHitbox = new Rectangle2D.Float(p.shape.position.getX()-p.shape.d/2,p.shape.position.getY()-p.shape.d/2,p.shape.d,p.shape.d);
        Rectangle2D.Float lHitbox = new Rectangle2D.Float(l.shape.position.getX()-l.shape.d/2,l.shape.position.getY()-l.shape.d/2,l.shape.d,l.shape.d);
        if(pHitbox.intersects(lHitbox)){ return true; }
        else return false;
    }
}
