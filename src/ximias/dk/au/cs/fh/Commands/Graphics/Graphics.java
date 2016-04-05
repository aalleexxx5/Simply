package ximias.dk.au.cs.fh.Commands.Graphics;

import ximias.dk.au.cs.fh.Components.ArgManipulation;
import ximias.dk.au.cs.fh.Components.Lookup;
import ximias.dk.au.cs.fh.Components.Mem;
import ximias.dk.au.cs.fh.Components.Viewer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.LookupOp;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Alex on 04/04/2016.
 * Reference for all graphics blocks and buffered images.
 */
public class Graphics {//TODO: possibly needs optimising
    static ArrayList<GraphicsBlock> graphicsBlocks=new ArrayList<>();
    static ArrayList<BufferedPair> bufferedImages=new ArrayList<>();
    final static ReentrantLock lock = new ReentrantLock();

    public static BufferedImage getImage(String key) {
        lock.lock();
        try {
            for (BufferedPair bufferedImage : bufferedImages) {
                if (bufferedImage.getKey().equals(key)) {
                    BufferedImage image =bufferedImage.getImage();
                    bufferedImages.remove(bufferedImage);
                    return image;
                }
            }
            for (GraphicsBlock graphicsBlock : graphicsBlocks) {
                if (graphicsBlock.getKey().equals(key)) {
                    return createImage(toGraphicsPair(graphicsBlock));
                }
            }
            return null;
        } finally {
            lock.unlock();
        }
    }
    public static boolean buffer(String key){
        lock.lock();
        try{
            for (BufferedPair bufferedImage : bufferedImages) {
                if (bufferedImage.getKey().equals(key)){
                    bufferedImages.remove(bufferedImage);
                    break;
                }
            }
            for (GraphicsBlock graphicsBlock : graphicsBlocks) {
                if (graphicsBlock.getKey().equals(key)){
                    bufferedImages.add(new BufferedPair(key,createImage(toGraphicsPair(graphicsBlock))));
                    return true;
                }
            }
            Viewer.print("an element with that name was not found");
            return false;
        }finally {
            lock.unlock();
        }
    }
    public static void add(String name,int width, int height,String[] elements){
        lock.lock();
        try{
            for (GraphicsBlock graphicsBlock : graphicsBlocks) {
                if (graphicsBlock.getKey().equals(name)){
                    graphicsBlocks.remove(graphicsBlock);
                    break;
                }
            }
            graphicsBlocks.add(new GraphicsBlock(name,width,height,elements));
        }finally {
            lock.unlock();
        }
    }

