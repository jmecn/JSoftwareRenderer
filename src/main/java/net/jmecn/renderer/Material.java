package net.jmecn.renderer;

import net.jmecn.scene.Texture;

/**
 * 材质
 * 
 * @author yanmaoyuan
 *
 */
public class Material {

    protected Texture texture;
    protected RenderState renderState;

    public Material() {
        renderState = new RenderState();
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

}
