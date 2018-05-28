import geneticAlgPack.Individual;

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
        setSize(600,300);
        setLocation(screenSize.width/2,6);

        setLayout(new GridLayout(1,1));
        setBackground(Color.WHITE);

        this.sWindow = sWindow;

        textListPanel = new TextListPanel(this, sWindow);
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
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
    private JTextArea[] textList;
    SWindow sWindow;
    CWindow cWindow;

    private ArrayList<Individual> currentIndividualsList;

    protected TextListPanel(CWindow parent, SWindow sWindow){
        this.sWindow = sWindow;
        this.cWindow = parent;
        page=0;
        setLayout(new GridLayout(1,3));
        prevBut = new JButton("Poprzednia generacja");
        prevBut.addActionListener(this);
        updateBut = new JButton("Zaktualizuj");
        updateBut.addActionListener(this);
        nextBut = new JButton("Następna generacja");
        nextBut.addActionListener(this);
        add(prevBut);
        add(updateBut);
        add(nextBut);
    }

    protected void update(){
        if(textList == null){
            System.out.println(sWindow);
            System.out.println(sWindow.simPanel);
            textList = new JTextArea[sWindow.simPanel.simulator.getPopSize()];
            setLayout(new GridLayout(((sWindow.simPanel.simulator.getPopSize()+3)/3+1),3));
            for(int i = 0; i < sWindow.simPanel.simulator.getPopSize(); i++){
                textList[i] = new JTextArea(4,1);
                add(textList[i]);
                textList[i].setEditable(false);
                textList[i].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                textList[i].setLineWrap(true);
                textList[i].setWrapStyleWord(true);
                textList[i].addMouseListener(this);
            }
        }

        System.out.println("AKTUALIZACJA");
        currentIndividualsList = sWindow.simPanel.simulator.getSortedList(page);
        for(int i = 0; i < textList.length; i++){
            textList[i].setText(currentIndividualsList.get(i).getDna().getString() + "\nPunktacja: " + currentIndividualsList.get(i).getFitness());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(((JButton)e.getSource()).getText().equals("Zaktualizuj")){
            update();
        } else if(((JButton)e.getSource()).getText().equals("Poprzednia generacja")){
            if(page == 0) return;
            page--;
            update();
            cWindow.setTitle("Katalog - pokolenie " + page);
        } else if(((JButton)e.getSource()).getText().equals("Następna generacja")){
            if(page == sWindow.simPanel.simulator.getLastTestedGen()) return;
            System.out.println("NASTEPNA");
            page++;
            update();
            cWindow.setTitle("Katalog - pokolenie " + page);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e){
        for(int i = 0; i < textList.length; i++){
            if(e.getSource() == textList[i]){
                sWindow.simPanel.simulator.overviewIndividual(currentIndividualsList.get(i));
            }
        }
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