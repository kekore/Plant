package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Sun implements Serializable{ //TODO nie uzywac spawnerow
    private final Vector2D center;
    private float distance;
    private Vector2D spinningVector;
    //ArrayList<ParticleSpawner> sList;
    private Vector2D roofCenter;
    private float roofLength;
    private static final int dayTime = 1000;
    private static final float angleStep = (float)Math.PI/(dayTime/2);
    private static final int fotonsSpacing = 20;
    private static final int frequency = 25;
    private boolean isRising;
    private boolean sunSide;
    int spawnersAmount;

    ArrayList<Rect> rList;
    Line2D.Float roof;

    protected Sun(int sunTime, boolean sunSide, int width, int height){
        isRising=true;
        this.sunSide = sunSide;
        center = new Vector2D(width/2,height);
        distance = (float)Math.sqrt((width/2)*(width/2)+height*height);
        //distance = 200;
        if(sunSide){
            spinningVector = new Vector2D(distance,0);
            spinningVector.rotate((1F-(float)sunTime/100F)*((float)dayTime/2F)*angleStep);
            System.out.println("COONSTRUCTOR SPINNING: " + spinningVector.getX() + " " + spinningVector.getY());
        } else {
            spinningVector = new Vector2D(-distance,0);
            spinningVector.rotate(-(1F-(float)sunTime/100F)*((float)dayTime/2F)*angleStep);
        }
        //roofCenter = center.addNC(spinningVector);

        if(height > width/2){
            roofLength = height*2;
        } else{
            roofLength = width;
        }
        spawnersAmount = (int)(roofLength/fotonsSpacing);

        rList = new ArrayList<Rect>();
    }

    protected ArrayList<Particle> proc(long time){
        if(time % (dayTime/2) == 0) isRising = !isRising;
        if ((isRising && sunSide) || (!isRising && !sunSide)) {
            spinningVector.rotate(-angleStep);
            System.out.println("SV x y: " + spinningVector.getX() + " " + spinningVector.getY());
        } else{
            spinningVector.rotate(angleStep);
        }
        roofCenter = center.addNC(spinningVector);
        System.out.println("RC x y: " + roofCenter.getX() + " " + roofCenter.getY());
        roof = new Line2D.Float(center.getX(),center.getY(),roofCenter.getX(),roofCenter.getY());

        ArrayList<Vector2D> spawningPosList = new ArrayList<Vector2D>();
        for(int i = 0; i < spawnersAmount/2; i++){
            Vector2D relPos = spinningVector.getPerpendicular(true, fotonsSpacing*(i+1));
            spawningPosList.add(roofCenter.addNC(relPos));
        }
        for(int i = 0; i < spawnersAmount - spawnersAmount/2; i++){
            Vector2D relPos = spinningVector.getPerpendicular(false, fotonsSpacing*i);
            spawningPosList.add(roofCenter.addNC(relPos));
        }

        Vector2D velocity = spinningVector.scaleToNC(-100F);
        ArrayList<Particle> pList = new ArrayList<Particle>();
        if(time % frequency == 0){
            rList.clear();
            for(Vector2D spawnPos : spawningPosList){
                pList.add(new Particle(spawnPos,new Vector2D(velocity),new Vector2D(),0,2,Particle.Type.FOTON));
                rList.add(new Rect(spawnPos,6,6,Color.RED,true));
            }
        }
        return pList;
    }

    protected ArrayList<Rect> getRects(){
        return rList;
    }

    protected ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        if (roof != null)lList.add(roof);
        return lList;
    }
}
