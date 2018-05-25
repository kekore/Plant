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

    static public float countA(float xH, float x1, float y1, float x2, float y2, float d, boolean side){
        float a;
        if(side){
            a = xH + (d*d*(y1-y2)*(y1-y2))/(2*(float)Math.sqrt(d*d*(y1-y2)*(y1-y2)*(x1*x1-2*x1*x2+y1*y1-2*y1*y2+x2*x2+y2*y2)));
        } else{
            a = xH - (d*d*(y1-y2)*(y1-y2))/(2*(float)Math.sqrt(d*d*(y1-y2)*(y1-y2)*(x1*x1-2*x1*x2+y1*y1-2*y1*y2+x2*x2+y2*y2)));
        }
        return a;
    }

    static public float countB(float yH, float x1, float y1, float x2, float y2, float d, boolean side){
        //float yHH = -yH;
        //float y11 = -y1;
        //float y22 = -y2;
        float b;
        //System.out.println("yH x1 y1 x2 y2 d " + yH + " "+ x1 + " "+ y1 + " "+ x2 + " "+ y2 + " "+ d);
        //System.out.println("POD PIERWIASTKIEM: "+ d*d*(x1-x2)*(x1-x2)*(x1*x1-2*x1*x2+y1*y1-2*y2*y1+x2*x2-1));
        //double pierwiastek = (double)Math.sqrt(d*d*(x1-x2)*(x1-x2)*(x1*x1-2*x1*x2+y1*y1-2*y2*y1+x2*x2-1));
        //System.out.println("PIERWIASTEK: "+pierwiastek);
        if(side){
            b = yH + (d*d*(x1-x2)*(x1-x2))/(2*(float)Math.sqrt(-d*d*(x1-x2)*(x1-x2)*(x1*x1-2*x1*x2+y1*y1-2*y2*y1+x2*x2-1)));
        } else{
            b = yH - (d*d*(x1-x2)*(x1-x2))/(2*(float)Math.sqrt(-d*d*(x1-x2)*(x1-x2)*(x1*x1-2*x1*x2+y1*y1-2*y2*y1+x2*x2-1)));
        }
        return b;
    }
}
