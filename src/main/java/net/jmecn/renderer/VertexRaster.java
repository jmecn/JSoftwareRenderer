package net.jmecn.renderer;

import net.jmecn.math.Vector4f;
import net.jmecn.scene.Texture;

public class VertexRaster extends ImageRaster {

    protected float[] depthBuffer;
    
    public VertexRaster(Image image) {
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
     * 光栅化点
     * @param x
     * @param y
     * @param frag
     */
    public void rasterizePoint(int x, int y, VertexOut frag) {
        
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        
        // 执行片段着色器
        fragmentShader(frag);

        float depth = frag.position.z / frag.position.w;
        
        // 深度测试
        if (depthBuffer[x + y * width] > depth) {
            depthBuffer[x + y * width] = depth;
        }
        
        Vector4f color = frag.color;
        
        // TODO BLEND混色
        
        int index = (x + y * width) * 4;

        components[index] = (byte)(color.x * 0xFF);
        components[index + 1] = (byte)(color.y * 0xFF);
        components[index + 2] = (byte)(color.z * 0xFF);
        components[index + 3] = (byte)(color.w * 0xFF);
    }

    public void drawTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        rasterizeLine(v0, v1);
        rasterizeLine(v0, v2);
        rasterizeLine(v1, v2);
    }
    
    /**
     * 光栅化三角形
     * @param v0
     * @param v1
     * @param v2
     */
    public void rasterizeTriangle(VertexOut v0, VertexOut v1, VertexOut v2) {
        // 按Y坐标把三个顶点从上到下冒泡排序
        VertexOut tmp;
        if (v0.position.y > v1.position.y) {
            tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        if (v1.position.y > v2.position.y) {
            tmp = v1;
            v1 = v2;
            v2 = tmp;
        }
        if (v0.position.y > v1.position.y) {
            tmp = v0;
            v0 = v1;
            v1 = tmp;
        }
        
        float y0 = v0.position.y;
        float y1 = v1.position.y;
        float y2 = v2.position.y;
        
        if (y0 == y1) {// 平顶
            fillTopLineTriangle(v0, v1, v2);
        } else if (y1 == y2) {// 平底
            fillBottomLineTriangle(v0, v1, v2);
        } else {// 分割三角形
            
            // 线性插值
            float t = (y1 - y0) / (y2 - y0);
            VertexOut middleVert = new VertexOut();
            middleVert.interpolateLocal(v0, v2, t);
            
            if (middleVert.position.x <= v1.position.x)  {// 左三角形
                // 画平底
                fillBottomLineTriangle(v0, middleVert, v1);
                // 画平顶
                fillTopLineTriangle(middleVert, v1, v2);
            } else {// 右三角形
                // 画平底
                fillBottomLineTriangle(v0, v1, middleVert);
                // 画平顶
                fillTopLineTriangle(v1, middleVert, v2);
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
        int y0 = (int) Math.ceil(v0.position.y);
        int y2 = (int) Math.ceil(v2.position.y);
        
        for (int y = y0; y <y2; y++) {
            if (y >= 0 && y < this.height) {
                
                // 插值生成左右顶点
                // FIXME 需要透视校正
                float t = (y - v0.position.y) / (v1.position.y - v0.position.y);
                
                VertexOut vl = new VertexOut();
                vl.interpolateLocal(v0, v1, t);
                VertexOut vr = new VertexOut();
                vr.interpolateLocal(v0, v2, t);

                //扫描线填充
                rasterizeScanline(vl, vr, y);
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
        int y0 = (int) Math.ceil(v0.position.y);
        int y2 = (int) Math.ceil(v2.position.y);

        for (int y = y0; y < y2; y++) {
            if (y >= 0 && y < this.height) {
                // 插值生成左右顶点
                // FIXME 需要透视校正
                float t = (y - v0.position.y) / (v2.position.y - v0.position.y);
                
                VertexOut vl = new VertexOut();
                vl.interpolateLocal(v0, v2, t);
                VertexOut vr = new VertexOut();
                vr.interpolateLocal(v1, v2, t);
                
                //扫描线填充
                rasterizeScanline(vl, vr, y);
            }
        }
    }
    
    /**
     * 光栅化扫描线
     * @param v0
     * @param v1
     * @param y
     */
    public void rasterizeScanline(VertexOut v0, VertexOut v1, int y) {
        int x0 = (int) Math.ceil(v0.position.x);
        // 按照DirectX和OpenGL的光栅化规则，舍弃右下的顶点。
        int x1 = (int) Math.floor(v1.position.x);
        
        for (int x = x0; x <= x1; x++) {
            if (x < 0 || x >= width)
                continue;
            
            // 线性插值
            // FIXME 需要透视校正
            float t = (x - v0.position.x) / (v1.position.x - v0.position.x);
            VertexOut frag = new VertexOut();
            frag.interpolateLocal(v0, v1, t);
            
            rasterizePoint(x, y, frag);
        }
    }
    
    /**
     * 光栅化线段，使用Bresenham算法。
     */
    public void rasterizeLine(VertexOut v0, VertexOut v1) {
        int x = (int) v0.position.x;
        int y = (int) v0.position.y;

        int w = (int) (v1.position.x - v0.position.x);
        int h = (int) (v1.position.y - v0.position.y);

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
            // 线性插值
            float t = (y - v0.position.y) / (v1.position.y - v0.position.y);
            VertexOut frag = new VertexOut();
            frag.interpolateLocal(v0, v1, t);
            rasterizePoint(x, y, frag);
            
            numerator += slowStep;
            if (numerator >= fastStep) {
                numerator -= fastStep;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
            
            // 线性插值
            t = (y - v0.position.y) / (v1.position.y - v0.position.y);
            frag = new VertexOut();
            frag.interpolateLocal(v0, v1, t);
            
            rasterizePoint(x, y, frag);
        }
    }
    
    /**
     * 片段着色器
     * @param frag
     */
    private void fragmentShader(VertexOut frag) {
        if (texture != null && frag.hasTexCoord) {
            Vector4f texColor = texture.sample2d(frag.texCoord);
            frag.color.multLocal(texColor);
        }
    }
}
