import javax.swing.JFrame;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SWindow extends JFrame implements ActionListener{

    public SWindow(){
        super("Simulation");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,400);
        setLocation(screenSize.width/2+250,screenSize.height/2+100);
        setResizable(false);
        //setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        System.out.println("Scatch");
        setVisible(true);
        /*Object source = e.getSource();
        if(source.toString() == "Symulacja"){
            setVisible(true);
        }*/
    }

    private void doSetVisible(){
        setVisible(true);
    }
}
