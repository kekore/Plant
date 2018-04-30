import javax.swing.JFrame;
import javax.tools.Tool;
import java.awt.*;

public class SWindow extends JFrame{

    public SWindow(){
        super("Simulation");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,400);
        setLocation(screenSize.width/2+250,screenSize.height/2+100);
        setResizable(false);
        setVisible(true);
    }
}
