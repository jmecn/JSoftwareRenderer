package net.jmecn.material;

import net.jmecn.math.Vector4f;

/**
 * 渲染状态
 * 
 * @author yanmaoyuan
 *
 */
public class RenderState {

    public enum FaceCullMode {
        NEVER, FACE, BACK, ALWAYS
    }

    public  enum DepthMode {
        ALWAYS, LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL, NOT_EQUAL
    }

    public enum BlendMode {
        OPACITY, // 实心
        ADD, // 叠加
        ALPHA_BLEND// ALPHA混合
    }

    private boolean isWireframe;
    private Vector4f wireframeColor;

    private FaceCullMode faceCullMode;
    
    private boolean isAlphaTest;
    private float alphaFalloff;
    
    private DepthMode depthMode;
    private boolean isDepthTest;
    private boolean isDepthWrite;
    
    private BlendMode blendMode;

    public RenderState() {
        isWireframe = false;
        wireframeColor = new Vector4f(0, 1, 0, 1);
        
        faceCullMode = FaceCullMode.BACK;
        
        isAlphaTest = false;
        alphaFalloff = 0f;
        
        depthMode = DepthMode.LESS;
        isDepthTest = true;
        isDepthWrite = true;
        
        blendMode = BlendMode.OPACITY;
    }

    public boolean isWireframe() {
        return isWireframe;
    }

    public void setWireframe(boolean isWireframe) {
        this.isWireframe = isWireframe;
    }

    public Vector4f getWireframeColor() {
        return wireframeColor;
    }

    public void setWireframeColor(Vector4f color) {
        this.wireframeColor.set(color);
    }

    public FaceCullMode getFaceCullMode() {
        return faceCullMode;
    }

    public void setFaceCullMode(FaceCullMode faceCullMode) {
        this.faceCullMode = faceCullMode;
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
