package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Factory implements Serializable{
    private Rect building;
    private Rect chimney;
    private Rect window;
    private Color color;
    private Vector2D spawnPoint;
    private Vector2D startV;

    public Factory(Vector2D spawnPoint, Vector2D startVelocity){
        this.spawnPoint = new Vector2D(spawnPoint);
        startV = new Vector2D(startVelocity);
        color = Color.BLACK;
        Rectangle2D.Float chimneyRect = new Rectangle2D.Float(spawnPoint.getX()-3, spawnPoint.getY(), 6, 20); //TODO poprawic to
        Rectangle2D.Float buildingRect = new Rectangle2D.Float(spawnPoint.getX()-20, spawnPoint.getY()+20, 30, 16);
        Rectangle2D.Float windowRect = new Rectangle2D.Float(spawnPoint.getX()-17, spawnPoint.getY()+23, 10, 10);
        chimney = new Rect(chimneyRect, Color.BLACK, true);
        building = new Rect(buildingRect, Color.BLACK, false);
        window = new Rect(windowRect, Color.BLACK, false);
    }

    protected ArrayList<Rect> getRects(){
        ArrayList<Rect> rects = new ArrayList<Rect>();
        rects.add(building);
        rects.add(chimney);
        rects.add(window);
        return rects;
    }
}
