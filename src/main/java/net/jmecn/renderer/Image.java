package net.jmecn.renderer;

/**
 * 用于保存图像数据。 图像格式采用4BYTE_RGBA。
 * 
 * @author yanmaoyuan
 *
 */
public class Image {

    // 图片的宽度
    protected final int width;
    // 图片的高度
    protected final int height;
    // 颜色数据
    protected final byte[] components;

    public Image(int width, int height) {
        this.width = width;
        this.height = height;
        this.components = new byte[width * height * 4];
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public byte[] getComponents() {
        return components;
    }

}