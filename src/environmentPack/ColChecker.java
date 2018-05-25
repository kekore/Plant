package environmentPack;

import java.awt.geom.Rectangle2D;

public class ColChecker {
    static protected boolean doCollide(Particle p, Branch b){
        if(b.line.intersects(new Rectangle2D.Float(p.shape.position.getX()-p.shape.d/2,p.shape.position.getY()-p.shape.d/2,p.shape.d,p.shape.d))){
            return true;
        }
        else return false;
    }
    static protected boolean doCollide(Particle p, Leaf l){
        Rectangle2D.Float pHitbox = new Rectangle2D.Float(p.shape.position.getX()-p.shape.d/2,p.shape.position.getY()-p.shape.d/2,p.shape.d,p.shape.d);
        Rectangle2D.Float lHitbox = new Rectangle2D.Float(l.shape.position.getX()-l.shape.d/2,l.shape.position.getY()-l.shape.d/2,l.shape.d,l.shape.d);
        if(pHitbox.intersects(lHitbox)){ return true; }
        else return false;
    }
}
