package physicsPack;

import java.io.Serializable;

public class Vector2D implements Serializable, Cloneable{
    private float x,y;

    public Vector2D(){
        x = 0;
        y = 0;
    }
    public Vector2D(float x, float y){
        this.x = x;
        this.y = y;
    }
    public Vector2D(Vector2D v){
        this.x = v.getX();
        this.y = v.getY();
    }
    public double length(){
        return Math.sqrt(x*x+y*y);
    }
    public Vector2D add(Vector2D v)
    {
        x += v.x;
        y += v.y;
        return this;
    }
    public Vector2D addNC(Vector2D v){
        Vector2D temp = new Vector2D(x,y);
        return temp.add(v);
    }
    public Vector2D set(Vector2D v)
    {
        x = v.x;
        y = v.y;
        return this;
    }
    public Vector2D scale(float a)
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

    @Override
    public Vector2D clone(){
        Vector2D ret;
        try{
            ret = (Vector2D) super.clone();
        } catch (CloneNotSupportedException e){
            System.out.println("Particle cloning exception!");
            throw new RuntimeException();
        }
        return ret;
        //return (Vector2D) super.clone();
    }
}
