package geneticAlgPack;

import java.util.ArrayList;
import java.util.Random;

public class Mutation {
    private int mutProbability; //in percent
    private int maxMutedGenes; //maximal amount of mutated genes in one DNA

    protected Mutation(int mutationProbability, int maxMutedGenes){
        mutProbability = mutationProbability;
        this.maxMutedGenes = maxMutedGenes;
    }

    protected void Mutate(ArrayList<DNA> DNAList){
        Random generator = new Random();
        for(DNA d : DNAList) {
            if (generator.nextInt(100) < mutProbability) {
                int genesToMutate = generator.nextInt(maxMutedGenes) + 1;
                for(int i = 0; i < genesToMutate; i++){ //Can mutate same gene few times atm!
                    int index = generator.nextInt(DNA.genesAmount);
                    //int newValue = ( ( (d.getGene(index) + 8) + (generator.nextInt(5)+6) ) % 17 ) - 8;
                    int newValue = ( ( (d.getGene(index) + DNA.geneValueInterval) + (generator.nextInt(5)+(3*DNA.geneValueInterval/4)) ) % (2*DNA.geneValueInterval+1) ) - DNA.geneValueInterval;
                    d.changeGene(index,newValue);
                }
            }
        }
    }
}
