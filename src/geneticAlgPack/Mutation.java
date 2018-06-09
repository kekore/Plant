package geneticAlgPack;

import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa mutująca geny w {@link DNA} - wprowadza losowe zmiany wartości liczbowych.
 */
public class Mutation {
    /**
     * Prawdopodobieństwo zajścia mutacji (w procentach).
     */
    private final int mutProbability;
    /**
     * Maksymalna liczba zmutowanych genów w wypadku zajścia mutacji.
     */
    private final int maxMutedGenes;

    protected Mutation(int mutationProbability, int maxMutedGenes) {
        mutProbability = mutationProbability;
        this.maxMutedGenes = maxMutedGenes;
    }

    /**
     * Przegląda podaną listę {@link DNA} i przeprowadza mutację jeśli zostanie wylosowane jej zajście.
     *
     * @param DNAList Lista {@link DNA} mająca szansę na mutację.
     */
    protected void Mutate(ArrayList<DNA> DNAList) {
        Random generator = new Random();
        for (DNA d : DNAList) {
            if (generator.nextInt(100) < mutProbability) {
                int genesToMutate = generator.nextInt(maxMutedGenes) + 1;
                for (int i = 0; i < genesToMutate; i++) { //can mutate same gene few times
                    int index = generator.nextInt(DNA.genesAmount);
                    int newValue = (((d.getGene(index) + DNA.geneValueInterval) + (generator.nextInt(5) + (3 * DNA.geneValueInterval / 4))) % (2 * DNA.geneValueInterval + 1)) - DNA.geneValueInterval;
                    d.changeGene(index, newValue);
                }
            }
        }
    }
}
