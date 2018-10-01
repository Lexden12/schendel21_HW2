package schendel21.up.edu.hw2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Class to hold all the information needed to draw a Face. Contains a method to draw this Face
 * on a given canvas.
 * @author Alex Schendel
 */
public class Face {
    private final int STYLES = 3;
    private int skinColor, eyeColor, hairColor, hairStyle;
    private RectF face;
    private int width, height;
    private Point screen;
    private Paint paint;

    /**
     * Initialize the Face object
     * @param screen the dimensions of the view
     */
    public Face(Point screen){
        width = screen.x-screen.x/10;
        height = (int)(Math.min(width*3/2, screen.y*0.9));
        int left = screen.x/20;
        int top = (int)(screen.y/2-height/2.1);
        face = new RectF(left, top, left+width, top+height);
        paint = new Paint();
        randomize();
    }

    /**
     * randomize the colors and hairstyle of this face
     */
    public void randomize(){
        int red, green, blue;
        for(int i = 0; i < 3; i++){
            red=(int)(Math.random()*256);
            green=(int)(Math.random()*256);
            blue=(int)(Math.random()*256);
            if(i == 0)
                skinColor = 0xff << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
            else if(i == 1)
                eyeColor = 0xff << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
            else
                hairColor = 0xff << 24 | (red & 0xff) << 16 | (green & 0xff) << 8 | (blue & 0xff);
        }
        hairStyle = (int)(Math.random()*STYLES);
    }
    /**
     * External Citation
     Date:     30 September 2018
     Problem:  Could not set random RGB values for the four 8 bit values in the color
     Resource: https://developer.android.com/reference/android/graphics/Color

     Solution: I looked at how the Color object in Android does it and applied it to this solution.
     */

    public int getHairStyle() {
        return hairStyle;
    }

    public int getEyeColor() {
        return eyeColor;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getSkinColor() {
        return skinColor;
    }

    /**
     * Color setters used to set colors properly (r, g, and b are set separately because each
     * SeekBar in the activity changes each one individually)
     * @param color the color level to set (0-255)
     * @param rgb the color part to set (0 = red, 1 = green, else = blue)
     */
    public void setEyeColor(int color, int rgb) {
        if(rgb == 0) {
            eyeColor = ((eyeColor >> 24) & 0xff) << 24 | ((eyeColor >> 8) & 0xff) << 8 | (eyeColor & 0xff);
            eyeColor = eyeColor | color << 16;
        }
        else if(rgb == 1) {
            eyeColor = ((eyeColor >> 24) & 0xff) << 24 | ((eyeColor >> 16) & 0xff) << 16 | (eyeColor & 0xff);
            eyeColor = eyeColor | color << 8;
        }
        else {
            eyeColor = ((eyeColor >> 24) & 0xff) << 24 | ((eyeColor >> 16) & 0xff) << 16 | ((eyeColor >> 8) & 0xff) << 8;
            eyeColor = eyeColor | color;
        }
    }

    public void setHairColor(int color, int rgb) {
        if(rgb == 0) {
            hairColor = ((hairColor >> 24) & 0xff) << 24 | ((hairColor >> 8) & 0xff) << 8 | (hairColor & 0xff);
            hairColor = hairColor | color << 16;
        }
        else if(rgb == 1) {
            hairColor = ((hairColor >> 24) & 0xff) << 24 | ((hairColor >> 16) & 0xff) << 16 | (hairColor & 0xff);
            hairColor = hairColor | color << 8;
        }
        else {
            hairColor = ((hairColor >> 24) & 0xff) << 24 | ((hairColor >> 16) & 0xff) << 16 | ((hairColor >> 8) & 0xff) << 8;
            hairColor = hairColor | color;
        }
    }

    public void setSkinColor(int color, int rgb) {
        if(rgb == 0) {
            skinColor = ((skinColor >> 24) & 0xff) << 24 | ((skinColor >> 8) & 0xff) << 8 | (skinColor & 0xff);
            skinColor = skinColor | color << 16;
        }
        else if(rgb == 1) {
            skinColor = ((skinColor >> 24) & 0xff) << 24 | ((skinColor >> 16) & 0xff) << 16 | (skinColor & 0xff);
            skinColor = skinColor | color << 8;
        }
        else {
            skinColor = ((skinColor >> 24) & 0xff) << 24 | ((skinColor >> 16) & 0xff) << 16 | ((skinColor >> 8) & 0xff) << 8;
            skinColor = skinColor | color;
        }
    }

    public void setHairStyle(int hairStyle) {
        this.hairStyle = hairStyle;
    }

    /**
     * Draws this Face object on the given Canvas
     * @param c Canvas to draw this Face on
     */
    public void onDraw(Canvas c){
        drawFace(c);
        drawEyes(c);
        drawHair(c);
        drawMouth(c);
    }

    /**
     * Helper methods for onDraw "so that it's not one giant method"
     * @param c Canvas to draw on
     */
    private void drawFace(Canvas c){
        paint.setColor(skinColor);
        c.drawOval(face, paint);
    }

    private void drawEyes(Canvas c){
        paint.setColor(0xFFFFFFFF);//draw whites
        c.drawCircle(face.centerX()-face.width()/4, face.centerY()-face.width()/7, face.width()/8, paint);
        c.drawCircle(face.centerX()+face.width()/4, face.centerY()-face.width()/7, face.width()/8, paint);
        paint.setColor(eyeColor);//draw irises
        c.drawCircle(face.centerX()-face.width()/4, face.centerY()-face.width()/7, (face.width()/8)*5/8, paint);
        c.drawCircle(face.centerX()+face.width()/4, face.centerY()-face.width()/7, (face.width()/8)*5/8, paint);
        paint.setColor(0xFF000000);//draw pupils
        c.drawCircle(face.centerX()-face.width()/4, face.centerY()-face.width()/7, ((face.width()/8)*5/8)/2, paint);
        c.drawCircle(face.centerX()+face.width()/4, face.centerY()-face.width()/7, ((face.width()/8)*5/8)/2, paint);
    }

    private void drawHair(Canvas c){
        paint.setColor(hairColor);
        c.drawArc((int)(face.left), face.top, (int)(face.right),
                face.top+face.height()/2, 180, 180, true, paint);
        if(hairStyle == 1) {
            c.drawRect(new Rect((int) (face.left), (int) (face.top + face.height() / 4),
                    (int) (face.left + face.width() / 10), (int) (face.bottom - face.width() / 3)), paint);
            c.drawRect(new Rect((int) (face.right - face.width() / 10), (int) (face.top + face.height() / 4),
                    (int) (face.right), (int) (face.bottom - face.width() / 3)), paint);
        }
        else if(hairStyle == 2){
            c.drawOval(face.left + face.width()/3, face.top - face.height()/10,
                    face.right - face.width()/3, face.top + face.height()/20, paint);
        }
    }

    private void drawMouth(Canvas c){
        paint.setColor(0xFFFF0000);
        c.drawArc(face.left+face.width()/4, face.bottom-face.height()/4,
                face.right-face.width()/4, face.bottom*0.95f, 0,
                180, true, paint);
    }


}
