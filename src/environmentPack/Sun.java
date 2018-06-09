package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa słońca - generuje cząsteczki światła dla środowiska - "Sklepienie" oznacza abstrakcyjną linię poruszającą się poza obszarem symulacji
 * obracającą się wględem środka dolnej granicy obszaru i emitująca cząsteczki światła.
 */
public class Sun implements Serializable{
    /**Środek dolnej granicy obszaru symulacji*/
    private final Vector2D center;
    /**Odległość "sklepienia" od {@link #center}*/
    private float distance;
    /**"Wirujący wektor", do którego "przyczepiony" jest środek "sklepienia"*/
    private Vector2D spinningVector;
    /**Położenie środka sklepienia*/
    private Vector2D roofCenter;
    /**Długość sklepienia.*/
    private float roofLength;
    /**Długość doby(dzień plus noc).*/
    private static final int dayTime = 1000;
    /**Krok obrotu "Wirującego wektora".*/
    private static final float angleStep = (float)Math.PI/(dayTime/2);
    /**Odległość między wygenerowanymi cząsteczkami światła w pikselach.*/
    private static final int fotonsSpacing = 20;
    /**Częstotliwość generacji cząsteczek*/
    private static final int frequency = 25;
    /**<i>true</i> - słońce zbiża się do zenitu, <i>false</i> - słońce zachodzi*/
    private boolean isRising;
    /**<i>true</i> - słońce wschodzi z lewej strony.*/
    private boolean sunSide;
    /**łączna liczba cząsteczek do wygenerowania jednocześnie.*/
    int spawnersAmount;

    ArrayList<Rect> rList;//both for debug
    Line2D.Float roof;

    /**
     * Przygotowanie "Wirującego wektora" - obliczenie go i obrót na początkową pozycję. Obliczenie długości "sklepienia".
     * @param sunTime Określa słoneczną część doby - <i>0</i> brak słońca, <i>50</i> pół dobry słońca, <i>100</i> słońce bez przerwy nad horyzontem.
     * @param sunSide <i>true</i> - słońce wschodzi z prawej strony, <i>false</i> - z lewej
     * @param width Szerokość obszaru symulacji
     * @param height Wysokość obszau symulacji
     */
    protected Sun(int sunTime, boolean sunSide, int width, int height){
        isRising=true;
        this.sunSide = sunSide;
        center = new Vector2D(width/2,height);
        distance = (float)Math.sqrt((width/2)*(width/2)+height*height);
        if(sunSide){
            spinningVector = new Vector2D(distance,0);
            spinningVector.rotate((1F-(float)sunTime/100F)*((float)dayTime/2F)*angleStep);
        } else {
            spinningVector = new Vector2D(-distance,0);
            spinningVector.rotate(-(1F-(float)sunTime/100F)*((float)dayTime/2F)*angleStep);
        }

        if(height > width/2){
            roofLength = height*2;
        } else{
            roofLength = width;
        }
        spawnersAmount = (int)(roofLength/fotonsSpacing);

        rList = new ArrayList<Rect>();
    }

    /**
     * Oblicznie nowych wartości wektorów oraz pozycji generacji cząsteczek światła. Ewentualna generacja cząsteczek.
     * @param time Aktualny czas(moment) w środowisku.
     * @return Jeśli czas na generację cząsteczek, zwraca listę cząsteczek dla środowiska. Jeśli nie - zwraca pustą listę.
     */
    protected ArrayList<Particle> proc(long time){
        if(time % (dayTime/2) == 0) isRising = !isRising;
        if ((isRising && sunSide) || (!isRising && !sunSide)) {
            spinningVector.rotate(-angleStep);
        } else{
            spinningVector.rotate(angleStep);
        }
        roofCenter = center.addNC(spinningVector);
        roof = new Line2D.Float(center.getX(),center.getY(),roofCenter.getX(),roofCenter.getY());

        Vector2D velocity = spinningVector.scaleToNC(-100F);
        ArrayList<Particle> pList = new ArrayList<Particle>();
        if(time % frequency == 0){

            ArrayList<Vector2D> spawningPosList = new ArrayList<Vector2D>();
            for(int i = 0; i < spawnersAmount/2; i++){
                Vector2D relPos = spinningVector.getPerpendicular(true, fotonsSpacing*(i+1));
                spawningPosList.add(roofCenter.addNC(relPos));
            }
            for(int i = 0; i < spawnersAmount - spawnersAmount/2; i++){
                Vector2D relPos = spinningVector.getPerpendicular(false, fotonsSpacing*i);
                spawningPosList.add(roofCenter.addNC(relPos));
            }

            rList.clear();
            for(Vector2D spawnPos : spawningPosList){
                pList.add(new Particle(spawnPos,new Vector2D(velocity),new Vector2D(),0,2,Particle.Type.FOTON));
                rList.add(new Rect(spawnPos,6,6,Color.RED,true));
            }
        }
        return pList;
    }
    //both for debug:
    protected ArrayList<Rect> getRects(){
        return rList;
    }
    protected ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        if (roof != null)lList.add(roof);
        return lList;
    }
}
