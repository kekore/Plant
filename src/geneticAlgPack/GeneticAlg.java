package geneticAlgPack;

import environmentPack.Tree;

import java.util.ArrayList;

public class GeneticAlg { //TODO make serializable (?) not important
    private ArrayList<Population> populations;
    private final int popSize;
    private final int fittestAmount;
    private int currentGen;
    private Crossover crossover;
    private Mutation mutation;

    public GeneticAlg(int populationSize, int fittestAmount, int mutationProbability, int maxMutedGenes){
        populations = new ArrayList<Population>();
        popSize = populationSize;
        this.fittestAmount = fittestAmount;
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
        ArrayList<DNA> newGenDNAList = crossover.shuffle(populations.get(currentGen).getFittest(fittestAmount));
        mutation.Mutate(newGenDNAList);
        populations.add(new Population(++currentGen,newGenDNAList));
    }

    public Individual getIndividual(int generation, int index){ //TODO not sure if will be used in other packs ( especially in simulator)
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
