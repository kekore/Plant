import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;

public class MWindow extends JFrame{

    public MWindow(SWindow s){
        super("Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //do zmiany
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(500,500);
        setLocation(screenSize.width/4-200,screenSize.height/4-150);
        setLayout(new GridLayout(2, 1, 20, 20));
        //setLayout(new FlowLayout(FlowLayout.CENTER));

        /*JButton s = new JButton("Symulacja");
        JButton w = new JButton("Wyjście");
        s.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));
        w.setFont(new Font("Comic Sans MS", Font.PLAIN, 30));

        add(s);
        add(w);*/

        simWindow = s;
        JPanel bP = new ButtonPanel(simWindow);
        add(bP);
        JPanel c = new Foo();
        add(c);
        JPanel d = new D();
        add(d);
        //c.setSize(10,10);
        //c.setLocation(0,0);

        setResizable(false);
        setVisible(true);
        //pack();
    }

    private SWindow simWindow;

}

class ButtonPanel extends JPanel implements ActionListener{
    private JButton s;
    private JButton w;

    public ButtonPanel(SWindow simW){
        Dimension d = new Dimension(100,100);
        setPreferredSize(d);
        s = new JButton("Symulacja");
        w = new JButton("Dodaj");

        s.addActionListener(this);
        s.addActionListener((ActionListener)simW);
        w.addActionListener(this);
        w.addActionListener((ActionListener)simW.getPanel());

        setLayout(new GridLayout(1, 2, 20, 20));
        add(s);
        add(w);
        simV = false;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        if(source == s && !simV){
            /*EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new SWindow();
                }
            });*/
            simV = true;
        }
    }
    private boolean simV;
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

class D extends JPanel implements MouseMotionListener{
    D(){
        x = 20;
        y = 30;
        a = true;
        setPreferredSize(new Dimension(100,100));
        addMouseMotionListener(this);
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g2d = (Graphics2D)g;
        if(a) g2d.setColor(Color.GREEN);
        else g2d.setColor(Color.BLUE);
        rect = new Rectangle2D.Float(x,y,100,10);
        //g2d.draw(rect);
        g2d.fill(rect);
    }
    @Override
    public void mouseMoved(MouseEvent e){
        //rect = new Rectangle2D.Float(e.getX(), e.getY(), 100, 10);
        a = true;
        x = e.getX();
        y = e.getY();
        repaint();
    }
    @Override
    public void mouseDragged(MouseEvent e){
        a = false;
        x = e.getX();
        y = e.getY();
        repaint();
    }
    private int x,y;
    private Graphics2D g2d;
    private Rectangle2D rect;
    private boolean a;
}