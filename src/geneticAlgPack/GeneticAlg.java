package geneticAlgPack;

import environmentPack.Tree;

import java.util.ArrayList;

/**
 * Klasa algorytmu genetycznego - przeprowadza krzyżowanie i mutację genów {@link DNA} oraz przekazuje osobniki do testowania.
 */
public class GeneticAlg {
    /**Lista wszystkich populacji, czyli pokoleń.*/
    private ArrayList<Population> populations;
    /**Ilość osobników w jednym pokoleniu.*/
    protected final int popSize;
    /**Ilość najlepszych osobników wybieranych w kolejnych iteracjach algorytmu.*/
    private final int fittestAmount;
    /**Indeks aktualnie testowanego osobnika/pierwszego w kolejce do bycia przetestowanym.*/
    private int currentIndex;
    /**Numer aktualnie testowanego pokolenia*/
    private int currentGen;
    /**Numer ostatniego w pełni przetestowanego pokolenia.*/
    protected int lastTestedGen;
    /**Obiekt pozwalający na krzyżowanie genów - {@link geneticAlgPack.Crossover}.*/
    private final Crossover crossover;
    /**Obiekt pozwalający na mutację genów - {@link geneticAlgPack.Mutation}.*/
    private final Mutation mutation;
    /**Początkowa wartość sytości dla drzewa.*/
    public static final float startSatiety = 100;
    /**Czas symulacji jednego osobnika.*/
    public static final long simulationTime = 3000; //should be around 3000-5000

    /**
     * Konstruktor losuje pierwsze pokolenie jeśli nie zostało podane ziarno pierwszego pokolenia, w przeciwnym wypadku generuje pierwsze pokolenie
     * z użyciem podanego ziarna.
     * @param populationSize Ilość osobników w jednym pokoleniu.
     * @param fittestAmount Ilość najlepszych osobników wybieranych w kolejnych iteracjach algorytmu.
     * @param crossMap Wektor bitowy krzyżowania genów (wzorzec).
     * @param mutationProbability Prawdopodobieństwo zajścia mutacji (w procentach).
     * @param maxMutedGenes Maksymalna liczba zmutowanych genów w wypadku zajścia mutacji.
     * @param seed Ziarno pierwszego pokolenia.
     */
    protected GeneticAlg(int populationSize, int fittestAmount, boolean[] crossMap, int mutationProbability, int maxMutedGenes, String seed){
        populations = new ArrayList<Population>();
        popSize = populationSize;
        this.fittestAmount = fittestAmount;
        crossover = new Crossover(populationSize,crossMap);
        mutation = new Mutation(mutationProbability, maxMutedGenes);

        ArrayList<DNA> DNAList = new ArrayList<DNA>();
        if(seed == null) {
            for (int i = 0; i < popSize; i++) {
                DNAList.add(new DNA());
            }
        }
        else{
            for (int i = 0; i < popSize; i++) {
                DNAList.add(new DNA(seed,i));
            }
        }
        System.out.println("Wygenerowano: " + DNAList.size());
        populations.add(new Population(DNAList));
        currentIndex=0;
        currentGen = 0;
        lastTestedGen = -1;
    }

    /**
     * Generuje nowe pokolnie - przekazuje listę najwyżej punktowanych {@link DNA} do obiektu {@link #crossover} a następnie do obiektu
     * {@link #mutation}, z uzyskanych genów jest tworzony nowy obiekt {@link Population} i dodawany do listy {@link #populations}.
     */
    private void createNewGen(){
        ArrayList<DNA> newGenDNAList = crossover.shuffle(populations.get(currentGen).getFittest(fittestAmount));
        mutation.Mutate(newGenDNAList);
        populations.add(new Population(newGenDNAList));
        currentGen++;
    }

    /**
     *
     * @param generation Pożadany numer pokolenia.
     * @param index Pożadany indeks.
     * @return Zwraca odnośnik do osobnika z podanego pokolenia o podanym indeksie.
     */
    private Individual getIndividual(int generation, int index){
        Individual ret;
        try{
            ret = populations.get(generation).getIndividual(index);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException();
        }
        return ret;
    }

    /**
     *
     * @return Zwraca odnośnik do pierwszego w kolejce nieprzetestowanego osobnika {@link Individual}.
     */
    synchronized protected Individual getNextIndividual(){
        System.out.println("pop size: "+populations.size() + " ind: "+populations.get(currentGen).individuals.size() );
        System.out.println("Giving from gen, with index: " + currentGen + " "+currentIndex);
        return getIndividual(currentGen, currentIndex);
    }

    /**
     * Służy do zasygnalizowania że dany osobnik został przetestowany, wygenerował dane drzewo i uzyskał podany wynik.
     * @param individual Dany osobnik.
     * @param tree Wygenerowane drzewo.
     * @param fitness Uzyskany wynik.
     * @return Zwraca <i>true</i> jesli był to ostati osobnik z pokolenia lub <i>false</i> jeśli nie.
     */
    synchronized protected boolean signalTested(Individual individual, Tree tree, float fitness){
        boolean generateNextGen = false;
        if(currentIndex+1==popSize){
            generateNextGen = true;
            lastTestedGen = currentGen;
            currentIndex = 0;
        }else{
            currentIndex++;
        }
        individual.setTested(tree,fitness);
        if(generateNextGen){
            createNewGen();
        }
        return generateNextGen;
    }

    /**
     *
     * @param generation Numer pożądanej generacji.
     * @return Zwraca posortowaną według punktacji(od największej do najmniejszej) listę osobników w danej generacji lub <i>null</i> jeśli
     * podana została liczba większa niż numer ostatniego przetestowanego pokolenia {@link #lastTestedGen}.
     */
    synchronized protected ArrayList<Individual> getSortedList(int generation){
        if(generation > lastTestedGen) return null;
        return populations.get(generation).getSortedList();
    }
}
