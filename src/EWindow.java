import environmentPack.Environment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

//(E)nvironment window
public class EWindow extends JFrame implements ActionListener {
    protected OWindow overviewWindow;
    private FirstPage firstPage;
    private SecondPage secondPage;
    private boolean page;

    public EWindow(){
        super("Edytor środowiska");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(660,400);
        setLocation(screenSize.width/32-30,screenSize.height/2-75);


        OvrRunnable ovrRunnable = new OvrRunnable(this);
        overviewWindow = ovrRunnable.getRef();
        //overviewWindow = o;
        firstPage = new FirstPage(this, overviewWindow);
        //secondPage = new SecondPage(this, overviewWindow);
        page = false;
        //setLayout(new GridLayout(2,1));
        //setContentPane(firstPage);
        add(firstPage);

        //add(new EButtonPanel(overviewWindow));

        //setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Edytor środowiska")){
            if(!isVisible()){
                setVisible(true);
                overviewWindow.setVisible(true);
                overviewWindow.ovrPanel.timer.start();
            }
            else{
                setVisible(false);
                overviewWindow.setVisible(false);
                overviewWindow.ovrPanel.timer.stop();
            }
        } else if(((JButton)e.getSource()).getText().equals("Zainicjuj") || ((JButton)e.getSource()).getText().equals("Utwórz inne")){
            alterPage();
        }
    }

    protected Environment getEnvironment(){
        if(overviewWindow.ovrPanel.isInitialized) return overviewWindow.ovrPanel.environment;
        else return null;
    }

    protected void alterPage(){
        if(!page){
            //System.out.println("try");
            //removeAll();
            /*SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    remove(firstPage);
                    add(secondPage);
                }
            });*/
            remove(firstPage);
            //removeAll();
            //System.out.println("removed");
            secondPage = new SecondPage(this, overviewWindow);
            add(secondPage);
            //add(secondPage);
            //System.out.println("added");
            revalidate();
        } else{
            remove(secondPage);
            //removeAll();
            firstPage = new FirstPage(this, overviewWindow);
            add(firstPage);
            revalidate();
        }
        page = !page;
    }
}

class OvrRunnable implements Runnable {
    OvrRunnable(EWindow eWindow){
        e=eWindow;
        o = new OWindow(e);
    }
    @Override
    public void run() {
        //o = new OWindow(e);
    }
    protected OWindow getRef() {
        return o;
    }
    private OWindow o;
    private EWindow e;
}

class EButtonPanel extends JPanel{ //TODO cant edit when working
    //private OWindow overviewWindow; //TODO druga strona
    private JButton seedPlaceBut;

    protected EButtonPanel(OWindow ovrW){

        setLayout(new FlowLayout());


        //overviewWindow = ovrW;
        /*
        seedPlaceBut = new JButton("Posadź ziarno");


        seedPlaceBut.addActionListener((ActionListener)ovrW.ovrPanel);


        add(seedPlaceBut);*/
    }
}

class FirstPage extends JPanel{
    private JButton loadFileBut;
    private JButton initBut;

    protected FirstPage(EWindow parent, OWindow ovrW){
        loadFileBut = new JButton("Wczytaj z pliku");
        initBut = new JButton("Zainicjuj");

        loadFileBut.addActionListener(ovrW);
        loadFileBut.addActionListener(parent);
        initBut.addActionListener((ActionListener)ovrW.ovrPanel);
        initBut.addActionListener(parent);

        //setLayout(new GridLayout(2, 3, 10, 10));
        setLayout(new FlowLayout());
        add(new SizeSliders(ovrW));
        add(new OtherSliders(ovrW));
        add(new RainSliders(ovrW));
        add(new WindSliders(ovrW));
        add(loadFileBut);
        add(initBut);
    }
}

class SecondPage extends JPanel{
    private JButton addFactoryBut;
    private JButton addNucBut;
    private JButton addSpawnerBut;
    private JButton saveFileBut;
    private JButton deInitBut;

    protected SecondPage(EWindow parent, OWindow ovrW){
        addFactoryBut = new JButton("Dodaj fabrykę");
        addNucBut = new JButton("Dodaj elektrownię nuklearną");
        addSpawnerBut = new JButton("Dodaj źródło tlenu");
        saveFileBut = new JButton("Zapisz do pliku");
        deInitBut = new JButton("Utwórz inne");

        addFactoryBut.addActionListener(ovrW.ovrPanel);
        addNucBut.addActionListener(ovrW.ovrPanel);
        addSpawnerBut.addActionListener(ovrW.ovrPanel);
        saveFileBut.addActionListener(ovrW);
        deInitBut.addActionListener(ovrW.ovrPanel);
        deInitBut.addActionListener(ovrW);
        deInitBut.addActionListener(parent);

        add(addFactoryBut);
        add(addNucBut);
        add(addSpawnerBut);
        add(saveFileBut);
        add(deInitBut);
    }
}

class SizeSliders extends JPanel{
    private JTextField widthText;
    private JSlider widthSlider;
    private JTextField heightText;
    private JSlider heightSlider;

