package geneticAlgPack;

import java.util.ArrayList;

public class Population {
    private int generation;
    private ArrayList<Individual> individuals;

    Population(int genNr, ArrayList<DNA> DNAList){
        individuals = new ArrayList<Individual>();
        generation = genNr;
        for(DNA dna : DNAList){
            individuals.add(new Individual(dna));
        }
    }
}
