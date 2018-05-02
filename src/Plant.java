/*import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;*/
import javax.swing.*;
import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
        SimRunnable SimR = new SimRunnable();
        EventQueue.invokeLater(SimR);
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MWindow(SimR.getRef());
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