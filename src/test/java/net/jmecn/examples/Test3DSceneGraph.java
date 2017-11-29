package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Camera;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Node;
import net.jmecn.scene.shape.Box;

/**
 * 测试3D场景图
 * 
 * @author yanmaoyuan
 *
 */
public class Test3DSceneGraph extends Application {

    public static void main(String[] args) {
        Test3DSceneGraph app = new Test3DSceneGraph();
        app.setResolution(400, 300);
        app.setFrameRate(60);
        app.setTitle("Test 3D SceneGraph");
        app.start();
    }
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    private Node node;

    @Override
    protected void initialize() {
        // 初始化摄像机
        Camera cam = getCamera();
        cam.lookAt(new Vector3f(0, 4, 6), new Vector3f(2, 2, 0), Vector3f.UNIT_Y);

        // 放一个方块在世界原点
        Geometry geom = new Geometry(new Box(), new Material());
        geom.getLocalTransform().setScale(0.1f);
        rootNode.attachChild(geom);

        // 创建一个子节点
        node = new Node();
        node.getLocalTransform().setTranslation(2, 2, 0);
        rootNode.attachChild(node);

        // 放个方块在该节点的原点处
        geom = new Geometry(new Box(), new Material());
        geom.getLocalTransform().setScale(0.1f);
        node.attachChild(geom);

        // 再放个方块，相对该节点，偏移(-2, 0, 0)处
        geom = new Geometry(new Box(), new Material());
        geom.getLocalTransform().setTranslation(-2, 0, 0);
        geom.getLocalTransform().setScale(0.5f);
        node.attachChild(geom);
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
        node.getLocalTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Y, -angle);
    }

}
