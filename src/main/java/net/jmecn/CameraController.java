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
    
    private Vector3f forward = new Vector3f(0, 0, 1);
    private Vector3f right = new Vector3f(-1, 0, 0);
    
    private Vector3f step = new Vector3f();
    
    public CameraController(Camera camera, Input input) {
        this.camera = camera;
        this.input = input;
    }
    
    public void update(float delta) {
        boolean changed = false;
        step.set(0, 0, 0);
        
        forward.set(camera.getDirection());
        forward.cross(Vector3f.UNIT_Y, right);
        
        forward.multLocal(delta * moveSpeed);
        right.multLocal(delta * moveSpeed);

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
        
        if (changed) {
            camera.updateViewProjectionMatrix();
        }
    }
    
}
