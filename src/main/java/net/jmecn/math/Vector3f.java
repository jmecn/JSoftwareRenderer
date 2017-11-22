package net.jmecn.math;

/**
 * 三维向量
 * 
 * @author yanmaoyuan
 *
 */
public class Vector3f {

    public float x, y, z;

    public final static Vector3f UNIT_X = new Vector3f(1, 0, 0);
    public final static Vector3f UNIT_Y = new Vector3f(0, 1, 0);
    public final static Vector3f UNIT_Z = new Vector3f(0, 0, 1);
    public final static Vector3f ZERO = new Vector3f(0, 0, 0);
    
    public Vector3f() {
        x = y = z = 0;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3f(Vector3f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
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
    
    /**
     * 向量加法
     * 
     * @param v
     * @return
     */
    public Vector3f add(Vector3f v) {
        if (v == null)
            return null;
        return new Vector3f(x + v.x, y + v.y, z + v.z);
    }

    /**
     * 向量减法
     * 
     * @param v
     * @return
     */
    public Vector3f subtract(Vector3f v) {
        if (v == null)
            return null;
        return new Vector3f(x - v.x, y - v.y, z - v.z);
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
        this.x *= scalor;
        this.y *= scalor;
        this.z *= scalor;
        return this;
    }
    
    /**
     * 向量乘法
     * 
     * @param v
     * @return
     */
    public Vector3f mult(Vector3f v) {
        if (v == null)
            return null;
        return new Vector3f(x * v.x, y * v.y, z * v.z);
    }

    /**
     * 标量除法
     * @param scalor
     * @return
     */
    public Vector3f divide(float scalor) {
        return mult(1 / scalor);
    }

    /**
     * 向量除法
     * @param v
     * @return
     */
    public Vector3f divide(Vector3f v) {
        if (v == null)
            return null;
        return new Vector3f( x / v.x, y / v.y, z / v.z);
    }
    /**
     * 向量的内积，或者叫点积。
     * 
     * @param v
     * @return
     */
    public float dot(Vector3f v) {
        return x * v.x + y * v.y + z * v.z;
    }

    /**
     * 向量的外积，或者叫叉乘。
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
     * 求负向量
     * 
     * @return
     */
    public Vector3f negate() {
        return new Vector3f(-x, -y, -z);
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
     * 返回向量的长度。
     * 
     * @return
     */
    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    /**
     * 求两个向量之间距离的平方
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
     * 求两个向量之间的距离
     * 
     * @param v
     * @return
     */
    public float distance(Vector3f v) {
        return (float) Math.sqrt(distanceSquared(v));
    }

    /**
     * 求单位向量
     */
    public Vector3f normalize() {
        float length = x * x + y * y + z * z;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            return mult(length);
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
            return multLocal(length);
        }
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