    private static GraphicsPair toGraphicsPair(GraphicsBlock block){
        String[] commands = block.getElements();
        ArrayList<GraphicsElement> graphicsElements=new ArrayList<>();
        ArrayList<SectionPair> section = new ArrayList<>();
        for (int line = 0; line < commands.length; line++){
            String element = commands[line];
            if (element.length()>4&&!element.toLowerCase().startsWith("graphics")&&!element.toLowerCase().startsWith("endgraphics")) {
                String[] params = element.split(" ");
                if (element.toLowerCase().startsWith("section")){
                    section.add(new SectionPair(line,params[1]));

                }else if(element.toLowerCase().startsWith("if")) {
                    String[] args =new String[params.length-1];
                    System.arraycopy(params,1,args,0,params.length-1);
                    int a =GraphicIf.execute(args);
                    if (a > 0) {
                        if (element.toLowerCase().contains("jumpto")){
                            for (SectionPair sectionPair : section) {
                                if (sectionPair.getKey().equals(params[params.length-1])){
                                    line=sectionPair.getLine();
                                    break;
                                }
                            }
                        }else{
                            String[] args2 =new String[args.length-a-1];
                            System.arraycopy(args,a+1,args2,0,args.length-a-1);
                            Lookup.runMainCommand(args[a],args2);
                        }
                    }
                }else if (element.toLowerCase().contains("jumpto")){
                    for (SectionPair sectionPair : section) {
                        if (sectionPair.getKey().equals(params[1])){
                            line=sectionPair.getLine();
                        }
                    }
                }else{
                    for (int i = 0; i < commands.length; i++) {
                        params=Mem.getValuesInArgs(params);
                    }
                    Color color = Color.black;
                    int locX, locY;
                    if (params.length>3&&params[0].length()>3 && ArgManipulation.isNumber(params[1]) && ArgManipulation.isNumber(params[2]) && ArgManipulation.isHex(params[3])) {
                        locX = Integer.valueOf(params[1]);
                        locY = Integer.valueOf(params[2]);
                        if (params[3].length() == 6) {
                            color = new Color(Integer.parseInt(params[3], 16));
                        } else if (params[3].length() == 9 && ArgManipulation.isNumber(params[3])) {
                            color = new Color(Integer.valueOf(params[3].substring(0,3)),Integer.valueOf(params[3].substring(3,6)),Integer.valueOf(params[3].substring(6,9)));
                        } else {
                            Viewer.print(params[0]+": The length of the color value was not either 6(hex) or 9(dec) digits: "+params[3]);
                            return null;
                        }
                        GraphicsElement ge;
                        if (element.startsWith("pixel")) {
                            ge = new GraphicsElement(GraphicsElement.PIXEL, locX, locY, color);
                        } else {
                            int width;
                            if (ArgManipulation.isNumber(params[4])) {
                                width = Integer.valueOf(params[4]);
                            } else {
                                Viewer.print("4th argument was not a number!");
                                width = 0;
                            }
                            if (element.startsWith("line")) {
                                if (ArgManipulation.isNumber(params[5])) {
                                    ge = new GraphicsElement(GraphicsElement.LINE, locX, locY, color);
                                    ge.setWidth(width);
                                    ge.setHeight(Integer.valueOf(params[5]));
                                } else {
                                    Viewer.print("line, y-pos was not a number");
                                    ge = null;
                                }
                            } else if (element.startsWith("rectangle")) {
                                if (params.length>6&&ArgManipulation.isNumber(params[5]) && ArgManipulation.isNumber(params[6])) {
                                    ge = new GraphicsElement(GraphicsElement.RECTANGLE, locX, locY, color);
                                    ge.setWidth(Math.abs(width));
                                    ge.setHeight(Math.abs(Integer.valueOf(params[5])));
                                    ge.setRotation(Integer.valueOf(params[6]));
                                } else {
                                    Viewer.print("Rectangle, height or rotation was not a number or were missing");
                                    ge = null;

                                }
                            } else if (element.startsWith("polygon")) {
                                if (ArgManipulation.isNumber(params[5])) {
                                    ge = new GraphicsElement(GraphicsElement.POLYGON, locX, locY, color);
                                    if (ArgManipulation.isNumber(params[params.length - 1])) {
                                        ge.setRotation(Integer.valueOf(params[params.length - 1]));
                                    }
                                    int[] xs = new int[(params.length - 2) / 2];
                                    int[] ys = new int[(params.length - 2) / 2];
                                    xs[0] = locX;
                                    xs[1] = width;
                                    ys[0] = locY;
                                    for (int i = 5; i < params.length - 1; i++) {
                                        if (ArgManipulation.isNumber(params[i])) {
                                            if (i % 2 == 1) {
                                                ys[((i - 1) / 2) - 1] = Integer.valueOf(params[i]);
                                            } else {
                                                xs[(i - 1) / 2] = Integer.valueOf(params[i]);
                                            }
                                        } else {
                                            Viewer.print("Polygon, a value was not a number");
                                            ge = null;

                                        }
                                    }
                                    ge.setXs((xs));
                                    ge.setYs(ys);
                                } else {
                                    Viewer.print("Polygon, a value was not a number");
                                    ge = null;
                                }
                            } else if (element.startsWith("circle")) {
                                ge = new GraphicsElement(GraphicsElement.CIRCLE, locX, locY, color);
                                ge.setWidth(Math.abs(width));
                            } else if (element.startsWith("text")) {
                                if (ArgManipulation.isNumber(params[params.length - 1])) {
                                    ge = new GraphicsElement(GraphicsElement.TEXT, locX, locY, color);
                                    ge.setWidth(Math.abs(width));
                                    ge.setMessage(ArgManipulation.argsToString(5, params.length - 1, params));
                                    ge.setRotation(Integer.valueOf(params[params.length - 1]));
                                } else {
                                    Viewer.print("text, height or rotation was not a number");
                                    ge = null;
                                }
                            }else{
                                ge = null;
                            }
                        }
                            graphicsElements.add(ge);
                    } else {
                        if (params[0].equalsIgnoreCase("draw")){
                            GraphicsElement ge;
                            ge=new GraphicsElement(GraphicsElement.IMAGE,Integer.valueOf(params[2]),Integer.valueOf(params[3]),Color.black);
                            BufferedImage image = getImage(params[1]);
                            ge.setWidth(image.getWidth());
                            ge.setHeight(image.getHeight());
                            ge.setRotation(Integer.valueOf(params[params.length-1]));
                            ge.setImage(image);
                            graphicsElements.add(ge);
                        }else {
                            String[] a = new String[params.length - 1];
                            System.arraycopy(params, 1, a, 0, params.length - 1);
                            Lookup.runMainCommand(params[0], a);
                        }
                    }
                }
            }
        }
        return new GraphicsPair(block.getKey(),block.getWidth(),block.getHeight(),graphicsElements.toArray(new GraphicsElement[0]));
    }

