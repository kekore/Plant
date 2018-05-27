package geneticAlgPack;

import environmentPack.Tree;
import javafx.util.Pair;

import java.util.ArrayList;

public class GeneticAlg { //TODO make serializable (?) not important
    private ArrayList<Population> populations;
    private final int popSize;
    private final int fittestAmount;
    private int currentIndex;
    private int currentGen;
    private int lastTestedGen;
    private Crossover crossover;
    private Mutation mutation;
    public static final float startSatiety = 100;
    public static final long simulationTime = 3000; //should be around 3000-5000

    public GeneticAlg(int populationSize, int fittestAmount, boolean[] crossMap, int mutationProbability, int maxMutedGenes, String seed){
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

    public void createNewGen(){
        ArrayList<DNA> newGenDNAList = crossover.shuffle(populations.get(currentGen).getFittest(fittestAmount));
        mutation.Mutate(newGenDNAList);
        populations.add(new Population(newGenDNAList));
        currentGen++;
    }

    private Individual getIndividual(int generation, int index){ //TODO not sure if will be used in other packs ( especially in simulator)
        Individual ret;
        try{
            ret = populations.get(generation).getIndividual(index);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException();
        }
        return ret;
    }

    /*public Tree getTree(int generation, int index){
        return getIndividual(generation,index).tree;
    }*/

    synchronized public Individual getNextIndividual(){
        System.out.println("pop size: "+populations.size() + " ind: "+populations.get(currentGen).individuals.size() );
        System.out.println("Giving from gen, with index: " + currentGen + " "+currentIndex);
        return getIndividual(currentGen, currentIndex);
    }

    synchronized public boolean signalTested(Individual individual, Tree tree, float fitness){
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

    synchronized public ArrayList<Individual> getSortedList(int generation){
        return populations.get(generation).getSortedList();
    }

    public int getPopSize(){
        return popSize;
    }
    public int getCurrentGen(){
        return currentGen;
    }
    public int getLastTestedGen(){ return lastTestedGen; }
}
