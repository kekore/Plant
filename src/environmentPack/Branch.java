package environmentPack;

import physicsPack.Maths;
import physicsPack.Vector2D;

import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa pojedynczej gałęzi drzewa {@link Tree}.
 */
public class Branch implements Serializable{
    /**Reprezentacja graficzna gałęzi*/
    protected Line2D.Float line;
    /** Współrzędna X początku gałęzi*/
    protected float x1;
    /** Współrzędna Y początku gałęzi*/
    protected float y1;
    /** Współrzędna X końca gałęzi*/
    protected float x2;
    /** Współrzędna Y końca gałęzi*/
    protected float y2;
    /** Odnośnik do gałęzi, z której wyrasta ta gałąź.*/
    private Branch parentBranch;
    /** Odnośnik do drzewa, w którym znajduje się ta gałąź - null jeśli gałąź zerowego poziomu.*/
    private Tree parentTree;
    /** Lista gałęzi, które wyrastają z tej gałęzi.*/
    private ArrayList<Branch> branches;
    /** Lista gałęzi, które są na tym samym poziomie co dana gałąź i wyrastają z tej samej gałęzi.*/
    private ArrayList<Branch> brothers;
    /** Lista liści {@link Leaf}, które wyrastają z tej gałęzi. */
    private ArrayList<Leaf> leaves;
    /** Poziom na którym znajduje się gałąź. */
    private int level;
    /** Kąt w radianach względem osi X.*/
    protected float angle;
    /** <i>true</i> - liście wyrastają z gałęzi, <i>false</i> - liście nie wyrastają z gałęzi.*/
    private boolean doesGrowLeaves;
    /** Wartość sytości gałęzi - sytość jest używana do wzrostu gałęzi i liści {@link Leaf} oraz rozprowadzana do sąsiadujacych gałęzi*/
    protected float satiety;
    /** Bufor, do którego trafia sytość od sąsiednich gałęzi - wartość jest przekierowywana do {@link #satiety} po tym jak wszystkie gałęzie
     * podzielą się z sąsiadami, dzięki czemu rozprowadzanie pokarmu przebiega poprawnie.*/
    private float satietyBuffer;

    /**
     * Konstruktor inicjujący potrzebne pola w klasie - ustawia wartości początkowe zmiennych oraz dodaje obiekty liści {@link Leaf} do {@link #leaves}
     * jeśli gałąź ma mieć liście.
     * @param parentTree Odnośnik do drzewa, w którym znajduje się ta gałąź.
     * @param parentBranch Odnośnik do drzewa, w którym znajduje się ta gałąź - null jeśli gałąź zerowego poziomu.
     * @param angle <i>true</i> - liście wyrastają z gałęzi, <i>false</i> - liście nie wyrastają z gałęzi.
     * @param doesGrowLeaves <i>true</i> - liście wyrastają z gałęzi, <i>false</i> - liście nie wyrastają z gałęzi.
     * @param initSatiety Początkowa wartość sytości gałęzi.
     */
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

    /**
     * Dodaje gałąź do listy {@link #brothers}.
     * @param b Gałąź dodawana do listy.
     */
    protected void addBrother(Branch b){
        brothers.add(b);
    }

