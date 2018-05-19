package environmentPack;

import java.io.Serializable;
import java.util.ArrayList;

public class Rain implements Serializable{
    private int frequency;
    private int intensity;

    protected Rain(int frequency, int intensity){
        this.frequency = frequency;
        this.intensity = intensity;
    }
}
