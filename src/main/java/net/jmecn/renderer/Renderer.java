package net.jmecn.renderer;

import net.jmecn.math.ColorRGBA;

/**
 * 渲染器
 * @author yanmaoyuan
 *
 */
public class Renderer {

    /**
     * 渲染内容
     */
    private Image renderContext;
    private ImageRaster imageRaster;
    
    private ColorRGBA clearColor = ColorRGBA.WHITE;
    
    public Renderer(int width, int height) {
        renderContext = new Image(width, height);
        imageRaster = new ImageRaster(renderContext);
    }

    public void setBackgroundColor(ColorRGBA color) {
        if (color != null) {
            this.clearColor = color;
        }
    }
    
    /**
     * 使用背景色填充图像数据
     */
    public void clear() {
        imageRaster.fill(clearColor);
    }

    public Image getRenderContext() {
        return renderContext;
    }

    /**
     * 获得光栅器
     * @return
     */
    public ImageRaster getImageRaster() {
        return imageRaster;
    }
}
