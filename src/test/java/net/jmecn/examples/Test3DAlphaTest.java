package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.RenderState.CullMode;
import net.jmecn.material.Texture;
import net.jmecn.material.Texture.MagFilter;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.shape.Box;

/**
 * 测试Alpha
 * @author yanmaoyuan
 *
 */
public class Test3DAlphaTest extends Application {

    public static void main(String[] args) {
        Test3DAlphaTest app = new Test3DAlphaTest();
        app.setResolution(800, 600);
        app.setTitle("Test AlphaTest");
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(3, 4, 5),
                Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 定义材质
        Material material = new Material();
        
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setMagFilter(MagFilter.BILINEAR);
        material.setTexture(texture);

        // 不裁剪，这样我们就能看到立方体的内部。
        material.getRenderState().setCullMode(CullMode.NEVER);
        
        // 开启Alpha测试
        material.getRenderState().setAlphaTest(true);
        material.getRenderState().setAlphaFalloff(0.75f);
        
        // 添加到场景中
        rootNode.attachChild(new Geometry(new Box(), material));
    }

    @Override
    protected void update(float delta) {}

}
