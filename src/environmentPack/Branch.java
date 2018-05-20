package environmentPack;

import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    private Branch parentBranch;
    private ArrayList<Branch> branches;
    private ArrayList<Leaf> leaves;
    private int level;
    private int green;
    private float angle;

    private int satiety;
    private int growCost;
    private int growCounter;

    protected Branch(Branch parent, int green){
        parentBranch = parent;
        branches = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        level = parent.level+1;
        this.green = green;
        satiety = 0;
        //level =
        //green =
    }

    protected void addSatiety(int n){
        satiety = satiety + n;
    }

    protected void proc(){
        distributeFood();
        for(Branch b : branches){
            b.distributeFood();
        }
    }

    private void distributeFood(){

    }

    protected void grow(Vector2D newHook){

    }

    protected void doBranch(int n){

    }
}
