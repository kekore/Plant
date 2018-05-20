package environmentPack;

import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    private Branch parentBranch;
    private Tree parentTree;
    private ArrayList<Branch> branches;
    private ArrayList<Leaf> leaves;
    private int level;
    private int green;
    private float angle;

    private float satiety;
    private float satietyBuffer;
    private int growCost;
    private int growCounter;

    protected Branch(Tree parentTree, Branch parentBranch, int green){
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        branches = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        level = parentBranch.level+1;
        this.green = green;
        satiety = 0;
        satietyBuffer = 0;
        //level =
        //green =
    }

    protected void addSatiety(float n){
        satiety = satiety + n;
    }

    protected void addSatietyBuf(float n){
        satietyBuffer = satietyBuffer + n;
    }

    protected void receiveBuffer(){
        satiety = satiety + satietyBuffer;
        satietyBuffer = 0;
    }

    protected void proc(){
        distributeFood();
        for(Branch b : branches){
            b.proc();
        }
        receiveBuffer();
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

    protected void grow(Vector2D newHook){

    }

    protected void doBranch(int n){

    }
}
