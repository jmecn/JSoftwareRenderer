package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Box;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.Texture;

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
        
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setLinearFilter(true);
        
        Mesh mesh = new Box();
        mesh.setTexture(texture);
        
        // 添加到场景中
        meshes.add(mesh);
    }

    @Override
    protected void update(float delta) {}

}
