/*import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;*/
import javax.swing.*;
import java.awt.EventQueue;

public class Plant {
    public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				JFrame frame = new DrawFrame();
//				frame.setTitle("Plant");
//				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//				frame.setVisible(true);
//			}
//		});

        /*JFrame frame = new DrawFrame();
        frame.setTitle("Plant");
        frame.setSize(800, 600);
        frame.setVisible(true);*/
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
        /*while(true){
            s.update(System.currentTimeMillis()%100);
        }*/
    }
    protected SWindow getRef(){
        return s;
    }
    private SWindow s;
}

/* class DrawFrame extends JFrame
{
    private static final long serialVersionUID = 1L;

    public DrawFrame()
    {
        add(new DrawComponent());
        pack();
    }
}

class DrawComponent extends JComponent
{
    //private static final int DEFAULT_WIDTH = 800;
    //private static final int DEFAULT_HEIGHT = 600;
    private static final long serialVersionUID = 1L;

    public void paintComponent(Graphics g)
    {
        Graphics2D g2 = (Graphics2D) g;

        Rectangle2D rect = new Rectangle2D.Double(10, 10, 40, 80);
        g2.draw(rect);
    }
}*/