import environmentPack.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//(E)nvironment window
public class EWindow extends JFrame implements ActionListener {
    protected OWindow overviewWindow;

    public EWindow(OWindow o){
        super("Edytor środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(660,350);
        setLocation(screenSize.width/32-30,screenSize.height/2-25);
        //setLayout(new GridLayout(2,1));

        overviewWindow = o;
        add(new EButtonPanel(overviewWindow));

        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Edytor środowiska")){
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
        if(overviewWindow.ovrPanel.isInitialized) return overviewWindow.ovrPanel.environment;
        else return null;
    }
}

class EButtonPanel extends JPanel{
    //private OWindow overviewWindow;
    private JButton addFactoryBut;
    private JButton addSpawnerBut;
    private JButton saveFileBut;
    private JButton loadFileBut;
    private JButton seedPlaceBut;
    private JButton initBut;

    protected EButtonPanel(OWindow ovrW){
        //overviewWindow = ovrW;
        addFactoryBut = new JButton("Dodaj fabrykę");
        addSpawnerBut = new JButton("Dodaj spawner");
        saveFileBut = new JButton("Zapisz do pliku");
        loadFileBut = new JButton("Wczytaj z pliku");
        seedPlaceBut = new JButton("Posadź ziarno");
        initBut = new JButton("Zainicjuj");

        addFactoryBut.addActionListener((ActionListener)ovrW.ovrPanel);
        addSpawnerBut.addActionListener((ActionListener)ovrW.ovrPanel);
        saveFileBut.addActionListener(ovrW);
        loadFileBut.addActionListener(ovrW);
        seedPlaceBut.addActionListener((ActionListener)ovrW.ovrPanel);
        initBut.addActionListener((ActionListener)ovrW.ovrPanel);

        setLayout(new GridLayout(3, 3, 20, 20));
        add(addFactoryBut);
        add(addSpawnerBut);
        add(saveFileBut);
        add(loadFileBut);
        add(new SizeSliders(ovrW));
        add(new OtherSliders(ovrW));
        add(new RainSliders(ovrW));
        add(seedPlaceBut);
        add(initBut);
    }
}

class SizeSliders extends JPanel{
    private JSlider widthSlider;
    private JSlider heightSlider;

    protected SizeSliders(OWindow ovrW){
        setLayout(new GridLayout(2,1));

        widthSlider = new JSlider(JSlider.HORIZONTAL,400,1200,600);
        widthSlider.setName("widthSlider");
        widthSlider.setMinorTickSpacing(50);
        widthSlider.setMajorTickSpacing(200);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.addChangeListener(ovrW);

        heightSlider = new JSlider(JSlider.HORIZONTAL,400,800,700);
        heightSlider.setName("heightSlider");
        heightSlider.setMinorTickSpacing(50);
        heightSlider.setMajorTickSpacing(200);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.addChangeListener(ovrW);

        add(widthSlider);
        add(heightSlider);
    }
}

class OtherSliders extends JPanel{
    private JSlider groundSlider;
    private JSlider sunSlider;

    protected OtherSliders(OWindow ovrW){
        groundSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        groundSlider.setName("groundSlider");
        groundSlider.setMinorTickSpacing(10);
        groundSlider.setMajorTickSpacing(50);
        groundSlider.setPaintTicks(true);
        groundSlider.setPaintLabels(true);
        groundSlider.addChangeListener(ovrW.ovrPanel);

        sunSlider = new JSlider(JSlider.HORIZONTAL, 0, 24, 12);
        sunSlider.setName("sunSlider");
        sunSlider.setMinorTickSpacing(2);
        sunSlider.setMajorTickSpacing(6);
        sunSlider.setPaintTicks(true);
        sunSlider.setPaintLabels(true);
        sunSlider.addChangeListener(ovrW.ovrPanel);

        add(groundSlider);
        add(sunSlider);
    }
}

class RainSliders extends JPanel{
    private JSlider rainFrequencySlider;
    private JSlider rainIntensitySlider;

    protected RainSliders(OWindow ovrW){
        rainFrequencySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        rainFrequencySlider.setName("rainFreqSlider");
        rainFrequencySlider.setMinorTickSpacing(5);
        rainFrequencySlider.setMajorTickSpacing(25);
        rainFrequencySlider.setPaintTicks(true);
        rainFrequencySlider.setPaintLabels(true);
        rainFrequencySlider.addChangeListener(ovrW.ovrPanel);

        rainIntensitySlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        rainIntensitySlider.setName("rainIntSlider");
        rainIntensitySlider.setMinorTickSpacing(5);
        rainIntensitySlider.setMajorTickSpacing(25);
        rainIntensitySlider.setPaintTicks(true);
        rainIntensitySlider.setPaintLabels(true);
        rainIntensitySlider.addChangeListener(ovrW.ovrPanel);

        add(rainFrequencySlider);
        add(rainIntensitySlider);
    }
}