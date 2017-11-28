package net.jmecn.scene;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;

public class VertexOut {

    public Vector4f position = new Vector4f();   // 片段顶点
    public Vector4f fragCoord = new Vector4f();  // 片段位置
    public Vector4f fragColor = new Vector4f(1); // 片段颜色
    public Vector3f normal = new Vector3f();     // 片段法线
    public Vector2f texCoord = new Vector2f();   // 纹理坐标

    public boolean hasNormal = false;
    public boolean hasTexCoord = false;
    
    public VertexOut() {
        
    }
    
    /**
     * 插值
     * @param beginVec
     * @param finalVec
     * @param changeAmnt
     * @return
     */
    public VertexOut interpolateLocal(VertexOut beginVec, VertexOut finalVec, float changeAmnt) {
        position.interpolateLocal(beginVec.position, finalVec.position, changeAmnt);
        // 顶点插值
        fragCoord.interpolateLocal(beginVec.fragCoord, finalVec.fragCoord, changeAmnt);
        // 法线插值
        normal.interpolateLocal(beginVec.normal, finalVec.normal, changeAmnt);
        
        // 颜色插值
        fragColor.interpolateLocal(beginVec.fragColor, finalVec.fragColor, changeAmnt);
        
        // 纹理插值
        texCoord.interpolateLocal(beginVec.texCoord, finalVec.texCoord, changeAmnt);
        
        return this;
    }

    /**
     * 计算片段顶点
     */
    public void calcFragCoord() {
        fragCoord.set(position);
        fragCoord.multLocal(1f / position.w);
    }
}
