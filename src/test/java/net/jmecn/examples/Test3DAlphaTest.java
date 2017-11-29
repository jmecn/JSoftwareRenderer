package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.material.RenderState.FaceCullMode;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;
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
        
        // 第一个方块
        Mesh mesh = new Box();
        
        // 定义材质
        Material material = new Material();
        mesh.setMaterial(material);
        
        // 不裁剪，这样我们就能看到立方体的内部。
        material.getRenderState().setFaceCullMode(FaceCullMode.NEVER);
        
        // 开启Alpha测试
        material.getRenderState().setAlphaTest(true);
        material.getRenderState().setAlphaFalloff(0.75f);
        
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
