package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.shape.Box;

/**
 * 测试深度缓冲
 * @author yanmaoyuan
 *
 */
public class Test3DDepthBuffer extends Application {

    public static void main(String[] args) {
        Test3DDepthBuffer app = new Test3DDepthBuffer();
        app.setResolution(800, 600);
        app.setTitle("Test DepthBuffer");
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(3, 4, 5),
                Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 第一个方块
        Geometry geom = new Geometry(new Box());
        // 定义材质
        Material material = new Material();
        geom.setMaterial(material);
        
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setLinearFilter(true);
        material.setTexture(texture);
        
        // 添加到场景中
        rootNode.attachChild(geom);
        
        // 第二个方块
        geom = new Geometry(new Box());
        
        // 定义材质
        material = new Material();
        geom.setMaterial(material);
        
        // 使用程序纹理
        texture = new Texture();
        texture.setLinearFilter(false);
        material.setTexture(texture);
        geom.getLocalTransform().setTranslation(0.5f, -0.5f, -0.5f);
        
        // 添加到场景中
        rootNode.attachChild(geom);
    }

    @Override
    protected void update(float delta) {
    }

}
