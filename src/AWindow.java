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
    private JSlider popSizeSlider;
    private int popSize;
    private JSlider fittestAmountSlider;
    private int fittestAmount;
    private JSlider mutProbSlider;
    private int mutProb;
    private JSlider maxMutGenSlider;
    private int maxMutGen;
    private JButton initBut;
    protected boolean isInitialized;

    protected APanel(){
        popSizeSlider = new JSlider(JSlider.HORIZONTAL,200,2000,200);
        //widthSlider.setName("widthSlider");
        popSizeSlider.setMinorTickSpacing(50);
        popSizeSlider.setMajorTickSpacing(200);
        popSizeSlider.setPaintTicks(true);
        popSizeSlider.setPaintLabels(true);
        popSizeSlider.addChangeListener(this);

        fittestAmountSlider = new JSlider(JSlider.HORIZONTAL,50,500,50);
        //widthSlider.setName("widthSlider");
        fittestAmountSlider.setMinorTickSpacing(10);
        fittestAmountSlider.setMajorTickSpacing(50);
        fittestAmountSlider.setPaintTicks(true);
        fittestAmountSlider.setPaintLabels(true);
        fittestAmountSlider.addChangeListener(this);

        mutProbSlider = new JSlider(JSlider.HORIZONTAL,0,100,1);
        //widthSlider.setName("widthSlider");
        mutProbSlider.setMinorTickSpacing(2);
        mutProbSlider.setMajorTickSpacing(10);
        mutProbSlider.setPaintTicks(true);
        mutProbSlider.setPaintLabels(true);
        mutProbSlider.addChangeListener(this);

        maxMutGenSlider = new JSlider(JSlider.HORIZONTAL,1,5,3);
        //widthSlider.setName("widthSlider");
        //maxGenMutSlider.setMinorTickSpacing(1);
        maxMutGenSlider.setMajorTickSpacing(1);
        maxMutGenSlider.setPaintTicks(true);
        maxMutGenSlider.setPaintLabels(true);
        maxMutGenSlider.addChangeListener(this);

        initBut = new JButton("Zainicjuj");
        initBut.addActionListener(this);

        setLayout(new GridLayout(3, 2, 20, 20));
        add(popSizeSlider);
        add(fittestAmountSlider);
        add(mutProbSlider);
        add(maxMutGenSlider);
        add(initBut);
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
    public void stateChanged (ChangeEvent e){
        if(e.getSource() == popSizeSlider){
            noInit();
            popSize = ((JSlider) e.getSource()).getValue();
            if(popSize < fittestAmount){
                fittestAmountSlider.setValue(popSize);
                fittestAmount = popSize;
            }
            System.out.println(popSize);
        } else if(e.getSource() == fittestAmountSlider){
            noInit();
            fittestAmount = ((JSlider) e.getSource()).getValue();
            if(fittestAmount > popSize){
                popSizeSlider.setValue(fittestAmount);
                popSize = fittestAmount;
            }
        } else if(e.getSource() == mutProbSlider){
            noInit();
            mutProb = ((JSlider) e.getSource()).getValue();
        } else if (e.getSource() == maxMutGenSlider){
            noInit();
            maxMutGen = ((JSlider) e.getSource()).getValue();
        }
    }
}