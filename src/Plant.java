/*import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;*/
import javax.swing.*;
import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
        SimRunnable simR = new SimRunnable();
        EnvRunnable envR = new EnvRunnable();
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
    @Override
    public void run() { e = new EWindow(); }
    protected EWindow getRef() { return e; }
    private EWindow e;
}