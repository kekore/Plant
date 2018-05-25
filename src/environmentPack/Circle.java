package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Circle implements Serializable{
    public Ellipse2D.Float ellipse;
    protected Vector2D position;
    //public Rectangle2D.Float hitbox;
    private boolean isFilled;
    protected int d;
    public Color color;
    Circle(Vector2D p, int a, Color c, boolean isFilled){
        position = p;
        d=a;
        this.isFilled = isFilled;
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
        color = c;
    }
    protected void setPos(Vector2D p){
        position = p;
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
    }
    public boolean isFilled(){ return isFilled; }
}
