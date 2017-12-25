package net.jmecn.renderer;

import net.jmecn.material.RenderState;
import net.jmecn.math.Matrix4f;
import net.jmecn.math.Vector4f;
import net.jmecn.scene.RasterizationVertex;
import net.jmecn.shader.Shader;

/**
 * 软件光栅器
 * @author yanmaoyuan
 *
 */
public class SoftwareRaster extends ImageRaster {

    private final static float INV_SCALE = 1f / 255f;
    
    // 深度缓冲
    protected float[] depthBuffer;

    // 渲染器
    protected Renderer renderer;

    // 渲染状态
    protected RenderState renderState;
    
    public void setRenderState(RenderState renderState) {
        this.renderState = renderState;
    }
    
    // 着色器
    private Shader shader;
    
    public void setShader(Shader shader) {
        this.shader = shader;
    }
    
    public SoftwareRaster(Renderer renderer, Image image) {
        super(image);
        this.depthBuffer = new float[width * height];
        this.renderer = renderer;
    }
    
    /**
     * 清除深度缓冲
     */
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
    public void rasterizePixel(int x, int y, RasterizationVertex frag) {
        
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }
        
        // 透视投影修正
        float w = 1f / frag.position.w;
        frag.texCoord.multLocal(w);
        frag.color.multLocal(w);
        frag.normal.multLocal(w);
        
        frag.worldSpacePosition.multLocal(w);
        
        // 执行片段着色器
        if ( !shader.fragmentShader(frag) )
            return;

        int index = x + y * width;
        float depth = frag.position.z;
        
        // 深度测试
        if (renderState.isDepthTest()) {
            if (!depthTest(depthBuffer[index], depth))
            return;
        }
        
        // Alpha测试
        if (renderState.isAlphaTest()) {
            if (frag.color.w < renderState.getAlphaFalloff())
                return;
        }

        // 颜色混合
        Vector4f srcColor = frag.color;
        Vector4f destColor = getColor(x, y);
        
        switch (renderState.getBlendMode()) {
        case OFF:
            destColor.x = srcColor.x;
            destColor.y = srcColor.y;
            destColor.z = srcColor.z;
            break;
        case ADD:
            destColor.x += srcColor.x;
            destColor.y += srcColor.y;
            destColor.z += srcColor.z;
            break;
        case ALPHA_BLEND:
            destColor.x = destColor.x + (srcColor.x - destColor.x) * srcColor.w;
            destColor.y = destColor.y + (srcColor.y - destColor.y) * srcColor.w;
            destColor.z = destColor.z + (srcColor.z - destColor.z) * srcColor.w;
            break;
        }
        
        destColor.x = clamp(destColor.x, 0, 1);
        destColor.y = clamp(destColor.y, 0, 1);
        destColor.z = clamp(destColor.z, 0, 1);
        destColor.w = clamp(destColor.w, 0, 1);
        
        // 写入depthBuffer
        if (renderState.isDepthWrite()) {
            depthBuffer[index] = depth;
        }
        
        // 写入frameBuffer
        index *= 4;

