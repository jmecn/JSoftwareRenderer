package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Camera;
import net.jmecn.scene.Box;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.Texture;

/**
 * 测试3D观察的效果
 * @author yanmaoyuan
 *
 */
public class Test3DRasterization extends Application {

    public static void main(String[] args) {
        Test3DRasterization app = new Test3DRasterization();
        app.setResolution(800, 600);
        app.setTitle("3D View");
        //app.setFrameRate(60);
        app.start();
    }

    private Mesh mesh;// 网格
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    
    @Override
    protected void initialize() {
        // 使用程序纹理
        Texture texture = new Texture();
        texture.setLinearFilter(true);
        
        mesh = new Box();
        mesh.setTexture(texture);
        
        // 添加到场景中
        meshes.add(mesh);
        
        // 调整摄像机的位置
        Camera cam = getCamera();
        cam.setLocation(new Vector3f(3, 4, 8));
        cam.setDirection(new Vector3f(-3, -4, -8).normalizeLocal());
        cam.updateViewProjectionMatrix();
    }

    @Override
    protected void update(float delta) {
        // 每秒旋转180°
        angle += delta * PI;
        
        // 若已经旋转360°，则减去360°。
        if (angle > _2PI) {
            angle -= _2PI;
        }
        
        // 计算旋转：绕Z轴顺时针方向旋转
        //mesh.getTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Y, -angle);
    }

}
