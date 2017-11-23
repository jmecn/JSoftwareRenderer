package net.jmecn.math;

/**
 * 三维向量
 * 
 * @author yanmaoyuan
 *
 */
public class Vector3f {

    public float x, y, z;

    // 单位向量
    public final static Vector3f UNIT_X = new Vector3f(1, 0, 0);
    public final static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public final static Vector3f UNIT_Z = new Vector3f(0, 0, 1);
    
    // 零向量
    public final static Vector3f ZERO = new Vector3f(0, 0, 0);
    
    /**
     * 构造方法
     */
    public Vector3f() {
        x = y = z = 0;
    }

    /**
     * 使用已知的3个分量来构造向量。
     */
    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * 使用已知向量来构造另一个向量。
     * @param v
     */
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    /**
     * 求负向量
     * 
     * @return
     */
    public Vector3f negate() {
        return new Vector3f(-x, -y, -z);
    }
    
    /**
     * 求负向量
     * 
     * @return
     */
    public Vector3f negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    /**
     * 返回向量长度的平方
     * 
     * @return
     */
    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    /**
     * 返回向量的长度
     * 
     * @return
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

    /**
     * 求单位向量
     */
    public Vector3f normalize() {
        float length = x * x + y * y + z * z;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            return new Vector3f(x * length, y * length, z * length);
        }
        return new Vector3f(x, y, z);
    }
    
    /**
     * 求单位向量
     */
    public Vector3f normalizeLocal() {
        float length = x * x + y * y + z * z;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            x *= length;
            y *= length;
            z *= length;
        }
        return this;
    }
    
    /**
     * 求两点之间的距离
     * 
     * @param v
     * @return
     */
    public float distance(Vector3f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        double dz = z - v.z;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    /**
     * 求两点之间距离的平方
     * 
     * @param v
     * @return
     */
    public float distanceSquared(Vector3f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        double dz = z - v.z;
        return (float) (dx * dx + dy * dy + dz * dz);
    }

    /**
     * 向量加法
     * 
     * @param v
     * @return
     */
    public Vector3f add(Vector3f v) {
        return new Vector3f(x + v.x, y + v.y, z + v.z);
    }
    
    /**
     * 向量加法
     * @param v
     * @return
     */
    public Vector3f addLocal(Vector3f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    /**
     * 向量减法
     * 
     * @param v
     * @return
     */
    public Vector3f subtract(Vector3f v) {
        return new Vector3f(x - v.x, y - v.y, z - v.z);
    }
    
    /**
     * 向量减法
     * 
     * @param v
     * @return
     */
    public Vector3f subtractLocal(Vector3f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    /**
     * 标量乘法
     * 
     * @param scalor
     * @return
     */
    public Vector3f mult(float scalor) {
        return new Vector3f(x * scalor, y * scalor, z * scalor);
    }

    /**
     * 标量乘法
     * 
     * @param scalor
     * @return
     */
    public Vector3f multLocal(float scalor) {
        x *= scalor;
        y *= scalor;
        z *= scalor;
        return this;
    }
    
    /**
     * 向量乘法
     * 
     * @param v
     * @return
     */
    public Vector3f mult(Vector3f v) {
        return new Vector3f(x * v.x, y * v.y, z * v.z);
    }

    /**
     * 向量乘法
     * 
     * @param v
     * @return
     */
    public Vector3f multLocal(Vector3f v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }
    
    /**
     * 标量除法
     * @param scalor
     * @return
     */
    public Vector3f divide(float scalor) {
        scalor = 1 / scalor;
        return new Vector3f(x * scalor, y * scalor, z * scalor);
    }
    
    /**
     * 标量除法
     * @param scalor
     * @return
     */
    public Vector3f divideLocal(float scalor) {
        scalor = 1 / scalor;
        x *= scalor;
        y *= scalor;
        z *= scalor;
        return this;
    }

    /**
     * 向量除法
     * @param v
     * @return
     */
    public Vector3f divide(Vector3f v) {
        return new Vector3f( x / v.x, y / v.y, z / v.z);
    }
    
    /**
     * 向量除法
     * @param v
     * @return
     */
    public Vector3f divideLocal(Vector3f v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }
    
    /**
     * 向量点乘（内积）
     * 
     * @param v
     * @return
     */
    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * 向量叉乘（外积）。
     * 
     * @param v
     * @return 返回一个新的向量，它垂直于当前两个向量。
     */
    public Vector3f cross(Vector3f v) {
        float rx = y * v.z - z * v.y;
        float ry = z * v.x - x * v.z;
        float rz = x * v.y - y * v.x;
        return new Vector3f(rx, ry, rz);
    }
    
    /**
     * 设置向量的三个分量。
     * 
     * @param x
     * @param y
     * @param z
     */
    public Vector3f set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * 设置向量的三个分量
     * @param v
     */
    public Vector3f set(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }
    
    // 基本的Getter和Setter

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
