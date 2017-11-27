package net.jmecn.renderer;

import net.jmecn.math.Vector4f;
import net.jmecn.scene.Vertex;

public class RenderContext extends ImageRaster {

    protected float[] depthBuffer;
    
    public RenderContext(Image image) {
        super(image);
        depthBuffer = new float[width * height];
    }
    
    /**
     * 画点
     * 
     * @param x
     * @param y
     * @param color
     */
    public void drawPixel(int x, int y, Vector4f color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        int index = (x + y * width) * 4;

        components[index] = (byte)(color.x * 0xFF);
        components[index + 1] = (byte)(color.y * 0xFF);
        components[index + 2] = (byte)(color.z * 0xFF);
        components[index + 3] = (byte)(color.w * 0xFF);
    }

    public void drawTriangle(Vertex v0, Vertex v1, Vertex v2) {
        drawLineBresenham(v0, v1);
        drawLineBresenham(v0, v2);
        drawLineBresenham(v1, v2);
    }
    
    public void fillTriangle(Vertex v0, Vertex v1, Vertex v2) {
        // 按Y坐标把三个顶点从上到下冒泡排序
        Vertex tmp;
        if (v0.fragCoord.y > v1.fragCoord.y) {
            tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        if (v1.fragCoord.y > v2.fragCoord.y) {
            tmp = v1;
            v1 = v2;
            v2 = tmp;
        }
        if (v0.fragCoord.y > v1.fragCoord.y) {
            tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        
        float x0 = v0.fragCoord.x;
        float y0 = v0.fragCoord.y;
        float x1 = v1.fragCoord.x;
        float y1 = v1.fragCoord.y;
        float x2 = v2.fragCoord.x;
        float y2 = v2.fragCoord.y;
        
        if (y0 == y1) {// 平顶
            fillTopLineTriangle(v0, v1, v2);
        } else if (y1 == y2) {// 平底
            fillBottomLineTriangle(v0, v1, v2);
        } else {// 分割三角形
            float t = (y1 - y0) / (y2 - y0);
            // 长边在ymiddle时的x，来决定长边是在左边还是右边
            float middleX = (float)(t * (x2 - x0) + x0);
            
            Vertex newVert = new Vertex();
            newVert.interpolateLocal(v0, v2, t);
            
            if (middleX <= x1)  {// 左三角形
                // 画平底
                fillBottomLineTriangle(v0, newVert, v1);
                // 画平顶
                fillTopLineTriangle(newVert, v1, v2);
            } else {// 右三角形
                // 画平底
                fillBottomLineTriangle(v0, v1, newVert);
                // 画平顶
                fillTopLineTriangle(v1, newVert, v2);
            }
        }
    }

    /**
     * 画平底实心三角形
     * @param v0 上顶点
     * @param v1 底边左顶点
     * @param v2 底边右顶点
     */
    private void fillBottomLineTriangle(Vertex v0, Vertex v1, Vertex v2) {
        int y0 = (int) (v0.fragCoord.y + 0.5f);
        int y2 = (int) (v2.fragCoord.y + 0.5f);
        
        // 左边线的斜率
        float dyl = (v1.fragCoord.x - v0.fragCoord.x) / (v1.fragCoord.y - v0.fragCoord.y);
        // 右边线的斜率
        float dyr = (v2.fragCoord.x - v0.fragCoord.x) / (v2.fragCoord.y - v0.fragCoord.y);
        
        for (int y = y0; y <= y2; y++) {
            int yIndex = (int)(Math.round(y)); 
            if (yIndex >= 0 && yIndex < this.height) {
                float xl = (y - v0.fragCoord.y) * dyl + v0.fragCoord.x + 0.5f;
                float xr = (y - v0.fragCoord.y) * dyr + v0.fragCoord.x + 0.5f;

                float dy = y - v0.fragCoord.y;
                float t = dy / (v1.fragCoord.y - v0.fragCoord.y);
                //插值生成左右顶点
                Vertex vl = new Vertex();
                vl.fragCoord.x = xl;
                vl.fragCoord.y = y;
                vl.interpolateLocal(v0, v1, t);
                //
                Vertex vr = new Vertex();
                vr.fragCoord.x = xr;
                vr.fragCoord.y = y;
                vr.interpolateLocal(v0, v2, t);
                //扫描线填充
                drawScanline(vl, vr, y);
            }
        }
    }

    /**
     * 画平顶实心三角形
     * @param v0 顶边左顶点
     * @param v1 顶边右顶点
     * @param v2 下顶点
     */
    private void fillTopLineTriangle(Vertex v0, Vertex v1, Vertex v2) {
        int y0 = (int) (v0.fragCoord.y + 0.5f);
        int y2 = (int) (v2.fragCoord.y + 0.5f);

        // 左边线的斜率
        float dyl = (v2.fragCoord.x - v0.fragCoord.x) / (v2.fragCoord.y - v0.fragCoord.y);
        // 右边线的斜率
        float dyr = (v2.fragCoord.x - v1.fragCoord.x) / (v2.fragCoord.y - v1.fragCoord.y);
        
        for (int y = y0; y <= y2; y++) {
            if (y >= 0 && y < this.height) {
                float xl = (y - v0.fragCoord.y) * dyl + v0.fragCoord.x + 0.5f;
                float xr = (y - v1.fragCoord.y) * dyr + v1.fragCoord.x + 0.5f;

                float dy = y - v0.fragCoord.y;
                float t = dy / (v2.fragCoord.y - v0.fragCoord.y);
                //插值生成左右顶点
                Vertex vl = new Vertex();
                vl.interpolateLocal(v0, v2, t);
                vl.fragCoord.x = xl;
                vl.fragCoord.y = y;
                //
                Vertex vr = new Vertex();
                vr.interpolateLocal(v1, v2, t);
                vr.fragCoord.x = xr;
                vr.fragCoord.y = y;
                //扫描线填充
                drawScanline(vl, vr, y);
            }
        }
    }
    
    public void drawScanline(Vertex v0, Vertex v1, int yIndex) {
        int x = (int) (v0.fragCoord.x + 0.5f);
        int y = (int) (v0.fragCoord.y + 0.5f);
        
        int w = (int) (v1.fragCoord.x - v0.fragCoord.x);
        
        Vector4f c0 = v0.fragColor;
        Vector4f c1 = v1.fragColor;
        Vector4f color = new Vector4f();
        
        for (int i = 0; i <= w; i++) {
            // 颜色线性插值
            color.interpolateLocal(c0, c1, (float)i/(w-1));
            drawPixel(x+i, y, color);
        }
    }
    
    /**
     * 画线
     * 
     * @param yIndex 
     */
    public void drawLineBresenham(Vertex v0, Vertex v1) {
        int x = (int) v0.fragCoord.x;
        int y = (int) v0.fragCoord.y;

        int w = (int) (v1.fragCoord.x - v0.fragCoord.x);
        int h = (int) (v1.fragCoord.y - v0.fragCoord.y);

        Vector4f c0 = v0.fragColor;
        Vector4f c1 = v1.fragColor;
        Vector4f color = new Vector4f();
        
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
            // 颜色线性插值
            color.interpolateLocal(c0, c1, (float)i/(fastStep-1));
            
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
}
