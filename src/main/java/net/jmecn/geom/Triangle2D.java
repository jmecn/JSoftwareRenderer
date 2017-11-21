package net.jmecn.geom;

import net.jmecn.math.ColorRGBA;
import net.jmecn.renderer.ImageRaster;

/**
 * 代表个三角形。
 * 
 * @author yanmaoyuan
 *
 */
public class Triangle2D implements Drawable {

    public int x0, y0;
    public int x1, y1;
    public int x2, y2;
    public ColorRGBA color = ColorRGBA.RED;
    
    @Override
    public void draw(ImageRaster imageRaster) {
        imageRaster.drawLine(x0, y0, x1, y1, color);
        imageRaster.drawLine(x0, y0, x2, y2, color);
        imageRaster.drawLine(x1, y1, x2, y2, color);
    }

}
