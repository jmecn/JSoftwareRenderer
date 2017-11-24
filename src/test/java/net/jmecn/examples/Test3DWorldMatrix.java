package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;

/**
 * 测试世界变换矩阵
 * @author yanmaoyuan
 *
 */
public class Test3DWorldMatrix extends Application {

    public static void main(String[] args) {
        Test3DWorldMatrix app = new Test3DWorldMatrix();
        app.setResolution(400, 400);
        app.setTitle("3D WorldMatrix");
        app.setFrameRate(60);
        app.start();
    }

    private Mesh mesh;// 网格
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    
    @Override
    protected void initialize() {
        // 一个四边形的顶点
        Vector3f[] positions = {
            new Vector3f(0, 0, 0),
            new Vector3f(1, 0, 0),
            new Vector3f(1, 1, 0),
            new Vector3f(0, 1, 0),
        };
        
        // 定义两个三角形
        int[] indexes = {
            0, 1, 2,
            0, 2, 3,
        };
        
        mesh = new Mesh(positions, indexes);
        
        // 初始化空间变换
        mesh.getTransform().setTranslation(200, 200, 0);
        mesh.getTransform().setScale(50);
        
        // 添加到场景中
        scene.add(mesh);
    }

    @Override
    protected void update(float delta) {
        // 每秒旋转180°
        angle += delta * PI;
        
        // 若已经旋转360°，则减去360°。
        if (angle > _2PI) {
            angle -= _2PI;
        }
        
        // 计算位移：以(200, 200)为中心，半径100做圆周运动。
        float x = (float) (Math.cos(angle) * 100 + 200);
        float y = (float) (Math.sin(angle) * 100 + 200);
        mesh.getTransform().setTranslation(x, y, 0);
        
        // 计算旋转：绕Z轴顺时针方向旋转
        mesh.getTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Z, -angle);
    }

}
