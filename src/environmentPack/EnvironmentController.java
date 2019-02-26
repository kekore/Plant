package environmentPack;

import geneticAlgPack.DNA;
import javafx.util.Pair;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa kontrolera obiektu {@link Environment} - udostępnia metody do wykonywania operacji na środowisku i decyduje czy pozwolić wykonać daną operację,
 * udostępnia także metody pozwalające na uzyskanie wartości niektórych zmiennych oraz kształtów obiektów znajdujących się w środowisku.
 */
public class EnvironmentController implements Serializable{
    /**Obiekt środowiska obsługiwany przez kontroler.*/
    Environment environment;
    public EnvironmentController(){}

    /**
     * Pozwala na zainicjowanie środowiska.
     * @param canvasWidth Szerokość obszaru symulacji.
     * @param canvasHeight Wysokość obszaru symulacji.
     * @param groundLevel Poziom gruntu.
     * @param seedPosX Pozycja startu drzewa w osi X.
     * @param sunTime Czas trwania dnia.
     * @param sunSide Strona z której wschodzi słońce - true: prawa strona, flase: lewa strona.
     * @param rainFreq Częstotliwość desczu.
     * @param rainInt Intensywność deszczu.
     * @param windDir1 Pierwszy kierunek wiatru - zobacz {@link Wind#template} oraz {@link Wind#Wind(Wind.Direction, Wind.Direction)}.
     * @param windDir2 Drugi kierunek wiatru - zobacz {@link Wind#template} oraz {@link Wind#Wind(Wind.Direction, Wind.Direction)}.
     * @return Zwraca <i>0</i> jeśli środowisko nie było uprzednio zainicjowane bądź <i>-1</i> jeśli było uprzednio zainijowane.
     */
    public int initEnvironment(int canvasWidth, int canvasHeight, int groundLevel, int seedPosX, int sunTime, boolean sunSide, int rainFreq, int rainInt, Wind.Direction windDir1, Wind.Direction windDir2){
        int ret = 0;
        if(environment != null) ret = 1;
        environment = new Environment(canvasWidth,canvasHeight,groundLevel,seedPosX,sunTime,sunSide,rainFreq,rainInt,windDir1,windDir2);
        return ret;
    }
    /**
     * Powoduje, że kontroler nie obsługuje żadnego środowiska.
     * @return Zwraca <i>-1</i> jeśli środowisko nie było uprzednio zainicjowane bądź <i>0</i> jeśli było uprzednio zainicjowane.
     */
    public int deInitEnvironment(){
        if(environment == null) return -1;
        else environment = null;
        return 0;
    }
    /**
     *
     * @return Zwraca <i>true</i> jeśli środowisko jest zainicjowane bądź <i>false</i> jeśli środowisko nie jest zainicjowane.
     */
    public boolean isInitialized(){return environment != null;}

    /**
     *
     * @return Zwraca wielkość okna, o ile została zapisana lub <i>null</i> jeśli środowisko nie jest zainicjowane bądź wielkość nie została zapisana.
     */
    public Dimension getWindowSize(){
        if(environment == null || environment.windowWidth == 0 && environment.windowHeight == 0) return null;
        return new Dimension(environment.windowWidth,environment.windowHeight);
    }

    /**
     * Pozwala na zapisanie wielkości okna - {@link Environment#saveWindowSize(int, int)}.
     * @param windowWidth Szerokość okna.
     * @param windowHeight Wysokość okna.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli środowisko nie jest zainicjowane.
     */
    public int saveWindowSize(int windowWidth, int windowHeight){
        if(environment == null) return -1;
        environment.saveWindowSize(windowWidth,windowHeight);
        return 0;
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Circle} widocznych elementów środowiska.
     */
    synchronized public ArrayList<Circle> getCircles(){
        ArrayList<Circle> sList = new ArrayList<Circle>();
        for(Particle p : environment.particleList){
            sList.add(p.shape);
        }
        if(environment.tree != null)sList.addAll(environment.tree.getCircles());
        return sList;
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Rect} widocznych elementów środowiska.
     */
    synchronized public ArrayList<Rect> getRects(){
        ArrayList<Rect> rList = new ArrayList<Rect>();
        for(Factory f : environment.factoryList){
            rList.addAll(f.getRects());
        }
        rList.add(environment.ground.rectangle);
        return rList;
    }

    /**
     *
     * @return Zwraca listę obiektów {@link java.awt.geom.Line2D} widocznych elementów środowiska.
     */
    synchronized public ArrayList<Line2D> getLines(){
        ArrayList<Line2D> lList = new ArrayList<Line2D>();
        return lList;
    }

    /**
     *
     * @param tickTime Relatywna długość tiku zegara - ten parametr jest przekazywany do obliczenia linii kolizji - {@link physicsPack.Physics#getColLine(long)}.
     * @return Zwraca listę obiektów {@link java.awt.geom.Line2D} niewidocznych elementów środowiska.
     */
    synchronized public ArrayList<Line2D> getInvisLines(long tickTime){
        ArrayList<Line2D> ilList = new ArrayList<Line2D>();
        for(Particle p : environment.particleList){
            ilList.add(p.physics.getColLine(tickTime));
        }
        ilList.addAll(environment.sun.getLines());
        return ilList;
    }

