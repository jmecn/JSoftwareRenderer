package net.jmecn.geom;
import net.jmecn.math.ColorRGBA;
import net.jmecn.renderer.ImageRaster;

/**
 * 代表一个2D点。
 * @author yanmaoyuan
 *
 */
public class Point2D implements Drawable{

    public int x, y;
    public ColorRGBA color;
    
    public void draw(ImageRaster raster) {
        raster.drawPixel(x, y, color);
    }
}
