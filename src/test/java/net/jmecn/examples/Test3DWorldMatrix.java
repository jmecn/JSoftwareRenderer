package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;

/**
 * 测试世界变换矩阵
 * @author yanmaoyuan
 *
 */
public class Test3DWorldMatrix extends Application {

    public static void main(String[] args) {
        Test3DWorldMatrix app = new Test3DWorldMatrix();
        app.setResolution(1080, 720);
        app.setTitle("3D WorldMatrix");
        app.setFrameRate(60);
        app.start();
    }

    private Geometry geom;// 网格
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    
    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(1, 1, 3),
                new Vector3f(1, 1, 0), Vector3f.UNIT_Y);
        
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
        
        geom = new Geometry(new Mesh(positions, indexes));
        geom.setMaterial(new Material());
        // 初始化空间变换
        geom.getLocalTransform().setTranslation(2, 1, 0);
        geom.getLocalTransform().setScale(0.5f);
        
        // 添加到场景中
        rootNode.attachChild(geom);
    }

    @Override
    protected void update(float delta) {
        // 每秒旋转180°
        angle += delta * PI;
        
        // 若已经旋转360°，则减去360°。
        if (angle > _2PI) {
            angle -= _2PI;
        }
        
        // 计算位移：以(1, 1)为中心，半径1做圆周运动。
        float x = (float) (Math.cos(angle) + 1);
        float y = (float) (Math.sin(angle) + 1);
        geom.getLocalTransform().setTranslation(x, y, 0);
        
        // 计算旋转：绕Z轴顺时针方向旋转
        geom.getLocalTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Z, -angle);
    }

}
