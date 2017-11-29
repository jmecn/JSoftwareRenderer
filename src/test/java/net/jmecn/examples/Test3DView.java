package net.jmecn.examples;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.RenderState;
import net.jmecn.material.RenderState.CullMode;
import net.jmecn.material.RenderState.FillMode;
import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Camera;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Box;

/**
 * 测试3D观察的效果
 * @author yanmaoyuan
 *
 */
public class Test3DView extends Application {

    public static void main(String[] args) {
        Test3DView app = new Test3DView();
        app.setResolution(400, 300);
        app.setTitle("3D View");
        app.setFrameRate(60);
        app.start();
    }

    private Geometry geom;
    
    private final static float PI = 3.1415626f;
    private final static float _2PI = PI * 2;
    private float angle = 0;// 旋转角度
    
    @Override
    protected void initialize() {
        
        // 网格
        Mesh mesh = new Box();
        
        // 材质
        Material material = new Material();
        RenderState renderState = material.getRenderState();

        // 不裁剪背面
        renderState.setCullMode(CullMode.NEVER);
        
        // 显示为线框
        renderState.setFillMode(FillMode.WIREFRAME);
        
        // 添加到场景中
        this.geom = new Geometry(mesh, material);
        rootNode.attachChild(geom);
        
        // 调整摄像机的位置
        Camera cam = getCamera();
        cam.lookAt(new Vector3f(2, 3, 5), Vector3f.ZERO, Vector3f.UNIT_Y);
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
        //geom.getLocalTransform().getRotation().fromAxisAngle(Vector3f.UNIT_Y, -angle);
    }

}
