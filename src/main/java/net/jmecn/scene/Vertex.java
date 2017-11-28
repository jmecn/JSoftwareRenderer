package net.jmecn.scene;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;

public class Vertex {

    public Vector3f position;  // 顶点位置
    public Vector3f normal;    // 顶点法线
    public Vector4f color;     // 顶点颜色
    public Vector2f texCoord;  // 纹理坐标
    
    public Vertex interpolateLocal(Vertex beginVec, Vertex finalVec, float changeAmnt) {
        // 顶点插值
        if (beginVec.position != null && finalVec.position != null) {
            position = new Vector3f();
            position.interpolateLocal(beginVec.position, finalVec.position, changeAmnt);
        }
        
        // 法线插值
        if (beginVec.normal != null && finalVec.normal != null) {
            normal = new Vector3f();
            normal.interpolateLocal(beginVec.normal, finalVec.normal, changeAmnt);
        }
        
        // 顶点插值
        if (beginVec.position != null && finalVec.position != null) {
            position = new Vector3f();
            position.interpolateLocal(beginVec.position, finalVec.position, changeAmnt);
        }
        
        // 颜色插值
        if (beginVec.color != null && finalVec.color != null) {
            color = new Vector4f();
            color.interpolateLocal(beginVec.color, finalVec.color, changeAmnt);
        }
        
        // 纹理插值
        if (beginVec.texCoord != null && finalVec.texCoord != null) {
            texCoord = new Vector2f();
            texCoord.interpolateLocal(beginVec.texCoord, finalVec.texCoord, changeAmnt);
        }
        return this;
    }
}
