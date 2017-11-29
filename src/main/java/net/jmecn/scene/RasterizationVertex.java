package net.jmecn.scene;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;

/**
 * 准备进入光栅化阶段的顶点数据
 * 
 * @author yanmaoyuan
 *
 */
public class RasterizationVertex {

    public Vector4f position = new Vector4f();  // 片段位置
    public Vector4f color = new Vector4f(1);    // 片段颜色
    public Vector3f normal = new Vector3f();    // 片段法线
    public Vector2f texCoord = new Vector2f();  // 纹理坐标

    public float w;// 透视除法之前的w坐标
    
    public boolean hasNormal = false;
    public boolean hasTexCoord = false;
    public boolean hasVertexColor = false;
    
    /**
     * 插值
     * @param 
     * @param v1
     * @param t
     * @return
     */
    public RasterizationVertex interpolateLocal(RasterizationVertex v0, RasterizationVertex v1, float t) {
        // 顶点插值
        position.interpolateLocal(v0.position, v1.position, t);
        w = (1 - t) * v0.w + t * v1.w;

        // 法线插值
        if (v0.hasNormal) {
            normal.interpolateLocal(v0.normal, v1.normal, t);
            this.hasNormal = v0.hasNormal;
        }
        // 颜色插值
        if (v0.hasVertexColor) {
            color.interpolateLocal(v0.color, v1.color, t);
            this.hasVertexColor = v0.hasVertexColor;
        }
        // 纹理插值
        if (v0.hasTexCoord) {
            texCoord.interpolateLocal(v0.texCoord, v1.texCoord, t);
            this.hasTexCoord = v0.hasTexCoord;
        }
        
        return this;
    }

    /**
     * 透视除法
     */
    public void perspectiveDivide() {
        // 保存w值，用于透视修正
        this.w = position.w;
        // 齐次坐标
        position.multLocal(1f / position.w);
    }
    
    /**
     * 判断变换后的顶点是否在齐次空间内。
     * 
     * @return
     */
    public boolean isValid() {
        return position.x > -1 && position.x < 1
                && position.y > -1 && position.y < 1
                && position.z > -1 && position.z < 1;
    }
}
