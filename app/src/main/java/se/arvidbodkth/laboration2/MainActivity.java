package se.arvidbodkth.laboration2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private NineMenMorrisRules model;
    private BoardView view;
    private int from;

    /**
     * Executes when the app starts from being destroy. Init's the model
     * and view.
     * @param savedInstanceState reference to a Bundle object.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init the model and view.
        model = new NineMenMorrisRules();
        view = new BoardView(this, this);

        //Set the content to the custom view.
        setContentView(view);

        from = 0;
    }

    /**
     * Executes when the app starts from being stopped. Loads the
     * last save file in to the view.
     */
    @Override
    protected void onStart() {
        super.onStart();
        try {
            //Load the file and save it to a new model
            State state = (State) model.readFile(this.getApplicationContext());
            model = state.getModel();

            //Update the model and view
            initGameFromFile(model.getBoard());
            view.invalidate();

        } catch (IOException e) {
            showToast("ERROR FAILED TO READ FROM FILE!");
        } catch (ClassNotFoundException c) {
            showToast("FILE NOT FOUND!");
        }
    }

    /**
     * Executes when the app is not in focus any more. Saves the current
     * model to file.
     */
    @Override
    protected void onStop() {
        super.onStop();
        State state = new State(model);
        try {
            model.writeFile(this.getApplicationContext(), state);
        } catch (IOException e) {
            e.printStackTrace();
            showToast("FAILED TO WRITE TO FILE!");
        }
    }

    /**
     * Executes when the app is destroyed. aves the current
     * model to file.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        State state = new State(model);
        try {
            model.writeFile(this.getApplicationContext(), state);
        } catch (IOException e) {
            e.printStackTrace();
            showToast("FAILED TO WRITE TO FILE!");
        }
    }

    /**
     * Opens the toolbar when hit.
     * @param menu the meny style
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Executes when an item in the toolbar is hit.
     * @param item the list of items.
     * @return true
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Get the item id's
        int id = item.getItemId();

        //If the item that was hit is the restart item.
        if (id == R.id.action_restart) {
            //Reset the game buy creating new clean
            //models and views.
            model = new NineMenMorrisRules();
            view = new BoardView(this, this);

            //Set the content to the new clean view.
            setContentView(view);

            //Redraw.
            view.invalidate();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Sets the color of the gamePieces when a file is loaded.
     * @param gameState the loaded int array with the color
     *                  information.
     */
    private void initGameFromFile(int[] gameState) {
        for (int i = 1; i < view.getGamePieces().size() - 1; i++) {
            //Takes the color value from gameState from the loaded
            //file and give the right piece that color.
            view.getGamePieces().get(i).setColor(gameState[i + 1]);
        }
    }

    /**
     * Get the int value for the color of a given position.
     * @param pos the position to get the color of.
     * @return int color of that pos
     */
    public int getColorOfPos(int pos) {
        return model.getColorOfPos(pos);
    }

    /**
     * get the int value for the vurrent turn.
     * @return int of the current turn.
     */
    public int getTurn() {
        return model.getTurn();
    }


    /**
     * Shows a toast on with the message given in msg.
     * @param msg the string to be shown.
     */
    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    /**
     * Make board piece actions based on the circumstances, whether it's the starting phase, mill phase
     * or move piece position phase.
     * @param pos coordinates of the piece position.
     */
    public void placePiece(int pos) {

        //checks the players phase
        placeMarkerAndRemove(pos);

        if (model.win(model.getMarkerColor(model.getLastTurn()))) {
            model.nextTurn();
            String msg = model.turnToString() + " player Wins!!!!!";
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            toast.show();
        }
        System.out.println(model.toString());
    }

    /**
     *Check whether the player has gotten a mill. Based on the position the player has chosen, checks
     * whether the position belongs is correct (not part of a mill, not players own color or a blank space)
     * a feedback is given
     * @param pos position of the game piece
     * @return whether the player succesfully removed a game piece.
     */
    public boolean checkIfRemovable(int pos) {

        //Check whether the player is allowed to remove
        if (model.getCanRemove()) {
            //check if place is not part of mill/blank space/players own color
            if (!model.clearSpace(pos, view.getColorOfPos(pos))) {
                view.setCurrentTurn(model.getTurn());
                showToast("Wrong pick!");
                return true;
            }
            System.out.println();
            view.paintEmptyPiece(pos, 0);
            model.nextTurn();
            view.setCurrentTurn(model.getTurn());
            from = 0;
            return true;
        }
        return false;
    }

    /**
     * Game piece interaction based on the current status of game pieces.
     *
     * @param pos position of the game piece
     */
    public void placeMarker(int pos) {
        //Checks whether the player has pieces left to place freely on the game board
        // and the position is free.
        if (model.markersLeft(model.getTurn()) && view.getColorOfPos(pos) == 0) {
            from = 0;
            //Check whether the move is legal
            if (model.legalMove(pos, 0, model.getTurn())) {
                //Check whether the placed game piece constructs a mill in the starting phase of
                //the game where the players place the 9 first pieces.
                if (model.remove(pos)) {
                    model.setCanRemove(true);
                    view.paintEmptyPiece(pos, model.getTurn());
                    return;
                }
                view.paintEmptyPiece(pos, model.getTurn());
                System.out.println("Placed marker on: " + pos);
                if (!model.getCanRemove()) {
                    model.nextTurn();
                }
                if (model.getNoOfMarkers() > 0)
                    showToast(model.turnToString() + " has " + model.getNoOfMarkers() + " markers left to place");
                view.setCurrentTurn(model.getTurn());
            }
        } else {
            //Checks whether the player has a piece selected and wants to move it.
            if (from != 0 && view.getColorOfPos(pos) == 0) {
                //Check whether the move is a legal one.
                if (model.legalMove(pos, from, model.getTurn())) {

                    view.movePiece(pos, from, model.getTurn());
                    System.out.println("Moved a marker from: " + from + " to: " + pos);
                    //If the move becomes a part of a mill, flag that the player can remove a game piece
                    //from the opponent.
                    if (model.remove(pos)) {
                        model.setCanRemove(true);
                    }
                    //If the piece is not part of a mill, flag to change the turn
                    if (!model.getCanRemove()) {
                        model.nextTurn();
                    }
                    view.setCurrentTurn(model.getTurn());
                    from = 0;
                } else {
                    System.out.println("Failed to move marker from: " + from + " to: " + pos);
                    from = 0;
                }
                //Checks whether the player selects a piece to move.
            } else if (from == 0 && view.getColorOfPos(pos) == model.getTurn()) {
                from = pos;
                System.out.println("Saved: " + pos);
            } else {
                if (!model.getCanRemove()) {
                    showToast("Wrong imput, try again.");
                    from = 0;
                }

            }
        }
    }

    /**
     *Check whether the player has to remove a game piece or place a marker
     * @param pos position of the game piece
     */
    public void placeMarkerAndRemove(int pos) {
        if (checkIfRemovable(pos)) return;
        placeMarker(pos);
    }
}
