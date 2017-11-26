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
        
        forward.set(camera.getDirection());
        right.set(camera.getRightVector());
        up.set(camera.getUpVector());
        
        float movement = delta * moveSpeed;
        forward.multLocal(movement);
        right.multLocal(movement);
        up.multLocal(movement);

        // 前后平移
        if (input.getKey(KeyEvent.VK_W)) {
            camera.getLocation().addLocal(forward);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_S)) {
            camera.getLocation().subtractLocal(forward);
            changed = true;
        }
        
        // 左右平移
        if (input.getKey(KeyEvent.VK_A)) {
            camera.getLocation().subtractLocal(right);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_D)) {
            camera.getLocation().addLocal(right);
            changed = true;
        }
        
        // 上下平移
        if (input.getKey(KeyEvent.VK_Z)) {
            camera.getLocation().subtractLocal(up);
            changed = true;
        } else if (input.getKey(KeyEvent.VK_Q)) {
            camera.getLocation().addLocal(up);
            changed = true;
        }
        
        if (changed) {
            camera.updateViewProjectionMatrix();
        }
    }
    
}
