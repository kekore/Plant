import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//(E)nvironment window
public class EWindow extends JFrame implements ActionListener {
    private OWindow overviewWindow;

    public EWindow(OWindow o){
        super("Edytor środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,300);
        setLocation(screenSize.width/32,screenSize.height/2);

        overviewWindow = o;
        JPanel bP = new EButtonPanel(overviewWindow);
        add(bP);

        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText() == "Edytor środowiska"){
            if(!isVisible()){
                setVisible(true);
                overviewWindow.setVisible(true);
            }
            else{
                setVisible(false);
                overviewWindow.setVisible(false);
            }
        }
    }
}

class EButtonPanel extends JPanel{
    private JButton addFactoryBut;
    private JButton saveFileBut;
    private JButton loadFileBut;

    protected EButtonPanel(OWindow ovrW){
        addFactoryBut = new JButton("Dodaj fabrykę");
        saveFileBut = new JButton("Zapisz do pliku");
        loadFileBut = new JButton("Wczytaj z pliku");

        addFactoryBut.addActionListener((ActionListener)ovrW.getPanel());
        saveFileBut.addActionListener((ActionListener)ovrW.getPanel());
        loadFileBut.addActionListener((ActionListener)ovrW.getPanel());

        setLayout(new GridLayout(2, 2, 20, 20));
        add(addFactoryBut);
        add(saveFileBut);
        add(loadFileBut);
    }
}