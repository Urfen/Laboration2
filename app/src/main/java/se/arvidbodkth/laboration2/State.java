package se.arvidbodkth.laboration2;

import java.io.Serializable;

/**
 * Created by Arvid on 2015-12-02.
 *
 */
public class State implements Serializable {

    private NineMenMorrisRules model;


    public State(NineMenMorrisRules model) {
        this.model = model;
    }

    public NineMenMorrisRules getModel() {
        return model;
    }

}

