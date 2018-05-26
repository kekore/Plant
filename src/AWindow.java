import geneticAlgPack.GeneticAlg;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AWindow extends JFrame implements ActionListener{
    private APanel aPanel;

    public AWindow() {
        super("Edytor algorytmu");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(660, 350);
        setLocation(screenSize.width / 32 - 30, screenSize.height / 2 - 25);

        aPanel = new APanel();
        add(aPanel);

        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(((JButton)e.getSource()).getText().equals("Edytor algorytmu")){
            if(isVisible()){
                setVisible(false);
            } else {
                setVisible(true);
            }
        }
    }

    protected GeneticAlg getAlgorithm(){
        if(aPanel.isInitialized) return aPanel.geneticAlg;
        else return null;
    }
}


class APanel extends JPanel implements ChangeListener, ActionListener{
    protected GeneticAlg geneticAlg;
    private PopPanel popPanel;
    private FitPanel fitPanel;
    private ProbPanel probPanel;
    private MutPanel mutPanel;

    private int popSize;
    private int fittestAmount;
    private int mutProb;
    private int maxMutGen;
    private JButton initBut;
    protected boolean isInitialized;

    protected APanel(){
        popPanel = new PopPanel(this);
        fitPanel = new FitPanel(this);
        probPanel = new ProbPanel(this);
        mutPanel = new MutPanel(this);

        initBut = new JButton("Zastosuj");
        initBut.addActionListener(this);

        setLayout(new GridLayout(3, 2, 20, 20));
        add(popPanel);
        add(fitPanel);
        add(probPanel);
        add(mutPanel);
        add(initBut);

        isInitialized=false;

        popSize=50; //TODO !!!!
        fittestAmount=20;//TODO !!!!
        mutProb=1;
        maxMutGen=3;
    }

    private void initAlg(){
        geneticAlg = new GeneticAlg(popSize,fittestAmount,mutProb,maxMutGen);
        isInitialized = true;
    }

    private void noInit(){
        isInitialized = false;
        geneticAlg = null;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == initBut){
            initAlg();
        }
    }

    @Override
    public void stateChanged (ChangeEvent e){ //it can jump better
        if(e.getSource() == popPanel.popSizeSlider){
            noInit();
            popPanel.popSizeSlider.setValue(((int)(popPanel.popSizeSlider.getValue()/50))*50);
            popSize = ((JSlider) e.getSource()).getValue();
            if(popSize < fittestAmount){
                fitPanel.fittestAmountSlider.setValue(popSize);
                fittestAmount = popSize;
            }
            //System.out.println(popSize);
        } else if(e.getSource() == fitPanel.fittestAmountSlider){
            noInit();
            fitPanel.fittestAmountSlider.setValue(((int)(fitPanel.fittestAmountSlider.getValue()/25))*25);
            fittestAmount = ((JSlider) e.getSource()).getValue();
            if(fittestAmount > popSize){
                popPanel.popSizeSlider.setValue(fittestAmount);
                popSize = fittestAmount;
            }
        } else if(e.getSource() == probPanel.mutProbSlider){
            noInit();
            mutProb = ((JSlider) e.getSource()).getValue();
        } else if (e.getSource() == mutPanel.maxMutGenSlider){
            noInit();
            maxMutGen = ((JSlider) e.getSource()).getValue();
        }
    }
}

class PopPanel extends JPanel{
    private JTextField popSizeName;
    protected JSlider popSizeSlider;

    protected PopPanel(APanel a){
        popSizeName = new JTextField("Wielkość populacji");
        popSizeName.setEditable(false);
        popSizeName.setBorder(null);
        popSizeName.setHorizontalAlignment(JTextField.CENTER);

        popSizeSlider = new JSlider(JSlider.HORIZONTAL,200,2000,200);
        popSizeSlider.setMinorTickSpacing(50);
        popSizeSlider.setMajorTickSpacing(200);
        popSizeSlider.setPaintTicks(true);
        popSizeSlider.setPaintLabels(true);
        popSizeSlider.addChangeListener(a);

        setLayout(new GridLayout(2,1));
        add(popSizeName);
        add(popSizeSlider);
    }
}

class FitPanel extends JPanel{
    private JTextField fittesName;
    protected JSlider fittestAmountSlider;

    protected FitPanel(APanel a){
        fittesName = new JTextField("Ilość najlepszych osobników");
        fittesName.setEditable(false);
        fittesName.setBorder(null);
        fittesName.setHorizontalAlignment(JTextField.CENTER);

        fittestAmountSlider = new JSlider(JSlider.HORIZONTAL,50,500,50);
        fittestAmountSlider.setMinorTickSpacing(10);
        fittestAmountSlider.setMajorTickSpacing(50);
        fittestAmountSlider.setPaintTicks(true);
        fittestAmountSlider.setPaintLabels(true);
        fittestAmountSlider.addChangeListener(a);

        setLayout(new GridLayout(2,1));
        add(fittesName);
        add(fittestAmountSlider);
    }
}

class ProbPanel extends JPanel{
    private JTextField probName;
    protected JSlider mutProbSlider;

    protected ProbPanel(APanel a){
        probName = new JTextField("Prawdopodobieństwo zajścia mutacji w procentach");
        probName.setEditable(false);
        probName.setBorder(null);
        probName.setHorizontalAlignment(JTextField.CENTER);

        mutProbSlider = new JSlider(JSlider.HORIZONTAL,0,10,1);
        //mutProbSlider.setMinorTickSpacing(2);
        mutProbSlider.setMajorTickSpacing(1);
        mutProbSlider.setPaintTicks(true);
        mutProbSlider.setPaintLabels(true);
        mutProbSlider.addChangeListener(a);

        setLayout(new GridLayout(2,1));
        add(probName);
        add(mutProbSlider);
    }
}

class MutPanel extends JPanel{
    private JTextField mutName;
    protected JSlider maxMutGenSlider;

    protected MutPanel(APanel a){
        mutName = new JTextField("Maksymalna ilość zmutowanych genów w DNA");
        mutName.setEditable(false);
        mutName.setBorder(null);
        mutName.setHorizontalAlignment(JTextField.CENTER);

        maxMutGenSlider = new JSlider(JSlider.HORIZONTAL,1,5,3);
        maxMutGenSlider.setMajorTickSpacing(1);
        maxMutGenSlider.setPaintTicks(true);
        maxMutGenSlider.setPaintLabels(true);
        maxMutGenSlider.addChangeListener(a);

        setLayout(new GridLayout(2,1));
        add(mutName);
        add(maxMutGenSlider);
    }
}