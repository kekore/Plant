package environmentPack;

import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;

/**
 * Klasa pojedynczego liścia w drzewie {@link Tree}.
 */
public class Leaf implements Serializable{
    /**Reprezentacja graficzna liścia*/
    protected Circle shape;
    /**Pozycja względem długości gałęzi - ułamek jej długości.*/
    private float relativePos;
    /**Strona gałęzi po której rośnie liść.*/
    protected boolean side;
    /**Średnica liścia.*/
    protected float d;
    /**Punkt zaczepienia w osi X*/
    private float xH;
    /**Punkt zaczepienia w osi Y*/
    private float yH;
    /** Gałąź, z której wyrasta liść.*/
    private Branch parentBranch;
    /** Drzewo, w którym rośnie liść.*/
    private Tree parentTree;

    /**
     * Konstruktor inicjujący potrzebne pola w klasie - ustawia wartości początkowe pól.
     * @param parentTree Drzewo, w którym rośnie liść.
     * @param parentBranch Gałąź, z której wyrasta liść.
     * @param relPos Pozycja względem długości gałęzi.
     * @param side Strona gałęzi po której rośnie liść.
     */
    protected Leaf(Tree parentTree, Branch parentBranch, float relPos, boolean side){
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        relativePos = relPos;
        this.side = side;
        d = 0;
        Vector2D cirCenter;
        float cA,cB;
        if(side){
            cA = xH + (float)Math.cos(Math.PI/2-parentBranch.angle)*(d/2);
            cB = yH + (float)-Math.sin(Math.PI/2-parentBranch.angle)*(d/2);
            cirCenter = new Vector2D(cA,cB);
        } else{
            cA = xH + (float)-Math.cos(Math.PI/2-parentBranch.angle)*(d/2);
            cB = yH + (float)Math.sin(Math.PI/2-parentBranch.angle)*(d/2);
            cirCenter = new Vector2D(cA,cB);
        }
        shape = new Circle(cirCenter,(int)d,new Color(0,parentTree.leafGreen,0),true);
    }

    /**
     * Powiększenie średnicy liścia o zadaną wartość.
     * @param dd Zadany przyrost średnicy.
     */
    protected void grow(float dd){
        if(d+dd < 0) d = 0;
        else d = d + dd;
    }

    /**
     * Funkcja wywoływana gdy cząsteczka zderzy się z liściem - zostają dodane lub odjęte punkty, dodana sytość lub zmniejszony liść
     * - zobacz {@link Environment#proc(long)}
     * @param p Cząsteczka, która zderzyła się z liściem.
     */
    protected void gotParticle(Particle p){
        switch (p.type){
            case DROP:{
                parentBranch.addSatiety((5.08F - 4F*((float)parentTree.leafGreen-128)/100)/2F);
                parentTree.addPoints((5.08F - 4F*((float)parentTree.leafGreen-128)/100)/2F);
                break;
            }
            case FOTON: {
                parentBranch.addSatiety(4F*((float)parentTree.leafGreen-128)/100);
                parentTree.addPoints(4F*((float)parentTree.leafGreen-128)/100);
                break;
            }
            case OXYGEN:{
                parentBranch.addSatiety(5.08F - 4F*((float)parentTree.leafGreen-128)/100);
                parentTree.addPoints(5.08F - 4F*((float)parentTree.leafGreen-128)/100);
                break;
            }
            case CARBOXIDE:{
                grow(-1);
                parentTree.addPoints(-3);
                break;
            }
            case TOXIC:{
                grow(-3);
                parentTree.addPoints(-9);
                break;
            }
        }
    }

    /**
     * Zaktualizowanie {@link #xH} i {@link #yH} na podstawie współrzędnych {@link #parentBranch} i {@link #relativePos}.
     */
    protected void updatePoint(){
        xH = parentBranch.x1 + (parentBranch.x2 - parentBranch.x1)*relativePos;
        yH = parentBranch.y1 + (parentBranch.y2 - parentBranch.y1)*relativePos;
    }

    /**
     * Zaktualizowanie kształtu.
     */
    protected void updateShape(){
        Vector2D cirCenter;
        float cA,cB;
        if(side){
            cA = xH + (float)Math.cos(Math.PI/2-parentBranch.angle)*(d/2);
            cB = yH + (float)-Math.sin(Math.PI/2-parentBranch.angle)*(d/2);
            cirCenter = new Vector2D(cA,cB);
        } else{
            cA = xH + (float)-Math.cos(Math.PI/2-parentBranch.angle)*(d/2);
            cB = yH + (float)Math.sin(Math.PI/2-parentBranch.angle)*(d/2);
            cirCenter = new Vector2D(cA,cB);
        }
        shape = new Circle(cirCenter,(int)d,new Color(0,parentTree.leafGreen,0),true);
    }
}
