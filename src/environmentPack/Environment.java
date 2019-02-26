package environmentPack;

import geneticAlgPack.DNA;
import physicsPack.Vector2D;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Klasa środowiska - jest główną klasą paczki <i>environmentPack</i> - zawiera wszystkie elementy środowiska i ich aktualny stan.
 */
public class Environment implements Serializable {
    /**Szerokość okna symulacji - używana podczas wczytywania z pliku w celu ustawienia odpowiedniej szerokości okna.*/
    protected int windowWidth;
    /**Wysokość obszaru symulacji - używana podczas wczytywania z pliku w celu ustawienia odpowiedniej wysokości okna.*/
    protected int windowHeight;
    /**Szerokość obszaru symulacji.*/
    private final int width;
    /**Wysokość obszaru symulacji.*/
    private final int height;
    /**Lista wszystkich cząsteczek jakie są aktualnie w środowisku - są one poddawane działaniu fizyki oraz sprawdza się ich kolizje z drzewem.*/
    protected ArrayList<Particle> particleList;
    /**Lista wszystkich fabryk znajdujących się w środowisku - w każdym kroku są pytane o listę cząsteczek które wygenerowały.*/
    protected ArrayList<Factory> factoryList;
    /**Lista wszystkich źródeł cząsteczek znajdujących się w środowisku - w każdym kroku są pytane o listę cząsteczek które wygenerowały.*/
    protected ArrayList<ParticleSpawner> spawnerList;
    /**Obiekt opisujący wysokość gruntu oraz jego wygląd.*/
    protected final Ground ground;
    /**Wektor wskazujący położenie ziarenka drzewa, czyli jego punktu startowego - są tam przyczepione początkowe gałęzie drzewa.*/
    private final Vector2D seedPlace;
    /**Prostokąt wizualizujący położenie ziarenka/*/
    protected final Rect seedRect;
    /**Aktualne drzewo znajdujące się w środowisku.*/
    protected Tree tree;
    /**Obiekt słońca generujący cząsteczki światła do środowiska.*/
    protected Sun sun;
    /**Obiekt deszczu generujący cząsteczki deszczu do środowiska.*/
    private Rain rain;
    /**Obiekt wiatru nakładający dodatkowe siły na cząsteczki.*/
    private Wind wind;
    /**Aktualny czas(moment) w środowisku.*/
    protected long time;
    /**Flaga pozwalająca na dodawanie źródeł i fabryk lub blokująca - metody {@link EnvironmentController#addFactory(Factory)} i {@link EnvironmentController#addSpawner(ParticleSpawner)}*/
    protected boolean isEditable;
    /**Czas trwania dnia - używa się do resetowania środowiska - {@link #reset()}*/
    private int sunTime;
    /**Strona z której wschodzi słońce - true: prawa strona, flase: lewa strona - używa się do resetowania środowiska - {@link #reset()}*/
    private boolean sunSide;

    /**
     * Konstruktor inicjuje zmienne, listy i obiekty klasy <i>Environment</i>.
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
     */
    protected Environment(int canvasWidth, int canvasHeight, int groundLevel, int seedPosX, int sunTime, boolean sunSide, int rainFreq, int rainInt, Wind.Direction windDir1, Wind.Direction windDir2){
        width = canvasWidth;
        height = canvasHeight;
        particleList = new ArrayList<Particle>();
        factoryList = new ArrayList<Factory>();
        spawnerList = new ArrayList<ParticleSpawner>();

        ground = new Ground(width,height,groundLevel);
        seedPlace = new Vector2D(seedPosX,height-groundLevel);
        seedRect = new Rect(new Vector2D(seedPosX,height-groundLevel),6,6,Color.BLUE,true);

        this.sunTime = sunTime;
        this.sunSide = sunSide;
        if(sunTime != 0) sun = new Sun(sunTime,sunSide,width,height-groundLevel);

        if(rainFreq != 0 && rainInt != 0) rain = new Rain(rainFreq,rainInt,width);
        wind = new Wind(windDir1,windDir2);

        time = 1;
        isEditable = true;
    }
    /**
     * Zapisuje wielkość okna - pozwala skojarzyć środowisko z wielkością okna np. przy zapisie do pliku.
     * @param windowWidth Szerokość okna.
     * @param windowHeight Wysokość okna.
     */
    protected void saveWindowSize(int windowWidth, int windowHeight){
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
    }

