package se.arvidbodkth.laboration2;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author Jonas Wåhslén, jwi@kth.se.
 *         Revised by Anders Lindström, anderslm@kth.se
 */

/*
 * The game board positions
    01			02			03
        04		05		06
            07	08	09
    10	11	12		13	14	15
            16	17	18
        19		20		21
    22			23			24
 *
 */

public class NineMenMorrisRules implements Serializable {
    private int[] gameplan;
    private int bluemarker, redmarker;
    private int turn; // player in turn

    public static final int BLUE_MOVES = 1;
    public static final int RED_MOVES = 2;

    public static final int EMPTY_SPACE = 0;
    public static final int BLUE_MARKER = 4;
    public static final int RED_MARKER = 5;

    private boolean canRemove;

    public NineMenMorrisRules() {
        gameplan = new int[25]; // zeroes
        bluemarker = 5;
        redmarker = 5;
        turn = RED_MOVES;
        canRemove = false;
    }

    /**
     * Returns true if a move is successful
     */
    public boolean legalMove(int To, int From, int color) {

        System.out.println("To: " + To + " From: " + From);
        if (color == turn) {
            if (turn == RED_MOVES) {
                if (redmarker > 0) {
                    if (gameplan[To] == EMPTY_SPACE) {
                        gameplan[To] = RED_MARKER;
                        redmarker--;
                        return true;
                    }
                }
                /*else*/
                if (gameplan[To] == EMPTY_SPACE) {
                    if (isValidMove(To, From)) {
                        gameplan[To] = RED_MARKER;
                        clearSpace(From, RED_MARKER);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                if (bluemarker > 0) {
                    if (gameplan[To] == EMPTY_SPACE) {
                        gameplan[To] = BLUE_MARKER;
                        bluemarker--;
                        return true;
                    }
                }
                if (gameplan[To] == EMPTY_SPACE) {
                    if (isValidMove(To, From)) {
                        gameplan[To] = BLUE_MARKER;
                        clearSpace(From, BLUE_MARKER);
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }


    /**
     * Returns true if position "to" is part of three in a row.
     */
    public boolean remove(int to) {
        boolean remove = false;

        if ((to == 7 || to == 8 || to == 9) && gameplan[7] == gameplan[8]
                && gameplan[8] == gameplan[9]) {
            remove = true;
        } else if ((to == 4 || to == 5 || to == 6)
                && gameplan[4] == gameplan[5] && gameplan[5] == gameplan[6]) {
            remove = true;
        } else if ((to == 1 || to == 2 || to == 3)
                && gameplan[1] == gameplan[2] && gameplan[2] == gameplan[3]) {
            remove = true;
        } else if ((to == 9 || to == 13 || to == 18)
                && gameplan[9] == gameplan[13] && gameplan[13] == gameplan[18]) {
            remove = true;
        } else if ((to == 6 || to == 14 || to == 21)
                && gameplan[6] == gameplan[14] && gameplan[14] == gameplan[21]) {
            remove = true;
        } else if ((to == 3 || to == 15 || to == 24)
                && gameplan[3] == gameplan[15] && gameplan[15] == gameplan[24]) {
            remove = true;
        } else if ((to == 16 || to == 17 || to == 18)
                && gameplan[16] == gameplan[17] && gameplan[17] == gameplan[18]) {
            remove = true;
        } else if ((to == 19 || to == 20 || to == 21)
                && gameplan[19] == gameplan[20] && gameplan[20] == gameplan[21]) {
            remove = true;
        } else if ((to == 22 || to == 23 || to == 24)
                && gameplan[22] == gameplan[23] && gameplan[23] == gameplan[24]) {
            remove = true;
        } else if ((to == 7 || to == 12 || to == 16)
                && gameplan[7] == gameplan[12] && gameplan[12] == gameplan[16]) {
            remove = true;
        } else if ((to == 4 || to == 11 || to == 19)
                && gameplan[4] == gameplan[11] && gameplan[11] == gameplan[19]) {
            remove = true;
        } else if ((to == 1 || to == 10 || to == 22)
                && gameplan[1] == gameplan[10] && gameplan[10] == gameplan[22]) {
            remove = true;
        } else if ((to == 12 || to == 11 || to == 10)
                && gameplan[12] == gameplan[11] && gameplan[11] == gameplan[10]) {
            remove = true;
        } else if ((to == 2 || to == 5 || to == 8)
                && gameplan[2] == gameplan[5] && gameplan[5] == gameplan[8]) {
            remove = true;
        } else if ((to == 13 || to == 14 || to == 15)
                && gameplan[13] == gameplan[14] && gameplan[14] == gameplan[15]) {
            remove = true;
        } else if ((to == 17 || to == 20 || to == 23)
                && gameplan[17] == gameplan[20] && gameplan[20] == gameplan[23]) {
            remove = true;
        }
        return remove;
    }

    /**
     * Request to remove a marker for the selected player.
     * Returns true if the marker where successfully removed
     */
    public boolean clearSpace(int From, int color) {
        if (canRemove == true) {
            if (color != turn && color != EMPTY_SPACE && !remove(From)) {
                gameplan[From] = EMPTY_SPACE;
                canRemove = false;
                return true;
            } else {
                return false;
            }
        }

        if (gameplan[From] == color) {
            gameplan[From] = EMPTY_SPACE;
            return true;
        } else {
            return false;
        }
    }

    /**
     * Returns true if the selected player have less than three markers left.
     */
    public boolean win(int color) {
        int countMarker = 0;
        int count = 0;
        while (count < 25) {
            if (gameplan[count] != EMPTY_SPACE && gameplan[count] != color)
                countMarker++;
            count++;
        }
        if (bluemarker <= 0 && redmarker <= 0 && countMarker < 3)
            return true;
        else
            return false;
    }

    public int[] getBoard(){
        return gameplan;
    }
    /**
     * Returns EMPTY_SPACE = 0 BLUE_MARKER = 4 READ_MARKER = 5
     */
    public int board(int From) {
        return gameplan[From];
    }

    public int getNumberOfMarkersLeft() {
        if (turn == BLUE_MOVES) return redmarker;
        else return redmarker;
    }

    public int getColorOfPos(int pos){
        return gameplan[pos-1];
    }


    public int getMarkerColor(int turn) {
        if (turn == BLUE_MOVES) return BLUE_MARKER;
        if (turn == RED_MOVES) return RED_MARKER;
        else return 0;
    }

    public int getTurn() {
        return turn;
    }

    public void nextTurn() {
        if (turn == BLUE_MOVES) turn = RED_MOVES;
        else turn = BLUE_MOVES;
    }

    public int getLastTurn() {
        if (turn == BLUE_MOVES) return RED_MOVES;
        else return BLUE_MOVES;
    }

    public boolean markersLeft(int color) {
        if (color == BLUE_MOVES && bluemarker > 0) return true;
        if (color == RED_MOVES && redmarker > 0) return true;
        return false;
    }

    /**
     * Check whether this is a legal move.
     */
    private boolean isValidMove(int to, int from) {

        if (this.gameplan[to] != EMPTY_SPACE) return false;

        switch (to) {
            case 1:
                return (from == 2 || from == 10);
            case 2:
                return (from == 5 || from == 3 || from == 1);
            case 3:
                return (from == 15 || from == 2);
            case 4:
                return (from == 11 || from == 5);
            case 5:
                return (from == 2 || from == 4 || from == 6 || from == 8);
            case 6:
                return (from == 5 || from == 14);
            case 7:
                return (from == 8 || from == 12);
            case 8:
                return (from == 5 || from == 7 || from == 9);
            case 9:
                return (from == 13 || from == 8);
            case 10:
                return (from == 1 || from == 22 || from == 11);
            case 11:
                return (from == 4 || from == 12 || from == 19 || from == 10);
            case 12:
                return (from == 7 || from == 16 || from == 11);
            case 13:
                return (from == 9 || from == 18 || from == 14);
            case 14:
                return (from == 21 || from == 6 || from == 15 || from == 13);
            case 15:
                return (from == 14 || from == 3 || from == 24);
            case 16:
                return (from == 12 || from == 17);
            case 17:
                return (from == 16 || from == 20 || from == 18);
            case 18:
                return (from == 17 || from == 13);
            case 19:
                return (from == 11 || from == 20);
            case 20:
                return (from == 19 || from == 23 || from == 17 || from == 21);
            case 21:
                return (from == 14 || from == 20);
            case 22:
                return (from == 10 || from == 23);
            case 23:
                return (from == 22 || from == 20 || from == 24);
            case 24:
                return (from == 15 || from == 23);
        }
        return false;
    }

    public boolean getCanRemove() {
        return canRemove;
    }

    public void setCanRemove(boolean canRemove) {
        this.canRemove = canRemove;
    }

    public int getNoOfMarkers() {
        if (turn == RED_MOVES) return redmarker;
        else return bluemarker;
    }


    public String toString() {
        String gameplanString = "";
        for (int i = 0; i < gameplan.length; i++) {
            gameplanString += gameplan[i];
            if (i < 24) gameplanString += ", ";
        }
        return gameplanString;
    }

    public String turnToString() {
        if (turn == RED_MOVES) return "Red";
        else return "Blue";
    }


    //Attempts to read the saved file
    public Object readFile(Context context) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = context.openFileInput("state");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Object state = in.readObject();
        return state;
    }

    //Attempts to write a file
    public void writeFile(Context context, State state) throws IOException {

        FileOutputStream fileOut = context.openFileOutput("state", Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(state);
        out.close();
        System.out.println(state.getModel().getBoard().toString());
        fileOut.close();
    }


}