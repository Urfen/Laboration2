package se.arvidbodkth.laboration2;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Arvid on 2015-12-02.
 */
public class State implements Serializable{

    private NineMenMorrisRules model;
    private BoardView board;

    public State(NineMenMorrisRules model, BoardView board){
        this.model = model;
        this.board = board;
    }

    public NineMenMorrisRules getModel() {
        return model;
    }

    public void setModel(NineMenMorrisRules model) {
        this.model = model;
    }

    public BoardView getBoard() {
        return board;
    }

    public void setBoard(BoardView board) {
        this.board = board;
    }
}
