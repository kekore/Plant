import java.awt.*;

/**
 * Program symulujący wzrost roślin w uproszczonym środowisku z użyciem algorytmu genetycznego do generowania kolejnych osobników.
 *
 * @author Eryk Prokopczuk
 */
public class Plant {
    /**
     * Metoda powołująca do życia okno główne <i>Menu</i>.
     * @param args Unused.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable(){
            @Override
            public void run(){
                new MWindow();
            }
        });
    }
}