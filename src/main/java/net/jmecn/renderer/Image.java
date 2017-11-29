package net.jmecn.renderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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

    public Image(String fileName) throws IOException {
        int width = 0;
        int height = 0;
        byte[] components = null;

        BufferedImage image = ImageIO.read(new File(fileName));

        width = image.getWidth();
        height = image.getHeight();

        int imgPixels[] = new int[width * height];
        image.getRGB(0, 0, width, height, imgPixels, 0, width);
        components = new byte[width * height * 4];

        for(int i = 0; i < width * height; i++) {
            int pixel = imgPixels[i];

            components[i * 4]     = (byte)((pixel >> 16) & 0xFF); // R
            components[i * 4 + 1] = (byte)((pixel >> 8 ) & 0xFF); // G
            components[i * 4 + 2] = (byte)((pixel      ) & 0xFF); // B
            components[i * 4 + 3] = (byte)((pixel >> 24) & 0xFF); // A
        }

        this.width = width;
        this.height = height;
        this.components = components;
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