package ximias.dk.au.cs.fh.Commands.Graphics;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by Alex on 03/04/2016.
 */
public class GraphicsElement {
    private int x, y,width,height,type,rotation;
    private int[] xs,ys;
    private String message;
    private Color color;
    private BufferedImage image;
    GraphicsElement(int type,int locX,int locY,Color color){
        this.type=type;
        this.x =locX;
        this.y =locY;
        this.color=color;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getType() {
        return type;
    }

    public int getRotation() {
        return rotation;
    }

    public Color getColor() {
        return color;
    }

    public int[] getXs() {
        return xs;
    }

    public void setXs(int[] xs) {
        this.xs = xs;
    }

    public int[] getYs() {
        return ys;
    }

    public void setYs(int[] ys) {
        this.ys = ys;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public static final int PIXEL=0;
    public static final int LINE=1;
    public static final int RECTANGLE=2;
    public static final int POLYGON=3;
    public static final int CIRCLE=4;
    public static final int TEXT=5;
    public static final int IMAGE=6;
}
