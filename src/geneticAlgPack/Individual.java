package geneticAlgPack;

import environmentPack.Tree;
import javafx.util.Pair;

public class Individual {
    protected DNA dna;
    protected Tree tree;
    private float fitness;
    private boolean tested;

    Individual(DNA dna){
        this.dna = dna;
        tested = false;
    }
    protected void setTree(Tree tree){
        this.tree = tree;
    }
    protected void setTested(int fitness){
        this.fitness = fitness;
        tested = true;
    }
    public Tree getTree(){
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