        components[index] = (byte)(destColor.x * 0xFF);
        components[index + 1] = (byte)(destColor.y * 0xFF);
        components[index + 2] = (byte)(destColor.z * 0xFF);
        components[index + 3] = (byte)(destColor.w * 0xFF);
    }
    
    /**
     * 对齐
     * @param v
     * @param min
     * @param max
     * @return
     */
    float clamp(float v, float min, float max) {
        if (min > max) {
            float tmp = max;
            max = min;
            min = tmp;
        }
        
        if (v < min)
            v = min;
        if (v > max)
            v = max;
        return v;
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

    // 为了避免通过引用改变顶点的原始值，这里复制一份顶点数据，用于实际计算。
    private RasterizationVertex v0 = new RasterizationVertex();
    private RasterizationVertex v1 = new RasterizationVertex();
    private RasterizationVertex v2 = new RasterizationVertex();
    
    /**
     * 光栅化三角形
     * @param a
     * @param b
     * @param c
     */
    public void rasterizeTriangle(final RasterizationVertex a, final RasterizationVertex b, final RasterizationVertex c) {
        // 为了避免通过引用改变顶点的原始值，这里复制一份顶点数据，用于实际计算。
        v0.copy(a);
        v1.copy(b);
        v2.copy(c);
        
        Matrix4f viewportMatrix = renderer.getViewportMatrix();
        
        // 把顶点位置修正到屏幕空间。
        viewportMatrix.mult(v0.position, v0.position);
        viewportMatrix.mult(v1.position, v1.position);
        viewportMatrix.mult(v2.position, v2.position);
        
        // 将顶点变换到投影平面
        v0.perspectiveDivide();
        v1.perspectiveDivide();
        v2.perspectiveDivide();

        switch (renderState.getFillMode()) {
        case POINT: {
            rasterizePixel((int)v0.position.x, (int)v0.position.y, v0);
            rasterizePixel((int)v1.position.x, (int)v1.position.y, v1);
            rasterizePixel((int)v2.position.x, (int)v2.position.y, v2);
            return;
        }
        case LINE : {
            rasterizeLine(v0, v1);
            rasterizeLine(v0, v2);
            rasterizeLine(v1, v2);
            return;
        }
        case FACE : {
            // 按Y坐标把三个顶点从上到下冒泡排序
            RasterizationVertex tmp;
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
                // FIXME 需要透视校正
                float t = (y1 - y0) / (y2 - y0);
                RasterizationVertex middleVert = new RasterizationVertex();
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
            return;
        }
        }
        
    }

    /**
     * 画平底实心三角形
     * @param v0 上顶点
     * @param v1 底边左顶点
     * @param v2 底边右顶点
     */
    private void fillBottomLineTriangle(RasterizationVertex v0, RasterizationVertex v1, RasterizationVertex v2) {
        int y0 = (int) Math.ceil(v0.position.y);
        int y2 = (int) Math.ceil(v2.position.y);
        
        for (int y = y0; y <y2; y++) {
            if (y >= 0 && y < this.height) {
                
                // 插值生成左右顶点
                // FIXME 需要透视校正
                float t = (y - v0.position.y) / (v1.position.y - v0.position.y);
                
                RasterizationVertex vl = new RasterizationVertex();
                vl.interpolateLocal(v0, v1, t);
                RasterizationVertex vr = new RasterizationVertex();
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
    private void fillTopLineTriangle(RasterizationVertex v0, RasterizationVertex v1, RasterizationVertex v2) {
        int y0 = (int) Math.ceil(v0.position.y);
        int y2 = (int) Math.ceil(v2.position.y);

        for (int y = y0; y < y2; y++) {
            if (y >= 0 && y < this.height) {
                // 插值生成左右顶点
                // FIXME 需要透视校正
                float t = (y - v0.position.y) / (v2.position.y - v0.position.y);
                
                RasterizationVertex vl = new RasterizationVertex();
                vl.interpolateLocal(v0, v2, t);
                RasterizationVertex vr = new RasterizationVertex();
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
    public void rasterizeScanline(RasterizationVertex v0, RasterizationVertex v1, int y) {
        int x0 = (int) Math.ceil(v0.position.x);
        // 按照DirectX和OpenGL的光栅化规则，舍弃右下的顶点。
        int x1 = (int) Math.floor(v1.position.x);
        
        for (int x = x0; x <= x1; x++) {
            if (x < 0 || x >= width)
                continue;
            
            // 线性插值
            // FIXME 需要透视校正
            float t = (x - v0.position.x) / (v1.position.x - v0.position.x);
            RasterizationVertex frag = new RasterizationVertex();
            frag.interpolateLocal(v0, v1, t);
            
            rasterizePixel(x, y, frag);
        }
    }
    
    /**
     * 光栅化线段，使用Bresenham算法。
     * @param v0
     * @param v1
     */
    public void rasterizeLine(RasterizationVertex v0, RasterizationVertex v1) {
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
        boolean ylerp = false;
        if (fastStep <= slowStep) {
            ylerp = true;
            fastStep = Math.abs(h);
            slowStep = Math.abs(w);

            dx2 = 0;
            dy2 = h < 0 ? -1 : (h > 0 ? 1 : 0);
        }
        int numerator = fastStep >> 1;

        for (int i = 0; i <= fastStep; i++) {
            // 线性插值
            float t = 0;
            if (ylerp)
                t= (y - v0.position.y) / (v1.position.y - v0.position.y);
            else
                t = (x - v0.position.x) / (v1.position.x - v0.position.x);
            
            RasterizationVertex frag = new RasterizationVertex();
            frag.interpolateLocal(v0, v1, t);
            rasterizePixel(x, y, frag);
            
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
            if (ylerp)
                t= (y - v0.position.y) / (v1.position.y - v0.position.y);
            else
                t = (x - v0.position.x) / (v1.position.x - v0.position.x);
            
            rasterizePixel(x, y, frag);
        }
    }
    
    /**
     * 深度测试
     * @param oldDepth
     * @param newDepth
     * @return
     */
    private boolean depthTest(float oldDepth, float newDepth) {
        switch (renderState.getDepthFunc()) {
        case ALWAYS:
            return true;
        case NEVER:
            return false;
        case LESS:
            return newDepth < oldDepth;
        case LESS_EQUAL:
            return newDepth <= oldDepth;
        case GREATER:
            return newDepth > oldDepth;
        case GREATER_EQUAL:
            return newDepth >= oldDepth;
        case EQUAL:
            return newDepth == oldDepth;
        case NOT_EQUAL:
            return newDepth != oldDepth;
        }
        return false;
    }
    
//    /**
//     * 片段着色器
//     * @param frag
//     */
//    private void fragmentShader(RasterizationVertex frag) {
//        Texture texture = renderer.getMaterial().getDiffuseMap();
//        if (texture != null && frag.hasTexCoord) {
//            Vector4f texColor = texture.sample2d(frag.texCoord);
//            frag.color.multLocal(texColor);
//        }
//    }
}
