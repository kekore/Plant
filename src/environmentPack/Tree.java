package environmentPack;

import geneticAlgPack.DNA;
import physicsPack.Maths;
import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Tree implements Serializable{
    protected DNA dna;
    private ArrayList<Branch> branches;
    protected int levels;
    protected float points;
    private int branchGreen;
    private int leafGreen;
    protected float seedX;
    protected float seedY;
    private boolean isSeeded;
    private long firstBranchTime;
    private long nextBranchTime;
    //private int satiety;

    Tree(DNA dna, int satiety, float seedX, float seedY){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        levels = 0;
        this.seedX = seedX;
        this.seedY = seedY;
        //isSeeded = false;
        firstBranchTime = (int)Maths.sig(dna.getGene(3)/2) * 900 + 100;
        //firstBranchTime = (Math.abs(dna.getGene(3))+1)*100;
        nextBranchTime = (int)Maths.sig(dna.getGene(11)/2) * 1000 + 1000;
        //nextBranchTime = (Math.abs(dna.getGene(11))+1);
        //nextBranchTime = (dna.getGene(11)+1)*2000;

        int rootBranchesN;
        if(dna.getGene(0) == 0) rootBranchesN = 1;
        else rootBranchesN = Math.abs(dna.getGene(0));

        float generalAngle = (float)(Maths.sig(dna.getGene(1)/2) * (Math.PI/2) - (3*Math.PI/4)); //~-135 to ~-45
        float freeAngle = (float)Math.max(-Math.PI-generalAngle,generalAngle);
        boolean isOdd = rootBranchesN%2 == 1;
        int divideN;
        float angleStep;
        float nextAngle;
        if(isOdd){
            divideN = (rootBranchesN - 1)/2 + 1;
            angleStep = (freeAngle/divideN) * Maths.sig(dna.getGene(2)/2);
            nextAngle = generalAngle+angleStep*(divideN-1);
            for(int i = 0; i < rootBranchesN; i++){
                addBranch(nextAngle, satiety/rootBranchesN);
                nextAngle = nextAngle - angleStep;
            }
        } else{
            divideN = rootBranchesN/2 + 1;
            angleStep = (freeAngle/divideN) * Maths.sig(dna.getGene(2)/2);
            nextAngle = generalAngle+angleStep*(divideN-1);
            for(int i = 0; i < rootBranchesN+1; i++){
                if(i == rootBranchesN/2){
                    nextAngle = nextAngle - angleStep;
                    continue;
                }
                addBranch(nextAngle, satiety/rootBranchesN);
                nextAngle = nextAngle - angleStep;
            }
        }
        points = 0;
    }
    private void addBranch(float angle, float initSatiety){
        boolean doesGrowLeaves;
        if(dna.getGene(10) == 0) doesGrowLeaves = true;
        else doesGrowLeaves = false;
        Branch newBranch = new Branch(this,null, angle, doesGrowLeaves, initSatiety); //TODO pomyslec czy moze rosnac liscie
        for(Branch b : branches){
            b.addBrother(newBranch);
            newBranch.addBrother(b);
        }
        branches.add(newBranch);
    }

    /*protected void seed(int seedX, int seedY){
        this.seedX = seedX;
        this.seedY = seedY;

        isSeeded = true;
    }*/

    protected void proc(long time){
        for(Branch b : branches){ b.distributeFoodRec(); }
        for(Branch b : branches){ b.receiveBufferRec(); }
        for(Branch b : branches){ b.growRec(); } //TODO opoznianie kolejnych branchowan
        if((time % firstBranchTime == 0 && levels == 0)||((time-firstBranchTime) % nextBranchTime == 0 && levels > 0)){
            System.out.println("BRANCH");
            levels++;
            for(Branch b : branches){ b.doBranchRec(); }
        }
        for(Branch b : branches){ b.updateLineRec(); }
    }

    protected void addPoints(float p){
        points = points + p;
    }

    protected ArrayList<Branch> getBranches(){
        ArrayList<Branch> bList = new ArrayList<Branch>();
        for(Branch b : branches){
            bList.addAll(b.getBranchesRec());
        }
        return bList;
    }

    protected ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        for(Branch b : branches){
            lList.addAll(b.getLinesRec());
        }
        return lList;
    }

    protected ArrayList<Circle> getCircles(){
        ArrayList<Circle> cList = new ArrayList<Circle>();
        for(Branch b : branches){
            cList.addAll(b.getCirclesRec());
        }
        return cList;
    }

    public void reset(){

    }

        /*Tree(int satiety){
        dna = new DNA();
        branches = new ArrayList<Branch>();
        //leaves = new ArrayList<Leaf>();
        //this.satiety = satiety;
    }
    Tree(int satiety, DNA dna){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        //leaves = new ArrayList<Leaf>();
        //this.satiety = satiety;
    }*/

    /*protected void addSatiety(int n){
        satiety = satiety + n;
    }*/
}
