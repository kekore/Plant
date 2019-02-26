package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Obiekty tej klasy mają na celu generowanie cząstek szkodliwych dla drzewa.
 */
public class Factory implements Serializable{
    /**Reprezentacja graficzna bryły budynku.*/
    private Rect building;
    /**Reprezentacja graficzna komina.*/
    private Rect chimney;
    /**Reprezentacja graficzna okna.*/
    private Rect window;
    /**Wektor wskazujący punkt w którym pojawiają się cząsteczki.*/
    private Vector2D spawnPoint;
    /**Wektor prędkości początkowej cząsteczki.*/
    private Vector2D startV;
    /**Odwrotność częstotliwości generowania cząsteczek.*/
    private int frequency;
    /**Możliwe typy fabryk.*/
    public enum Type{
        NORMAL, TOXIC
    }
    /**Typ fabyki.*/
    Type type;

    /**
     * Konstruktor zapisuje argumenty podane do konstruktora i na podstawie zadanego typu generuje odpowiednie obiekty {@link java.awt.geom.Rectangle2D}.
     * @param spawnPoint Wektor wskazujący punkt w którym pojawiają się cząsteczki.
     * @param startVelocity Wektor prędkości początkowej cząsteczki.
     * @param frequency Odwrotność częstotliwości generowania cząsteczek.
     * @param type Typ fabyki.
     */
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

    /**
     *
     * @return Zwraca listę obiektów {@link Rect} do wizualizacji fabryki
     */
    protected ArrayList<Rect> getRects(){
        ArrayList<Rect> rects = new ArrayList<Rect>();
        rects.add(building);
        rects.add(chimney);
        rects.add(window);
        return rects;
    }

    /**
     * Wykonuje krok czasowy.
     * @param time Aktualny czas(moment) w środowisku.
     * @return Zwraca cząsteczkę jeśli jest czas na jej wygenerowanie lub <i>null</i> jeśli nie jest w danym momencie czas.
     */
    protected Particle proc(long time){
        if(time % frequency == 0){
            if(type == Type.NORMAL)
                return new Particle(new Vector2D(spawnPoint), new Vector2D(startV), new Vector2D(), 10, 3, Particle.Type.CARBOXIDE);
            else
                return new Particle(new Vector2D(spawnPoint), new Vector2D(startV), new Vector2D(), 10, 4, Particle.Type.TOXIC);
        }
        else return null;
    }
}
