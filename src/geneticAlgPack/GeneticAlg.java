package geneticAlgPack;

import environmentPack.Tree;

import java.util.ArrayList;

public class GeneticAlg {
    private ArrayList<Population> populations;
    private final int popSize;
    private int currentGen;
    private Crossover crossover;
    private Mutation mutation;

    public GeneticAlg(int populationSize, int mutationProbability, int maxMutedGenes){
        populations = new ArrayList<Population>();
        popSize = populationSize;
        crossover = new Crossover(populationSize);
        mutation = new Mutation(mutationProbability, maxMutedGenes);

        ArrayList<DNA> DNAList = new ArrayList<DNA>();
        for(int i = 0; i < popSize; i++){
            DNAList.add(new DNA());
        }
        populations.add(new Population(0,DNAList));
        currentGen = 0;
    }

    public void createNewGen(){
        ArrayList<DNA> newGenDNAList = crossover.shuffle(populations.get(currentGen).DNAList);
        mutation.Mutate(newGenDNAList);
        populations.add(new Population(++currentGen,newGenDNAList));
    }

    public Individual getIndividual(int generation, int index){
        Individual ret;
        try{
            ret = populations.get(generation).getIndividual(index);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException();
        }
        return ret;
    }

    public Tree getTree(int generation, int index){
        return getIndividual(generation,index).tree;
    }
}
