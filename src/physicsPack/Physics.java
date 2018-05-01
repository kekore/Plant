package physicsPack;

public class Physics {
    private Vector2D pos;
    private Vector2D vel;
    private Vector2D acc;
    private Vector2D force;
    private float mass;
    private long lastUpdate;

    public Physics(Vector2D p, Vector2D v, float m, long when){
        pos = p;
        vel = v;
        acc = new Vector2D();
        force = new Vector2D();
        mass = m;
        lastUpdate = when;
    }

    public void proc(long when, long tickTime)
    {
        long currentTime = when;
        //System.out.println((float)(currentTime-lastUpdate)/tickTime);
        acc.set(force.scaleNC(1/mass));
        vel.add(acc.scaleNC((float)(currentTime-lastUpdate)/tickTime));
        pos.add(vel.scaleNC((float)(currentTime-lastUpdate)/tickTime));
        lastUpdate = when;
    }

    public Vector2D getPos() {
        return pos;
    }
    public void setPos(Vector2D pos) {
        this.pos = pos;
    }
    public Vector2D getVel() {
        return vel;
    }
    public void setVel(Vector2D vel) {
        this.vel = vel;
    }
    public Vector2D getAcc() {
        return acc;
    }
    public void setAcc(Vector2D acc) {
        this.acc = acc;
    }
    public Vector2D getForce() {
        return force;
    }
    public void setForce(Vector2D force) {
        this.force = force;
    }
    public float getMass() {
        return mass;
    }
    public void setMass(float mass) {
        this.mass = mass;
    }
}
