package simulatorPack;

import geneticAlgPack.GeneticAlg;

public class QuickSimThread extends Thread {
    Simulator simulator;
    QuickSimThread(Simulator simulator){
        super("QuickSimThread");

        this.simulator = simulator;
    }

    @Override
    public void run() {
        /*while (simulator.quickSim && simulator.getTime() < simulator.simulationTime){
            simulator.proc();
        }*/
        while(simulator.quickSim){
            simulator.proc();
            if(simulator.environment.getTime() == GeneticAlg.simulationTime) simulator.endSimulation();
        }
    }
}
