package net.jmecn.material;

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

    boolean isLinearFilter = false;
    public void setLinearFilter(boolean isLinearFilter) {
        this.isLinearFilter = isLinearFilter;
    }
    
    /**
     * 默认纹理，生成一个网格黑白相间的网格。
     */
    public Texture() {
        Image image = new Image(64, 64);
        
        // 创建一个ImageRaster用来画图。
        ImageRaster raster = new ImageRaster(image);
        
        // 底色填充为白色
        raster.fill(ColorRGBA.WHITE);

        // 纯黑
        ColorRGBA color = new ColorRGBA(0x00000000);
        for (int y = 0; y < 64; y++) {
            for (int x = 0; x < 64; x++) {
                int i = x / 8;
                int j = y / 8;
                if ((i + j) % 2 == 0) {
                    raster.drawPixel(x, y, color);
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
        // 计算坐标
        float u = (float) (width - 1) * s;
        float v = (float) (height - 1) * (1 - t);

        // 取整
        int iu0 = (int) u;
        int iv0 = (int) v;
        
        int iu1 = iu0 + 1;
        int iv1 = iv0 + 1;
        
        if (iu1 > width - 1)
            iu1 = iu0;
        
        if (iv1 > height - 1)
            iv1 = iv0;

        // 四个采样点
        Vector4f c0 = getColor(iu0, iv0);
        Vector4f c1 = getColor(iu1, iv0);
        Vector4f c2 = getColor(iu0, iv1);
        Vector4f c3 = getColor(iu1, iv1);
        
        // 计算四个采样点的贡献值
        float du0 = u - iu0;
        float dv0 = v - iv0;
        float du1 = 1f - du0;
        float dv1 = 1f - dv0;
        
        // 计算最终的颜色
        c0.x = c0.x * du1 * dv1 + c1.x * du0 * dv1 + c2.x * du1 * dv0 + c3.x * du0 * dv0;
        c0.y = c0.y * du1 * dv1 + c1.y * du0 * dv1 + c2.y * du1 * dv0 + c3.y * du0 * dv0;
        c0.z = c0.z * du1 * dv1 + c1.z * du0 * dv1 + c2.z * du1 * dv0 + c3.z * du0 * dv0;
        c0.w = c0.w * du1 * dv1 + c1.w * du0 * dv1 + c2.w * du1 * dv0 + c3.w * du0 * dv0;
        
        return c0;
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
    
    private final static float INV_SCALE = 1f / 255f;
    
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
