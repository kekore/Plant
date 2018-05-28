package geneticAlgPack;

import javafx.util.Pair;

import java.util.ArrayList;

public class Population {
    protected ArrayList<Individual> individuals;

    protected Population(ArrayList<DNA> DNAList){
        individuals = new ArrayList<Individual>();
        for(DNA dna : DNAList){
            individuals.add(new Individual(dna));
        }
    }

    protected Individual getIndividual(int index){
        Individual ret;
        try{
            ret = individuals.get(index);
        } catch (IndexOutOfBoundsException e){
            throw new RuntimeException();
        }
        return ret;
    }

    protected ArrayList<DNA> getFittest(int amount){
        ArrayList<Pair<DNA,Float>> pairs = new ArrayList<Pair<DNA,Float>>();
        for(Individual i : individuals){
            pairs.add(i.getPair());
        }
        ArrayList<DNA> fittest = new ArrayList<DNA>();
        for(int i = 0; i < amount; i++){
            Pair<DNA,Float> best = pairs.get(0);
            for(int j = 0; j < pairs.size(); j++){
                if(pairs.get(j).getValue() > best.getValue()) best = pairs.get(j);
            }
            fittest.add(best.getKey());
            pairs.remove(best);
        }
        return fittest;
    }

    protected ArrayList<Individual> getSortedList(){
        for(Individual i: individuals){
            if(!i.tested) return null;
        }
        ArrayList<Individual> unsortedList = new ArrayList<Individual>();
        unsortedList.addAll(individuals);
        ArrayList<Individual> sortedList = new ArrayList<Individual>();
        while(unsortedList.size() != 0){
            Individual best = unsortedList.get(0);
            for(int j = 0; j < unsortedList.size(); j++){
                if(unsortedList.get(j).getFitness() > best.getFitness()) best = unsortedList.get(j);
            }
            sortedList.add(best);
            unsortedList.remove(best);
        }
        return sortedList;
    }
}
