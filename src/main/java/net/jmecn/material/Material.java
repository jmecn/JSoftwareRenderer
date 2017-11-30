package net.jmecn.material;

import net.jmecn.shader.DefaultShader;
import net.jmecn.shader.Shader;

/**
 * 材质
 * 
 * @author yanmaoyuan
 *
 */
public class Material {

    protected Texture texture;
    protected RenderState renderState;
    protected Shader shader;
    
    public Material() {
        renderState = new RenderState();
        
        // 设置默认着色器
        shader = new DefaultShader();
        shader.setMaterial(this);
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public RenderState getRenderState() {
        return renderState;
    }

    public void setRenderState(RenderState renderState) {
        this.renderState = renderState;
    }

    public Shader getShader() {
        return shader;
    }
    
    public void setShader(Shader shader) {
        this.shader = shader;
    }

}
