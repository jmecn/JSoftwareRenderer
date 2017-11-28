package net.jmecn;

import java.awt.Canvas;
import java.awt.Color;
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
public class Screen {
    
    // 主窗口
    private JFrame frame;
    
    // 画布
    private Canvas canvas;
    // Canvas的双缓冲
    private BufferStrategy bufferStrategy;
    
    // 用于显示的图像
    private BufferedImage displayImage;
    private byte[] displayComponents;
    
    // 用户输入
    private Input input;
    
    public Screen(int width, int height, String title) {
        canvas = new Canvas();
        
        // 设置画布的尺寸
        Dimension size = new Dimension(width, height);
        canvas.setPreferredSize(size);
        canvas.setMaximumSize(size);
        canvas.setMinimumSize(size);
        canvas.setFocusable(true);

        input = new Input();
        canvas.addKeyListener(input);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);
        
        // 创建主窗口
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(width, height);
        frame.setTitle(title);
        frame.add(canvas);// 设置画布
        frame.pack();
        frame.setVisible(true);
        centerScreen();// 窗口居中
        
        // 焦点集中到画布上，响应用户输入。
        canvas.requestFocus();
        
        // 创建双缓冲
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        
        // 创建缓冲图像
        displayImage = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        // 获得图像中的数组
        displayComponents = ((DataBufferByte)displayImage.getRaster().getDataBuffer()).getData();

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
     * @param fps
     */
    public void swapBuffer(Image image, int fps) {
        // 把渲染好的图像拷贝到BufferedImage中。
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
        
        Graphics graphics = bufferStrategy.getDrawGraphics();
        
        // 将BufferedImage绘制到缓冲区
        graphics.drawImage(displayImage, 0, 0, displayImage.getWidth(), displayImage.getHeight(), null);
        
        // 显示帧率
        graphics.setColor(Color.WHITE);
        graphics.drawString("FPS:" + fps, 2, 16);
        
        graphics.dispose();
        
        // 显示图像
        bufferStrategy.show();
    }

    /**
     * 获得用户输入
     * @return
     */
    public Input getInput() {
        return input;
    }

}
