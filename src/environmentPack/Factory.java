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
    private Vector2D spawnPoint;
    private Vector2D startV;
    private int frequency;
    public enum Type{
        NORMAL, TOXIC
    }
    Type type;

    public Factory(Vector2D spawnPoint, Vector2D startVelocity, int frequency, Type type){
        this.spawnPoint = new Vector2D(spawnPoint);
        startV = new Vector2D(startVelocity);
        this.frequency = frequency;
        this.type = type;
        switch (type){
            case NORMAL:{
                chimney = new Rect(new Vector2D(spawnPoint.getX(),spawnPoint.getY()+10),6,20,Color.BLACK, true);
                building = new Rect(new Vector2D(spawnPoint.getX()-5,spawnPoint.getY()+28),30,16,Color.BLACK,false);
                window = new Rect(new Vector2D(spawnPoint.getX()-12,spawnPoint.getY()+28),10,10,Color.BLACK,false);
                break;
            }
            case TOXIC:{
                chimney = new Rect(new Vector2D(spawnPoint.getX(),spawnPoint.getY()+15),8,30,new Color(139,0,139), true);
                building = new Rect(new Vector2D(spawnPoint.getX()-5,spawnPoint.getY()+28),30,16,new Color(139,0,139),false);
                window = new Rect(new Vector2D(spawnPoint.getX()-12,spawnPoint.getY()+28),10,10,new Color(139,0,139),false);
                break;
            }
        }

    }

    protected ArrayList<Rect> getRects(){
        ArrayList<Rect> rects = new ArrayList<Rect>();
        rects.add(building);
        rects.add(chimney);
        rects.add(window);
        return rects;
    }

    protected Particle proc(long time){
        if(time % frequency == 0){ //should work with == !
            //System.out.println(time);
            if(type == Type.NORMAL)
                return new Particle(new Vector2D(spawnPoint), new Vector2D(startV), new Vector2D(), 10, 3, Particle.Type.CARBOXIDE);
            else
                return new Particle(new Vector2D(spawnPoint), new Vector2D(startV), new Vector2D(), 10, 4, Particle.Type.TOXIC);
        }
        else return null;
    }
}
