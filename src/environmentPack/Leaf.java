package environmentPack;

import java.io.Serializable;

public class Leaf implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    private Branch parentBranch;
    private int green;
    private int growCost;
    private int growCounter;

    protected Leaf(int green){
        this.green = green;
    }

    protected void proc(){

    }
}
