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
    private float lenght;

    private float satiety;
    private float satietyBuffer;
    private int growCost;
    private int growCounter;

    protected Branch(Tree parentTree, Branch parentBranch, float angle){
        if(parentBranch != null)line = new Line2D.Float(parentBranch.line.x2,parentBranch.line.y2,parentBranch.line.x2,parentBranch.line.y2);
        else line = new Line2D.Float(parentTree.seedX,parentTree.seedY,parentTree.seedX,parentTree.seedY);
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

    private void addSatietyBuf(Branch giver, float n){
        satietyBuffer = satietyBuffer + n;
        giver.satiety = giver.satiety - n;
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
        int connectedBCount = branches.size();//add sons
        connectedBCount = connectedBCount + brothers.size(); //add brothers
        if(level != 0) connectedBCount++; //add parent if not root

        float portion = (satiety/2)/connectedBCount;
        if(level != 0) parentBranch.addSatietyBuf(this,portion);
        for(Branch b : branches){
            b.addSatietyBuf(this,portion);
        }
        for(Branch b : brothers){
            b.addSatietyBuf(this,portion);
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

    protected void updateLine(Vector2D newHook){
        float shiftX = newHook.getX()-line.x1;
        float shiftY = newHook.getY()-line.y1;
        float newX1 = line.x1+shiftX;
        float newX2 = line.x2+shiftX;
        float newY1 = line.y1+shiftY;
        float newY2 = line.y2+shiftY;
        line = new Line2D.Float(newX1,newY1,newX2,newY2);
        for(Branch b : branches){
            b.updateLine(new Vector2D(newX2,newY2));
        }
    }

    protected ArrayList<Line2D> getLinesRec(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        lList.add(line);
        for(Branch b : branches){
            lList.addAll(b.getLinesRec());
        }
        return lList;
    }

    protected ArrayList<Circle> getCirclesRec(){
        ArrayList<Circle> cList = new ArrayList<Circle>();
        cList.addAll(getCircles());
        for(Branch b : branches){
            cList.addAll(b.getCirclesRec());
        }
        return cList;
    }

    private ArrayList<Circle> getCircles(){
        ArrayList<Circle> cList = new ArrayList<Circle>();
        for(Leaf l : leaves){
            cList.add(l.shape);
        }
        return cList;
    }

    protected void doBranch(int n){

    }
}
