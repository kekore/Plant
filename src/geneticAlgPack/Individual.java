package geneticAlgPack;

import environmentPack.Tree;
import javafx.util.Pair;

public class Individual {
    protected DNA dna;
    protected Tree tree;
    private float fitness;
    protected boolean tested;

    Individual(DNA dna){
        this.dna = dna;
        tested = false;
    }
    /*protected void setTree(Tree tree){
        this.tree = tree;
    }*/
    protected void setTested(Tree tree, float fitness){
        if(tested) throw new RuntimeException();
        tested = true;
        this.tree = tree;
        this.fitness = fitness;
    }
    public DNA getDna(){ return dna; }
    public Tree getTree(){
        if(!tested) throw new RuntimeException();
        return tree;
    }
    public float getFitness(){
        if(!tested) return -1;
        return fitness;
    }
    protected Pair<DNA,Float> getPair(){
        return new Pair<DNA,Float>(dna,new Float(fitness));
    }
}
