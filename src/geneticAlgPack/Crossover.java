package geneticAlgPack;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.util.ArrayList;
import java.util.Random;

public class Crossover { //TODO ZMIENIC TO NA INNE KRZYZOWANIE
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

            System.out.println("CROSSED: ");
            System.out.println(DNAlist.get(dnaIndex1).getString());
            System.out.println("WITH: ");
            System.out.println(DNAlist.get(dnaIndex2).getString());
            System.out.println("AND GOT: ");
            System.out.println(shuffledDNAList.get(shuffledDNAList.size()-1).getString());

        }

        /*System.out.println("Shuffled:");
        for(DNA d : shuffledDNAList){
            System.out.println("NEXT DNA: ");
            for(int i = 0; i<DNA.genesAmount;i++) {
                System.out.println(d.getGene(i));
            }
        }*/

        return shuffledDNAList;
    }
}
