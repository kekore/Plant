package environmentPack;

import physicsPack.Vector2D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Klasa wiatru - pozwala wprowadzać subtelne zmiany w torach ruchu cząsteczek poprzez generowanie dodatkowych wektorów siły dla środowiska.
 */
public class Wind implements Serializable{
    /**Lista szumów - są to wektory o losowym kierunku i ograniczonej sile, mają na celu zachwianie schematyczności wiatru*/
    private ArrayList<Vector2D> noise;
    /**Lista ze wzorcem sił wiatru - są to wektory, których połowa jest skierowana w pierwszym wybranym kierunku, a druga połowa w drugim*/
    private ArrayList<Vector2D> template;
    /**Liczba generowanych wektorów szumu*/
    protected static final int noiseAmount = 10;
    /**Maksymalna wartość obu współrzędnych wektora szumu*/
    protected static final int noiseStrength = 50;
    /**Liczba generowanych wektorów wiatru o wybranym kierunku.*/
    protected static final int templateAmount = 10;
    /**Spis kierunków.*/
    public enum Direction{
        NORTH,EAST,SOUTH,WEST
    }

    /**
     * Generuje listę szumów o współrzędnych z przedziału <i>-noiseStrength</i> do <i>+noiseStrength</i> oraz generuje listę wektorów wiartu
     * o wybranym kierunku.
     * @param windDir1 Kierunek pierwszy (dotyczy pierwszej połowy wektorów wiatru).
     * @param windDir2 Kierunek drugi (dotyczy drugiej połowy wektorów wiatru).
     */
    protected Wind(Direction windDir1, Direction windDir2){
        Random generator = new Random();
        noise = new ArrayList<Vector2D>();
        for(int i = 0; i < noiseAmount; i++){
            noise.add(new Vector2D(generator.nextFloat()*2*noiseStrength-noiseStrength,generator.nextFloat()*2*noiseStrength-noiseStrength));
        }
        template = new ArrayList<Vector2D>();
        float x;
        float y;
        for(int i = 0; i < templateAmount; i++){
            x=(generator.nextFloat()+1)*50;
            y=(generator.nextFloat()+1)*50;
            if(i<templateAmount/(generator.nextInt(3)+2)){
                switch (windDir1){
                    case EAST:{
                        x=x*(generator.nextInt(4)+1);
                        break;
                    }
                    case WEST:{
                        x=-x*(generator.nextInt(4)+1);
                        break;
                    }
                    case NORTH:{
                        y=-y*(generator.nextInt(4)+1);
                    }
                    case SOUTH:{
                        y=y*(generator.nextInt(4)+1);
                    }
                }
            } else{
                switch (windDir2){
                    case EAST:{
                        x=x*(generator.nextInt(4)+1);
                        break;
                    }
                    case WEST:{
                        x=-x*(generator.nextInt(4)+1);
                        break;
                    }
                    case NORTH:{
                        y=-y*(generator.nextInt(4)+1);
                    }
                    case SOUTH:{
                        y=y*(generator.nextInt(4)+1);
                    }
                }
            }
            template.add(new Vector2D(x,y));
        }
    }

    /**
     *
     * @param time Aktualny czas(moment) w środowisku.
     * @return Zwraca wektor siły wiatru odpowiadający podanemu czasu.
     */
    protected Vector2D getForce(long time){
        Vector2D force = (template.get((int)(Math.floor(time/200)%templateAmount))).addNC(noise.get((int)(Math.floor(time/200)%templateAmount)));
        return force;
    }
}
