package net.jmecn.math;

/**
 * 平面
 * @author yanmaoyuan
 *
 */
public class Plane {

    /**
     * 平面的法向量
     */
    private Vector3f normal = new Vector3f();

    /**
     * 常量
     */
    private float constant = 0;
    
    public Plane(float x, float y, float z, float constant) {
        this.normal.set(x, y, z).normalizeLocal();
        this.constant = constant;
    }
    
    public Plane(Vector3f normal, float d) {
        this.normal.set(normal).normalizeLocal();
        this.constant = d;
    }
    
    /**
     * 判断顶点是否在平面上。
     * @param v
     * @return
     */
    public float determine(Vector3f v) {
        return normal.dot(v) + constant;
    }
}
