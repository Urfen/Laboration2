package se.arvidbodkth.laboration2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Arvid on 2015-12-02.
 *
 */
public class State implements Serializable {

    private NineMenMorrisRules model;


    public State(NineMenMorrisRules model, BoardView board) {
        this.model = model;
    }

    public NineMenMorrisRules getModel() {
        return model;
    }

    public void setModel(NineMenMorrisRules model) {
        this.model = model;
    }

}

