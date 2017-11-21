package net.jmecn;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import javax.swing.JFrame;

import net.jmecn.renderer.Image;

/**
 * 代表显示图像的窗口
 * 
 * @author yanmaoyuan
 *
 */
public class Screen extends Canvas {
    
    private static final long serialVersionUID = 1L;

    // 主窗口
    private JFrame frame;
    
    // 用于显示的图像
    private BufferedImage displayImage;
    private byte[] displayComponents;
    
    // Canvas的双缓冲
    private BufferStrategy bufferStrategy;
    
    public Screen(int width, int height, String title) {
        // 设置画布的尺寸
        Dimension size = new Dimension(width, height);
        
        this.setPreferredSize(size);
        this.setMaximumSize(size);
        this.setMinimumSize(size);
        this.setFocusable(true);

        // 创建主窗口
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);// 设置画布
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setTitle(title);
        frame.pack();
        frame.setVisible(true);
        
        centerScreen();// 窗口居中
        
        // 焦点集中到画布上，响应用户输入。
        this.requestFocus();
        
        // 创建缓冲图像
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        // 获得图像中的数组
        this.displayComponents = ((DataBufferByte)displayImage.getRaster().getDataBuffer()).getData();
        
        // 创建双缓冲
        this.createBufferStrategy(2);
        this.bufferStrategy = this.getBufferStrategy();
    }

    /**
     * 使窗口位于屏幕的中央。
     */
    private void centerScreen() {
        Dimension size = frame.getSize();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screen.width - size.width) / 2;
        int y = (screen.height - size.height) / 2;
        frame.setLocation(x, y);
    }
    
    /**
     * 交换缓冲区，将渲染结果刷新到画布上。
     * @param image
     */
    public void swapBuffer(Image image, int fps) {
        
        // 把内存中的图像，整个拷贝到BufferedImage中。
        int width = image.getWidth();
        int height = image.getHeight();
        byte[] components = image.getComponents();
        int length = width * height;
        for (int i = 0; i < length; i++) {
            // blue
            displayComponents[i * 3] = components[i * 4 + 2];
            // green
            displayComponents[i * 3 + 1] = components[i * 4 + 1];
            // red
            displayComponents[i * 3 + 2] = components[i * 4];
        }
        
        // 将BufferedImage绘制到缓冲区
        Graphics graphics = bufferStrategy.getDrawGraphics();
        graphics.drawImage(displayImage, 0, 0, image.getWidth(), image.getHeight(), null);
        
        
        graphics.dispose();
        
        // 显示图像
        bufferStrategy.show();
    }

}
