package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Factory {
    private Rectangle2D.Float building;
    private Rectangle2D.Float chimney;
    private Rectangle2D.Float window;
    private Color color;
    private Vector2D spawnPoint;
    private Vector2D startV;

    Factory(Vector2D spawnPoint, Vector2D startVelocity){
        this.spawnPoint = new Vector2D(spawnPoint);
        startV = new Vector2D(startVelocity);
        color = Color.BLACK;
        chimney = new Rectangle2D.Float(20, 6, spawnPoint.getX()-3, spawnPoint.getY());
        building = new Rectangle2D.Float(30, 14, spawnPoint.getX()-11, spawnPoint.getY()+20);
        window = new Rectangle2D.Float(10, 10, spawnPoint.getX()-8, spawnPoint.getY()+23);
    }

    protected ArrayList<Rectangle2D> getRects(){
        ArrayList<Rectangle2D> rects = new ArrayList<Rectangle2D>();
        rects.add(building);
        rects.add(chimney);
        rects.add(window);
        return rects;
    }
}
