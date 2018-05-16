import environmentPack.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

// (O)verview window
public class OWindow extends JFrame implements ActionListener, MouseListener {
    private Environment e;
    private enum Choice{
        NULL, FACTORY
    }
    private Choice choice;
    private int x1;
    private int y1;
    private int x2;
    private int y2;

    public OWindow(){
        super("Podgląd środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //?
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,screenSize.height/2-350);

        choice = Choice.NULL;

        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText() == "Dodaj fabrykę"){
            choice = Choice.FACTORY;
        }
    }

    @Override
    public void mousePressed(MouseEvent e){

    }

    @Override
    public void mouseReleased(MouseEvent e){

    }
}
