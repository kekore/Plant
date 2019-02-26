package geneticAlgPack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa krzyżująca geny w {@link DNA}.
 */
public class Crossover {
    /**Ilość osobników w jednym pokoleniu.*/
    private final int popSize;
    /**Wektor bitowy krzyżowania genów (wzorzec).*/
    private final boolean[] crossMap;

    /**
     *
     * @param populationSize Ilość osobników w jednym pokoleniu.
     * @param crossMap Wektor bitowy krzyżowania genów (wzorzec).
     */
    protected Crossover(int populationSize, boolean[] crossMap){
        popSize = populationSize;
        this.crossMap = crossMap;
    }

    /**
     * Funkcja wykonująca krzyżowanie genów - zależnie od ostatniej wartości {@link #crossMap} - losowo bądź zgodnie ze wzorcem.
     * @param DNAList Lista {@link DNA} do przetasowania.
     * @return Zwraca listę {@link DNA} po przetasowaniu.
     */
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
