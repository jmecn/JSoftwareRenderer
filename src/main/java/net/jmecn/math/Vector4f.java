package net.jmecn.math;


/**
 * 四维向量
 * @author yanmaoyuan
 *
 */
public final class Vector4f {

    public float x, y, z, w;
    
    // 单位向量
    public final static Vector4f UNIT_X = new Vector4f(1, 0, 0, 0);
    public final static Vector4f UNIT_Y = new Vector4f(0, 1, 0, 0);
    public final static Vector4f UNIT_Z = new Vector4f(0, 0, 1, 0);
    public final static Vector4f UNIT_W = new Vector4f(0, 0, 0, 1);
    
    // 零向量
    public final static Vector4f ZERO = new Vector4f(0, 0, 0, 0);

    public Vector4f() {
        x = y = z = w = 0;
    }
    
    public Vector4f(float value) {
        x = y = z = w = value;
    }
    
    public Vector4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    /**
     * 复制另一个四维向量的值。
     * @param v
     */
    public Vector4f(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
    }

    /**
     * 使用一个三维向量+一个标量来构造四维向量。
     * @param v
     * @param w
     */
    public Vector4f(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
    }
    
    /**
     * 使用2个二维向量来构造四维向量。
     * @param v0
     * @param v1
     */
    public Vector4f(Vector2f v0, Vector2f v1) {
        this.x = v0.x;
        this.y = v0.y;
        this.z = v1.x;
        this.w = v1.y;
    }
    
    /**
     * 求负向量
     * @return
     */
    public Vector4f negate() {
        return new Vector4f(-x, -y, -z, -w);
    }

    /**
     * 求负向量
     * @return
     */
    public Vector4f negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }
    
    /**
     * 向量的长度
     * @return
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * 向量长度的平方
     * @return
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }
    
    /**
     * 求单位向量
     * @return
     */
    public Vector4f normalize() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f){
            length = 1.0f / (float) Math.sqrt(length);
            return new Vector4f(x * length, y * length, z * length, w * length);
        }
        return new Vector4f(this);
    }

    /**
     * 求单位向量
     * @return
     */
    public Vector4f normalizeLocal() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f){
            length = 1.0f / (float) Math.sqrt(length);
            x *= length;
            y *= length;
            z *= length;
            w *= length;
        }
        return this;
    }
    
    /**
     * 向量加法
     *
     * @param v
     * @return
     */
    public Vector4f add(Vector4f v) {
        return new Vector4f(x + v.x, y + v.y, z + v.z, w + v.w);
    }

    /**
     * 向量加法
     * 
     * @param v
     * @return
     */
    public Vector4f addLocal(Vector4f v) {
        x += v.x;
        y += v.y;
        z += v.z;
        w += v.w;
        return this;
    }

    /**
     * 向量减法
     * @param v
     * @return
     */
    public Vector4f subtract(Vector4f v) {
        return new Vector4f(x - v.x, y - v.y, z - v.z, w - v.w);
    }

    /**
     * 向量减法
     * @param v
     * @return
     */
    public Vector4f subtractLocal(Vector4f v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        w -= v.w;
        return this;
    }
    
    /**
     * 点乘
     * 
     * @param v
     * @return
     */
    public float dot(Vector4f v) {
        return x * v.x + y * v.y + z * v.z + w * v.w;
    }

    /**
     * 向量投影
     * @param other
     * @return
     */
    public Vector4f project(Vector4f other){
        float n = this.dot(other); // A . B
        float d = other.lengthSquared(); // |B|^2
        return new Vector4f(other).multLocal(n/d);
    }

    /**
     * 两点之间距离的平方
     * @param v
     * @return
     */
    public float distanceSquared(Vector4f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        double dz = z - v.z;
        double dw = w - v.w;
        return (float) (dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * 两点之间的距离
     * @param v
     * @return
     */
    public float distance(Vector4f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        double dz = z - v.z;
        double dw = w - v.w;
        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz + dw * dw);
    }

    /**
     * 标量乘法
     * @param scalar
     * @return
     */
    public Vector4f mult(float scalar) {
        return new Vector4f(x * scalar, y * scalar, z * scalar, w * scalar);
    }
    
    /**
     * 标量乘法
     * @param scalar
     * @return
     */
    public Vector4f multLocal(float scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * 向量乘法
     * @param v
     * @return
     */
    public Vector4f mult(Vector4f v) {
        return new Vector4f(x * v.x, y * v.y, z * v.z, w * v.w);
    }

    /**
     * 向量乘法
     * @param vec
     * @return
     */
    public Vector4f multLocal(Vector4f vec) {
        x *= vec.x;
        y *= vec.y;
        z *= vec.z;
        w *= vec.w;
        return this;
    }

    /**
     * 标量除法
     * @param scalar
     * @return
     */
    public Vector4f divide(float scalar) {
        scalar = 1f/scalar;
        return new Vector4f(x * scalar, y * scalar, z * scalar, w * scalar);
    }

    /**
     * 标量除法
     * @param scalar
     * @return
     */
    public Vector4f divideLocal(float scalar) {
        scalar = 1f/scalar;
        x *= scalar;
        y *= scalar;
        z *= scalar;
        w *= scalar;
        return this;
    }

    /**
     * 向量除法
     * @param v
     * @return
     */
    public Vector4f divide(Vector4f v) {
        return new Vector4f(x / v.x, y / v.y, z / v.z, w / v.w);
    }
    
    /**
     * 向量除法
     * @param v
     * @return
     */
    public Vector4f divideLocal(Vector4f v) {
        x /= v.x;
        y /= v.y;
        z /= v.z;
        w /= v.w;
        return this;
    }

    /**
     * 在当前向量与final向量之间线性插值。
     * 
     * this=(1-changeAmnt)*this + changeAmnt * finalVec
     * @param finalVec 终向量
     * @param changeAmnt 插值系数，取值范围为 0.0 - 1.0。
     */
    public Vector4f interpolateLocal(Vector4f finalVec, float changeAmnt) {
        this.x=(1-changeAmnt)*this.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*this.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*this.z + changeAmnt*finalVec.z;
        this.w=(1-changeAmnt)*this.w + changeAmnt*finalVec.w;
        return this;
    }

    /**
     * 在当开始向量与终向量之间线性插值。
     * this=(1-changeAmnt)*beginVec + changeAmnt * finalVec
     * @param beginVec 开始向量（changeAmnt = 0）
     * @param finalVec 终向量（changeAmnt = 1）
     * @param changeAmnt 插值系数，取值范围为 0.0 - 1.0。
     */
    public Vector4f interpolateLocal(Vector4f beginVec,Vector4f finalVec, float changeAmnt) {
        this.x=(1-changeAmnt)*beginVec.x + changeAmnt*finalVec.x;
        this.y=(1-changeAmnt)*beginVec.y + changeAmnt*finalVec.y;
        this.z=(1-changeAmnt)*beginVec.z + changeAmnt*finalVec.z;
        this.w=(1-changeAmnt)*beginVec.w + changeAmnt*finalVec.w;
        return this;
    }
    public Vector4f set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vector4f set(Vector3f v, float w) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = w;
        return this;
    }
    
    public Vector4f set(Vector4f v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        this.w = v.w;
        return this;
    }
    
    // 基本的Getter和Setter

    public float getX() {
        return x;
    }

    public Vector4f setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector4f setY(float y) {
        this.y = y;
        return this;
    }

    public float getZ() {
        return z;
    }

    public Vector4f setZ(float z) {
        this.z = z;
        return this;
    }

    public float getW() {
        return w;
    }

    public Vector4f setW(float w) {
        this.w = w;
        return this;
    }

}