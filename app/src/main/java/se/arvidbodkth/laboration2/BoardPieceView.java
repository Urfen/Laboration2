package se.arvidbodkth.laboration2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Arvid och Mattias  on 2015-11-30.
 *
 */
public class BoardPieceView extends View {

    private int px;
    private int py;
    private int ph;
    private int pw;
    private int pos;
    private Rect rect;

    private Paint rectPaint;

    public BoardPieceView(Context context,int x, int y, int h, int w, int pos, int color) {
        super(context);
        this.px = x;
        this.py = y;
        this.ph = h;
        this.pw = w;
        this.pos = pos;

        rect = new Rect(px,py,ph,pw);
        rectPaint = new Paint();

        if(color == 0) rectPaint.setColor(Color.BLACK);
        //Reversed, the models is done.
        if(color == 1 || color == 4) rectPaint.setColor(Color.BLUE);
        if(color == 2 || color == 5) rectPaint.setColor(Color.RED);
    }


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
        canvas.drawCircle((float) px + (ph - px) / 2, (float) py + (pw - py) / 2, 40, rectPaint);
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

    public void setPx(int px) {
        this.px = px;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getPh() {
        return ph;
    }

    public void setPh(int ph) {
        this.ph = ph;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

}