    private static BufferedImage createImage(GraphicsPair pair){
        if (pair == null) {
            return null;
        }
        BufferedImage image = new BufferedImage(pair.getWidth(),pair.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D gfx =image.createGraphics();
        AffineTransform origin = gfx.getTransform();
        for (GraphicsElement element : pair.getElements()) {
            gfx.setTransform(origin);
            if (element.getType()==0){
                try {//TODO: Please let there be a faster way
                    image.setRGB(element.getX(),element.getY(),element.getColor().getRGB());
                }catch (ArrayIndexOutOfBoundsException e){
                    Viewer.print("A Pixel was outside the window!");
                }
            }else {
                gfx.setColor(element.getColor());
                if (element.getType() == 1) {
                    gfx.drawLine(element.getX(),element.getY(),element.getWidth(),element.getHeight());
                } else if (element.getType() == GraphicsElement.RECTANGLE) {
                    gfx.translate(element.getX()+(element.getWidth()/2),element.getY()+(element.getHeight()/2));
                    double rot = element.getRotation()*(Math.PI/180);
                    gfx.rotate(rot);
                    gfx.translate(-(element.getWidth()/2),-(element.getHeight()/2));
                    gfx.fillRect(0,0,element.getWidth(),element.getHeight());
                } else if (element.getType() == 3) {
                    gfx.translate(element.getX(),element.getY());
                    int[] x=element.getXs();
                    int[] y=element.getYs();
                    for (int i = 0; i < x.length; i++) {
                        x[i] = x[i]-element.getX();
                    }
                    for (int i = 0; i < y.length; i++) {
                        y[i] = y[i]-element.getY();
                    }
                    double rot = element.getRotation()*(Math.PI/180);
                    gfx.rotate(rot);
                    gfx.fillPolygon(x,y,element.getXs().length);
                } else if (element.getType() == 4) {
                    gfx.fillOval(element.getX(),element.getY(),element.getWidth(),element.getWidth());
                } else if (element.getType() == 5) {
                    gfx.translate(element.getX(),element.getY());
                    double rot = element.getRotation()*(Math.PI/180);
                    gfx.rotate(rot);
                    gfx.setFont(new Font(gfx.getFont().getName(),Font.PLAIN,element.getWidth()));
                    gfx.drawString(element.getMessage(),0,0);
                } else if (element.getType()==GraphicsElement.IMAGE){
                    gfx.translate(element.getX()+element.getWidth()/2,element.getY()+element.getHeight()/2);
                    double rot = element.getRotation()*(Math.PI/180);
                    gfx.rotate(rot);
                    gfx.translate(-(element.getWidth()/2),-(element.getHeight()/2));
                    gfx.drawImage(element.getImage(), null,0,0);
                }
            }
        }
        return image;
    }
}
class GraphicsPair{
    private String key;
    private int width,height;
    private GraphicsElement[] elements;
    GraphicsPair(String name,int width,int height,GraphicsElement[] elements){
        this.key=name;
        this.width=width;
        this.height=height;
        this.elements=elements;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getKey() {
        return key;
    }

    public GraphicsElement[] getElements() {
        return elements;
    }
}
class GraphicsBlock{
    private String key;
    private int width,height;
    private String[] elements;
    GraphicsBlock(String name,int width,int height,String[] elements){
        this.key=name;
        this.width=width;
        this.height=height;
        this.elements=elements;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getKey() {
        return key;
    }

    public String[] getElements() {
        return elements;
    }
}
class BufferedPair{
    private String key;
    private BufferedImage image;
    BufferedPair(String key,BufferedImage image){
        this.image=image;
        this.key=key;
    }

    public String getKey() {
        return key;
    }

    public BufferedImage getImage() {
        return image;
    }
}
class SectionPair{
    private int line;
    private String key;
    SectionPair(int line, String key){
        this.key=key;
        this.line=line;
    }

    public int getLine() {
        return line;
    }

    public String getKey() {
        return key;
    }
}
