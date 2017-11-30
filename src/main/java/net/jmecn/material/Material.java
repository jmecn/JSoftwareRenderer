package net.jmecn.material;

import net.jmecn.math.Vector4f;
import net.jmecn.shader.DefaultShader;
import net.jmecn.shader.Shader;

/**
 * 材质
 * 
 * @author yanmaoyuan
 *
 */
public class Material {

    // 渲染状态
    private RenderState renderState;
    
    // 着色器
    private Shader shader;

    private boolean isUseVertexColor;   // 是否使用顶点色
    
    private Vector4f emissive;          // 自发光色
    private Vector4f diffuse;           // 漫反射光色
    private Vector4f ambient;           // 环境光色
    private Vector4f specular;          // 高光颜色
    private float shininess;            // 光泽度
    
    private Texture emssiveMap;         // 发光贴图
    private Texture diffuseMap;         // 漫反射贴图
    private Texture specularMap;        // 高光贴图
    
    private Texture normalMap;          // 法线贴图
    
    public Material() {
        // 初始化渲染状态
        renderState = new RenderState();
        
        // 初始化材质参数
        isUseVertexColor = false;   // 不使用顶点色
        emissive = new Vector4f(0);  // 黑色
        diffuse = new Vector4f(1);  // 白色
        ambient = new Vector4f(1);  // 白色
        specular = new Vector4f(0); // 黑色
        shininess = 1f;             // 不光泽
        
        emssiveMap = null;
        diffuseMap = null;
        specularMap = null;
        normalMap = null;
        
        // 设置默认着色器
        shader = new DefaultShader();
        shader.setMaterial(this);
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
        if (shader != null) {
            shader.setMaterial(this);
            this.shader = shader;
        }
    }

    public boolean isUseVertexColor() {
        return isUseVertexColor;
    }

    public void setUseVertexColor(boolean isUseVertexColor) {
        this.isUseVertexColor = isUseVertexColor;
    }

    public Vector4f getEmissive() {
        return emissive;
    }

    public void setEmissive(Vector4f emissive) {
        this.emissive.set(emissive);
    }

    public Vector4f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector4f diffuse) {
        this.diffuse.set(diffuse);
    }

    public Vector4f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector4f ambient) {
        this.ambient.set(ambient);
    }

    public Vector4f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector4f specular) {
        this.specular.set(specular);
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public Texture getDiffuseMap() {
        return diffuseMap;
    }

    public void setDiffuseMap(Texture diffuseMap) {
        this.diffuseMap = diffuseMap;
    }

    public Texture getSpecularMap() {
        return specularMap;
    }

    public void setSpecularMap(Texture specularMap) {
        this.specularMap = specularMap;
    }

    public Texture getEmssiveMap() {
        return emssiveMap;
    }

    public void setEmssiveMap(Texture emssiveMap) {
        this.emssiveMap = emssiveMap;
    }

    public Texture getNormalMap() {
        return normalMap;
    }

    public void setNormalMap(Texture normalMap) {
        this.normalMap = normalMap;
    }

}
