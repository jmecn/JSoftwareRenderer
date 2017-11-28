package net.jmecn.scene;

import net.jmecn.math.ColorRGBA;
import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Image;
import net.jmecn.renderer.ImageRaster;

/**
 * 纹理
 * 
 * @author yanmaoyuan
 *
 */
public class Texture {

    private int width;
    private int height;
    private byte[] components;

    private final static float INV_SCALE = 1f / 255f;

    boolean isLinearFilter = false;
    public void setLinearFilter(boolean isLinearFilter) {
        this.isLinearFilter = isLinearFilter;
    }
    
    /**
     * 默认纹理，生成一个网格黑白相间的网格。
     */
    public Texture() {
        Image image = new Image(64, 64);
        ImageRaster raster = new ImageRaster(image);
        raster.fill(ColorRGBA.WHITE);

        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                int i = x / 8;
                int j = y / 8;
                if ((i + j) % 2 == 0) {
                    raster.drawPixel(x, y, ColorRGBA.BLACK);
                }
            }
        }

        setImage(image);
    }

    public Texture(Image image) {
        setImage(image);
    }

    public void setImage(Image image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.components = image.getComponents();
    }

    /**
     * 根据UV进行采样
     * 
     * @param uv
     * @return
     */
    public Vector4f sample2d(Vector2f uv) {
        if (isLinearFilter)
            return linear(uv.x, uv.y);
        else
            return nearest(uv.x, uv.y);
    }

    /**
     * 线性采样
     * 
     * @param s
     * @param t
     * @return
     */
    protected Vector4f linear(float s, float t) {
        Vector4f color = new Vector4f(1);

        // 计算坐标
        float u = (float) (width - 1) * s;
        float v = (float) (height - 1) * (1 - t);

        // 取整
        int iu = (int) u;
        int iv = (int) v;

        int uNext = iu + 1 <= (width - 1) ? iu + 1 : iu;
        int vNext = iv + 1 <= (height - 1) ? iv + 1 : iv;

        // 计算贡献值
        float uNextPer = u - iu;
        float vNextPer = v - iv;
        float uPer = 1.0f - uNextPer;
        float vPer = 1.0f - vNextPer;

        color = getColor(iu, iv);

        // 另外三个采样点
        Vector4f colorNextU = getColor(uNext, iv);
        Vector4f colorNextV = getColor(iu, vNext);
        Vector4f colorNextUV = getColor(uNext, vNext);
        
        color.x=color.x*uPer*vPer+colorNextU.x*uNextPer*vPer+colorNextV.x*uPer*vNextPer+colorNextUV.x*uNextPer*vNextPer;  
        color.y=color.y*uPer*vPer+colorNextU.y*uNextPer*vPer+colorNextV.y*uPer*vNextPer+colorNextUV.y*uNextPer*vNextPer;  
        color.z=color.z*uPer*vPer+colorNextU.z*uNextPer*vPer+colorNextV.z*uPer*vNextPer+colorNextUV.z*uNextPer*vNextPer;  
        color.w=color.w*uPer*vPer+colorNextU.w*uNextPer*vPer+colorNextV.w*uPer*vNextPer+colorNextUV.w*uNextPer*vNextPer;  
        
        return color;
    }

    /**
     * 邻近点(NEAREST)采样
     * 
     * @param s
     * @param t
     * @return
     */
    protected Vector4f nearest(float s, float t) {

        // 计算坐标
        float u = (float) (width - 1) * s;
        float v = (float) (height - 1) * (1 - t);

        // 取整
        int iu = (int) u;
        int iv = (int) v;

        Vector4f color = getColor(iu, iv);
        return color;
    }
    
    /**
     * 提取颜色
     * @param x
     * @param y
     * @return
     */
    public Vector4f getColor(int x, int y) {
        Vector4f color = new Vector4f();
        
        int index = (x + y * width) * 4;
        float r = (float)(0xFF & components[index]) * INV_SCALE;
        float g = (float)(0xFF & components[index+1]) * INV_SCALE;
        float b = (float)(0xFF & components[index+2]) * INV_SCALE;
        float a = (float)(0xFF & components[index+3]) * INV_SCALE;
        
        color.set(r, g, b, a);
        return color;
    }
}
