package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Camera;
import net.jmecn.renderer.Image;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Sphere;
import net.jmecn.shader.UnshadedShader;

/**
 * 测试Sphere网格
 * @author yanmaoyuan
 *
 */
public class TestSphere extends Application {

    public static void main(String[] args) {
        TestSphere app = new TestSphere();
        app.setResolution(400, 300);
        app.setTitle("Test Sphere");
        app.setFrameRate(60);
        app.start();
    }
    
    @Override
    protected void initialize() {
        // 初始化摄像机
        Camera cam = getCamera();
        cam.lookAt(new Vector3f(3, 4, 5), Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 创建网格
        Mesh mesh = new Sphere(2f, 36, 32);
        
        // 创建材质
        Material material = new Material();
        
        // 设置颜色
        material.setDiffuse(new Vector4f(1, 1, 1, 1));
        
        try {
            material.setDiffuseMap(new Texture(new Image("res/earth.jpg")));
        } catch (Exception e){
            material.setDiffuseMap(new Texture());
        }
        
        // 添加到场景中
        Geometry geometry = new Geometry(mesh, material);
        rootNode.attachChild(geometry);
        
        // 设置着色器
        material.setShader(new UnshadedShader());
    }
    
    @Override
    protected void update(float delta) {}
}
