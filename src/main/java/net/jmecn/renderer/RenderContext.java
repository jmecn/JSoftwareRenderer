package net.jmecn.renderer;

import net.jmecn.math.Vector4f;
import net.jmecn.scene.Texture;
import net.jmecn.scene.VertexOut;

public class RenderContext extends ImageRaster {

    protected float[] depthBuffer;
    
    public RenderContext(Image image) {
        super(image);
        depthBuffer = new float[width * height];
    }
    
    private Texture texture;
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
    
    public void clearDepthBuffer() {
        int length = width * height;
        for(int i=0; i<length; i++) {
            depthBuffer[i] = 1.0f;
        }
    }
    
    /**
     * 画点
     * 
     * @param x
     * @param y
     * @param fragColor
     */
    public void drawFragment(int x, int y, Vector4f fragColor) {
        
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        int index = (x + y * width) * 4;

        components[index] = (byte)(fragColor.x * 0xFF);
        components[index + 1] = (byte)(fragColor.y * 0xFF);
        components[index + 2] = (byte)(fragColor.z * 0xFF);
        components[index + 3] = (byte)(fragColor.w * 0xFF);
    }
    
    /**
     * 片段着色
     * 
     * @param x
     * @param y
     * @param color
     */
    public void fragmentShader(VertexOut frag) {
        int x = Math.round(frag.fragCoord.x);
        int y = Math.round(frag.fragCoord.y);
        
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        // 深度测试
        if (depthBuffer[x + y * width] > frag.fragCoord.z) {
            depthBuffer[x + y * width] = frag.fragCoord.z;
        }
        
        Vector4f color = new Vector4f();
        color.set(frag.fragColor);
        
        if (texture != null) {
            Vector4f texColor = texture.sample2d(frag.texCoord);
            color.multLocal(texColor);
        }
        
        int index = (x + y * width) * 4;

        components[index] = (byte)(color.x * 0xFF);
        components[index + 1] = (byte)(color.y * 0xFF);
        components[index + 2] = (byte)(color.z * 0xFF);
        components[index + 3] = (byte)(color.w * 0xFF);
    }

    public void drawTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        drawLineBresenham(v0, v1);
        drawLineBresenham(v0, v2);
        drawLineBresenham(v1, v2);
    }
    
    public void fillTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        // 按Y坐标把三个顶点从上到下冒泡排序
        VertexOut tmp;
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
            
            VertexOut newVert = new VertexOut();
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
    private void fillBottomLineTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        int y0 = (int) Math.ceil(v0.fragCoord.y);
        int y2 = (int) Math.ceil(v2.fragCoord.y);
        
        for (int y = y0; y <y2; y++) {
            if (y >= 0 && y < this.height) {
                
                //插值生成左右顶点
                float t = (y - v0.fragCoord.y) / (v1.fragCoord.y - v0.fragCoord.y);
                
                VertexOut vl = new VertexOut();
                vl.interpolateLocal(v0, v1, t);
                VertexOut vr = new VertexOut();
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
    private void fillTopLineTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        int y0 = (int) Math.ceil(v0.fragCoord.y);
        int y2 = (int) Math.ceil(v2.fragCoord.y);

        for (int y = y0; y < y2; y++) {
            if (y >= 0 && y < this.height) {
                //插值生成左右顶点
                float t = (y - v0.fragCoord.y) / (v2.fragCoord.y - v0.fragCoord.y);
                
                VertexOut vl = new VertexOut();
                vl.interpolateLocal(v0, v2, t);
                VertexOut vr = new VertexOut();
                vr.interpolateLocal(v1, v2, t);
                
                //扫描线填充
                drawScanline(vl, vr, y);
            }
        }
    }
    
    /**
     * 画扫描线
     * @param v0
     * @param v1
     * @param y
     */
    public void drawScanline(VertexOut v0, VertexOut v1, int y) {
        int x0 = (int) Math.ceil(v0.fragCoord.x);
        // 按照DirectX和OpenGL的光栅化规则，舍弃右下的顶点。
        int x1 = (int) Math.floor(v1.fragCoord.x);
        
        for (int x = x0; x <= x1; x++) {
            if (x < 0 || x >= width)
                continue;
            // 线性插值
            float t = (x - v0.fragCoord.x) / (v1.fragCoord.x - v0.fragCoord.x);
            
            VertexOut frag = new VertexOut();
            frag.interpolateLocal(v0, v1, t);
            
            fragmentShader(frag);
        }
    }
    
    /**
     * 画线
     * 
     * @param yIndex 
     */
    public void drawLineBresenham(VertexOut v0, VertexOut v1) {
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
            
            drawFragment(x, y, color);
            numerator += slowStep;
            if (numerator >= fastStep) {
                numerator -= fastStep;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
            drawFragment(x, y, color);
        }
    }
}
