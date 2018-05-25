package simulatorPack;

public class QuickSimThread extends Thread {
    Simulator simulator;
    QuickSimThread(Simulator simulator){
        super("QuickSimThread");

        this.simulator = simulator;
    }

    @Override
    public void run() {
        while (simulator.quickSim && simulator.getTime() < simulator.simulationTime){
            simulator.proc();
        }
    }
}
