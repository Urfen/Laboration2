package se.arvidbodkth.laboration2;

import java.io.Serializable;

/**
 * Created by Arvid Bodin och Mattias Grehnic on 2015-11-30.
 *
 * This clas gets a copy of the current model.
 */
public class State implements Serializable {

    private NineMenMorrisRules model;

    /**
     * Constructor for the state.
     * @param model the model to copy
     */
    public State(NineMenMorrisRules model) {
        this.model = model;
    }

    /**
     * Get the model.
     * @return model.
     */
    public NineMenMorrisRules getModel() {
        return model;
    }
}

