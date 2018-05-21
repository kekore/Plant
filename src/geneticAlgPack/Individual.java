package geneticAlgPack;

import environmentPack.Tree;

public class Individual {
    private DNA dna;
    private Tree tree;
    private int fitness;
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
    public int getFitness(){
        if(!tested) return -1;
        return fitness;
    }
}
