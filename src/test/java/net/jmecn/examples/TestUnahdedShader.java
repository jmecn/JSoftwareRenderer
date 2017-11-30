package net.jmecn.examples;

import java.io.IOException;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Camera;
import net.jmecn.renderer.Image;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.shape.Box;
import net.jmecn.shader.UnshadedShader;

/**
 * 测试Unshaded Shader
 * @author yanmaoyuan
 *
 */
public class TestUnahdedShader extends Application {

    public static void main(String[] args) {
        TestUnahdedShader app = new TestUnahdedShader();
        app.setResolution(400, 300);
        app.setTitle("Test Unshaded Shader");
        app.setFrameRate(60);
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        Camera cam = getCamera();
        cam.lookAt(new Vector3f(2, 3, 4), Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 创建材质
        Material material = new Material();
        
        // 设置着色器
        material.setShader(new UnshadedShader());
        
        // 设置颜色
        material.setDiffuse(new Vector4f(1, 1, 1, 1));
        
        // 设置纹理
        try {
            // 加载一幅图片作为纹理
            Image image = new Image("res/Crate.png");
            Texture diffuseMap = new Texture(image);
            material.setDiffuseMap(diffuseMap);
        } catch (IOException e) {
            // 使用默认程序纹理
            material.setDiffuseMap(new Texture());
        }
        
        // 添加到场景中
        Geometry geometry = new Geometry(new Box(), material);
        rootNode.attachChild(geometry);
    }

    @Override
    protected void update(float delta) {}

}