    /**
     *
     * @return Zwraca listę obiektów {@link Rect} niewidocznych elementów środowiska.
     */
    synchronized public ArrayList<Rect> getInvisRects(){
        ArrayList<Rect> irList = new ArrayList<Rect>();
        irList.add(environment.seedRect);
        for(ParticleSpawner ps : environment.spawnerList){
            irList.add(ps.rectangle);
        }
        irList.addAll(environment.sun.getRects());
        return irList;
    }

    /**
     *
     * @return Zwraca listę par {@link Line2D} i {@link Color} skojarzonych z poszczególnymi gałęziami drzewa znajdującego się aktualnie w środowisku.
     */
    synchronized public ArrayList<Pair<Line2D,Color>> getBranchLines(){
        ArrayList<Pair<Line2D,Color>> array = new ArrayList<Pair<Line2D,Color>>();
        if(environment.tree == null) return array;
        for(Branch b : environment.tree.getBranches()){
            array.add(new Pair<Line2D,Color>(b.line,new Color(0,environment.tree.branchGreen,0)));
        }
        return array;
    }

    /**
     * Dodaje fabrykę, o ile środowisko jest zainicjowane i pozwala na bycie edytowanym.
     * @param f Obiekt fabryki, którą chcemy dodać do środowiska.
     * @return Zwraca <i>-1</i> jeśli środowisko nie jest zainicjowane bądź nie pozwala na edycję.
     */
    public int addFactory(Factory f){
        if(!isInitialized() || !environment.isEditable) return -1;
        environment.factoryList.add(f);
        return 0;
    }

    /**
     * Dodaje źródło cząsteczek, o ile środowisko jest zainicjowane i pozwala na bycie edytowanym.
     * @param ps Obiekt źródła cząsteczek, które chcemy dodać do środowiska.
     * @return Zwraca <i>-1</i> jeśli środowisko nie jest zainicjowane bądź nie pozwala na edycję.
     */
    public int addSpawner(ParticleSpawner ps){
        if(!isInitialized() || !environment.isEditable) return -1;
        environment.spawnerList.add(ps);
        return 0;
    }

    /**
     * Pozwala na wywołanie metody {@link Environment#proc(long)} na obsługiwanym środowisku.
     * @param tickTime Relatywna długość tiku zegara - ten parametr jest przekazywany do wykonania kroku w fizyce cząstek - {@link physicsPack.Physics#proc(long)}.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli środowisko nie jest zainicjowane.
     */
    public int proc(long tickTime){
        if(environment == null) return -1;
        else environment.proc(tickTime);
        return 0;
    }

    /**
     * Pozwala na wywołanie metody {@link Environment#insertTreeToTest(DNA, float)} na obsługiwanym środowisku.
     * @param dna Zadany zestaw cech, wynikający z danych zawartych w tym obiekcie.
     * @param startSatiety Początkowa wartość sytości drzewa, która jest używana do jego wzrostu.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli środowisko nie jest zainicjowane.
     */
    public int insertTreeToTest(DNA dna, float startSatiety){
        if(environment == null) return -1;
        environment.insertTreeToTest(dna,startSatiety);
        return 0;
    }

    /**
     * Pozwala na wywołanie metody {@link Environment#insertTestedTree(Tree)} na obsługiwanym środowisku.
     * @param tree Drzewo, które ma zostać umieszczone w środowisku.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli środowisko nie jest zainicjowane.
     */
    public int insertTestedTree(Tree tree){
        if(environment == null) return -1;
        environment.insertTestedTree(tree);
        return 0;
    }

    /**
     *
     * @return Zwraca odnośnik na drzewo znajdujące się aktualnie w środowisku lub <i>null</i> jeśli środowisko nie jest zainicjowane.
     */
    public Tree getTree(){
        if(environment == null) return null;
        return environment.tree;
    }

    /**
     *
     * @return Zwraca aktualny czas(moment) w obsługiwanym środowisku lub <i>-1</i> jeśli środowisko nie zostało zainicjowane.
     */
    public long getTime(){
        if(environment == null) return -1;
        return environment.time;
    }

    /**
     *
     * @return Zwraca aktualną punktację drzewa znajdującego się w środowisku lub <i>-1</i> jeśli środowisko nie zostało zainicjowane bądź nie ma drzewa w środowisku.
     */
    public float getPoints(){
        if(environment == null || environment.tree == null) return -1;
        else return environment.tree.points;
    }

    /**
     *
     * @return Zwraca aktualną sytość drzewa znajdującego się w środowisku lub <i>-2</i> jeśli środowisko nie zostało zainicjowane bądź nie ma drzewa w środowisku.
     */
    public float getSatiety(){
        if(environment == null) return -2;
        return environment.getSatiety();
    }

    /**
     *
     * @param value Wartość na jaką ma być ustawiona flaga {@link Environment#isEditable}.
     * @return Zwraca <i>0</i> przy sukcesie lub <i>-1</i> jeśli środowisko nie zostało zainicjowane.
     */
    public int setIsEditable(boolean value){
        if(environment == null) return -1;
        environment.isEditable = value;
        return 0;
    }
}
