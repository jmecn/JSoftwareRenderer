package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Camera;
import net.jmecn.scene.Mesh;

/**
 * 测试世界变换矩阵
 * @author yanmaoyuan
 *
 */
public class Test3DWorldViewMatrix extends Application {

    public static void main(String[] args) {
        Test3DWorldViewMatrix app = new Test3DWorldViewMatrix();
        app.setResolution(400, 300);
        app.setTitle("3D View");
        app.setFrameRate(60);
        app.start();
    }

    private Mesh mesh;// 网格
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    
    @Override
    protected void initialize() {
        
        // 立方体
        Vector3f[] positions = {
            new Vector3f(-1, -1, -1),
            new Vector3f(1, -1, -1),
            new Vector3f(-1, 1, -1),
            new Vector3f(1, 1, -1),
            new Vector3f(-1, -1, 1),
            new Vector3f(1, -1, 1),
            new Vector3f(-1, 1, 1),
            new Vector3f(1, 1, 1),
        };
        
        // 定义六面共12个三角形
        int[] indexes = {
            0, 2, 1, 1, 2, 3, // back
            4, 5, 7, 4, 7, 6, // front
            5, 1, 3, 5, 3, 7, // left
            0, 6, 2, 0, 4, 6, // right
            2, 6, 7, 2, 7, 3, // top
            1, 4, 0, 1, 5, 4 // bottom
        };
        
        mesh = new Mesh(positions, indexes);
        
        // 添加到场景中
        meshes.add(mesh);
        
        // 调整摄像机
        Camera cam = getCamera();
        
        cam.setLocation(new Vector3f(3, 4, 8));
        cam.setDirection(new Vector3f(-3, -4, -8).normalizeLocal());
        cam.initViewMatrix();
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
        mesh.getTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Y, -angle);
    }

}
