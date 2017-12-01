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
    
    // 顶点在世界空间中的模型坐标
    public Vector3f worldSpacePosition = new Vector3f();
    
    /**
     * 插值
     * @param 
     * @param v1
     * @param t
     * @return
     */
    public RasterizationVertex interpolateLocal(RasterizationVertex v0,
            RasterizationVertex v1, float t) {
        // 顶点插值
        position.interpolateLocal(v0.position, v1.position, t);
        // 法线插值
        normal.interpolateLocal(v0.normal, v1.normal, t);
        // 颜色插值
        color.interpolateLocal(v0.color, v1.color, t);
        // 纹理插值
        texCoord.interpolateLocal(v0.texCoord, v1.texCoord, t);

        worldSpacePosition.interpolateLocal(v0.worldSpacePosition, v1.worldSpacePosition, t);
        return this;
    }
    
    /**
     * 透视除法
     */
    public void perspectiveDivide() {
        float oneOverW = 1f / position.w;
        // 透视除法
        position.multLocal(oneOverW);
        texCoord.multLocal(oneOverW);
        color.multLocal(oneOverW);
        normal.multLocal(oneOverW);
        // 记录1 / w
        position.w = oneOverW;
        
        worldSpacePosition.multLocal(oneOverW);
    }
    
    /**
     * 判断变换后的顶点是否在齐次空间内。
     * 
     * @return
     */
    public boolean isValid() {
        float w = Math.abs(position.w);
        return position.x > -w && position.x < w
                && position.y > -w && position.y < w
                && position.z > -w && position.z < w;
    }
}
