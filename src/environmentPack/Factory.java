package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Factory implements Serializable{
    private Rectangle2D.Float building;
    private Rectangle2D.Float chimney;
    private Rectangle2D.Float window;
    private Color color;
    private Vector2D spawnPoint;
    private Vector2D startV;

    public Factory(Vector2D spawnPoint, Vector2D startVelocity){
        this.spawnPoint = new Vector2D(spawnPoint);
        startV = new Vector2D(startVelocity);
        color = Color.BLACK;
        chimney = new Rectangle2D.Float(spawnPoint.getX()-3, spawnPoint.getY(), 6, 20); //TODO poprawic to
        building = new Rectangle2D.Float(spawnPoint.getX()-20, spawnPoint.getY()+20, 30, 16);
        window = new Rectangle2D.Float(spawnPoint.getX()-17, spawnPoint.getY()+23, 10, 10);
    }

    protected ArrayList<Rectangle2D> getRects(){
        ArrayList<Rectangle2D> rects = new ArrayList<Rectangle2D>();
        rects.add(building);
        rects.add(chimney);
        rects.add(window);
        return rects;
    }
}
