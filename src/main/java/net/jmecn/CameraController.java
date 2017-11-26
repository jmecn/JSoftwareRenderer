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
    
    public float moveSpeed = 10f;
    
    private Vector3f forward = new Vector3f();
    private Vector3f right = new Vector3f();
    private Vector3f up = new Vector3f();
    
    private Vector3f step = new Vector3f();
    
    public CameraController(Camera camera, Input input) {
        this.camera = camera;
        this.input = input;
    }
    
    public void update(float delta) {
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
            camera.getLocation().addLocal(step);
            camera.updateViewProjectionMatrix();
        }
    }
    
}
