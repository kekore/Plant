package environmentPack;

import physicsPack.Maths;
import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{
    protected Line2D.Float line;
    protected float x1;
    protected float y1;
    protected float x2;
    protected float y2;
    private Branch parentBranch;
    private Tree parentTree;
    private ArrayList<Branch> branches;
    private ArrayList<Branch> brothers;
    private ArrayList<Leaf> leaves;
    private int level;
    protected float angle;
    private boolean doesGrowLeaves;

    protected float satiety;
    private float satietyBuffer;

    protected Branch(Tree parentTree, Branch parentBranch, float angle, boolean doesGrowLeaves, float initSatiety){
        if(parentBranch != null){
            line = new Line2D.Float(parentBranch.line.x2,parentBranch.line.y2,parentBranch.line.x2,parentBranch.line.y2);
            x1 = parentBranch.line.x2;
            y1 = parentBranch.line.y2;
            x2 = parentBranch.line.x2;
            y2 = parentBranch.line.y2;
        }
        else {
            line = new Line2D.Float(parentTree.seedX, parentTree.seedY, parentTree.seedX, parentTree.seedY);
            x1 = parentTree.seedX;
            y1 = parentTree.seedY;
            x2 = parentTree.seedX;
            y2 = parentTree.seedY;
        }
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        branches = new ArrayList<Branch>();
        brothers = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        if(doesGrowLeaves){
            float posStep = 1F/(1F+parentTree.leavesAmount/2);
            for(int i = 0; i < parentTree.leavesAmount/2; i++){
                leaves.add(new Leaf(parentTree,this,(i+1)*posStep,true));
            }
            posStep = 1F/(1F+parentTree.leavesAmount-parentTree.leavesAmount/2);
            for(int i = 0; i < parentTree.leavesAmount-parentTree.leavesAmount/2; i++){
                leaves.add(new Leaf(parentTree,this,(i+1)*posStep,false));
            }
        }
        if(parentBranch == null) level = 0;
        else level = parentBranch.level+1;

        this.angle = angle;
        this.doesGrowLeaves = doesGrowLeaves;
        satiety = initSatiety;
        satietyBuffer = 0;
    }

    protected void addBrother(Branch b){
        brothers.add(b);
    }

    protected void gotParticle(Particle p){
        switch (p.type){
            case DROP:{
                addSatiety((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/4F); //fotons were giving too much satiety
                parentTree.addPoints((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/16F); //was without /16F before but too much points
                break;
            }
            case FOTON:{
                addSatiety(2F*((float)parentTree.branchGreen-128)/100);
                parentTree.addPoints((2F*((float)parentTree.branchGreen-128)/100)/4F); //was without /4F before but too much points
                break;
            }
            case OXYGEN:{
                addSatiety(2.54F - 2F*((float)parentTree.branchGreen-128)/100);
                parentTree.addPoints((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/4F); //was without /4F before but too much points
                break;
            }
            case CARBOXIDE:{
                parentTree.addPoints(-1);
                break;
            }
            case TOXIC:{
                parentTree.addPoints(-3);
                break;
            }
        }
    }
    protected void addSatiety(float n){
        satiety = satiety + n;
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

    protected void distributeFoodRec(){
        distributeFood();
        for(Branch b : branches){ b.distributeFoodRec(); }
    }

    private void distributeFood(){
        int portionsCount = 2*branches.size();//add sons
        portionsCount = portionsCount + brothers.size(); //add brothers
        if(level != 0) portionsCount++; //add parent if not root

        float portion = (satiety/2F)/(float)portionsCount;
        if(level != 0) parentBranch.addSatietyBuf(this,portion);
        for(Branch b : branches){
            b.addSatietyBuf(this,2*portion);
        }
        for(Branch b : brothers){
            b.addSatietyBuf(this,portion);
        }
    }

    private void newBranch(float angle){
        boolean doesGrowLeaves;
        if(level+1 >= Math.abs(parentTree.dna.getGene(10))/2) doesGrowLeaves = true;
        else doesGrowLeaves = false;
        Branch newBranch = new Branch(parentTree, this, angle, doesGrowLeaves, 0);
        for(Branch b : branches){
            b.addBrother(newBranch);
            newBranch.addBrother(b);
        }
        branches.add(newBranch);
    }

    protected void growRec(){
        //System.out.println(satiety + " " + angle); //for debugging
        //float lenghtFormula = 10*getStaiety(((float)1/(float)(100*(Math.abs(parentTree.dna.getGene(4))+2)))*satiety);
        float lenghtFormula = getStaiety((1F/(float)(Math.abs(parentTree.dna.getGene(4))+2))*satiety*(1F/10F)); //very long branches
        //float lenghtFormula = (7F/new Vector2D(x2-x1,y2-y1).length())*getStaiety((1F/(float)(Math.abs(parentTree.dna.getGene(4))+2))*satiety*(1F/10F));
        //System.out.println("BRANCH: "+(1F/(float)(Math.abs(parentTree.dna.getGene(4))+2))*satiety*(1F/10F));
        if(doesGrowLeaves)growLeaves();
        //System.out.println((float)1/(float)(2*(Math.abs(parentTree.dna.getGene(4))+2)));
        growLineRec(countBGrowth(lenghtFormula));
        for(Branch b: branches){ b.growRec(); }
    }
    private void growLineRec(Vector2D shift){
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();
        for(Leaf l : leaves){ l.updatePoint(); }
        for(Branch b : branches){ b.updatePointsRec(shift); }
    }
    private void updatePointsRec(Vector2D shift){
        x1 = x1 + shift.getX();
        y1 = y1 + shift.getY();
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();
        for(Branch b : branches){ b.updatePointsRec(shift); }
    }

    protected void updateShapesRec(){
        line = new Line2D.Float(x1,y1,x2,y2);
        for(Leaf l : leaves){ l.updateShape(); }
        for(Branch b : branches){ b.updateShapesRec(); }
    }

    private Vector2D countBGrowth(float lenght){
        return new Vector2D(lenght*(float)Math.cos(angle),lenght*(float)Math.sin(angle));
    }

    private void growLeaves(){
        //float leavesFormula = getStaiety((1-((float)1/(float)(25*(Math.abs(parentTree.dna.getGene(4))+2))))*satiety);
        float leavesFormula = getStaiety((1F-(1F/(float)(Math.abs(parentTree.dna.getGene(4))+2)))*satiety*(1F/10F));
        //System.out.println("LEAVES: "+(1F-(1F/(float)(Math.abs(parentTree.dna.getGene(4))+2)))*satiety*(1F/10F));
        float leafPortion = leavesFormula/parentTree.leavesAmount;
        for(Leaf l : leaves){
            l.grow(leafPortion/5);
        }
    }

    protected ArrayList<Branch> getBranchesRec(){
        ArrayList<Branch> bList = new ArrayList<Branch>();
        bList.add(this);
        for(Branch b : branches){
            bList.addAll(b.getBranchesRec());
        }
        return bList;
    }

    protected ArrayList<Leaf> getLeavesRec(){
        ArrayList<Leaf> lList = new ArrayList<Leaf>();
        lList.addAll(leaves);
        for(Branch b : branches){
            lList.addAll(b.getLeavesRec());
        }
        return lList;
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

    protected void doBranchRec(){
        if(branches.size() == 0) doBranch();
        else{
            for(Branch b : branches) { b.doBranchRec(); }
        }
    }

    private void doBranch(){
        int branchesN;
        if(parentTree.dna.getGene(7) == 0) branchesN = 1;
        else branchesN = Math.abs(parentTree.dna.getGene(7))/2;//max 4, was 8 before

        float generalAngle = (float)(Maths.sig(parentTree.dna.getGene(8)/2) * Math.PI - Math.PI + level*parentTree.dna.getGene(13)*Math.PI/64); //~-180 to ~-45
        float freeAngle = (float)Math.max(-Math.PI-generalAngle,generalAngle);
        boolean isOdd = branchesN%2 == 1;
        int divideN;
        float angleStep;
        float nextAngle;
        if(isOdd){
            divideN = (branchesN - 1)/2 + 1;
            angleStep = (freeAngle/divideN) * Maths.sig(parentTree.dna.getGene(9));
            nextAngle = generalAngle+angleStep*(divideN-1);
            for(int i = 0; i < branchesN; i++){
                newBranch(nextAngle);
                nextAngle = nextAngle - angleStep;
            }
        } else {
            divideN = branchesN / 2 + 1;
            angleStep = (freeAngle / divideN) * Maths.sig(parentTree.dna.getGene(9));
            nextAngle = generalAngle + angleStep * (divideN - 1);
            for (int i = 0; i < branchesN + 1; i++) {
                if (i == branchesN / 2) {
                    nextAngle = nextAngle - angleStep;
                    continue;
                }
                newBranch(nextAngle);
                nextAngle = nextAngle - angleStep;
            }
        }
    }
}
