package environment;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Circle {
    private Ellipse2D.Float circle;
    private int r;
    private Color color;
    Circle(Vector2D p, int a, Color c){
        r=a;
        circle = new Ellipse2D.Float(p.getX(),p.getY(),r,r);
        color = c;
    }
    protected void setPos(Vector2D p){
        circle = new Ellipse2D.Float(p.getX(),p.getY(),r,r);
    }
    public Color getColor(){
        return color;
    }
    public Ellipse2D.Float getEllipse(){
        return circle;
    }
}
