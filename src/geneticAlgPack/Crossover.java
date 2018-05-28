package geneticAlgPack;

import java.util.ArrayList;
import java.util.Random;

public class Crossover {
    private final int popSize;
    private final boolean[] crossMap;

    protected Crossover(int populationSize, boolean[] crossMap){
        popSize = populationSize;
        this.crossMap = crossMap;
        for(int i = 0; i < DNA.genesAmount+1; i++){
            System.out.println(crossMap[i]);
        }
    }

    protected ArrayList<DNA> shuffle(ArrayList<DNA> DNAList){
        ArrayList<DNA> shuffledDNAList = new ArrayList<DNA>();
        Random generator = new Random();

        if(crossMap[DNA.genesAmount] == true){
            for(int i = 0; i < popSize; i++){
                int dnaIndex1 = generator.nextInt(DNAList.size());
                int dnaIndex2 = generator.nextInt(DNAList.size());
                int[] genes = new int[DNA.genesAmount];
                for(int j = 0; j < DNA.genesAmount; j++){
                    boolean giver = generator.nextBoolean();
                    if(giver) genes[j] = DNAList.get(dnaIndex1).getGene(j);
                    else genes[j] = DNAList.get(dnaIndex2).getGene(j);
                }
                shuffledDNAList.add(new DNA(genes));
            }
        }
        else{
            for(int i = 0; i < popSize; i++){
                int dnaIndex1 = generator.nextInt(DNAList.size());
                int dnaIndex2 = generator.nextInt(DNAList.size());
                int[] genes = new int[DNA.genesAmount];
                for(int j = 0; j < DNA.genesAmount; j++){
                    if(crossMap[j]) genes[j] = DNAList.get(dnaIndex1).getGene(j);
                    else genes[j] = DNAList.get(dnaIndex2).getGene(j);
                }
                shuffledDNAList.add(new DNA(genes));

                System.out.println("CROSSED: ");
                System.out.println(DNAList.get(dnaIndex1).getString());
                System.out.println("WITH: ");
                System.out.println(DNAList.get(dnaIndex2).getString());
                System.out.println("AND GOT: ");
                System.out.println(shuffledDNAList.get(shuffledDNAList.size()-1).getString());
            }
        }
        return shuffledDNAList;
    }
}
