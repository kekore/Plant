package physicsPack;

public class Maths {
    static public float sigmoid(int x){
        float y = -1;

        if( x < -6 )
            y = 0;
        else if( x > 6 )
            y = 1;
        else{
            switch (x){
                case -6:{
                    y = 0.0024725233F;
                    break;
                }
                case -5:{
                    y = 0.006692851F;
                    break;
                }
                case -4:{
                    y = 0.01798621F;
                    break;
                }
                case -3:{
                    y = 0.047425874F;
                    break;
                }
                case -2:{
                    y = 0.11920292F;
                    break;
                }
                case -1:{
                    y = 0.26894143F;
                    break;
                }
                case 0:{
                    y = 0.5F;
                    break;
                }
                case 1:{
                    y = 0.7310586F;
                    break;
                }
                case 2:{
                    y = 0.8807971F;
                    break;
                }
                case 3:{
                    y = 0.95257413F;
                    break;
                }
                case 4:{
                    y = 0.98201376F;
                    break;
                }
                case 5:{
                    y = 0.9933072F;
                    break;
                }
                case 6:{
                    y = 0.99752736F;
                    break;
                }
            }
        }
        return y;
    }

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
