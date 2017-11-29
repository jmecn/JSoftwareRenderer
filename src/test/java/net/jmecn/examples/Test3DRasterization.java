package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Box;

/**
 * 测试3D光栅化
 * @author yanmaoyuan
 *
 */
public class Test3DRasterization extends Application {

    public static void main(String[] args) {
        Test3DRasterization app = new Test3DRasterization();
        app.setResolution(800, 600);
        app.setTitle("3D Rasterization");
        app.setFrameRate(60);
        app.start();
    }
    
    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(3, 4, 5),
                Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 添加到场景中
        Mesh mesh = new Box();
        // 定义材质
        Material material = new Material();
        mesh.setMaterial(material);
        
        meshes.add(mesh);
    }

    @Override
    protected void update(float delta) {}

}
