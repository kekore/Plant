package simulatorPack;

import environmentPack.*;
import geneticAlgPack.GeneticAlg;
import geneticAlgPack.GeneticAlgController;
import geneticAlgPack.Individual;

/**
 * Klasa symulatora - wymusza kroki czasowe na symulowanym środowisku, umieszcza drzewa w środowisku i przekierowuje wyniki testów do algorytmu genetycznego.
 */
public class Simulator{
    /**Obiekt kontrolera algorytmu genetycznego do komunikacji z algorytmem.*/
    protected GeneticAlgController geneticAlgController;
    /**Obiekt kontrolera środowiska do komunikacji ze środowiskiem.*/
    protected EnvironmentController environmentController;
    //flagi:
    /**<i>true</i> - symulator posiada zainicjowany algorytm oraz środowisko*/
    protected boolean isSet;
    /**<i>true</i> - symulator jest w trakcie wykonywania jednej symulacji lub serii symulacji*/
    protected boolean isWorking;
    /**<i>true</i> - symulator wykonuje symulację całego pokolenia dla algorytmu genetycznego*/
    private boolean isWorkingForAlgorithm;
    /**<i>true</i> - symulator pokazuje ostateczny wygląd przetestowanego drzewa wybranego z katalogu*/
    private boolean isOverviewing;
    /**<i>true</i> - symulator wykonuje resymulację przetestowanego drzewa*/
    private boolean isResimulating;
    /**<i>true</i> - symulator jest w trybie szybkiej symulacji*/
    protected boolean quickSim;
    //stałe:
    /**Relatywna długość tiku zegara - zobacz {@link physicsPack.Physics#proc(long)}.*/
    protected final long tickTime;
    /**Długość pojedynczej symulacji.*/
    protected final long simulationTime;
    //zarządzanie czasem:
    /**Używana do zmiany tempa symulacji - zobacz {@link simulatorPack.SimulatorController#actionPerformed(java.awt.event.ActionEvent)}.*/
    protected int speed;
    /**Używana do odliczania kiedy należy wykonać kolejny krok czasowy - zobacz {@link simulatorPack.SimulatorController#actionPerformed(java.awt.event.ActionEvent)}.*/
    protected int cycle;
    //obliczanie akcji na sekundę:
    /**Moment rozpoczęcia odliczania 1000ms - zobacz {@link #proc()}.*/
    private long secStart;
    /**Liczba operacji wykonanych w aktualnym cyklu - zobacz {@link #proc()}.*/
    private int actions;
    /**Ostatnia obliczona liczba operacji na sekundę - zobacz {@link #proc()}.*/
    protected int PPS;
    //**Wątek do szybkiej symulacji.*/
    private QuickSimThread thread;
    /**Aktualnie rozpatrywany osobnik - zobacz {@link #startNewTestingSimulation(Individual)} oraz {@link #overviewIndividual(Individual)}*/
    private Individual currentIndividual;

    protected Simulator(long tickTime, long simulationTime){
        isSet = false;
        this.tickTime = tickTime;
        quickSim = false;
        geneticAlgController = new GeneticAlgController();
        environmentController = new EnvironmentController();
        speed = 4;
        cycle = 0;
        this.simulationTime = simulationTime;
        secStart = System.currentTimeMillis();
        actions=0;
        PPS=0;
        thread = new QuickSimThread(this);
        isWorking=false;
        isWorkingForAlgorithm=false;
        isOverviewing=false;
        isResimulating=false;
    }

    /**
     * Oblicza akcje na sekundę i wymusza krok czasowy na środowisku.
     */
    synchronized protected void proc(){
        if(System.currentTimeMillis() - secStart < 1000){
            actions++;
        } else{
            PPS = actions;
            actions = 0;
            secStart = System.currentTimeMillis();
        }
        environmentController.proc(tickTime);
    }

    /**
     * Kończy symulację, której czas dobiegł końca. Wykonuje inne czynności przy symulacji całego pokolenia dla algorytmu genetycznego i inne
     * przy resymulacji pojedynczego osobnika z katalogu. Przygotowuje symulator do kolejnej symulacji.
     */
    protected void endSimulation(){
        cycle=0;
        if(isResimulating){
            currentIndividual = null;
            isResimulating = false;
            isWorking = false;
            if(quickSim) quickSim = !quickSim;
        }
        else if(isWorkingForAlgorithm) {
            if (!geneticAlgController.signalTested(currentIndividual, environmentController.getTree(), environmentController.getPoints())) {
                System.out.println("DONE");
                startNewTestingSimulation(geneticAlgController.getNextIndividual());
            }
            else {
                currentIndividual = null;
                isWorkingForAlgorithm = false;
                isWorking = false;
                if (quickSim) quickSim = !quickSim;
            }
        }
    }

