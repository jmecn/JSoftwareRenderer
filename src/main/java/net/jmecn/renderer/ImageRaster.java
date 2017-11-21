package net.jmecn.renderer;

import java.util.Arrays;

import net.jmecn.math.ColorRGBA;

/**
 * 光栅器，用于绘制基本形状。
 * 
 * @author yanmaoyuan
 *
 */
public class ImageRaster {

    private int width;
    private int height;
    private byte[] components;

    public ImageRaster(Image image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.components = image.getComponents();
    }

    /**
     * 纯色填充
     * 
     * @param clearColor
     */
    public void fill(ColorRGBA color) {
        if (color.r == color.g && color.r == color.b) {
            Arrays.fill(components, color.r);
        } else {
            int length = width * height;
            for (int i = 0; i < length; i++) {
                int index = i * 4;
                components[index] = color.r;
                components[index + 1] = color.g;
                components[index + 2] = color.b;
                components[index + 3] = color.a;
            }
        }
    }

    /**
     * 画点
     * 
     * @param x
     * @param y
     * @param color
     */
    public void drawPixel(int x, int y, ColorRGBA color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        int index = (x + y * width) * 4;

        components[index] = color.r;
        components[index + 1] = color.g;
        components[index + 2] = color.b;
        components[index + 3] = color.a;
    }

    /**
     * 画线
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param color
     */
    public void drawLine(int x0, int y0, int x1, int y1, ColorRGBA color) {
        int x = x0;
        int y = y0;

        int w = x1 - x0;
        int h = y1 - y0;

        int dx1 = w < 0 ? -1: (w > 0 ? 1 : 0);
        int dy1 = h < 0 ? -1: (h > 0 ? 1 : 0);

        int dx2 = w < 0 ? -1: (w > 0 ? 1 : 0);
        int dy2 = 0;

        int fastStep = Math.abs(w);
        int slowStep = Math.abs(h);
        if (fastStep <=slowStep) {
               fastStep= Math.abs(h);
               slowStep= Math.abs(w);

               dx2= 0;
               dy2= h < 0 ? -1 : (h > 0 ? 1 : 0);
        } 
        int numerator = fastStep>> 1;

        for (int i = 0; i <=fastStep; i++) {
               drawPixel(x,y, color);
               numerator+= slowStep;
               if (numerator >=fastStep) {
                     numerator-= fastStep;
                     x+= dx1;
                     y+= dy1;
               }else {
                     x+= dx2;
                     y+= dy2;
               }
               drawPixel(x, y, color);
        }
    }
}
