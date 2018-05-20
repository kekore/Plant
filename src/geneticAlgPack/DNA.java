package geneticAlgPack;

import java.io.Serializable;
import java.util.Random;

public class DNA implements Serializable{
    private int[] gene;
    private static int genesAmount = 20;

    public DNA(){
        Random generator = new Random();
        gene = new int[genesAmount];
        for(int g : gene){
            g = generator.nextInt(20);
        }
    }

    public DNA(int[] genes){
        gene = genes.clone();
    }

    public int getGene(int index){
        int ret;
        try{
            ret = gene[index];
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException();
        }
        return ret;
    }
}
