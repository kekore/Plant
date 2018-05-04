package geneticAlgPack;

import java.util.Random;

public class DNA {
    int[] gene;
    Random generator;

    public DNA(){
        generator = new Random();
        gene = new int[20];
        for(int g : gene){
            g = generator.nextInt(20);
        }
    }
    DNA(int[] genes){
        gene = genes.clone();
    }
}
