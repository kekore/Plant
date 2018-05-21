package geneticAlgPack;

import java.util.ArrayList;

public class Population {
    private int generation;
    private ArrayList<Individual> individuals;
    protected ArrayList<DNA> DNAList;

    protected Population(int genNr, ArrayList<DNA> DNAList){
        this.DNAList = DNAList;
        individuals = new ArrayList<Individual>();
        generation = genNr;
        for(DNA dna : DNAList){
            individuals.add(new Individual(dna));
        }
    }
}
