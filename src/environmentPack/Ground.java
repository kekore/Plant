package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;

public class Ground implements Serializable {
    protected int groundLevel;
    protected Rect rectangle;

    protected Ground(int width, int height, int groundLevel){
        this.groundLevel = groundLevel;
        rectangle = new Rect(new Vector2D(width/2,height-groundLevel/2),width,groundLevel, new Color(139,69,19), true);
    }
}
