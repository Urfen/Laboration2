package se.arvidbodkth.laboration2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by Arvid Bodin och Mattias Grehnic on 2015-11-30.
 *
 * This class is a custom view for the gameBoard. It contains
 * creates and inits the BoardPieceWiew dots.
 */
public class BoardView extends View {

    private Drawable gameboard;
    private ArrayList<BoardPieceView> gamePieces;
    private MainActivity controller;
    private Paint currentTurn;
    private boolean inited = false;
    private Paint paint;

    /**
     * Constructor for the board view. Inits the gamBoard background
     * image and the arrayList for the gamePieces.
     * @param context the context.
     * @param controller the controller.
     */
    public BoardView(Context context, MainActivity controller) {
        super(context);
        //Get the current turn.
        currentTurn = new Paint();
        this.controller =  controller;

        // Get a representation of the image
        Resources resources = context.getResources();
        gameboard = resources.getDrawable(R.drawable.gameboard);

        //Arraylist to be filled with
        gamePieces = new ArrayList<>();

        invalidate();

        //The paint to color
        paint = new Paint();

    }

    /**
     * Initiates the gameBoard with the hitboxes that are 1/7 of the
     * screen of the unit.
     */
    private void init(){
        //Get the a width and and hight of the screen.
        int stepH = getHeight()/7;
        int stepV = getWidth()/7;
        int row;
        int position = 1;

        //Set the middle turn indicator to the correct color.
        if(controller.getTurn() == 1) currentTurn.setColor(Color.BLUE);
        else currentTurn.setColor(Color.RED);

        //Create the 7 rows of circles with the appropriate color.
        for (int i = 0; i < 7; i++) {
            if(i == 3) row = 0;
            else row = 4;

            for (int k = 0; k < 7-row; k++) {
                if(i == 0 || i == 6){
                    gamePieces.add(new BoardPieceView(super.getContext(),k*3*stepV
                            , i*stepH,  (k*3*stepV+stepV) , (i*stepH+stepH),position++
                            ,controller.getColorOfPos(position)));
                }
                if(i == 1 || i == 5){
                    gamePieces.add(new BoardPieceView(super.getContext(), (k * 2 + 1) * stepV
                            , i * stepH, ((k * 2 + 1) * stepV + stepV), (i * stepH + stepH)
                            ,position++,controller.getColorOfPos(position)));
                }
                if(i == 2 || i == 4){
                    gamePieces.add(new BoardPieceView(super.getContext(), (k+2)*stepV
                            , i*stepH,  ((k+2)*stepV+stepV) , (i*stepH+stepH),position++
                            ,controller.getColorOfPos(position)));
                }
                if(i == 3) {
                    if(k != 3) gamePieces.add(new BoardPieceView(super.getContext(), (k)*stepV
                            , i*stepH,  ((k)*stepV+stepV) , (i*stepH+stepH),position++
                            ,controller.getColorOfPos(position)));
                }
            }
        }
        inited = true;
    }

    /**
     * If the view is hit. It compares the coordinates of the
     * event and compares it to the 24 different hatboxes.
     * @param event the touchEvent
     * @return if the event is handled
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //If the event was a touch.
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            //Loop through all the hitboxes.
            for(BoardPieceView b: gamePieces){
                //Se if the hitbox contains the events coordinates.
                if(b.isHit((int)event.getX(),(int)event.getY())){
                    //b.dispatchTouchEvent(event);
                    controller.placePiece(b.getPos());
                }
            }
            //Redraw
            invalidate();
            return true;
        }
        return false;
    }

    /**
     * Executes eventide invalidate is called. Sets the turn
     * indicator color, draws the background and the gamePieces.
     * @param canvas the canvas.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw the image and scale it to the scren size.
        gameboard.setBounds(getLeft(), getTop(), getRight(), getBottom());
        gameboard.draw(canvas);

        //If not inited, init.
        if(!inited){
            init();
        }

        //Draw  the game pieces.
        for(BoardPieceView b: gamePieces){
            b.draw(canvas);
        }

        //Set the color of the text for the turnIndicator.
        paint.setColor(Color.BLACK);
        paint.setTextSize((gamePieces.get(8).getPx() - gamePieces.get(7).getPx()) / 4);
        canvas.drawText("Turn:", 45 * getRight() / 100, 45 * getBottom() / 100, paint);

        //Toggle the color.
        if(controller.getTurn() == 1) currentTurn.setColor(Color.BLUE);
        else currentTurn.setColor(Color.RED);

        //Draw the turnIndicator.
        canvas.drawCircle((float) canvas.getWidth() / 2, (float) canvas.getHeight() / 2
                , (getHeight() / 7) / 5, currentTurn);
    }

    /**
     * Paints a gamePice with the given color.
     * @param pos the position of the piece.
     * @param color the color to paint with.
     */
    public void paintEmptyPiece(int pos, int color){
        gamePieces.get(pos-1).setColor(color);
    }

    /**
     * Move the color of a piece to another pos.
     * @param pos pos to move to.
     * @param from pos to move from.
     * @param color the color of the piece.
     */
    public void movePiece(int pos, int from, int color){
        //Set the color to black.
        gamePieces.get(from-1).setColor(0);
        //Paint the new place to the correct color.
        gamePieces.get(pos-1).setColor(color);
    }

    /**
     * Get the color of a given position.
     * @param pos the position to get the color of.
     * @return int color value.
     */
    public int getColorOfPos(int pos){
        return gamePieces.get(pos-1).getColor();
    }

    /**
     * Set the turn to a given color.
     * @param color turn to set.
     */
    public void setCurrentTurn(int color){
        if(color == 1){
            currentTurn.setColor(Color.BLUE);
        }else {
            currentTurn.setColor(Color.RED);
        }
    }

    /**
     * Get the list of pieces.
     * @return the list of gamePices.
     */
    public ArrayList<BoardPieceView> getGamePieces() {
        return gamePieces;
    }

}
