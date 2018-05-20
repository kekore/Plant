package environmentPack;

import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    protected Line2D.Float line;
    private Branch parentBranch;
    private Tree parentTree;
    private ArrayList<Branch> branches;
    private ArrayList<Branch> brothers;
    private ArrayList<Leaf> leaves;
    private int level;
    //private int green;
    private float angle;

    private float satiety;
    private float satietyBuffer;
    private int growCost;
    private int growCounter;

    protected Branch(Tree parentTree, Branch parentBranch, float angle){
        line = new Line2D.Float(parentBranch.line.x2,parentBranch.line.y2,parentBranch.line.x2,parentBranch.line.y2);
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        branches = new ArrayList<Branch>();
        brothers = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        level = parentBranch.level+1;
        this.angle = angle;
        //this.green = green;
        satiety = 0;
        satietyBuffer = 0;
        //level =
        //green =
    }

    protected void addBrother(Branch b){
        brothers.add(b);
    }

    protected void addSatiety(float n){
        satiety = satiety + n;
    }

    private void addSatietyBuf(float n){
        satietyBuffer = satietyBuffer + n;
    }

    private void receiveBuffer(){
        satiety = satiety + satietyBuffer;
        satietyBuffer = 0;
    }

    protected void proc(){

    }

    protected void distributeFoodRec(){
        distributeFood();
        for(Branch b : branches){
            b.distributeFoodRec();
        }
    }

    private void distributeFood(){
        int connectedBCount = branches.size();
        if(level != 0) connectedBCount++;
        float portion = (satiety/2)/connectedBCount;
        if(level != 0) parentBranch.addSatietyBuf(portion);
        for(Branch b : branches){
            b.addSatietyBuf(portion);
        }
    }

    /*protected void newBranch(){
        Branch newBranch = new Branch(parentTree, this);
        for(Branch b : branches){
            b.addBrother(newBranch);
            newBranch.addBrother(b);
        }
        branches.add(newBranch);
    }*/

    protected void grow(Vector2D newHook){

    }

    protected void doBranch(int n){

    }
}
