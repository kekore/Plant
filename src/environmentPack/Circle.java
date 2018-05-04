package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle {
    private Ellipse2D.Float ellipse;
    private int d;
    private Color color;
    Circle(Vector2D p, int a, Color c){
        d=a;
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
        color = c;
    }
    protected void setPos(Vector2D p){
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
    }
    public Color getColor(){
        return color;
    }
    public Ellipse2D.Float getEllipse(){
        return ellipse;
    }
}
