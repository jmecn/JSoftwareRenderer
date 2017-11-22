package net.jmecn.geom;

import net.jmecn.math.ColorRGBA;
import net.jmecn.renderer.ImageRaster;

/**
 * 代表一个三角形。
 * 
 * @author yanmaoyuan
 *
 */
public class Triangle2D implements Drawable {

    public int x0, y0;
    public int x1, y1;
    public int x2, y2;
    public ColorRGBA color = ColorRGBA.RED;
    public boolean isSolid = true;// 是否实心
    
    @Override
    public void draw(ImageRaster imageRaster) {
        if (isSolid) {
            imageRaster.fillTriangle(x0, y0, x1, y1, x2, y2, color);
        } else {
            imageRaster.drawTriangle(x0, y0, x1, y1, x2, y2, color);
        }
    }

}
