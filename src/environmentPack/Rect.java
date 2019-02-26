package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rect implements Serializable{
    public Rectangle2D.Float rectangle;
    public int width;
    private int height;
    public Color color;
    private boolean isFilled;
    Rect(Vector2D p, int width, int height, Color color, boolean isFilled){
        this.width = width;
        this.height = height;
        this.rectangle = new Rectangle2D.Float(p.getX()-width/2,p.getY()-height/2,width,height);
        this.color = color;
        this.isFilled = isFilled;
    }

    protected void setPos(Vector2D p){
        rectangle = new Rectangle2D.Float(p.getX()-width/2,p.getY()-height/2,width,height);
    }
    public boolean isFilled(){ return isFilled; }
}
