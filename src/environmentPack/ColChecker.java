package environmentPack;

import java.awt.geom.Rectangle2D;

public class ColChecker {
    static protected boolean doCollide(Particle p, Branch b){
        if(b.line.intersects(new Rectangle2D.Float(p.shape.position.getX()-p.shape.d/2,p.shape.position.getY()-p.shape.d/2,p.shape.d,p.shape.d))){
            return true;
        }
        else return false;
    }
}
