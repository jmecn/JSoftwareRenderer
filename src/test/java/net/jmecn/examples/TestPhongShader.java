package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.light.AmbientLight;
import net.jmecn.light.DirectionalLight;
import net.jmecn.material.Material;
import net.jmecn.math.Quaternion;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Camera;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Sphere;
import net.jmecn.shader.BlinnPhongShader;

/**
 * 测试Blinn-Phong Shader
 * @author yanmaoyuan
 *
 */
public class TestPhongShader extends Application {

    public static void main(String[] args) {
        TestPhongShader app = new TestPhongShader();
        app.setResolution(400, 300);
        app.setTitle("Test Blinn-Phong Shader");
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
        Mesh mesh = new Sphere(1f, 32, 32);
        
        // 创建材质
        Material material = new Material();
        
        // 设置颜色
        material.setDiffuse(new Vector4f(1, 0, 0, 1));
        material.setAmbient(new Vector4f(1, 0, 0, 1));
        material.setSpecular(new Vector4f(1, 1, 1, 1));
        material.setShininess(16);
        
        // 添加到场景中
        geometry = new Geometry(mesh, material);
        rootNode.attachChild(geometry);
        
        // 设置着色器
        material.setShader(new BlinnPhongShader());
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
        rot.rotateY(delta * 0.3f);
        geometry.getLocalTransform().getRotation().multLocal(rot);
    }
}
