package simulatorPack;

import environmentPack.Circle;
import environmentPack.EnvironmentController;
import environmentPack.Rect;
import geneticAlgPack.GeneticAlg;
import geneticAlgPack.GeneticAlgController;
import geneticAlgPack.Individual;
import javafx.util.Pair;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * Klasa kontrolera obiektu {@link Simulator} - udostępnia metody do wykonywania operacji na symulatorze i decyduje czy pozwolić wykonać daną operację,
 * udostępnia także metody pozwalające na uzyskanie wartości niektórych zmiennych.
 */
public class SimulatorController implements ActionListener{
    Simulator simulator;

    /**
     * Tworzy obiekt symulatora o odpowiednich parametrach - tickTime (zobacz {@link physicsPack.Physics#proc(long)})
     * oraz {@link GeneticAlg#simulationTime}.
     * @param tickTime zobacz - {@link Simulator#Simulator(long, long)}
     */
    public SimulatorController(long tickTime){
        simulator = new Simulator(tickTime, GeneticAlg.simulationTime);
    }

    /**
     *
     * @return <i>true</i> jeśli jest tryb szybkiej symulacj, <i>false</i> jeśli jest tryb normalnej symulacji
     */
    public boolean isQuickSim(){return simulator.quickSim;}

    /**
     *
     * @return Zwraca listę obiektów {@link Circle} widocznych elementów środowiska.
     */
    public ArrayList<Circle> getCircles(){
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Circle>();
        return simulator.environmentController.getCircles();
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Line2D} widocznych elementów środowiska.
     */
    public ArrayList<Line2D> getLines(){
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Line2D>();
        return simulator.environmentController.getLines();
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Line2D} niewidocznych elementów środowiska.
     */
    public ArrayList<Line2D> getInvisLines(){
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Line2D>();
        return simulator.environmentController.getInvisLines(simulator.tickTime);
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Rect} widocznych elementów środowiska.
     */
    public ArrayList<Rect> getRects(){
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Rect>();
        return simulator.environmentController.getRects();
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Rect} niewidocznych elementów środowiska.
     */
    public ArrayList<Rect> getInvisRects() {
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Rect>();
        return simulator.environmentController.getInvisRects();
    }

    /**
     *
     * @return Zwraca listę par {@link Line2D} i {@link Color} skojarzonych z poszczególnymi gałęziami drzewa znajdującego się aktualnie w środowisku.
     */
    public ArrayList<Pair<Line2D,Color>> getBranchLines(){
        if(simulator.quickSim || simulator.environmentController == null) return new ArrayList<Pair<Line2D,Color>>();
        return simulator.environmentController.getBranchLines();
    }

    /**
     *
     * @return Zobacz {@link EnvironmentController#getTime()}.
     */
    public long getTime(){
        if(!simulator.isSet) return -1;
        return simulator.environmentController.getTime();
    }

    /**
     *
     * @return Zobacz {@link EnvironmentController#getPoints()}.
     */
    public float getPoints(){ //should be synchronized instead of this condition (?)
        if(!simulator.isSet || simulator.quickSim) return -2;
        return simulator.environmentController.getPoints();
    }

    /**
     *
     * @return Zobacz {@link EnvironmentController#getSatiety()}.
     */
    public float getSatiety(){ //should be synchronized instead of this condition (?)
        if(!simulator.isSet || simulator.quickSim) return -3;
        return simulator.environmentController.getSatiety();
    }

    /**
     *
     * @return Zwraca liczbę operacji wykonywanych przez symulator na sekundę.
     */
    public int getPPS(){
        return simulator.PPS;
    }

    /**
     * Pozwala ustawić szybkość symulacji w trybie normalnym.
     * @param a Parametr szybkości - większa liczba to wolniejsza symulacja.
     * @return Zwraca <i>0</i> przy sukcesie, <i>-1</i> jeśli podana liczba jest mniejsza od <i>1</i>.
     */
    public int setSpeed(int a){
        if(a<1)return -1;
        simulator.speed = a;
        return 0;
    }

    /**
     * Pozwala przełączać między trybem normalnej symulacji a trybem szybkiej symulacji - zobacz {@link Simulator#alterQuickSim()}.
     * @return Zwraca <i>0</i> przy sukcesie, <i>-1</i> jeśli symulator nie pracuje lub nie ma podłączonego środowiska.
     */
    public int alterQuickSim(){
        if(!simulator.isWorking || simulator.environmentController==null) return -1;
        simulator.alterQuickSim();
        return 0;
    }