    /**
     * Ustawia zainicjowany kontroler środowiska (pilnuje tego kontroler symulatora), jeśli jest też ustaniowy kontroler algorytmu to ustawia
     * flagę {@link #isSet} na true.
     * @param environmentController Zainicjowany kontroler środowiska do podłączenia pod symulator.
     */
    protected void setEnvironmentController(EnvironmentController environmentController){
        this.environmentController = environmentController;
        if(geneticAlgController.isInitialized()){
            environmentController.setIsEditable(false);
            isSet = true;
            System.out.println("IS SET == TRUE");
        }
    }

    /**
     * Ustawia zainicjowany kontroler algorytmu genetycznego (pilnuje tego kontroler symulatora), jeśli jest też ustawionyy kontroler
     * środowiska to ustawia flagę {@link #isSet} na true.
     * @param geneticAlgController Zainicjowany kontroler środowiska do podłączenia pod symulator.
     */
    protected void setAlgorithmController(GeneticAlgController geneticAlgController){
        this.geneticAlgController = geneticAlgController;
        if(environmentController.isInitialized()){
            environmentController.setIsEditable(false);
            isSet = true;
            System.out.println("IS SET == TRUE");
        }
    }

    /**
     * Przełącza symulator między szybkim trybem symulacji a normalnym. Przy włączeniu tworzy obiekt wątku {@link QuickSimThread} zmienia
     * flagę {@link #quickSim} na <i>true</i> i uruchamia go, a przy wyłączeniu zmiena flagę {@link #quickSim} na <i>false</i>
     * i czeka na dołączenie(zakończenie) tego wątku.
     */
    protected void alterQuickSim(){
        if(!quickSim) {
            quickSim = true;
            thread = new QuickSimThread(this);
            thread.start();
        } else{
            quickSim = false;
            try{
                thread.join();
            } catch (InterruptedException e){
                throw new RuntimeException();
            }
        }
    }

    /**
     * Ustawia flagi {@link #isOverviewing}, {@link #isWorking}, {@link #isWorkingForAlgorithm} na odpowiednie wartości <i>[false,true,true]</i>
     * i rozpoczyna proces symulacji całego pokolenia.
     */
    protected void simulateGeneration(){
        /*isOverviewing=false;
        isWorking = true;
        isWorkingForAlgorithm = true;*/
        startNewTestingSimulation(geneticAlgController.getNextIndividual());
    }

    /**
     * Służy do uruchamiania symulacji kolejnych osobników w trakcie symulacji całego pokolenia
     * @param individual Osobnik, którego symulację należy teraz rozpocząć.
     */
    private void startNewTestingSimulation(Individual individual){
        currentIndividual = individual;
        System.out.println("STARTING SIMULATION OF INDIVIDUAL WITH DNA: "+individual.getDna().getString());
        environmentController.insertTreeToTest(individual.getDna(),GeneticAlg.startSatiety);
        isOverviewing=false;
        isWorking = true;
        isWorkingForAlgorithm = true;
    }

    /**
     * Uruchamia podgląd przetestowanego osobnika bądź uruchamia jego resymulację, zależnie wartości flagi {@link #isOverviewing} i zmiennej
     * {@link #currentIndividual}.
     * @param individual Osobnik wybrany w katalogu.
     */
    protected void overviewIndividual(Individual individual){
        if(isOverviewing && individual==currentIndividual){
            resimulate(individual);
            return;
        }
        isOverviewing = true;
        currentIndividual=individual;
        environmentController.insertTestedTree(individual.getTree());
    }

    /**
     * Uruchamia resymulację gdy ten sam osobnik w katalogu został naciśnięty drugi raz z rzędu.
     * @param individual Osobnik do resymulacji.
     */
    private void resimulate(Individual individual){
        isOverviewing = false;
        isWorking = true;
        isResimulating = true;
        environmentController.insertTreeToTest(individual.getDna(),GeneticAlg.startSatiety);
    }
}
