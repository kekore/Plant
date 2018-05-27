import geneticAlgPack.GeneticAlg;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CWindow extends JFrame implements ActionListener, MouseListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JTextField text1;
    JTextField text2;
    TextList textList;
    public CWindow(SWindow sWindow){
        super("Katalog");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(600,700);
        setLocation(screenSize.width/2,6);

        setLayout(new GridLayout(1,1));
        //add(new CatPanel(sWindow));
        setBackground(Color.WHITE);

        //setResizable(false);


        //textArea = new JTextArea(sWindow.simPanel.simulator.getPopSize(), 2);
        text1 = new JTextField("COSTOJEST");
        text1.setBounds(2,2,100,30);
        text1.addMouseListener(this);
        text2 = new JTextField("JESTTOCOS");
        text2.addMouseListener(this);
        textArea = new JTextArea(100,2);
        //textArea.setEditable(false);
        //scrollPane = new JScrollPane(textArea);
        textList = new TextList(this);
        scrollPane = new JScrollPane();
        //scrollPane.setBorder(null);
        scrollPane.setViewportView(textList);
        //scrollPane.add(text1);
        //scrollPane.add(text2);
        //scrollPane.setPreferredSize(new Dimension(parent.getSize().width, parent.getSize().height));
        add(scrollPane);

        /*textArea.addMouseListener(this);

        textArea.append("COS");
        textArea.append("JAKIS");
        textArea.append("CO TO JEST KURDE");*/
    }

    @Override
    public void mouseReleased(MouseEvent e){
        System.out.println(e.getComponent());
        //System.out.println(e.);
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

    @Override
    public void mousePressed(MouseEvent e){}
    @Override
    public void mouseEntered(MouseEvent e){}
    @Override
    public void mouseExited(MouseEvent e){}
    @Override
    public void mouseClicked(MouseEvent e){}
}

class TextList extends JPanel{
    private JTextField[] textList;

    protected TextList(CWindow parent){
        textList = new JTextField[1000];
        setLayout(new GridLayout(334,3));
        for(int i = 0; i < 1000; i++){
            textList[i] = new JTextField();
            add(textList[i]);
            textList[i].setText("TEXT NUMER: " + i);
            textList[i].setEditable(false);
            textList[i].addMouseListener(parent);
        }
    }
}

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
