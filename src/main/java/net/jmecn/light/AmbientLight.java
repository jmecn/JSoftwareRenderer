package net.jmecn.light;

import net.jmecn.math.Vector4f;

/**
 * 环境光
 * @author yanmaoyuan
 *
 */
public class AmbientLight extends Light {

    public AmbientLight() {
        super();
    }

    public AmbientLight(Vector4f color) {
        super(color);
    }
    
}
