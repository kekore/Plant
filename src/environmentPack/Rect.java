package environmentPack;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rect implements Serializable{
    public Rectangle2D.Float rectangle;
    public Color color;
    public boolean isFilled;
    Rect(Rectangle2D.Float rectangle, Color color, boolean isFilled){
        this.rectangle = rectangle;
        this.color = color;
        this.isFilled = isFilled;
    }
}
