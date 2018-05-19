package environmentPack;

import java.io.Serializable;
import java.util.ArrayList;

public class Branch implements Serializable{ //TODO HAS TO HAVE RECTANGLE HITBOXES (?)
    private ArrayList<Branch> branches;
    private ArrayList<Leaf> leaves;
    private int level;
    private int green;

    protected Branch(){
        branches = new ArrayList<Branch>();
        leaves = new ArrayList<Leaf>();
        //level =
        //green =
    }
}
