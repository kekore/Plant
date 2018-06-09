package geneticAlgPack;

import environmentPack.Tree;
import javafx.util.Pair;

/**
 * Klasa osobnika - identyfikuje się {@link DNA}, gdy zostanie przetestowany to otrzymuje wygenerowane drzewo wraz z wynikiem punktowym.
 */
public class Individual {
    /**Cechy osobnika*/
    protected DNA dna;
    /**Uzyskane drzewo w wyniku testu*/
    private Tree tree;
    /**Uzyskana punktacja*/
    private float fitness;
    /**<i>true</i> - osobnik został przetestowany, <i>false</i> - osobnik nie został przetestowany.*/
    protected boolean tested;

    Individual(DNA dna){
        this.dna = dna;
        tested = false;
    }

    /**
     * Oznacza osobnika jako przetestowanego.
     * @param tree Uzyskane drzewo w wyniku symulacji.
     * @param fitness Uzyskany wynik punktowy.
     */
    protected void setTested(Tree tree, float fitness){
        if(tested) throw new RuntimeException();
        tested = true;
        this.tree = tree;
        this.fitness = fitness;
    }
    public DNA getDna(){ return dna; }
    public Tree getTree(){
        if(!tested) throw new RuntimeException();
        return tree;
    }
    public float getFitness(){
        if(!tested) return -1;
        return fitness;
    }

    /**
     * Zobacz {@link Population#getFittest(int)}.
     * @return Zwraca parę: {@link DNA} tego osobnika oraz jego wynik lub <i>null</i> jeśli ten osobnik nie został przetestowany.
     */
    protected Pair<DNA,Float> getPair(){
        if(!tested) return null;
        return new Pair<DNA,Float>(dna,new Float(fitness));
    }
}
