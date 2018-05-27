import geneticAlgPack.GeneticAlg;
import geneticAlgPack.Individual;
import simulatorPack.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class CWindow extends JFrame implements ActionListener{
    private JScrollPane scrollPane;
    private TextListPanel textListPanel;
    private SWindow sWindow;

    public CWindow(SWindow sWindow){
        super("Katalog");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,6);

        setLayout(new GridLayout(1,1));
        setBackground(Color.WHITE);

        //setResizable(false);
        this.sWindow = sWindow;

        textListPanel = new TextListPanel(sWindow);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        //scrollPane.setBorder(null);
        scrollPane.setViewportView(textListPanel);
        add(scrollPane);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().equals("Katalog")){
            if(isVisible()){
                setVisible(false);
            } else if(sWindow.simPanel.simulator.getLastTestedGen() >= 0) {
                setVisible(true);
                textListPanel.update();
            }
        }
    }
}

class TextListPanel extends JPanel implements ActionListener, MouseListener{
    private JButton prevBut;
    private JButton updateBut;
    private JButton nextBut;
    private int page;
    //private JTextField[] textList;
    private JTextArea[] textList;
    SWindow sWindow;
    //Simulator simulator;

    private ArrayList<Individual> currentIndividualsList;

    protected TextListPanel(SWindow sWindow){
        this.sWindow = sWindow;
        //simulator = sWindow.simPanel.simulator;
        page=0;
        //textList = new JTextField[1000];
        setLayout(new GridLayout(1,3));
        prevBut = new JButton("Poprzednia generacja");
        updateBut = new JButton("Zaktualizuj");
        updateBut.addActionListener(this);
        nextBut = new JButton("Następna generacja");
        add(prevBut);
        add(updateBut);
        add(nextBut);
        /*for(int i = 0; i < 1000; i++){
            textList[i] = new JTextField();
            add(textList[i]);
            textList[i].setText("TEXT NUMER: " + i);
            textList[i].setEditable(false);
            textList[i].addMouseListener(this);
        }*/
    }

    protected void update(){
        //if(sWindow.simPanel.simulator.)
        //if(simulator.getLastTestedGen() < 0) return;
        if(textList == null){
            System.out.println(sWindow);
            System.out.println(sWindow.simPanel);
            //textList = new JTextField[sWindow.simPanel.simulator.getPopSize()];
            textList = new JTextArea[sWindow.simPanel.simulator.getPopSize()];
            setLayout(new GridLayout(((sWindow.simPanel.simulator.getPopSize()+3)/3+1),3));
            for(int i = 0; i < sWindow.simPanel.simulator.getPopSize(); i++){
                //textList[i] = new JTextField();
                textList[i] = new JTextArea(4,1);
                add(textList[i]);
                //textList[i].setText();
                textList[i].setEditable(false);
                textList[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                textList[i].setLineWrap(true);
                textList[i].setWrapStyleWord(true);
                //textList[i].setPreferredSize(new Dimension(300,100));
                textList[i].addMouseListener(this);
            }
        }

        currentIndividualsList = sWindow.simPanel.simulator.getSortedList(page);
        for(int i = 0; i < textList.length; i++){
            textList[i].setText(currentIndividualsList.get(i).getDna().getString() + "\nPunktacja: " + currentIndividualsList.get(i).getFitness());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().equals("Zaktualizuj")){
            update();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        //System.out.println((JTextField)(e.getSource()).getText());

        for(int i = 0; i < textList.length; i++){
            if(e.getSource() == textList[i]){
                sWindow.simPanel.simulator.overviewIndividual(currentIndividualsList.get(i));
            }
        }

        /*for(JTextArea t : textList){
            if(e.getSource() == t){
                System.out.println(t.getText());
                break;
            }
        }*/
    }
    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}
}

/*class UpdateThread extends Thread{
    CWindow cWindow;
    protected UpdateThread(CWindow cWindow){
        this.cWindow = cWindow;
    }
    @Override
    public void run() {
        while(cWindow.isVisible()){
            //update
        }
    }
}*/

class CatPanel extends JPanel{
    JTextArea textArea;
    JScrollPane scrollPane;

    protected CatPanel(SWindow sWindow){

        //textArea = new JTextArea(sWindow.simPanel.simulator.getPopSize(), 2);
        //textArea = new JTextArea(1000,2);
        //textArea.setEditable(false);
        scrollPane = new JScrollPane();
        scrollPane.setBorder(null);
        //scrollPane.setPreferredSize(new Dimension(parent.getSize().width, parent.getSize().height));
        add(scrollPane);

        textArea.append("COS");
        textArea.append("JAKIS");
        textArea.append("CO TO JEST KURDE");
    }
}
