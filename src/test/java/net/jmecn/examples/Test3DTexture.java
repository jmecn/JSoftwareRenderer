package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.material.Texture.MagFilter;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.shape.Quad;

/**
 * 测试纹理采样效果
 * @author yanmaoyuan
 *
 */
public class Test3DTexture extends Application {

    public static void main(String[] args) {
        Test3DTexture app = new Test3DTexture();
        app.setResolution(1080, 720);
        app.setTitle("Test Texture");
        app.setFrameRate(60);
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(1, 2, 3),
                Vector3f.ZERO, Vector3f.UNIT_Y);

        
        // 定义材质
        Material material = new Material();
        
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setMagFilter(MagFilter.NEAREST);
        material.setDiffuseMap(texture);
        
        // 添加到场景中
        Geometry geom = new Geometry(new Quad(), material);
        rootNode.attachChild(geom);
    }

    @Override
    protected void update(float delta) {}

}
