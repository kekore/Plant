package geneticAlgPack;

import javafx.util.Pair;

import java.util.ArrayList;

public class Population {
    //private int generation;
    protected ArrayList<Individual> individuals;
    //private ArrayList<DNA> DNAList;

    protected Population(ArrayList<DNA> DNAList){
        //this.DNAList = DNAList;
        individuals = new ArrayList<Individual>();
        //generation = genNr;
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

    protected ArrayList<DNA> getFittest(int amount){ //TODO check if well done cause i was sleepy doing it :(
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
}
