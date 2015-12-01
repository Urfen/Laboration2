package se.arvidbodkth.laboration2;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NineMenMorrisRules model;
    private BoardView view;
    private TextView textView;

    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new NineMenMorrisRules();
        view = new BoardView(this, this);
        textView = (TextView) findViewById(R.id.textView);
        setContentView(view);
        from = 0;
    }

    public void placePiece(int pos) {

        placeMarkerAndRemove(pos);

        if(model.win(model.getMarkerColor())){
            String msg = model.turnToString() + " player Wins!!!!!";
            Toast toast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
            toast.show();
        }
        System.out.println(model.toString());
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    public boolean checkIfRemovable(int pos){
        if (model.getCanRemove()) {
            if (!model.clearSpace(pos, view.getColorOfPos(pos))) {
                view.setCurrentTurn(model.getTurn());
                showToast("Pick your opponents board piece!");
                return false;
            }
            view.paintEmptyPiece(pos, 0);
            model.nextTurn();
            view.setCurrentTurn(model.getTurn());
            from = 0;
            return true;
        }
        return false;
    }

    public void placeMarker(int pos){
        if (model.markersLeft(model.getTurn()) && view.getColorOfPos(pos) == 0) {
            from = 0;
            if (model.legalMove(pos, 0, model.getTurn())) {
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
            if (from != 0 && view.getColorOfPos(pos) == 0) {
                if (model.legalMove(pos, from, model.getTurn())) {

                    view.movePiece(pos, from, model.getTurn());
                    System.out.println("Moved a marker from: " + from + " to: " + pos);
                    if (model.remove(pos)) {
                        model.setCanRemove(true);
                    }
                    if (!model.getCanRemove()) {
                        model.nextTurn();
                    }
                    view.setCurrentTurn(model.getTurn());
                    from = 0;
                } else {
                    System.out.println("Failed to move marker from: " + from + " to: " + pos);
                    from = 0;
                }
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

    public void placeMarkerAndRemove(int pos){
        if(checkIfRemovable(pos)) return;
        placeMarker(pos);
    }
}
