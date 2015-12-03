package se.arvidbodkth.laboration2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Arvid Bodin och Mattias Grehnic on 2015-11-30.
 *
 * This class is a custom view for the gameBoard. It contains
 * creates and inits the BoardPieceWiew dots.
 */
public class BoardPieceView extends View {

    private int px;
    private int py;
    private int ph;
    private int pw;
    private int pos;

    private Rect rect;

    private Paint rectPaint;

    /**
     * Construkcor for the gamePiece.
     * @param context the context.
     * @param x coordinate.
     * @param y coordinate.
     * @param h coordinate.
     * @param w coordinate.
     * @param pos the position number.
     * @param color the color of the pice.
     */
    public BoardPieceView(Context context,int x, int y, int h, int w, int pos, int color) {
        super(context);
        this.px = x;
        this.py = y;
        this.ph = h;
        this.pw = w;
        this.pos = pos;

        //Create the rect tha is the hitpox of the piece.
        rect = new Rect(px,py,ph,pw);
        rectPaint = new Paint();

        //Paint the color.
        if(color == 0) rectPaint.setColor(Color.BLACK);
        if(color == 1 || color == 4) rectPaint.setColor(Color.BLUE);
        if(color == 2 || color == 5) rectPaint.setColor(Color.RED);
    }

    /**
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            System.out.println("Hit piece! Pos:" + pos);
            invalidate();
            return true;
        }
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle((float) px + (ph - px) / 2, (float) py + (pw - py) / 2,(pw - py)/5, rectPaint);
    }

    public void setColor(int color){
        if(color == 0) rectPaint.setColor(Color.BLACK);
        //Reversed, the models is done.
        if(color == 1 || color == 4) rectPaint.setColor(Color.BLUE);
        if(color == 2 || color == 5) rectPaint.setColor(Color.RED);
        invalidate();
    }

    public int getColor(){
        if(rectPaint.getColor() == Color.BLACK) return 0;
        if(rectPaint.getColor() == Color.BLUE) return 1;
        if(rectPaint.getColor() == Color.RED) return 2;
        else return 0;
    }

    public boolean isHit(int x, int y){
        return rect.contains(x,y);
    }

    public int getPos(){ return pos;}

    public int getPx() {
        return px;
    }
}
