import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
        SimRunnable simR = new SimRunnable(); //TODO ogarnac invokeLater
        EventQueue.invokeLater(simR);
        EnvRunnable envR = new EnvRunnable();
        AlgRunnable algR = new AlgRunnable();
        CatRunnable catR = new CatRunnable(simR.getRef());

        EventQueue.invokeLater(envR);
        EventQueue.invokeLater(algR);
        EventQueue.invokeLater(catR);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MWindow(simR.getRef(),envR.getRef(),algR.getRef(),catR.getRef());
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
    @Override
    public void run() { e = new EWindow(); }
    protected EWindow getRef() { return e; }
    private EWindow e;
}

class AlgRunnable implements Runnable{
    AlgRunnable() { a = new AWindow(); }
    @Override
    public void run() {
        //a = new AWindow();
    }
    protected AWindow getRef() { return a; }
    private AWindow a;
}

class CatRunnable implements Runnable{
    CatRunnable(SWindow sWindow) { c = new CWindow(sWindow); }
    @Override
    public void run() {
        //c = new CWindow;
    }
    protected CWindow getRef() { return c; }
    private CWindow c;
}