package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.light.AmbientLight;
import net.jmecn.light.DirectionalLight;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.math.Quaternion;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Camera;
import net.jmecn.renderer.Image;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Sphere;
import net.jmecn.shader.UnshadedShader;

/**
 * 测试Gouraud Shader
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
    
    // 几何体
    private Geometry geometry;
    // 旋转
    private Quaternion rot = new Quaternion();
    
    @Override
    protected void initialize() {
        // 初始化摄像机
        Camera cam = getCamera();
        cam.lookAt(new Vector3f(3, 4, 5), Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 设置场景
        setupScene();
        
        // 设置光源
        setupLights();
    }

    /**
     * 设置场景
     */
    private void setupScene() {
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
        geometry = new Geometry(mesh, material);
        rootNode.attachChild(geometry);
        
        // 设置着色器
        material.setShader(new UnshadedShader());
    }
    
    /**
     * 设置光源
     */
    private void setupLights() {
        // 环境光
        AmbientLight ambient = new AmbientLight();
        ambient.setColor(new Vector4f(1, 1, 1, 0.3f));
        
        // 定向光
        DirectionalLight dl = new DirectionalLight();
        dl.setColor(new Vector4f(1, 1, 1, 0.7f));
        dl.setDirection(new Vector3f(-3,-4,-5).normalizeLocal());
        
        lights.add(ambient);
        lights.add(dl);
    }
    
    @Override
    protected void update(float delta) {
        rot.rotateY(delta);
        geometry.getLocalTransform().getRotation().multLocal(rot);
    }
}
