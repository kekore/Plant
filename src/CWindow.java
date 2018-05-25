import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CWindow extends JFrame implements ActionListener {
    public CWindow(){
        super("Katalog");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,6);

        //setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().equals("Katalog")){
            if(isVisible()){
                setVisible(false);
            } else {
                setVisible(true);
            }
        }
    }
}
