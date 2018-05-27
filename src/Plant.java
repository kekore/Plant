import physicsPack.Maths;

import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
        SimRunnable simR = new SimRunnable(); //TODO ogarnac invokeLater
        EventQueue.invokeLater(simR);
        //OvrRunnable ovrR = new OvrRunnable();
        //EventQueue.invokeLater(ovrR);
        //while(!ovrR.isRan());
        //System.out.println(ovrR.getRef());
        //EnvRunnable envR = new EnvRunnable(ovrR.getRef());
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

/*class EnvRunnable implements Runnable{
    EnvRunnable(OWindow oRef){
        o = oRef;
    }
    @Override
    public void run() { e = new EWindow(o); }
    protected EWindow getRef() { return e; }
    private EWindow e;
    private OWindow o;
}*/

class EnvRunnable implements Runnable{
    @Override
    public void run() { e = new EWindow(); }
    protected EWindow getRef() { return e; }
    private EWindow e;
}

/*class OvrRunnable implements Runnable {
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
}*/

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