package se.arvidbodkth.laboration2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Arvid och Mattias on 2015-11-30.
 *
 */
public class BoardView extends View {

    private Drawable gameboard;
    private ArrayList<BoardPieceView> gamePieces;
    private MainActivity controller;


    private boolean inited = false;


    public BoardView(Context context, MainActivity controller) {
        super(context);
        this.controller =  controller;
        // Get a representation of the image
        //Test
        Resources resources = context.getResources();
        gameboard = (Drawable) resources.getDrawable(R.drawable.gameboard);
        gamePieces = new ArrayList<>();
    }

    private void init(){

        int stepH = getHeight()/7;
        int stepV = getWidth()/7;
        int row = 4;
        int position = 1;
        for (int i = 0; i < 7; i++) {
            if(i == 3) row = 0;
            else row = 4;

            for (int k = 0; k < 7-row; k++) {

                if(i == 0 || i == 6){
                    gamePieces.add(new BoardPieceView(super.getContext(),k*3*stepV, i*stepH,  (k*3*stepV+stepV) , (i*stepH+stepH),position++));
                }
                if(i == 1 || i == 5){
                    gamePieces.add(new BoardPieceView(super.getContext(), (k * 2 + 1) * stepV, i * stepH, ((k * 2 + 1) * stepV + stepV), (i * stepH + stepH),position++));
                }
                if(i == 2 || i == 4){
                    gamePieces.add(new BoardPieceView(super.getContext(), (k+2)*stepV, i*stepH,  ((k+2)*stepV+stepV) , (i*stepH+stepH),position++));
                }
                if(i == 3) {
                    if(k != 3) gamePieces.add(new BoardPieceView(super.getContext(), (k)*stepV, i*stepH,  ((k)*stepV+stepV) , (i*stepH+stepH),position++));
                }
            }
        }
        inited = true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Log.i("TouchView.onTouchEvent", "event = " + event);
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            for(BoardPieceView b: gamePieces){
                if(b.isHit((int)event.getX(),(int)event.getY())){
                    //b.dispatchTouchEvent(event);
                    controller.placePiece(b.getPos());
                }
            }
            // Request the system to redraw the view (call onDraw at
            // some point in the future)
            // From a non-UI thread, call postInvalidate instead
            invalidate();
            return true;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gameboard.setBounds(getLeft(), getTop(), getRight(), getBottom());
        gameboard.draw(canvas);

        if(!inited)init();

        for(BoardPieceView b: gamePieces){
            b.draw(canvas);
        }

       // controller.setTextView("TEST");
    }

    public void paintEmptyPice(int pos, int color){
        gamePieces.get(pos-1).setColor(color);
    }

    public void movePiece(int pos, int from, int color){
        gamePieces.get(from-1).setColor(0);
        gamePieces.get(pos-1).setColor(color);
    }

    public int getColorOfPos(int pos){
        return gamePieces.get(pos-1).getColor();
    }



}
