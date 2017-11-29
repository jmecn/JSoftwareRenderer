package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Box;

/**
 * 测试纹理采样效果
 * @author yanmaoyuan
 *
 */
public class Test3DTexture extends Application {

    public static void main(String[] args) {
        Test3DTexture app = new Test3DTexture();
        app.setResolution(800, 600);
        app.setTitle("Test Texture");
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(3, 4, 5),
                Vector3f.ZERO, Vector3f.UNIT_Y);
        
        
        Mesh mesh = new Box();
        
        // 定义材质
        Material material = new Material();
        mesh.setMaterial(material);
        
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setLinearFilter(true);
        material.setTexture(texture);
        
        // 添加到场景中
        meshes.add(mesh);
    }

    @Override
    protected void update(float delta) {}

}
