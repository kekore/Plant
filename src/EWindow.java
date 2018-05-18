import environmentPack.Environment;

import javax.swing.*;
import javax.swing.event.ChangeListener;
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
        //setLayout(new GridLayout(2,1));

        overviewWindow = o;
        //JPanel bP = new EButtonPanel(overviewWindow);
        add(new EButtonPanel(overviewWindow));
        //add(new Sliders());

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

    protected Environment getEnvironment(){
        return overviewWindow.ovrPanel.environment;
    }
}

class EButtonPanel extends JPanel{
    //private OWindow overviewWindow;
    private JButton addFactoryBut;
    private JButton addSpawnerBut;
    private JButton saveFileBut;
    private JButton loadFileBut;
    private JButton applySizeBut;

    protected EButtonPanel(OWindow ovrW){
        //overviewWindow = ovrW;
        addFactoryBut = new JButton("Dodaj fabrykę");
        addSpawnerBut = new JButton("Dodaj spawner");
        saveFileBut = new JButton("Zapisz do pliku");
        loadFileBut = new JButton("Wczytaj z pliku");
        applySizeBut = new JButton("Zainicjuj");

        addFactoryBut.addActionListener((ActionListener)ovrW.ovrPanel);
        addSpawnerBut.addActionListener((ActionListener)ovrW.ovrPanel);
        saveFileBut.addActionListener((ActionListener)ovrW.ovrPanel);
        loadFileBut.addActionListener((ActionListener)ovrW.ovrPanel);
        applySizeBut.addActionListener((ActionListener)ovrW);

        setLayout(new GridLayout(2, 2, 20, 20));
        add(addFactoryBut);
        add(addSpawnerBut);
        add(saveFileBut);
        add(loadFileBut);
        add(new Sliders(ovrW));
        add(applySizeBut);
    }
}

class Sliders extends JPanel{
    private JSlider widthSlider;
    private JSlider heightSlider;
    Sliders(OWindow ovrW){
        setLayout(new GridLayout(2,1));
        widthSlider = new JSlider(JSlider.HORIZONTAL,400,1200,600);
        widthSlider.setName("widthSlider");
        widthSlider.setMinorTickSpacing(50);
        widthSlider.setMajorTickSpacing(200);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.addChangeListener((ChangeListener) ovrW);
        heightSlider = new JSlider(JSlider.HORIZONTAL,400,800,700);
        heightSlider.setName("heightSlider");
        heightSlider.setMinorTickSpacing(50);
        heightSlider.setMajorTickSpacing(200);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.addChangeListener((ChangeListener)ovrW);
        add(widthSlider);
        add(heightSlider);
    }
}