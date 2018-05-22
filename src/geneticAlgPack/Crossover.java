package geneticAlgPack;

import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    private final int popSize;

    protected Crossover(int populationSize){
        popSize = populationSize;
    }

    protected ArrayList<DNA> shuffle(ArrayList<DNA> DNAlist){ //TODO check if well done - gets 250 gives 1000
        ArrayList<DNA> shuffledDNAList = new ArrayList<DNA>();
        Random generator = new Random();
        for(int i = 0; i < popSize; i++){
            int dnaIndex1 = generator.nextInt(DNAlist.size());
            int dnaIndex2 = generator.nextInt(DNAlist.size());
            int[] genes = new int[DNA.genesAmount];
            for(int j = 0; j < DNA.genesAmount; j++){
                genes[j] = (DNAlist.get(dnaIndex1).getGene(j) + DNAlist.get(dnaIndex2).getGene(j))/2;
            }
            shuffledDNAList.add(new DNA(genes));
        }
        return shuffledDNAList;
    }
}
