package geneticAlgPack;

import environmentPack.Tree;

import java.util.ArrayList;

/**
 * Klasa kontrolera obiektu {@link GeneticAlg} - udostępnia metody do wykonywania operacji w algorytmie i decyduje czy pozwolić wykonać daną operację,
 * udostępnia także metody pozwalające na uzyskanie wartości niektórych zmiennych.
 */
public class GeneticAlgController {
    /**Obiekt algorytmu genetycznego obsługiwany przez kontroler.*/
    GeneticAlg geneticAlg;
    public GeneticAlgController(){}

    /**
     * Pozwala na zainicjowanie algorytmu genetycznego.
     * @param populationSize Ilość osobników w jednym pokoleniu.
     * @param fittestAmount Ilość najlepszych osobników wybieranych w kolejnych iteracjach algorytmu.
     * @param crossMap Wektor bitowy krzyżowania genów (wzorzec).
     * @param mutationProbability Prawdopodobieństwo zajścia mutacji (w procentach).
     * @param maxMutedGenes Maksymalna liczba zmutowanych genów w wypadku zajścia mutacji.
     * @param seed Ziarno pierwszego pokolenia.
     * @return Zwraca <i>0</i> jeśli algorytm nie był uprzednio zainicjowany bądź <i>-1</i> jeśli był uprzednio zainicjowany.
     */
    public int initGeneticAlg(int populationSize, int fittestAmount, boolean[] crossMap, int mutationProbability, int maxMutedGenes, String seed){
        int ret = 0;
        if(geneticAlg != null) ret = 1;
        geneticAlg = new GeneticAlg(populationSize,fittestAmount,crossMap,mutationProbability,maxMutedGenes,seed);
        return ret;
    }

    /**
     * Powoduje, że kontroler nie obsługuje żadnego algorytmu.
     * @return Zwraca <i>-1</i> jeśli algorytm nie był uprzednio zainicjowany bądź <i>0</i> jeśli były uprzednio zainicjowany.
     */
    public int deInitGeneticAlg(){
        if(geneticAlg == null) return -1;
        else geneticAlg = null;
        return 0;
    }

    /**
     *
     * @return Zwraca <i>true</i> jeśli algorytm jest zainicjowany bądź <i>false</i> jeśli algorytm nie jest zainicjowany.
     */
    public boolean isInitialized(){
        return geneticAlg != null;
    }

    /**
     *
     * @return Zwraca numer ostatniego przetestowanego pokolenia lub <i>-2</i> jeśli algorytm nie został zainicjowany.
     */
    public int getLastTestedGen(){
        if(geneticAlg == null) return -2;
        return geneticAlg.lastTestedGen;
    }

    /**
     *
     * @return Zwraca liczność pojedynczego pokolenia lub <i>-1</i> jeśli algorytm nie został zainicjowany.
     */
    public int getPopSize(){
        if(geneticAlg == null) return -1;
        return geneticAlg.popSize;
    }

    /**
     *
     * @return Zwraca odnośnik do pierwszego w kolejce nieprzetestowanego osobnika {@link Individual} lub null jeśli algorytm nie został zainicjowany.
     */
    synchronized public Individual getNextIndividual(){
        if(geneticAlg == null) return null;
        return geneticAlg.getNextIndividual();
    }

    /**
     * Służy do zasygnalizowania że dany osobnik został przetestowany, wygenerował dane drzewo i uzyskał podany wynik.
     * @param individual Dany osobnik.
     * @param tree Wygenerowane drzewo.
     * @param fitness Uzyskany wynik.
     * @return Zwraca <i>true</i> jesli był to ostati osobnik z pokolenia lub <i>false</i> jeśli nie lub algorytm nie został zainicjowany.
     */
    synchronized public boolean signalTested(Individual individual, Tree tree, float fitness){
        if(geneticAlg == null) return false;
        return geneticAlg.signalTested(individual,tree,fitness);
    }

    /**
     *
     * @param generation Numer pożądanej generacji.
     * @return Zwraca posortowaną według punktacji(od największej do najmniejszej) listę osobników w danej generacji lub <i>null</i> jeśli algorytm nie został zainicjowany.
     */
    synchronized public ArrayList<Individual> getSortedList(int generation){
        if(geneticAlg == null) return null;
        return geneticAlg.getSortedList(generation);
    }
}
