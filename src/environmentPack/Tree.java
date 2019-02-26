package environmentPack;

import geneticAlgPack.DNA;
import physicsPack.Maths;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa drzewa, które może być symulowane w środowisku.
 */
public class Tree implements Serializable{
    /**Cechy drzewa*/
    protected DNA dna;
    /**Gałęzie zerowego poziomu.*/
    private ArrayList<Branch> branches;
    /**Aktualna liczba poziomów gałęzi*/
    protected int levels;
    /**Aktualna punktacja(jakość) drzewa*/
    protected float points;
    /**Zieleń gałęzi w skali RGB*/
    protected int branchGreen;
    /**Zieleń liści w skali RGB*/
    protected int leafGreen;
    /**Liczba liści na jednej gałęzi.*/
    protected int leavesAmount;
    /**Pozycja punktu startowego drzewa na osi X - początek gałęzi zerowego poziomu.*/
    protected float seedX;
    /**Pozycja punktu startowego drzewa na osi Y - początek gałęzi zerowego poziomu.*/
    protected float seedY;
    /**Czas, po którym następuje pierwsze rozgałęzienie.*/
    private long firstBranchTime;
    /**Czas, po którym następują wszystkie kolejne rozgałęzienia po pierwszym.*/
    private long nextBranchTime;

    /**
     * Konstuktor inicjuje pola klasy oraz pobiera niektóre cechy z {@link #dna} i wykonuje obliczenia.
     * @param dna Cechy drzewa
     * @param startSatiety Początkowa wartość nasycenia drzewa - rozdzielana między gałęzie zerowego poziomu.
     * @param seedX Pozycja punktu startowego drzewa na osi X - początek gałęzi zerowego poziomu.
     * @param seedY Pozycja punktu startowego drzewa na osi Y - początek gałęzi zerowego poziomu.
     */
    Tree(DNA dna, float startSatiety, float seedX, float seedY){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        levels = 0;
        points = 0;
        branchGreen = (int)(Maths.sig(dna.getGene(5)) * 128)+128;
        leafGreen = (int)(Maths.sig(dna.getGene(6)) * 128)+128;
        if(dna.getGene(12)==0) leavesAmount = 1;
        else leavesAmount = Math.abs(dna.getGene(12));
        this.seedX = seedX;
        this.seedY = seedY;
        firstBranchTime = (int)Maths.sig(dna.getGene(3)/2) * 900 + 100;
        nextBranchTime = (int)Maths.sig(dna.getGene(11)/2) * 1000 + 1000;

        int rootBranchesN;
        if(dna.getGene(0) == 0) rootBranchesN = 1;
        else rootBranchesN = Math.abs(dna.getGene(0))/2; //max 4, was 8 before

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
                addBranch(nextAngle, startSatiety/rootBranchesN);
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
                addBranch(nextAngle, startSatiety/rootBranchesN);
                nextAngle = nextAngle - angleStep;
            }
        }
    }

    /**
     * Tworzy nową gałąź wyrastajacą z tej gałęzi i dodaje ją jako brat do już istniejących gałęzi, które wyrastają z tej gałęzi.
     * @param angle Kąt w radianach względem osi X.
     * @param initSatiety Początkowa wartość sytości.
     */
    private void addBranch(float angle, float initSatiety){
        boolean doesGrowLeaves;
        if(dna.getGene(10) == 0) doesGrowLeaves = true;
        else doesGrowLeaves = false;
        Branch newBranch = new Branch(this,null, angle, doesGrowLeaves, initSatiety);
        for(Branch b : branches){
            b.addBrother(newBranch);
            newBranch.addBrother(b);
        }
        branches.add(newBranch);
    }

    /**
     * Najważniejsza metoda drzewa. Wykonuje kolejno operacje: rozdzielenie pokarmu, przyjęcie pokarmu z buforów, rozrost gałęzi i liści,
     * ewentualne rozgałęzienie, zaktualizowanie kształtów.
     * @param time Aktualny czas potrzebny do zdecydowania czy pora na rozgałęzienie.
     */
    protected void proc(long time){
        for(Branch b : branches){ b.distributeFoodRec(); }
        for(Branch b : branches){ b.receiveBufferRec(); }
        for(Branch b : branches){ b.growRec(); }
        if((time % firstBranchTime == 0 && levels == 0)||((time-firstBranchTime) % nextBranchTime == 0 && levels > 0)){
            levels++;
            for(Branch b : branches){ b.doBranchRec(); }
        }
        for(Branch b : branches){ b.updateShapesRec(); }
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

    protected ArrayList<Leaf> getLeaves(){
        ArrayList<Leaf> lList = new ArrayList<Leaf>();
        for(Branch b : branches){
            lList.addAll(b.getLeavesRec());
        }
        return lList;
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
}
