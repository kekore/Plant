package environmentPack;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Rect implements Serializable{
    public Rectangle2D.Float rectangle;
    public Color color;
    Rect(Rectangle2D.Float r, Color c){
        rectangle = r;
        color = c;
    }
}