    /**
     * Funkcja wywoływana gdy cząsteczka zderzy się z gałęzią - zostają dodane lub odjęte punkty i dodana sytość - zobacz {@link Environment#proc(long)}.
     * @param p Cząsteczka, która zderzyła się z gałęzią.
     */
    protected void gotParticle(Particle p){
        switch (p.type){
            case DROP:{
                addSatiety((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/4F);
                parentTree.addPoints((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/16F);
                break;
            }
            case FOTON:{
                addSatiety(2F*((float)parentTree.branchGreen-128)/100);
                parentTree.addPoints((2F*((float)parentTree.branchGreen-128)/100)/4F);
                break;
            }
            case OXYGEN:{
                addSatiety(2.54F - 2F*((float)parentTree.branchGreen-128)/100);
                parentTree.addPoints((2.54F - 2F*((float)parentTree.branchGreen-128)/100)/4F);
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

    /**
     * Konsumuje sytość - zobacz {@link #growRec()} i {@link #growLeaves()}.
     * @param n Wartość sytości do skonsumowania.
     * @return Zwraca skonsumowaną wartość pokarmu.
     */
    protected float getStaiety(float n){
        if(n > satiety) throw new RuntimeException();
        satiety = satiety - n;
        return n;
    }

    private void addSatietyBuf(Branch giver, float n){
        satietyBuffer = satietyBuffer + giver.getStaiety(n);
    }

    /**
     * Rekurencyjne przyjęcie sytości z bufora do {@link #satiety} - zobacz {@link Tree#proc(long)}.
     */
    protected void receiveBufferRec(){
        satiety = satiety + satietyBuffer;
        satietyBuffer = 0;
        for(Branch b : branches){ b.receiveBufferRec(); }
    }

    /**
     * Rekurencyjne rozprowadzenie pokarmu do sąsiednich gałęzi - zobacz @link Tree#proc(long)}.
     */
    protected void distributeFoodRec(){
        distributeFood();
        for(Branch b : branches){ b.distributeFoodRec(); }
    }

    /**
     * Przekazuje część swojej sytości do buforów sąsiadujących gałęzi.
     */
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

    /**
     * Tworzy nową gałąź wyrastajacą z tej gałęzi i dodaje ją jako brat do już istniejących gałęzi, które wyrastają z tej gałęzi.
     * @param angle Kąt w radianach względem osi X.
     */
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

    /**
     * Rekurencyjny wzrost gałęzi i liści.
     */
    protected void growRec(){
        float lenghtFormula = getStaiety((1F/(float)(Math.abs(parentTree.dna.getGene(4))+2))*satiety); //very long branches
        if(doesGrowLeaves)growLeaves();
        growLineRec(countBGrowth(lenghtFormula));
        for(Branch b: branches){ b.growRec(); }
    }

    /**
     * Przesunięcie współrzędnych końca gałęzi i rekurencyjne przesunięcie wszystkich gałęzi i liści związanych z tą pozycją.
     * @param shift Wektor przesunięcia.
     */
    private void growLineRec(Vector2D shift){
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();
        for(Leaf l : leaves){ l.updatePoint(); }
        for(Branch b : branches){ b.updatePointsRec(shift); }
    }

    /**
     * Rekurencyjne przesunięcie współrzędnych gałęzi.
     * @param shift Wektor przesunięcia.
     */
    private void updatePointsRec(Vector2D shift){
        x1 = x1 + shift.getX();
        y1 = y1 + shift.getY();
        x2 = x2 + shift.getX();
        y2 = y2 + shift.getY();
        for(Branch b : branches){ b.updatePointsRec(shift); }
    }

    /**
     * Rekurencyjne zaktualizowanie kształtów reprezentujących gałęzie i liście - zobacz {@link Tree#proc(long)}.
     */
    protected void updateShapesRec(){
        line = new Line2D.Float(x1,y1,x2,y2);
        for(Leaf l : leaves){ l.updateShape(); }
        for(Branch b : branches){ b.updateShapesRec(); }
    }

    /**
     *
     * @param lenght Zadana długość o jaką ma wzrosnąć gałąź.
     * @return Zwraca wektor o jaki musi przesunąć się koniec gałęzi w wyniku wzrotu.
     */
    private Vector2D countBGrowth(float lenght){
        return new Vector2D(lenght*(float)Math.cos(angle),lenght*(float)Math.sin(angle));
    }

    /**
     * Obliczenie wzrostu liści i wywołanie funkcji {@link Leaf#grow(float)};
     */
    private void growLeaves(){
        float leavesFormula = getStaiety((1F-(1F/(float)(Math.abs(parentTree.dna.getGene(4))+2)))*satiety);

        float leafPortion = leavesFormula/parentTree.leavesAmount;
        for(Leaf l : leaves){
            l.grow(leafPortion/5);
        }
    }

    /**
     *
     * @return Zwraca listę gałęzi zawierajacą wszystkie podrzędne gałęzie i tą gałąź - zobacz {@link Tree#getBranches()}.
     */
    protected ArrayList<Branch> getBranchesRec(){
        ArrayList<Branch> bList = new ArrayList<Branch>();
        bList.add(this);
        for(Branch b : branches){
            bList.addAll(b.getBranchesRec());
        }
        return bList;
    }

    /**
     *
     * @return Zwraca listę liści zawierającą wszystkie podrzędne liście - zobacz {@link Tree#getLeaves()}.
     */
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

    /**
     * Rekurencyjnie dociera do ostatniego poziomu gałęzi i wydaje sygnał do rozgałęzienia.
     */
    protected void doBranchRec(){
        if(branches.size() == 0) doBranch();
        else{
            for(Branch b : branches) { b.doBranchRec(); }
        }
    }

    /**
     * Tworzy nowe rozgałęzienie - tworzy odpowiednią ilość nowych gałęzi o odpowiednich parametrach - zgodnych z DNA drzewa.
     */
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
