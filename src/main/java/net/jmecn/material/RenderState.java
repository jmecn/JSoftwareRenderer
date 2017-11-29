package net.jmecn.material;

import net.jmecn.math.Vector4f;

/**
 * 渲染状态
 * 
 * @author yanmaoyuan
 *
 */
public class RenderState {

    /**
     * 剔除模式
     */
    public enum CullMode {
        NEVER, FACE, BACK, ALWAYS
    }

    /**
     * 深度测试模式
     */
    public enum DepthMode {
        ALWAYS, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL, NOT_EQUAL
    }

    /**
     * 混色模式
     */
    public enum BlendMode {
        OPACITY, // 实心
        ADD, // 叠加
        ALPHA_BLEND// ALPHA混合
    }

    /**
     * 填充模式
     */
    public enum FillMode {
        POINT,// 点填充模式，在每个顶点绘制一个像素
        WIREFRAME,// 线框模式，在每个边绘制一条直线
        SOLID,// 实心模式，对每个面进行填充
    }

    private CullMode cullMode;
    
    private FillMode fillMode;
    private Vector4f wireframeColor;
    
    private boolean isAlphaTest;
    private float alphaFalloff;
    
    private DepthMode depthMode;
    private boolean isDepthTest;
    private boolean isDepthWrite;
    
    private BlendMode blendMode;

    public RenderState() {
        fillMode = FillMode.SOLID;
        
        cullMode = CullMode.BACK;
        
        isAlphaTest = false;
        alphaFalloff = 0f;
        
        depthMode = DepthMode.LESS;
        isDepthTest = true;
        isDepthWrite = true;
        
        blendMode = BlendMode.OPACITY;
    }

    public FillMode getFillMode() {
        return fillMode;
    }


    public void setFillMode(FillMode fillMode) {
        this.fillMode = fillMode;
    }


    public Vector4f getWireframeColor() {
        return wireframeColor;
    }

    public void setWireframeColor(Vector4f color) {
        this.wireframeColor.set(color);
    }

    public CullMode getCullMode() {
        return cullMode;
    }

    public void setCullMode(CullMode faceCullMode) {
        this.cullMode = faceCullMode;
    }

    public boolean isAlphaTest() {
        return isAlphaTest;
    }

    public void setAlphaTest(boolean isAlphaTest) {
        this.isAlphaTest = isAlphaTest;
    }

    public float getAlphaFalloff() {
        return alphaFalloff;
    }

    public void setAlphaFalloff(float alphaFalloff) {
        this.alphaFalloff = alphaFalloff;
    }

    public DepthMode getDepthMode() {
        return depthMode;
    }

    public void setDepthMode(DepthMode depthMode) {
        this.depthMode = depthMode;
    }

    public boolean isDepthTest() {
        return isDepthTest;
    }

    public void setDepthTest(boolean isDepthTest) {
        this.isDepthTest = isDepthTest;
    }

    public boolean isDepthWrite() {
        return isDepthWrite;
    }

    public void setDepthWrite(boolean isDepthWrite) {
        this.isDepthWrite = isDepthWrite;
    }

    public BlendMode getBlendMode() {
        return blendMode;
    }

    public void setBlendMode(BlendMode blendMode) {
        this.blendMode = blendMode;
    }

}
