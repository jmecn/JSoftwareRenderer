package net.jmecn.geom;

import net.jmecn.renderer.ImageRaster;

/**
 * 代表一个可渲染物体。
 * 
 * @author yanmaoyuan
 *
 */
public interface Drawable {

    public void draw(ImageRaster imageRaster);
    
}
