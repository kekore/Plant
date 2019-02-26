import geneticAlgPack.DNA;
import geneticAlgPack.GeneticAlgController;

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
        setSize(660, 480);
        setLocation(screenSize.width / 32 - 30, screenSize.height / 2 - 155);

        aPanel = new APanel();
        add(aPanel);
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

    protected GeneticAlgController getAlgorithmController(){
        if(aPanel.geneticAlgController.isInitialized()) return aPanel.geneticAlgController;
        else return null;
    }
}


class APanel extends JPanel implements ChangeListener, ActionListener{
    protected GeneticAlgController geneticAlgController;
    private PopPanel popPanel;
    private FitPanel fitPanel;
    private ProbPanel probPanel;
    private MutPanel mutPanel;
    private SeedPanel seedPanel;
    private LastPanel lastPanel;

    private int popSize;
    private int fittestAmount;
    private int mutProb;
    private int maxMutGen;
    private String seed;
    private boolean[] crossMap;

    protected APanel(){
        geneticAlgController = new GeneticAlgController();

        popPanel = new PopPanel(this);
        fitPanel = new FitPanel(this);
        probPanel = new ProbPanel(this);
        mutPanel = new MutPanel(this);
        seedPanel = new SeedPanel(this);
        lastPanel = new LastPanel(this);

        setLayout(new GridLayout(3, 2, 20, 20));
        add(popPanel);
        add(fitPanel);
        add(probPanel);
        add(mutPanel);
        add(seedPanel);
        add(lastPanel);

        popSize=10; //TODO !!!!
        fittestAmount=7;//TODO !!!!
        mutProb=1;
        maxMutGen=3;
        seed = null;
        crossMap = new boolean[DNA.genesAmount+1];
        for(int i = 0; i < DNA.genesAmount; i++){
            crossMap[i] = false;
        }
        crossMap[DNA.genesAmount] = true;
    }

    private int initAlg(){
        return geneticAlgController.initGeneticAlg(popSize,fittestAmount,crossMap,mutProb,maxMutGen,seed);
    }

    private int noInit(){
        return geneticAlgController.deInitGeneticAlg();
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == lastPanel.initBut){
            initAlg();
        } else if (e.getSource() == seedPanel.seedField){
            seed = seedPanel.seedField.getText();
            if(seed.equals("")){
                seedPanel.currentSeedText.setText("<Bez ziarna>");
                seed = null;
            }
            else seedPanel.currentSeedText.setText(seed);
        } else {
            for(int i = 0; i < DNA.genesAmount+1; i++){
                if(e.getSource() == lastPanel.checkPanel.checkList[i]){
                    crossMap[i]=lastPanel.checkPanel.checkList[i].isSelected();
                    return;
                }
            }
        }
    }

    @Override
    public void stateChanged (ChangeEvent e){
        if(e.getSource() == popPanel.popSizeSlider){
            noInit();
            popPanel.popSizeSlider.setValue(((int)(popPanel.popSizeSlider.getValue()/50))*50);
            popSize = ((JSlider) e.getSource()).getValue();
            if(popSize < fittestAmount){
                fitPanel.fittestAmountSlider.setValue(popSize);
                fittestAmount = popSize;
            }
        } else if(e.getSource() == fitPanel.fittestAmountSlider){
            noInit();
            fitPanel.fittestAmountSlider.setValue(((fitPanel.fittestAmountSlider.getValue()/25))*25);
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
        popSizeName = new JTextField("Liczność populacji");
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
    private JTextField crossText;

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

        crossText = new JTextField("\\/  Wzorzec krzyżowania genów  \\/");
        crossText.setEditable(false);
        crossText.setBorder(null);
        crossText.setHorizontalAlignment(JTextField.CENTER);

        setLayout(new GridLayout(3,1));
        add(mutName);
        add(maxMutGenSlider);
        add(crossText);
    }
}

class SeedPanel extends JPanel{
    private JTextField seedText;
    protected JTextField seedField;
    private JTextField seedText2;
    protected JTextField currentSeedText;

    protected SeedPanel(APanel a){
        seedText = new JTextField("Ziarno pierwszej generacji (zatwierdź enterem)");
        seedText.setEditable(false);
        seedText.setBorder(null);
        seedText.setHorizontalAlignment(JTextField.CENTER);

        seedField = new JTextField();
        seedField.setEditable(true);
        seedField.setHorizontalAlignment(JTextField.CENTER);
        seedField.addActionListener(a);

        seedText2 = new JTextField("Aktualne ziarno");
        seedText2.setEditable(false);
        seedText2.setBorder(null);
        seedText2.setHorizontalAlignment(JTextField.CENTER);

        currentSeedText = new JTextField();
        currentSeedText.setEditable(false);
        currentSeedText.setText("<Bez ziarna>");

        setLayout(new GridLayout(4,1));
        add(seedText);
        add(seedField);
        add(seedText2);
        add(currentSeedText);
    }
}

class LastPanel extends JPanel{
    protected CheckPanel checkPanel;
    protected JButton initBut;

    protected LastPanel(APanel a){
        checkPanel = new CheckPanel(a);
        initBut = new JButton("Zastosuj");
        initBut.addActionListener(a);
        setLayout(new GridLayout(2,1));
        add(checkPanel);
        add(initBut);
    }
}

class CheckPanel extends JPanel{
    protected JCheckBox[] checkList;

    protected CheckPanel(APanel a){
        setLayout(new FlowLayout());
        checkList = new JCheckBox[DNA.genesAmount+1];
        for(int i = 0; i < DNA.genesAmount; i++){
            checkList[i] = new JCheckBox(new Integer(i).toString());
            add(checkList[i]);
            checkList[i].addActionListener(a);
        }
        checkList[DNA.genesAmount] = new JCheckBox("Los");
        add(checkList[DNA.genesAmount]);
        checkList[DNA.genesAmount].doClick();
        checkList[DNA.genesAmount].addActionListener(a);
    }
}