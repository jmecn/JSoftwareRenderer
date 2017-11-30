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
import net.jmecn.scene.shape.Box;
import net.jmecn.shader.GouraudShader;

/**
 * 测试Gouraud Shader
 * @author yanmaoyuan
 *
 */
public class TestGouraudShader extends Application {

    public static void main(String[] args) {
        TestGouraudShader app = new TestGouraudShader();
        app.setResolution(400, 300);
        app.setTitle("Test Gouraud Shader");
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
        
        // 创建材质
        Material material = new Material();
        
        // 设置着色器
        material.setShader(new GouraudShader());
        
        // 设置颜色
        material.setDiffuse(new Vector4f(1, 1, 1, 1));
        
        // 添加到场景中
        geometry = new Geometry(new Box(), material);
        rootNode.attachChild(geometry);
        
        // 添加光源
        lights.add(new AmbientLight(new Vector4f(0.3f, 0.0f, 0.0f, 1f)));
        lights.add(new DirectionalLight(new Vector4f(0.7f, 0.0f, 0.0f, 1f), new Vector3f(-3, -2, -4).normalizeLocal()));
    }

    @Override
    protected void update(float delta) {
        rot.rotateY(delta);
        geometry.getLocalTransform().getRotation().multLocal(rot);
    }
}
