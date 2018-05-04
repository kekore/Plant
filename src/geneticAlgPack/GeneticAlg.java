package geneticAlgPack;

import java.util.ArrayList;

public class GeneticAlg {
    private ArrayList<Population> populations;
    private int genSize;
    private int currentGen;

    GeneticAlg(int genSize){
        populations = new ArrayList<Population>();
        this.genSize = genSize;
    }

    public void init(){
        ArrayList<DNA> DNAList = new ArrayList<DNA>();
        for(int i = 0; i < genSize; i++){
            DNAList.add(new DNA());
        }
        populations.add(new Population(0,DNAList));
    }

    public void generateNewGen(){
        //TODO
    }
}
