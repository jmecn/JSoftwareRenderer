package net.jmecn.renderer;

import net.jmecn.math.ColorRGBA;

/**
 * 光栅器，用于绘制基本形状。
 * 
 * @author yanmaoyuan
 *
 */
public class ImageRaster {

    private int width;
    private int height;
    private byte[] components;

    // Cohen-Sutherland算法的空间编码
    private final static int INSIDE = 0; // 0000
    private final static int LEFT   = 1; // 0001
    private final static int RIGHT  = 2; // 0010
    private final static int BOTTOM = 4; // 0100
    private final static int TOP    = 8; // 1000
    
    // 剪切矩形
    private final int xmin, ymin;
    private final int xmax, ymax;
    
    /**
     * 初始化光栅器
     * @param image
     */
    public ImageRaster(Image image) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.components = image.getComponents();
        
        // 初始化剪切矩形
        xmin = ymin = 0;
        xmax = width - 1;
        ymax = height - 1;
    }

    /**
     * 纯色填充
     * 
     * @param color
     */
    public void fill(ColorRGBA color) {
        int length = width * height;
        for (int i = 0; i < length; i++) {
            int index = i * 4;
            
            // 使用一个判断，避免无谓的赋值。
            if (components[index] != color.r && components[index + 1] != color.g
                    && components[index + 2] != color.b && components[index + 3] != color.a) {
                components[index] = color.r;
                components[index + 1] = color.g;
                components[index + 2] = color.b;
                components[index + 3] = color.a;
            }
        }
    }

    /**
     * 画点
     * 
     * @param x
     * @param y
     * @param color
     */
    public void drawPixel(int x, int y, ColorRGBA color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        int index = (x + y * width) * 4;

        components[index] = color.r;
        components[index + 1] = color.g;
        components[index + 2] = color.b;
        components[index + 3] = color.a;
    }

    /**
     * 画线
     * 
     * http://www.cnblogs.com/gamesky/archive/2012/08/21/2648623.html
     * 
     * http://tech-algorithm.com/articles/drawing-line-using-bresenham-algorithm/
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param color
     */
    public void drawLineBresenham(int x0, int y0, int x1, int y1, ColorRGBA color) {
        int x = x0;
        int y = y0;

        int w = x1 - x0;
        int h = y1 - y0;

        int dx1 = w < 0 ? -1 : (w > 0 ? 1 : 0);
        int dy1 = h < 0 ? -1 : (h > 0 ? 1 : 0);

        int dx2 = w < 0 ? -1 : (w > 0 ? 1 : 0);
        int dy2 = 0;

        int fastStep = Math.abs(w);
        int slowStep = Math.abs(h);
        if (fastStep <= slowStep) {
            fastStep = Math.abs(h);
            slowStep = Math.abs(w);

            dx2 = 0;
            dy2 = h < 0 ? -1 : (h > 0 ? 1 : 0);
        }
        int numerator = fastStep >> 1;

        for (int i = 0; i <= fastStep; i++) {
            drawPixel(x, y, color);
            numerator += slowStep;
            if (numerator >= fastStep) {
                numerator -= fastStep;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
            drawPixel(x, y, color);
        }
    }

    /**
     * 使用剪切矩形的边界来计算点(x, y) 的二进制编码
     * 
     * @param x
     * @param y
     * @return
     */
    private int computeOutCode(int x, int y) {
        int code;

        code = INSIDE;          // 初始值，位于剪切窗口内部

        if (x < xmin)           // 位于剪切窗口左侧
            code |= LEFT;
        else if (x > xmax)      // 位于剪切窗口右侧
            code |= RIGHT;
        if (y < ymin)           // 位于剪切窗口下方
            code |= BOTTOM;
        else if (y > ymax)      // 位于剪切窗口上方
            code |= TOP;

        return code;
    }
    
    /**
     * Cohen–Sutherland 剪切算法
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param color
     */
    public void drawLine(int x0, int y0, int x1, int y1, ColorRGBA color) {
        // 分别计算 P0 和 P1 的OutCode，判断它们是否位于剪切窗口外。
        int outcode0 = computeOutCode(x0, y0);
        int outcode1 = computeOutCode(x1, y1);
        boolean accept = false;

        while (true) {
            if ((outcode0 | outcode1) == 0) { // OR运算的结果为0，说明线段位于剪切窗口内，直接画吧。
                accept = true;
                break;
            } else if ((outcode0 & outcode1) != 0) {     // AND运算的结果不为0，说明两个点位于剪切窗口外的相同区域，终止循环。
                break;
            } else {
                // 前两个判断都不通过，说明需要计算线段与剪切矩形的交点。
                double x = 0, y = 0;

                // 至少有一个端点位于剪切窗口外，判断它的位置。
                int outcodeOut = outcode0 != 0 ? outcode0 : outcode1;

                // 现在计算交点
                // 使用公式:
                //   slope = (y1 - y0) / (x1 - x0)
                //   x = x0 + (1 / slope) * (ym - y0), ym 等于 ymin 或 ymax
                //   y = y0 + slope * (xm - x0), xm 等于 xmin 或 xmax
                if ((outcodeOut & TOP) != 0) {           // 位于窗口上方
                    x = x0 + (x1 - x0) * (ymax - y0) / (y1 - y0);
                    y = ymax;
                } else if ((outcodeOut & BOTTOM) != 0) { // 位于窗口下方
                    x = x0 + (x1 - x0) * (ymin - y0) / (y1 - y0);
                    y = ymin;
                } else if ((outcodeOut & RIGHT) != 0) {  // 位于窗口右侧
                    y = y0 + (y1 - y0) * (xmax - x0) / (x1 - x0);
                    x = xmax;
                } else if ((outcodeOut & LEFT) != 0) {   // 位于窗口左侧
                    y = y0 + (y1 - y0) * (xmin - x0) / (x1 - x0);
                    x = xmin;
                }

                // 现在把窗口外面这个端点移到交点处，准备下一轮剪切测试。
                if (outcodeOut == outcode0) {
                    x0 = (int) Math.round( x );
                    y0 = (int) Math.round( y );
                    outcode0 = computeOutCode(x0, y0);
                } else {
                    x1 = (int) Math.round( x );
                    y1 = (int) Math.round( y );
                    outcode1 = computeOutCode(x1, y1);
                }
            }
        }
        if (accept) {
            drawLineBresenham(x0, y0, x1, y1, color);
        }
    }
    
    /**
     * 画三角形
     * 
     * @param x0
     * @param y0
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @param color
     */
    public void drawTriangle(int x0, int y0, int x1, int y1, int x2, int y2, ColorRGBA color) {
        drawLine(x0, y0, x1, y1, color);
        drawLine(x0, y0, x2, y2, color);
        drawLine(x2, y2, x1, y1, color);
    }
}
