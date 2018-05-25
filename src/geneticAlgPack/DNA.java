package geneticAlgPack;

import java.io.Serializable;
import java.util.Random;

public class DNA implements Serializable{
    private int[] gene;
    protected static final int genesAmount = 20;
    protected static final int geneValueInterval = 8;

    public DNA(){
        Random generator = new Random();
        gene = new int[genesAmount];
        for(int g : gene){
            g = generator.nextInt(geneValueInterval+1)-geneValueInterval; //from -8 to 8
        }
    }

    public DNA(int[] genes){
        gene = genes.clone(); //maybe doesnt have to clone
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

    protected void changeGene(int index, int value){
        try{
            gene[index] = value;
        } catch (ArrayIndexOutOfBoundsException e){
            throw new RuntimeException();
        }
    }
}

/*
0 - początkowa ilość gałęzi
1 - kąt główny osi symetrii całego zerowego poziomu
2 - kąt między gałęziami zerowego poziomu
3 - czas pierwszego rozgałęzienia (*500)
4 - jaką część pokarmu przeznaczać na wzrost gałęzi(1/(|gen|+2)
5 - zieleń gałęzi
6 - zieleń liści
7 - ilość gałęzi w kolejnych rozgałęzieniach
8 - kąt główny kolejnych poziomów
9 - kąt między gałęziami kolejnych poziomów
10 - od którego poziomu rosną liście
11 - czas kolejnych rozgałęzień (*1000)
12 - liczba liści
 */