import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.TextEvent;

public class MWindow extends JFrame{

    public MWindow(JFrame s){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,160);
        setLocation(screenSize.width/2-250,screenSize.height/2-200);
        setLayout(new GridLayout(1, 2, 20, 20));

        /*JButton s = new JButton("Symulacja");
        JButton w = new JButton("Wyjście");
        s.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        w.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));

        add(s);
        add(w);*/

        sim = s;
        JPanel bP = new ButtonPanel((ActionListener) sim);
        add(bP);
        JPanel c = new Foo();
        add(c);
        //c.setSize(10,10);
        //c.setLocation(0,0);

        setResizable(false);
        setVisible(true);
    }

    private JFrame sim;

}

class ButtonPanel extends JPanel implements ActionListener{
    private JButton s;
    private JButton w;

    public ButtonPanel(ActionListener sim){
        s = new JButton("Symulacja");
        w = new JButton("Wyjście");

        s.addActionListener(this);
        s.addActionListener(sim);
        w.addActionListener(this);

        setLayout(new GridLayout(1, 2, 20, 20));
        add(s);
        add(w);
        simW = false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == s && !simW){
            /*EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new SWindow();
                }
            });*/
            simW = true;
        }
    }
    private boolean simW;
}

class Foo extends JPanel{
    public Foo(){
        setBackground(Color.cyan);
        setLayout(new FlowLayout());
        TextField tf1 = new TextField();
        TextField tf2 = new TextField();
        TextField tf3 = new TextField();
        Dimension d = new Dimension(200,20);
        tf1.setPreferredSize(d);
        tf2.setPreferredSize(d);
        tf3.setPreferredSize(d);
        add(tf1);
        add(tf2);
        add(tf3);
    }
}