    /**
     * Pozwala rozpocząć symulację całego pokolenia przeznaczonego do testów - zobacz {@link Simulator#simulateGeneration()}.
     * @return Zwraca <i>0</i> przy sukcesie, <i>-1</i> jeśli symulator nie pracuje lub nie jest gotowy do pracy
     * (zobacz {@link Simulator#isWorking} oraz {@link Simulator#isSet}).
     */
    public int simulateGeneration(){
        if(simulator.isWorking || !simulator.isSet) return -1;
        simulator.simulateGeneration();
        return 0;
    }

    /**
     * Pozwala ustawić kontroler środowiska, który jest <b>zainicjowany</b>.
     * @param environmentController <b>Zainicjowany</b> kontroler środowiska.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli symulator jest gotowy do pracy lub podany kontroler jest <i>null</i>
     * lub podany kontroler nie jest zainicjowany.
     */
    public int setEnvironmentController(EnvironmentController environmentController){
        if(simulator.isSet || environmentController == null || !environmentController.isInitialized()) return -1;
        simulator.setEnvironmentController(environmentController);
        return 0;
    }

    /**
     * Pozwala ustawić kontroler algorytmu, który jest <b>zainicjowany</b>.
     * @param geneticAlgController <b>Zainicjowany</b> kontroler algorytmu.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli symulator jest gotowy do pracy lub podany kontroler jest <i>null</i>
     * lub podany kontroler nie jest zainicjowany.
     */
    public int setAlgorithmController(GeneticAlgController geneticAlgController){
        if(simulator.isSet || geneticAlgController == null || !geneticAlgController.isInitialized()) return -1;
        simulator.setAlgorithmController(geneticAlgController);
        return 0;
    }

    /**
     * Pozwala na obejrzenie lub resymulację danego osobnika - zobacz {@link Simulator#overviewIndividual(Individual)}.
     * @param individual Osobnik wybrany z katalogu.
     * @return Zwraca <i>0</i> przy pomyślnym wykonaniu jednej z dwóch oczekiwanych operacji lub <i>-1</i> jeśli symulator przeprowadza już
     * jakąś inną symulację.
     */
    public int overviewIndividual(Individual individual){
        if(simulator.isWorking) return -1;
        simulator.overviewIndividual(individual);
        return 0;
    }
    //dla katalogu:

    /**
     * Zobacz {@link GeneticAlgController#getLastTestedGen()}.
     * @return Zwraca numer ostatniego przetestowanego pokolenia lub numer błędu - zobacz {@link GeneticAlgController#getLastTestedGen()}.
     */
    public int getLastTestedGen(){
        return simulator.geneticAlgController.getLastTestedGen();
    }

    /**
     * Zobacz {@link GeneticAlgController#getPopSize()}.
     * @return Zwraca liczbę osobników w jednym pokoleniu lub numer błędu - zobacz {@link GeneticAlgController#getPopSize()}.
     */
    public int getPopSize(){
        return simulator.geneticAlgController.getPopSize();
    }

    /**
     * Zobacz {@link GeneticAlgController#getSortedList(int)}.
     * @param generation Numer generacji, z której żądamy posortowanej listy osobników
     * @return Zwraca posortowaną(od najwyższej punktacji do najniższej) listę osobników z danej przetestowanej generacji - zobacz
     * {@link GeneticAlgController#getSortedList(int)}.
     */
    public ArrayList<Individual> getSortedList(int generation){
        return simulator.geneticAlgController.getSortedList(generation);
    }

    /**
     * Obsługa zdarzenia timera - jeśli spełnione są warunki to spowoduje wykonanie kroku czasowego na symulatorze.
     * @param e Zdarzenie
     */
    @Override
    public void actionPerformed(ActionEvent e){
        //System.out.println((simulator == null) + " " +!simulator.isWorking + " " + !simulator.isSet + " " +simulator.quickSim);
        if(!simulator.isWorking || !simulator.isSet || simulator.quickSim) return;
        if(simulator.quickSim) return;
        if(simulator.cycle >= simulator.speed){
            simulator.proc();
            simulator.cycle = 0;
        }
        else{
            simulator.cycle++;
        }
        if(simulator.environmentController.getTime() == GeneticAlg.simulationTime) simulator.endSimulation();
    }
}
