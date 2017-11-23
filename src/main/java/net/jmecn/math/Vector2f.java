package net.jmecn.math;

/**
 * 二维向量
 * @author yanmaoyuan
 *
 */
public final class Vector2f {

    public float x, y;
    
    // 零向量
    public static final Vector2f ZERO = new Vector2f(0f, 0f);

    public Vector2f() {
        x = y = 0;
    }
    
    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2f(Vector2f v) {
        this.x = v.x;
        this.y = v.y;
    }

    /**
     * 求负向量
     * @return
     */
    public Vector2f negate() {
        return new Vector2f(-x, -y);
    }

    /**
     * 求负向量
     * @return
     */
    public Vector2f negateLocal() {
        x = -x;
        y = -y;
        return this;
    }
    
    /**
     * 向量加法
     * @param v
     * @return
     */
    public Vector2f add(Vector2f v) {
        return new Vector2f(x + v.x, y + v.y);
    }

    /**
     * 向量加法
     * @param v
     * @return
     */
    public Vector2f addLocal(Vector2f v) {
        x += v.x;
        y += v.y;
        return this;
    }

    /**
     * 向量减法
     * @param v
     * @return
     */
    public Vector2f subtract(Vector2f v) {
        return new Vector2f(x - v.x, y - v.y);
    }

    /**
     * 向量减法
     * @param v
     * @return
     */
    public Vector2f subtractLocal(Vector2f v) {
        x -= v.x;
        y -= v.y;
        return this;
    }
    
    /**
     * 向量点乘
     * @param v
     * @return
     */
    public float dot(Vector2f v) {
        return x * v.x + y * v.y;
    }

    /**
     * 向量叉乘
     * @param v
     * @return
     */
    public Vector3f cross(Vector2f v) {
        return new Vector3f(0, 0, determinant(v));
    }

    /**
     * 判别式。
     * 若结果 &gt 0，点v位于该向量左侧；
     * 若结果 &lt; 0，点v位于该向量右侧；
     * 若结果 == 0，点v与该向量共线。
     * @param v
     * @return
     */
    public float determinant(Vector2f v) {
        return (x * v.y) - (y * v.x);
    }
    
    /**
     * 线性插值
     * @param finalVec
     * @param changeAmnt
     * @return
     */
    public Vector2f interpolateLocal(Vector2f finalVec, float changeAmnt) {
        this.x = (1 - changeAmnt) * this.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * this.y + changeAmnt * finalVec.y;
        return this;
    }

    /**
     * 线性插值
     * @param beginVec
     * @param finalVec
     * @param changeAmnt
     * @return
     */
    public Vector2f interpolateLocal(Vector2f beginVec, Vector2f finalVec,
            float changeAmnt) {
        this.x = (1 - changeAmnt) * beginVec.x + changeAmnt * finalVec.x;
        this.y = (1 - changeAmnt) * beginVec.y + changeAmnt * finalVec.y;
        return this;
    }

    /**
     * 求向量的长度
     * @return
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * 求向量长度的平方
     * @return
     */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /**
     * 求两点间距离的平方
     * @param v
     * @return
     */
    public float distanceSquared(Vector2f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        return (float) (dx * dx + dy * dy);
    }

    /**
     * 求两点间的距离
     * @param v
     * @return
     */
    public float distance(Vector2f v) {
        double dx = x - v.x;
        double dy = y - v.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * 标量乘法
     * @param scalar
     * @return
     */
    public Vector2f mult(float scalar) {
        return new Vector2f(x * scalar, y * scalar);
    }

    /**
     * 标量乘法
     * @param scalar
     * @return
     */
    public Vector2f multLocal(float scalar) {
        x *= scalar;
        y *= scalar;
        return this;
    }

    /**
     * 向量乘法
     * @param v
     * @return
     */
    public Vector2f mult(Vector2f v) {
        return new Vector2f(x * v.x, y * v.y);
    }
    
    /**
     * 向量乘法
     * @param v
     * @return
     */
    public Vector2f multLocal(Vector2f v) {
        x *= v.x;
        y *= v.y;
        return this;
    }

    /**
     * 标量除法
     * @param scalar
     * @return
     */
    public Vector2f divide(float scalar) {
        return new Vector2f(x / scalar, y / scalar);
    }

    /**
     * 标量除法
     * @param scalar
     * @return
     */
    public Vector2f divideLocal(float scalar) {
        x /= scalar;
        y /= scalar;
        return this;
    }

    /**
     * 规范化向量
     * @return
     */
    public Vector2f normalize() {
        float length = length();
        if (length != 0) {
            return divide(length);
        }

        return divide(1);
    }

    /**
     * 规范化向量
     * @return
     */
    public Vector2f normalizeLocal() {
        float length = length();
        if (length != 0) {
            return divideLocal(length);
        }

        return divideLocal(1);
    }

    /**
     * 求两个向量之间最小的角
     * @param otherVector
     * @return
     */
    public float smallestAngleBetween(Vector2f otherVector) {
        float dotProduct = dot(otherVector);
        float angle = (float) Math.acos(dotProduct);
        return angle;
    }

    /**
     * 求两个向量的夹角
     * @param otherVector
     * @return
     */
    public float angleBetween(Vector2f otherVector) {
        float angle = (float)(Math.atan2(otherVector.y, otherVector.x)
                - Math.atan2(y, x));
        return angle;
    }

    /**
     * 根据tan值求向量与x轴的夹角。
     * @return
     */
    public float getAngle() {
        return (float) Math.atan2(y, x);
    }
    
    // 基本的Getter和Setter
    
    public Vector2f set(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2f set(Vector2f vec) {
        this.x = vec.x;
        this.y = vec.y;
        return this;
    }
    
    public float getX() {
        return x;
    }

    public Vector2f setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Vector2f setY(float y) {
        this.y = y;
        return this;
    }

}
