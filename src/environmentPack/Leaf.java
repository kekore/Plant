package environmentPack;

import physicsPack.Maths;
import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;

public class Leaf implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    protected Circle shape;
    private float relativePos;
    private float d;
    private float xH;
    private float yH;
    protected boolean side;
    private Branch parentBranch;
    private Tree parentTree;
    private int growCost;
    private int growCounter;

    protected Leaf(Tree parentTree, Branch parentBranch, float relPos, boolean side){
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        System.out.println("Rel pos: "+relPos);
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

    protected void grow(float dd){
        d = d + dd;
        //System.out.println("DIAMETER: " + d);
    }

    protected void gotParticle(Particle p){
        switch (p.type){
            case DROP:{
                parentBranch.addSatiety(8F*((float)parentTree.leafGreen-128)/100);
                parentTree.addPoints(8F*((float)parentTree.leafGreen-128)/100);
                break;
            }
            case FOTON: {}
            case OXYGEN:{
                parentBranch.addSatiety(10.16F - ((float)parentTree.leafGreen-128)/100);
                parentTree.addPoints(10.16F - ((float)parentTree.leafGreen-128)/100);
                break;
            }
            case CARBOXIDE:{
                parentTree.addPoints(-3);
                break;
            }
            case TOXIC:{
                parentTree.addPoints(-9);
                break;
            }
        }
    }

    protected void updatePoint(){
        xH = parentBranch.x1 + (parentBranch.x2 - parentBranch.x1)*relativePos;
        yH = parentBranch.y1 + (parentBranch.y2 - parentBranch.y1)*relativePos;
        //System.out.println("xH yH: " + xH + " "+yH);
    }

    protected void updateShape(){
        /*float cA = Maths.countA(xH,parentBranch.x1,parentBranch.y1,parentBranch.x2,parentBranch.y2,d,side);
        System.out.println("cA: " + cA);
        float cB = Maths.countB(yH,parentBranch.x1,parentBranch.y1,parentBranch.x2,parentBranch.y2,d,side);
        System.out.println("cB "+ cB);*/
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