    protected SizeSliders(OWindow ovrW){
        setLayout(new GridLayout(4,1));

        widthText = new JTextField("Szerokość obszaru");
        widthText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        widthText.setEditable(false);
        widthText.setBorder(null);
        widthText.setHorizontalAlignment(JTextField.CENTER);

        widthSlider = new JSlider(JSlider.HORIZONTAL,400,1200,600);
        widthSlider.setName("widthSlider");
        widthSlider.setMinorTickSpacing(50);
        widthSlider.setMajorTickSpacing(200);
        widthSlider.setPaintTicks(true);
        widthSlider.setPaintLabels(true);
        widthSlider.addChangeListener(ovrW);

        heightText = new JTextField("Wysokość obszaru");
        heightText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        heightText.setEditable(false);
        heightText.setBorder(null);
        heightText.setHorizontalAlignment(JTextField.CENTER);

        heightSlider = new JSlider(JSlider.HORIZONTAL,400,800,700);
        heightSlider.setName("heightSlider");
        heightSlider.setMinorTickSpacing(50);
        heightSlider.setMajorTickSpacing(200);
        heightSlider.setPaintTicks(true);
        heightSlider.setPaintLabels(true);
        heightSlider.addChangeListener(ovrW);

        add(widthText);
        add(widthSlider);
        add(heightText);
        add(heightSlider);
    }
}

class OtherSliders extends JPanel{
    private JTextField groundText;
    private JSlider groundSlider;
    private JTextField sunText;
    private JSlider sunSlider;

    protected OtherSliders(OWindow ovrW){
        setLayout(new GridLayout(4,1));
        groundText = new JTextField("Wysokość gruntu");
        groundText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        groundText.setEditable(false);
        groundText.setBorder(null);
        groundText.setHorizontalAlignment(JTextField.CENTER);

        groundSlider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        groundSlider.setName("groundSlider");
        groundSlider.setMinorTickSpacing(10);
        groundSlider.setMajorTickSpacing(50);
        groundSlider.setPaintTicks(true);
        groundSlider.setPaintLabels(true);
        groundSlider.addChangeListener(ovrW.ovrPanel);

        sunText = new JTextField("Długość dnia");
        sunText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        sunText.setEditable(false);
        sunText.setBorder(null);
        sunText.setHorizontalAlignment(JTextField.CENTER);

        sunSlider = new JSlider(JSlider.HORIZONTAL, -24, 24, 12);
        sunSlider.setName("sunSlider");
        sunSlider.setMinorTickSpacing(2);
        sunSlider.setMajorTickSpacing(6);
        sunSlider.setPaintTicks(true);
        sunSlider.setPaintLabels(true);
        sunSlider.addChangeListener(ovrW.ovrPanel);

        add(groundText);
        add(groundSlider);
        add(sunText);
        add(sunSlider);
    }
}

class RainSliders extends JPanel{
    private JTextField freqText;
    private JSlider rainFrequencySlider;
    private JTextField intText;
    private JSlider rainIntensitySlider;

    protected RainSliders(OWindow ovrW){
        setLayout(new GridLayout(4,1));
        freqText = new JTextField("Częstotliwość deszczu");
        freqText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        freqText.setEditable(false);
        freqText.setBorder(null);
        freqText.setHorizontalAlignment(JTextField.CENTER);

        rainFrequencySlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        rainFrequencySlider.setName("rainFreqSlider");
        rainFrequencySlider.setMinorTickSpacing(5);
        rainFrequencySlider.setMajorTickSpacing(25);
        rainFrequencySlider.setPaintTicks(true);
        rainFrequencySlider.setPaintLabels(true);
        rainFrequencySlider.addChangeListener(ovrW.ovrPanel);

        intText = new JTextField("Intensywność deszczu");
        intText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        intText.setEditable(false);
        intText.setBorder(null);
        intText.setHorizontalAlignment(JTextField.CENTER);

        rainIntensitySlider = new JSlider(JSlider.HORIZONTAL,0,100,50);
        rainIntensitySlider.setName("rainIntSlider");
        rainIntensitySlider.setMinorTickSpacing(5);
        rainIntensitySlider.setMajorTickSpacing(25);
        rainIntensitySlider.setPaintTicks(true);
        rainIntensitySlider.setPaintLabels(true);
        rainIntensitySlider.addChangeListener(ovrW.ovrPanel);

        add(freqText);
        add(rainFrequencySlider);
        add(intText);
        add(rainIntensitySlider);
    }
}

class WindSliders extends JPanel{
    private JTextField firstText;
    private JSlider firstSlider;
    private JTextField secondText;
    private JSlider secondSlider;

    protected WindSliders(OWindow ovrW){
        setLayout(new GridLayout(4,1));
        firstText = new JTextField("Pierwszy kierunek wiatru");
        firstText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        firstText.setEditable(false);
        firstText.setBorder(null);
        firstText.setHorizontalAlignment(JTextField.CENTER);

        firstSlider = new JSlider(JSlider.HORIZONTAL, 0, 3, 1);
        firstSlider.setName("firstWindSlider");
        firstSlider.setMajorTickSpacing(1);
        firstSlider.setPaintTicks(true);
        Hashtable<Integer,JLabel> labels1 = new Hashtable();
        labels1.put(0, new JLabel("N"));
        labels1.put(1, new JLabel("E"));
        labels1.put(2, new JLabel("S"));
        labels1.put(3, new JLabel("W"));
        firstSlider.setLabelTable(labels1);
        firstSlider.setPaintLabels(true);
        firstSlider.addChangeListener(ovrW.ovrPanel);

        secondText = new JTextField("Drugi kierunek wiatru");
        secondText.setFont(new Font("ComicSansMS", Font.PLAIN, 10));
        secondText.setEditable(false);
        secondText.setBorder(null);
        secondText.setHorizontalAlignment(JTextField.CENTER);

        secondSlider = new JSlider(JSlider.HORIZONTAL,0,3,3);
        secondSlider.setName("secondWindSlider");
        secondSlider.setMajorTickSpacing(1);
        secondSlider.setPaintTicks(true);
        secondSlider.setLabelTable(labels1);
        secondSlider.setPaintLabels(true);
        secondSlider.addChangeListener(ovrW.ovrPanel);

        add(firstText);
        add(firstSlider);
        add(secondText);
        add(secondSlider);
    }
}