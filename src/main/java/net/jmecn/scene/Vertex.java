package net.jmecn.scene;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;

public class Vertex {

    public Vector3f position;  // 顶点位置
    public Vector3f normal;    // 顶点法线
    public Vector4f color;     // 顶点颜色
    public Vector2f texCoord;  // 纹理坐标
    
    public Vector4f fragCoord = new Vector4f(0);  // 顶点位置
    public Vector4f fragColor = new Vector4f(1);  // 顶点颜色
    
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
        
        // 片段坐标插值
        if (beginVec.fragCoord != null && finalVec.fragCoord != null) {
            fragCoord = new Vector4f();
            fragCoord.interpolateLocal(beginVec.fragCoord, finalVec.fragCoord, changeAmnt);
        }
        // 片段颜色插值
        if (beginVec.fragColor != null && finalVec.fragColor != null) {
            fragColor = new Vector4f();
            fragColor.interpolateLocal(beginVec.fragColor, finalVec.fragColor, changeAmnt);
        }
        return this;
    }
}
