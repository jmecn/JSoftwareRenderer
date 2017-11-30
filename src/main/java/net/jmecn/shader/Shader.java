package net.jmecn.shader;

import java.util.List;

import net.jmecn.light.Light;
import net.jmecn.material.Material;
import net.jmecn.math.Matrix4f;
import net.jmecn.scene.RasterizationVertex;
import net.jmecn.scene.Vertex;

/**
 * 着色器
 * @author yanmaoyuan
 *
 */
public abstract class Shader {

    // uniforms
    protected Matrix4f worldMatrix;
    protected Matrix4f viewMatrix;
    protected Matrix4f projectionMatrix;
    protected Matrix4f viewProjectionMatrix;
    protected Matrix4f worldViewMatrix;
    protected Matrix4f worldViewProjectionMatrix;
    
    // attributes
    protected Material material;
    protected List<Light> lights;
    
    /**
     * 顶点着色器
     * @param vertex
     * @return
     */
    public abstract RasterizationVertex vertexShader(Vertex vertex);
    
    /**
     * 片段着色器
     * @param frag
     */
    public abstract boolean fragmentShader(RasterizationVertex frag);

    /**
     * 复制顶点数据
     * @param vertex
     * @return
     */
    protected RasterizationVertex copy(Vertex vertex) {
        RasterizationVertex out = new RasterizationVertex();
        // 顶点位置
        out.position.set(vertex.position, 1f);
        // 顶点法线
        if (vertex.normal != null) {
            out.normal.set(vertex.normal);
            out.hasNormal = true;
        }
        // 纹理坐标
        if (vertex.texCoord != null) {
            out.texCoord.set(vertex.texCoord);
            out.hasTexCoord = true;
        }
        // 顶点颜色
        if (vertex.color != null) {
            out.color.set(vertex.color);
            out.hasVertexColor = true;
        }
        
        return out;
    }
    
    // getter/setters
    public Matrix4f getWorldMatrix() {
        return worldMatrix;
    }

    public void setWorldMatrix(Matrix4f worldMatrix) {
        this.worldMatrix = worldMatrix;
    }

    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }

    public void setViewMatrix(Matrix4f viewMatrix) {
        this.viewMatrix = viewMatrix;
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public void setProjectionMatrix(Matrix4f projectionMatrix) {
        this.projectionMatrix = projectionMatrix;
    }

    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

    public void setViewProjectionMatrix(Matrix4f viewProjectionMatrix) {
        this.viewProjectionMatrix = viewProjectionMatrix;
    }

    public Matrix4f getWorldViewMatrix() {
        return worldViewMatrix;
    }

    public void setWorldViewMatrix(Matrix4f worldViewMatrix) {
        this.worldViewMatrix = worldViewMatrix;
    }

    public Matrix4f getWorldViewProjectionMatrix() {
        return worldViewProjectionMatrix;
    }

    public void setWorldViewProjectionMatrix(Matrix4f worldViewProjectionMatrix) {
        this.worldViewProjectionMatrix = worldViewProjectionMatrix;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public List<Light> getLights() {
        return lights;
    }

    public void setLights(List<Light> lights) {
        this.lights = lights;
    }
    
}