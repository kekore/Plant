package environmentPack;

import java.io.Serializable;

public class Leaf implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    private Branch parentBranch;
    private Tree parentTree;
    private int green;
    private int growCost;
    private int growCounter;

    protected Leaf(Tree parentTree, Branch parentBranch, int green){
        this.parentBranch = parentBranch;
        this.parentTree = parentTree;
        this.green = green;
    }

    protected void proc(){

    }
}
