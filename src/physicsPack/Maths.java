package physicsPack;

public class Maths {

    static public float sig(int x){
        float y;
        if( x < -10 )
            y = 0;
        else if( x > 10 )
            y = 1;
        else
            y = (float)(1 / (1 + Math.exp(-x)));
        return y;
    }
}