    /**
     * Główna metoda środowiska - pobiera nowe cząsteczki z generatorów, powoduje krok w fizyce cząstek, sprawdza kolizje, usuwa cząsteczki poza obszarem symulacji.
     * @param tickTime Relatywna długość tiku zegara - ten parametr jest przekazywany do wykonania kroku w fizyce cząstek - {@link physicsPack.Physics#proc(long)}.
     */
    protected void proc(long tickTime){
        //rain:
        if(rain != null && rain.proc(time))particleList.addAll(rain.getParticles(time,width));
        //sun:
        if(sun != null) particleList.addAll(sun.proc(time));
        //count forces:
        for(Particle p : particleList){
            p.setForce(new Vector2D(0,300));
            p.physics.getForce().add(wind.getForce(time));
        }
        //proceed particles' physics and fotons' age:
        for(Particle p : particleList){
            p.proc(tickTime);
            p.age++;
        }
        //proceed factories:
        for(Factory f : factoryList){
            Particle factoryRet = f.proc(time);
            if(factoryRet != null) particleList.add(factoryRet);
        }
        //proceed spawners:
        for(ParticleSpawner ps : spawnerList){
            Particle spawnerRet = ps.proc(time,tickTime);
            if(spawnerRet != null) particleList.add(spawnerRet);
        }
        //Check particles out of canvas or old and erase them:
        ArrayList<Particle> toErase = new ArrayList<Particle>();
        for(Particle p : particleList){
            if(p.type == Particle.Type.FOTON && p.age > 3000){ toErase.add(p); }
            else if((p.physics.getPos().getX()<0 || p.physics.getPos().getX() > width) && p.type != Particle.Type.FOTON){
                toErase.add(p);
            } else if ((p.physics.getPos().getY()<0 && p.type != Particle.Type.FOTON) || p.physics.getPos().getY() > height-ground.groundLevel){
                toErase.add(p);
            }
        }
        for(Particle te : toErase){
            particleList.remove(te);
        }
        //proceed tree:
        tree.proc(time);
        //Check collisions:
        toErase.clear();
        ArrayList<Leaf> lList = tree.getLeaves();
        ArrayList<Branch> bList = tree.getBranches();
        for(Particle p : particleList){
            for(Leaf l : lList){
                if(ColChecker.doCollide(p,l)){
                    l.gotParticle(p);
                    toErase.add(p);
                    break;
                }
            }
            if(toErase.contains(p)) continue;
            for(Branch b : bList){
                if(ColChecker.doCollide(p,b)){
                    b.gotParticle(p);
                    toErase.add(p);
                    break;
                }
            }
        }
        for(Particle te : toErase){
            particleList.remove(te);
        }
        //increment time:
        time++;
    }
    /**
     * Powoduje utworzenie obiektu {@link Tree} o zadanych parametrach i zresetowanie środowiska w celu przygotowania do przesymulowania wzrostu osobnika - {@link #reset()}.
     * @param dna Zadany zestaw cech, wynikający z danych zawartych w tym obiekcie.
     * @param startSatiety Początkowa wartość sytości drzewa, która jest używana do jego wzrostu.
     */
    protected void insertTreeToTest(DNA dna, float startSatiety){
        System.out.println("MAKING NEW TREE");
        this.tree = new Tree(dna,startSatiety,seedPlace.getX(),seedPlace.getY());
        reset();
    }
    /**
     * Powoduje umieszczenie obiektu drzewa w środowisku i zresetowanie środowiska - {@link #reset()}.
     * @param tree Drzewo, które ma zostać umieszczone w środowisku.
     */
    protected void insertTestedTree(Tree tree){
        this.tree = tree;
        reset();
    }
    /**
     * Resetuje środowisko - usuwa wszystkie cząsteczki z {@link #particleList} i reinicjuje obiekt {@link Sun} oraz {@link Rain}.
     */
    private void reset(){
        time = 1;
        particleList.clear();
        sun = new Sun(sunTime,sunSide,width,height-ground.groundLevel);
        rain.reset();
     }
    /**
     * Sumuje sytość we wszystkich gałęziach i zwraca obliczoną wartość.
     * @return Zwraca sytość aktualnie umieszczonego drzewa {@link #tree} w środowisku lub <i>-1</i> gdy żadne drzewo nie jest umieszczone.
     */
    protected float getSatiety(){
        if(tree==null) return -1;
        ArrayList<Branch> bList = tree.getBranches();
        float satiety=0;
        for(Branch b : bList){
            satiety = satiety + b.satiety;
        }
        return satiety;
    }
}
