package net.jmecn;

import java.awt.event.KeyEvent;

import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Camera;

/**
 * 摄像机控制器
 * @author yanmaoyuan
 *
 */
public class CameraController {

    private Camera camera;
    private Input input;
    
    // 运动速度
    public float moveSpeed = 10f;
    
    // 临时变量，用于计算移动的方向和距离。
    private Vector3f step = new Vector3f();
    private Vector3f forward = new Vector3f();
    private Vector3f right = new Vector3f();
    private Vector3f up = new Vector3f();
    
    public CameraController(Camera camera, Input input) {
        this.camera = camera;
        this.input = input;
    }
    
    public void update(float delta) {
        
        // 左右旋转
        if (input.getKey(KeyEvent.VK_LEFT)) {
            camera.rotate(0, delta, 0);
        } else if (input.getKey(KeyEvent.VK_RIGHT)) {
            camera.rotate(0, -delta, 0);
        }
        
        // 上下旋转
        if (input.getKey(KeyEvent.VK_UP)) {
            camera.rotate(delta, 0, 0);
        } else if (input.getKey(KeyEvent.VK_DOWN)) {
            camera.rotate(-delta, 0, 0);
        }
        
        boolean changed = false;
        step.set(0, 0, 0);
        
        // 计算移动的方向
        forward.set(camera.getDirection());
        right.set(camera.getRightVector());
        up.set(camera.getUpVector());
        
        // 计算移动的距离
        float movement = delta * moveSpeed;
        forward.multLocal(movement);
        right.multLocal(movement);
        up.multLocal(movement);
        
        // 前后平移
        if (input.getKey(KeyEvent.VK_W)) {
            step.addLocal(forward);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_S)) {
            step.subtractLocal(forward);
            changed = true;
        }
        
        // 左右平移
        if (input.getKey(KeyEvent.VK_A)) {
            step.subtractLocal(right);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_D)) {
            step.addLocal(right);
            changed = true;
        }
        
        // 上下平移
        if (input.getKey(KeyEvent.VK_Z)) {
            step.subtractLocal(up);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_Q)) {
            step.addLocal(up);
            changed = true;
        }
        
        if (changed) {
            // 更新摄像机位置
            camera.getLocation().addLocal(step);
            // 更新观察-投影矩阵
            camera.updateViewProjectionMatrix();
        }
    }
}