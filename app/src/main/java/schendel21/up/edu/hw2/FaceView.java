package schendel21.up.edu.hw2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.SurfaceView;

/**
 * Overrides SurfaceView to gain the ability to draw a Face on a SurfaceView
 * @author Alex Schendel
 */
public class FaceView extends SurfaceView {
    Face face;

    public FaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setWillNotDraw(false);
    }

    /**
     * Face class methods that should invalidate after being called (calls the method in the Face
     * object and then invalidates)
     */
    public void randomize(){
        face.randomize();
        invalidate();
    }

    public void setEyeColor(int color, int rgb) {
        face.setEyeColor(color, rgb);
        invalidate();
    }

    public void setHairColor(int color, int rgb) {
        face.setHairColor(color, rgb);
        invalidate();
    }

    public void setHairStyle(int hairStyle) {
        face.setHairStyle(hairStyle);
        invalidate();
    }

    public void setSkinColor(int color, int rgb) {
        face.setSkinColor(color, rgb);
        invalidate();
    }

    /**
     * Method called when the method invalidate() is called.
     * Draws the face object on the canvas by calling its onDraw method and passing it the canvas.
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if(face == null) {
            Point screen = new Point(getWidth(), getHeight());
            face = new Face(screen);
        }
        face.onDraw(canvas);
    }
}
