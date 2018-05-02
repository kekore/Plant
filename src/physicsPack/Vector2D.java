package physicsPack;

public class Vector2D {
    private float x,y;

    public Vector2D(){
        x = 0;
        y = 0;
    }
    public Vector2D(float a, float b){
        x = a;
        y = b;
    }
    public double length(){
        return Math.sqrt(x*x+y*y);
    }
    protected Vector2D add(Vector2D v)
    {
        x += v.x;
        y += v.y;
        return this;
    }
    protected Vector2D addNC(Vector2D v){
        Vector2D temp = new Vector2D(x,y);
        return temp.add(v);
    }
    protected Vector2D set(Vector2D v)
    {
        x = v.x;
        y = v.y;
        return this;
    }
    protected Vector2D scale(float a)
    {
        x *= a;
        y *= a;
        return this;
    }

    public Vector2D scaleNC(float a)
    {
        Vector2D temp = new Vector2D(x,y);
        return temp.scale(a);
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
}
