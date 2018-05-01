package physicsPack;

public class Time {
    private long startMoment;
    private long timePaused;
    private boolean isPaused;
    private long pauseMoment;

    public Time(){
        startMoment = System.currentTimeMillis();
        isPaused = false;
        timePaused = 0;
    }
    public long time(){
        if(!isPaused){
            return System.currentTimeMillis()-timePaused-startMoment;
        }
        else{
            return pauseMoment-timePaused-startMoment;
        }
    }
    public boolean pause(){
        if(isPaused) return true;
        pauseMoment = System.currentTimeMillis();
        isPaused = true;
        return false;
    }
    public boolean unpause(){
        if(!isPaused) return true;
        timePaused += System.currentTimeMillis() - pauseMoment;
        isPaused = false;
        return false;
    }
}
