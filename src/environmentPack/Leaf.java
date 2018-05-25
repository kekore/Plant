package environmentPack;

import java.io.Serializable;

public class Leaf implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    protected Circle shape;
    protected float relativePos;
    private float xH;
    private float yH;
    protected boolean side;
    private Branch parentBranch;
    private Tree parentTree;
    private int growCost;
    private int growCounter;

    protected Leaf(Tree parentTree, Branch parentBranch, float relPos){
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        //this.green = green;
    }

    protected void proc(){

    }
}
