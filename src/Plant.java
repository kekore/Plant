import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
        SimRunnable simR = new SimRunnable(); //TODO ogarnac invokeLater
        OvrRunnable ovrR = new OvrRunnable();
        EventQueue.invokeLater(ovrR);
        //while(!ovrR.isRan());
        //System.out.println(ovrR.getRef());
        EnvRunnable envR = new EnvRunnable(ovrR.getRef());
        EventQueue.invokeLater(simR);
        EventQueue.invokeLater(envR);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MWindow(simR.getRef(),envR.getRef());
            }
        });
    }
}

class SimRunnable implements Runnable{
    @Override
    public void run() {
        s = new SWindow();
    }
    protected SWindow getRef(){
        return s;
    }
    private SWindow s;
}

class EnvRunnable implements Runnable{
    EnvRunnable(OWindow oRef){
        o = oRef;
    }
    @Override
    public void run() { e = new EWindow(o); }
    protected EWindow getRef() { return e; }
    private EWindow e;
    private OWindow o;
}

class OvrRunnable implements Runnable {
    OvrRunnable(){
        o = new OWindow();
    }
    @Override
    public void run() {
        //o = new OWindow();
    }
    protected OWindow getRef() {
        return o;
    }
    private OWindow o;
}