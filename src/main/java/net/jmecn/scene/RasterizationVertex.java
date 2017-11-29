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
    public Vector4f color = new Vector4f(1); // 片段颜色
    public Vector3f normal = new Vector3f();     // 片段法线
    public Vector2f texCoord = new Vector2f();   // 纹理坐标

    public float w;
    
    public boolean hasNormal = false;
    public boolean hasTexCoord = false;
    public boolean hasVertexColor = false;
    
    public RasterizationVertex() {
        
    }
    
    /**
     * 插值
     * @param beginVec
     * @param finalVec
     * @param changeAmnt
     * @return
     */
    public RasterizationVertex interpolateLocal(RasterizationVertex beginVec, RasterizationVertex finalVec, float changeAmnt) {
        // 顶点插值
        position.interpolateLocal(beginVec.position, finalVec.position, changeAmnt);

        // 法线插值
        if (beginVec.hasNormal) {
            normal.interpolateLocal(beginVec.normal, finalVec.normal, changeAmnt);
            this.hasNormal = beginVec.hasNormal;
        }
        // 颜色插值
        if (beginVec.hasVertexColor) {
            color.interpolateLocal(beginVec.color, finalVec.color, changeAmnt);
            this.hasVertexColor = beginVec.hasVertexColor;
        }
        // 纹理插值
        if (beginVec.hasTexCoord) {
            texCoord.interpolateLocal(beginVec.texCoord, finalVec.texCoord, changeAmnt);
            this.hasTexCoord = beginVec.hasTexCoord;
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
