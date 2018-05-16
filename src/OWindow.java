import environmentPack.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// (O)verview window
public class OWindow extends JFrame implements ActionListener {
    private Environment e;

    public OWindow(){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);



        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){

    }
}
