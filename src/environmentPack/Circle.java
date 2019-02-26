package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.io.Serializable;

/**
 * Klasa opisująca graficzną reprezentację koła
 */
public class Circle implements Serializable{
    /**Kształt koła w postaci elipsy z biblioteki AWT.*/
    public Ellipse2D.Float ellipse;
    /*Wektor pozycji koła - zobacz {@link environmentPack.ColChecker}.*/
    protected Vector2D position;
    /**<i>true</i> - kształt powinien być rysowany jako wypełniony w środku*/
    private boolean isFilled;
    /**Średnica koła.*/
    protected int d;
    /**Kolor koła.*/
    public Color color;

    Circle(Vector2D p, int a, Color c, boolean isFilled){
        position = p;
        d=a;
        this.isFilled = isFilled;
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
        color = c;
    }

    /**
     * Zmiana pozycji koła.
     * @param p Wektor nowej pozycji.
     */
    protected void setPos(Vector2D p){
        position = p;
        ellipse = new Ellipse2D.Float(p.getX()-d/2,p.getY()-d/2,d,d);
    }
    public boolean isFilled(){ return isFilled; }
}
