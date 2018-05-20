package environmentPack;

import geneticAlgPack.DNA;

import java.io.Serializable;
import java.util.ArrayList;

public class Tree implements Serializable{
    private DNA dna;
    private ArrayList<Branch> branches;
    private int points;
    //private int satiety;

    Tree(DNA dna){
        this.dna = dna;
        branches = new ArrayList<Branch>();
        points = 0;
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
            b.proc();
        }
    }

    protected void grow(long time){

    }

    public void reset(){

    }
}
