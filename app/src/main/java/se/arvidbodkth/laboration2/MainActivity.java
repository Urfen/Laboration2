package se.arvidbodkth.laboration2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private NineMenMorrisRules model;
    private BoardView view;
    private TextView textView;

    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        model = new NineMenMorrisRules();
        view = new BoardView(this,this);
        textView = (TextView) findViewById(R.id.textView);
        setContentView(view);
        from = 0;
    }

    public void placePiece(int pos) {
        if (model.markersLeft(model.getTurn()) && view.getColorOfPos(pos) == 0) {
            if (model.legalMove(pos, 0, model.getTurn())) {

                view.paintEmptyPice(pos, model.getTurn());
                System.out.println("Placed marker on: " + pos);
                from = 0;}
        }
        else {
            if (from != 0 && view.getColorOfPos(pos) == 0) {
                if (model.legalMove(pos, from, model.getTurn())) {

                    view.movePiece(pos, from, model.getTurn());
                    System.out.println("Moved a marker from: " + from + " to: " + pos);
                    from = 0;
                } else{
                    System.out.println("Failed to move marker from: " + from + " to: " + pos);
                    from = 0;
                }
            }
            else if(from == 0 && view.getColorOfPos(pos) == model.getTurn()){
                from = pos;
                System.out.println("Saved: " + pos);
            }else {
                showToast("Wrong imput, try again.");
                from = 0;
            }
        }
        System.out.println(model.toString());
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

   /* public void setTextView(String text){
        textView.setText(text);
    }*/
}
