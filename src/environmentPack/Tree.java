package environmentPack;

import geneticAlgPack.DNA;

import java.util.ArrayList;

public class Tree {
    private DNA dna;
    private ArrayList<Branch> branches;
    private ArrayList<Leaf> leaves;
    private int points;
    private int satiety;

    Tree(int satiety){
        dna = new DNA();
        branches = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        this.satiety = satiety;
    }
    Tree(DNA dna, int satiety){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        this.satiety = satiety;
    }

    public void reset(){

    }
}
