package environmentPack;

import geneticAlgPack.DNA;
import physicsPack.Maths;

import java.io.Serializable;
import java.util.ArrayList;

public class Tree implements Serializable{
    protected DNA dna;
    private ArrayList<Branch> branches;
    private int points;
    private int branchGreen;
    private int leafGreen;
    //private int satiety;

    Tree(DNA dna, int satiety){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        int rootBranchesN;
        if(dna.getGene(0) == 0) rootBranchesN = 1;
        else rootBranchesN = Math.abs(dna.getGene(0));

        float generalAngle = (float)(Maths.sig(dna.getGene(1)) * (Math.PI/2) - (3*Math.PI/4)); //-135 to -45
        float freeAngle = (float)Math.max(-Math.PI-generalAngle,generalAngle);
        boolean isOdd = rootBranchesN%2 == 1;
        int divideN;
        float angleStep;
        float nextAngle;
        if(isOdd){
            divideN = (rootBranchesN - 1)/2 + 1;
            angleStep = (freeAngle/2*divideN) * Maths.sig(dna.getGene(2));
            nextAngle = generalAngle+angleStep*(divideN-1);
            for(int i = 0; i < rootBranchesN; i++){
                addBranch(nextAngle);
                nextAngle = nextAngle - angleStep;
            }
        } else{
            divideN = rootBranchesN/2 + 1;
            angleStep = (freeAngle/2*divideN) * Maths.sig(dna.getGene(2));
            nextAngle = generalAngle+angleStep*(divideN-1);
            for(int i = 0; i < rootBranchesN+1; i++){
                if(i == rootBranchesN/2){
                    nextAngle = nextAngle - angleStep;
                    continue;
                }
                addBranch(nextAngle);
                nextAngle = nextAngle - angleStep;
            }
        }

        points = 0;
    }

    private void addBranch(float angle){
        Branch newBranch = new Branch(this,null, angle);
        for(Branch b : branches){
            b.addBrother(newBranch);
            newBranch.addBrother(b);
        }
        branches.add(newBranch);
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

    protected void proc(){
        for(Branch b : branches){
            b.distributeFoodRec();
        }
    }

    /*protected void grow(long time){

    }*/

    protected void addPoints(int p){
        points = points + p;
    }

    public void reset(){

    }
}
