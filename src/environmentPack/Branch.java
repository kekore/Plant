package environmentPack;

import physicsPack.Vector2D;
import sun.security.krb5.internal.PAData;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    protected Line2D.Float line;
    private float x1;
    private float y1;
    private float x2;
    private float y2;
    private Branch parentBranch;
    private Tree parentTree;
    private ArrayList<Branch> branches;
    private ArrayList<Branch> brothers;
    private ArrayList<Leaf> leaves;
    private int level;
    //private int green;
    private float angle;
    private float lenght;
    private boolean doesGrowLeaves;

    private float satiety;
    private float satietyBuffer;
    private int growCost;
    private int growCounter;

    protected Branch(Tree parentTree, Branch parentBranch, float angle, boolean doesGrowLeaves, float initSatiety){
        if(parentBranch != null)line = new Line2D.Float(parentBranch.line.x2,parentBranch.line.y2,parentBranch.line.x2,parentBranch.line.y2);
        else line = new Line2D.Float(parentTree.seedX,parentTree.seedY,parentTree.seedX,parentTree.seedY);
        x1 = parentTree.seedX;
        y1 = parentTree.seedY;
        x2 = parentTree.seedX;
        y2 = parentTree.seedY;
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        branches = new ArrayList<Branch>();
        brothers = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        if(parentBranch == null) level = 0;
        else level = parentBranch.level+1;
        this.angle = angle;
        this.doesGrowLeaves = doesGrowLeaves;
        //this.green = green;
        satiety = initSatiety;
        satietyBuffer = 0;
        //level =
        //green =
    }

    protected void addBrother(Branch b){
        brothers.add(b);
    }

    protected void gotParticle(Particle p){
        switch (p.type){
            case DROP:{ addSatiety(4); }
            case OXYGEN:{ addSatiety(10); }
        }
    }
    private void addSatiety(float n){
        satiety = satiety + n;
        System.out.println(satiety);
    }

    protected float getStaiety(float n){
        if(n > satiety) throw new RuntimeException(); // for debug
        satiety = satiety - n;
        return n;
    }

    private void addSatietyBuf(Branch giver, float n){
        satietyBuffer = satietyBuffer + giver.getStaiety(n);
    }

    protected void receiveBufferRec(){
        satiety = satiety + satietyBuffer;
        satietyBuffer = 0;
        for(Branch b : branches){ b.receiveBufferRec(); }
    }

    /*protected void proc(){

    }*/

    protected void distributeFoodRec(){
        distributeFood();
        for(Branch b : branches){ b.distributeFoodRec(); }
    }

    private void distributeFood(){
        int connectedBCount = branches.size();//add sons
        connectedBCount = connectedBCount + brothers.size(); //add brothers
        if(level != 0) connectedBCount++; //add parent if not root

        float portion = (satiety/32)/connectedBCount;
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

    protected void growRec(){
        //System.out.println(satiety + " " + angle); //for debugging
        float lenghtFormula = getStaiety(((float)1/(float)(50*(Math.abs(parentTree.dna.getGene(4))+2)))*satiety);
        //System.out.println((float)1/(float)(2*(Math.abs(parentTree.dna.getGene(4))+2)));
        growLineRec(countBGrowth(lenghtFormula));
    }
    private void growLineRec(Vector2D shift){
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();
        for(Branch b : branches){ b.updateXYRec(shift); }
    }
    private void updateXYRec(Vector2D shift){
        x1 = x1 + shift.getX();
        y1 = y1 + shift.getY();
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();

        for(Branch b : branches){ b.updateXYRec(shift); }
    }

    protected void updateLineRec(){
        System.out.println(x1 + " " + y1 + " " + x2 + " " + y2);
        line = new Line2D.Float(x1,y1,x2,y2);
        for(Branch b : branches){ b.updateLineRec(); }
    }

    /*protected void updateLineRec(){ updateLineRec(); }

    private void updateLineRec(float newXHook, float newYHook){
        float shiftX = newXHook-line.x1;
        float shiftY = newYHook-line.y1;
        float newX1 = line.x1+shiftX;
        float newX2 = line.x2+shiftX;
        float newY1 = line.y1+shiftY;
        float newY2 = line.y2+shiftY;
        line = new Line2D.Float(newX1,newY1,newX2,newY2);
        for(Branch b : branches){ b.updateLineRec(newX2,newY2); }
    }*/

    private Vector2D countBGrowth(float lenght){
        return new Vector2D(lenght*(float)Math.cos(angle),lenght*(float)Math.sin(angle));
    }

    protected ArrayList<Branch> pleaseAddYourselfToBranchListAndTellYourEveryChildToDoTheSameThankYou(){
        ArrayList<Branch> bList = new ArrayList<Branch>();
        bList.add(this);
        for(Branch b : branches){
            bList.addAll(b.pleaseAddYourselfToBranchListAndTellYourEveryChildToDoTheSameThankYou());
        }
        return bList;
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

    protected void doBranch(){

    }
}
