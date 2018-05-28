package geneticAlgPack;

import java.io.Serializable;
import java.util.Random;

public class DNA implements Serializable{
    private final int[] gene;
    public static final int genesAmount = 14;
    protected static final int geneValueInterval = 8;

    protected DNA(){
        Random generator = new Random();
        gene = new int[genesAmount];
        for(int i = 0; i < genesAmount; i++){
            gene[i] = generator.nextInt(2*geneValueInterval+1)-geneValueInterval; //from -8 to 8
        }
    }

    protected DNA(String seed, int shift){
        gene = new int[genesAmount];
        System.out.println("GENERATING WITH SEED DNA NUMBER "+shift);
        for(int i = 0; i < genesAmount; i++){
            gene[i] = ((Math.abs(Character.getNumericValue(seed.charAt((7*i+11*shift)%seed.length())))+13*shift)%17)-8;
        }
        System.out.println("GENERATED: " + getString());
    }

    protected DNA(int[] genes){
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

    public String getString(){
        String s = "";
        for(int i = 0;i<genesAmount;i++){
            s = s+i+"["+gene[i]+"] ";
        }
        return s;
    }
}

/*
0 - początkowa ilość gałęzi (/2)
1 - kąt główny osi symetrii całego zerowego poziomu
2 - kąt między gałęziami zerowego poziomu
3 - czas pierwszego rozgałęzienia
4 - jaką część pokarmu przeznaczać na wzrost gałęzi(1/(|gen|+2)
5 - zieleń gałęzi
6 - zieleń liści
7 - ilość gałęzi w kolejnych rozgałęzieniach (/2)
8 - kąt główny kolejnych poziomów
9 - kąt między gałęziami kolejnych poziomów
10 - od którego poziomu rosną liście (/2)
11 - czas kolejnych rozgałęzień
12 - liczba liści na gałęzi
13 - odchylenie kolejnych gałęzi względem poprzednich
 */