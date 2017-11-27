package net.jmecn.math;

/**
 * 四元数
 * 
 * @author yanmaoyuan
 *
 */
public class Quaternion {

    public float x, y, z, w;

    // 零四元数
    public final static Quaternion ZERO = new Quaternion(0, 0, 0, 0);
    // 单位四元数
    public final static Quaternion IDENTITY = new Quaternion(0, 0, 0, 1);
    
    public Quaternion() {
        x = y = z = 0;
        w = 1;
    }

    public Quaternion(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public Quaternion(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
    }

    /**
     * 使用角轴对来计算四元数
     * 
     * @param axis 由单位向量定义的旋转轴
     * @param angle 旋转角（弧度制）
     */
    public Quaternion(Vector3f axis, float angle) {
        axis.normalizeLocal();
        
        float cosHalfAngle = (float) Math.cos(angle * 0.5);
        float sinHalfAngle = (float) Math.sin(angle * 0.5);
        
        w = cosHalfAngle;
        x = axis.x * sinHalfAngle;
        y = axis.y * sinHalfAngle;
        z = axis.z * sinHalfAngle;
    }

    /**
     * 使用角轴对来计算四元数
     * @param axis 由单位向量定义的旋转轴
     * @param angle 旋转角（弧度制）
     * @return
     */
    public Quaternion fromAxisAngle(Vector3f axis, float angle) {
        axis.normalizeLocal();
        
        float sinHalfAngle = (float) Math.sin(angle * 0.5);
        float cosHalfAngle = (float) Math.cos(angle * 0.5);

        w = cosHalfAngle;
        
        x = axis.x * sinHalfAngle;
        y = axis.y * sinHalfAngle;
        z = axis.z * sinHalfAngle;
        
        return this;
    }
    
    /**
     * 绕X轴旋转
     * @param angle
     * @return
     */
    public Quaternion rotateX(float angle) {
        float sinHalfAngle = (float) Math.sin(angle * 0.5);
        float cosHalfAngle = (float) Math.cos(angle * 0.5);

        w = cosHalfAngle;
        
        x = sinHalfAngle;
        y = 0;
        z = 0;
        
        return this;
    }

    /**
     * 绕Y轴旋转
     * @param angle
     * @return
     */
    public Quaternion rotateY(float angle) {
        float sinHalfAngle = (float) Math.sin(angle * 0.5);
        float cosHalfAngle = (float) Math.cos(angle * 0.5);

        w = cosHalfAngle;
        
        x = 0;
        y = sinHalfAngle;
        z = 0;
        
        return this;
    }

    /**
     * 绕Z轴旋转
     * @param angle
     * @return
     */
    public Quaternion rotateZ(float angle) {
        float sinHalfAngle = (float) Math.sin(angle * 0.5);
        float cosHalfAngle = (float) Math.cos(angle * 0.5);

        w = cosHalfAngle;
        
        x = 0;
        y = 0;
        z = sinHalfAngle;
        
        return this;
    }

    /**
     * 欧拉角旋转（弧度制）
     * @param xAngle
     * @param yAngle
     * @param zAngle
     * @return
     */
    public Quaternion fromAngles(float xAngle, float yAngle, float zAngle) {
        float angle;
        float sinY, sinZ, sinX, cosY, cosZ, cosX;
        angle = zAngle * 0.5f;
        sinZ = (float)Math.sin(angle);
        cosZ = (float)Math.cos(angle);
        angle = yAngle * 0.5f;
        sinY = (float)Math.sin(angle);
        cosY = (float)Math.cos(angle);
        angle = xAngle * 0.5f;
        sinX = (float)Math.sin(angle);
        cosX = (float)Math.cos(angle);

        // variables used to reduce multiplication calls.
        float cosYXcosZ = cosY * cosZ;
        float sinYXsinZ = sinY * sinZ;
        float cosYXsinZ = cosY * sinZ;
        float sinYXcosZ = sinY * cosZ;

        w = (cosYXcosZ * cosX - sinYXsinZ * sinX);
        x = (cosYXcosZ * sinX + sinYXsinZ * cosX);
        y = (sinYXcosZ * cosX + cosYXsinZ * sinX);
        z = (cosYXsinZ * cosX - sinYXcosZ * sinX);

        normalizeLocal();
        return this;
    }
    
    /**
     * 负四元数
     * 
     * @return
     */
    public Quaternion negate() {
        return new Quaternion(-x, -y, -z, -w);
    }

    /**
     * 负四元数
     * 
     * @return
     */
    public Quaternion negateLocal() {
        x = -x;
        y = -y;
        z = -z;
        w = -w;
        return this;
    }
    
    /**
     * 将四元数设为 (0, 0, 0, 1)
     */
    public void loadIdentity() {
        x = y = z = 0;
        w = 1;
    }

    /**
     * 判断是否为单位四元数
     * 
     * @return true
     */
    public boolean isIdentity() {
        if (x == 0 && y == 0 && z == 0 && w == 1) {
            return true;
        } else {
            return false;
        }
    }
    

    /**
     * 求长度的平方
     * 
     * @return
     */
    public float lengthSquared() {
        return x * x + y * y + z * z + w * w;
    }

    /**
     * 求四元数的模，或者叫长度。
     * 
     * @return
     */
    public float length() {
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    /**
     * 规范化四元数
     * 
     * @return
     */
    public Quaternion normalize() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            return new Quaternion(length * x, length * y, length * z, length * w);
        }
        return new Quaternion(x, y, z, w);
    }

    /**
     * 规范化四元数
     * 
     * @return
     */
    public Quaternion normalizeLocal() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            x *= length;
            y *= length;
            z *= length;
            w *= length;
        }
        return this;
    }
    
    /**
     * 求共轭四元数
     * 
     * @return
     */
    public Quaternion conjugate() {
        return new Quaternion(-x, -y, -z, w);
    }

    /**
     * 求共轭四元数
     * 
     * @return
     */
    public Quaternion conjugateLocal() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    /**
     * 求四元数的逆。 四元数的逆 = 四元数的共轭 / 四元数的模
     * 
     * @return
     */
    public Quaternion inverse() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            return new Quaternion(-x * length, -y * length, -z * length, w * length);
        }
        return new Quaternion(-x, -y, -z, w);
    }
    
    /**
     * 求四元数的逆。 四元数的逆 = 四元数的共轭 / 四元数的模
     * 
     * @return
     */
    public Quaternion inverseLocal() {
        float length = x * x + y * y + z * z + w * w;
        if (length != 1f && length != 0f) {
            length = (float) (1.0 / Math.sqrt(length));
            x *= length;
            y *= length;
            z *= length;
        }
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    /**
     * 四元数乘法，或者叫叉乘。
     * 
     * @param q
     * @return
     */
    public Quaternion mult(Quaternion q) {
        float qw = q.w, qx = q.x, qy = q.y, qz = q.z;
        
        float rw = w * qw - x * qx - y * qy - z * qz;
        float rx = w * qx + x * qw + y * qz - z * qy;
        float ry = w * qy + y * qw + z * qx - x * qz;
        float rz = w * qz + z * qw + x * qy - y * qx;
        
        return new Quaternion(rx, ry, rz, rw);
    }
    
    /**
     * 四元数乘法
     * @param q
     * @param res
     * @return
     */
    public Quaternion mult(Quaternion q, Quaternion res) {
        if (res == null) {
            res = new Quaternion();
        }
        float qw = q.w, qx = q.x, qy = q.y, qz = q.z;
        res.x = x * qw + y * qz - z * qy + w * qx;
        res.y = -x * qz + y * qw + z * qx + w * qy;
        res.z = x * qy - y * qx + z * qw + w * qz;
        res.w = -x * qx - y * qy - z * qz + w * qw;
        return res;
    }
    
    /**
     * 四元数乘法，或者叫叉乘。
     * 
     * @param q
     * @return
     */
    public Quaternion multLocal(Quaternion q) {
        float qw = q.w, qx = q.x, qy = q.y, qz = q.z;
        
        float rw = w * qw - x * qx - y * qy - z * qz;
        float rx = w * qx + x * qw + y * qz - z * qy;
        float ry = w * qy + y * qw + z * qx - x * qz;
        float rz = w * qz + z * qw + x * qy - y * qx;
        
        x = rx;
        y = ry;
        z = rz;
        w = rw;
        return this;
    }
    
    /**
     * 使用这个四元数来旋转一个三维向量。
     * 
     * @param v
     * @return
     */
    public Vector3f mult(Vector3f v) {
        if (v.x == 0 && v.y == 0 && v.z == 0) {
            return new Vector3f(0, 0, 0);
        } else {
            float vx = v.x, vy = v.y, vz = v.z;
            float rx = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x * vx + 2 * y * x * vy + 2 * z * x * vz
                    - z * z * vx - y * y * vx;
            float ry = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w * z * vx - z * z * vy + w * w * vy
                    - 2 * x * w * vz - x * x * vy;
            float rz = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w * y * vx - y * y * vz + 2 * w * x * vy
                    - x * x * vz + w * w * vz;
            return new Vector3f(rx, ry, rz);
        }
    }
    
    /**
     * 旋转一个三维向量。
     * @param v
     * @param store
     * @return
     */
    public Vector3f mult(Vector3f v, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }
        if (v.x == 0 && v.y == 0 && v.z == 0) {
            store.set(0, 0, 0);
        } else {
            float vx = v.x, vy = v.y, vz = v.z;
            store.x = w * w * vx + 2 * y * w * vz - 2 * z * w * vy + x * x
                    * vx + 2 * y * x * vy + 2 * z * x * vz - z * z * vx - y
                    * y * vx;
            store.y = 2 * x * y * vx + y * y * vy + 2 * z * y * vz + 2 * w
                    * z * vx - z * z * vy + w * w * vy - 2 * x * w * vz - x
                    * x * vy;
            store.z = 2 * x * z * vx + 2 * y * z * vy + z * z * vz - 2 * w
                    * y * vx - y * y * vz + 2 * w * x * vy - x * x * vz + w
                    * w * vz;
        }
        return store;
    }
    
    /**
     * 旋转一个三维向量
     * @param v
     * @return
     */
    public Vector3f multLocal(Vector3f v) {
        float tempX, tempY;
        tempX = w * w * v.x + 2 * y * w * v.z - 2 * z * w * v.y + x * x * v.x
                + 2 * y * x * v.y + 2 * z * x * v.z - z * z * v.x - y * y * v.x;
        tempY = 2 * x * y * v.x + y * y * v.y + 2 * z * y * v.z + 2 * w * z
                * v.x - z * z * v.y + w * w * v.y - 2 * x * w * v.z - x * x
                * v.y;
        v.z = 2 * x * z * v.x + 2 * y * z * v.y + z * z * v.z - 2 * w * y * v.x
                - y * y * v.z + 2 * w * x * v.y - x * x * v.z + w * w * v.z;
        v.x = tempX;
        v.y = tempY;
        return v;
    }
    
    /**
     * 计算两个四元数之间的“差”。
     * <pre>
     * 已知四元数a和b，计算从a旋转到b的角位移d。
     * a * d = b
     *     d = a的逆 * b
     * </pre>
     * @param q
     * @return
     */
    public Quaternion delta(Quaternion q) {
        return this.inverse().mult(q);
    }
    /**
     * 四元数点乘。这个值越大，说明两个四元数的旋转角度越接近。
     * 当两个四元数都是单位四元数时，返回值是它们之间夹角的余弦值。
     * 
     * @param q
     * @return
     */
    public float dot(Quaternion q) {
        return x * q.x + y * q.y + z * q.z + w * q.w;
    }

    /**
     * 四元数加法
     * @param q
     * @return
     */
    public Quaternion add(Quaternion q) {
        return new Quaternion(x + q.x, y + q.y, z + q.z, w + q.w);
    }
    
    /**
     * 四元数减法
     * @param q
     * @return
     */
    public Quaternion subtract(Quaternion q) {
        return new Quaternion(x - q.x, y - q.y, z - q.z, w - q.w);
    }
    
    /**
     * 球面线性插值(Spherical Linear intERPolation)
     *
     * @param src
     * @param dest
     * @param t
     */
    public Quaternion slerp(Quaternion src, Quaternion dest, float t) {
        if (src.x == dest.x && src.y == dest.y && src.z == dest.z && src.w == dest.w) {
            return new Quaternion(src);
        }

        // 用点乘计算两四元数夹角的 cos 值
        float cos = src.dot(dest);

        // 如果点乘为负，则反转一个四元数以取得短的4D“弧”
        if (cos < 0.0f) {
            cos = -cos;
            dest = dest.negate();
        }

        // 计算两个四元数的插值系数
        float srcFactor = 1 - t;
        float destFactor = t;

        // 检查它们是否过于接近以避免除零
        if (cos > 0.999f) {
            // 非常接近 -- 即线性插值
            srcFactor = 1 - t;
            destFactor = t;
        } else {
            // 计算两个四元数之间的夹角
            float angle = (float) Math.acos(cos);
            // 计算分母的倒数，这样就只需要一次除法
            float invSin = 1f / (float) Math.sin(angle);

            // 计算插值系数
            srcFactor = (float) Math.sin((1 - t) * angle) * invSin;
            destFactor = (float) Math.sin((t * angle)) * invSin;
        }
        
        // 插值
        float rx = (srcFactor * src.x) + (destFactor * dest.x);
        float ry = (srcFactor * src.y) + (destFactor * dest.y);
        float rz = (srcFactor * src.z) + (destFactor * dest.z);
        float rw = (srcFactor * src.w) + (destFactor * dest.w);

        // 返回插值结果
        return new Quaternion(rx, ry, rz, rw);
    }
    
    /**
     * 球面线性插值(Spherical Linear intERPolation)
     *
     * @param dest
     * @param t
     */
    public Quaternion slerp(Quaternion dest, float t) {
        if (x == dest.x && y == dest.y && z == dest.z && w == dest.w) {
            return new Quaternion(this);
        }

        // 用点乘计算两四元数夹角的 cos 值
        float cos = dot(dest);

        // 如果点乘为负，则反转一个四元数以取得短的4D“弧”
        if (cos < 0.0f) {
            cos = -cos;
            dest = dest.negate();
        }

        // 计算两个四元数的插值系数
        float srcFactor = 1 - t;
        float destFactor = t;

        // 检查它们是否过于接近以避免除零
        if (cos > 0.999f) {
            // 非常接近 -- 即线性插值
            srcFactor = 1 - t;
            destFactor = t;
        } else {
            // 计算两个四元数之间的夹角
            float angle = (float) Math.acos(cos);
            // 计算分母的倒数，这样就只需要一次除法
            float invSin = 1f / (float) Math.sin(angle);

            // 计算插值系数
            srcFactor = (float) Math.sin((1 - t) * angle) * invSin;
            destFactor = (float) Math.sin((t * angle)) * invSin;
        }
        
        // 插值
        float rx = (srcFactor * x) + (destFactor * dest.x);
        float ry = (srcFactor * y) + (destFactor * dest.y);
        float rz = (srcFactor * z) + (destFactor * dest.z);
        float rw = (srcFactor * w) + (destFactor * dest.w);

        // 返回插值结果
        return new Quaternion(rx, ry, rz, rw);
    }
    
    /**
     * 四元数转旋转矩阵
     * @return
     */
    public Matrix3f toRotationMatrix() {
        Matrix3f matrix = new Matrix3f();
        return toRotationMatrix(matrix);
    }
    
    /**
     * 四元数转旋转矩阵
     * @param result
     * @return
     */
    public Matrix3f toRotationMatrix(Matrix3f result) {
        
        if (result == null)
            result = new Matrix3f();
        
        // 先计算2x、2y、2z的值，可以节省多次乘法运算。
        float _2x = x * 2;
        float _2y = y * 2;
        float _2z = z * 2;
        float _2xx = x * _2x;
        float _2xy = x * _2y;
        float _2xz = x * _2z;
        float _2xw = w * _2x;
        float _2yy = y * _2y;
        float _2yz = y * _2z;
        float _2yw = w * _2y;
        float _2zz = z * _2z;
        float _2zw = w * _2z;
        
        result.m00 = 1 - (_2yy + _2zz);
        result.m01 = (_2xy - _2zw);
        result.m02 = (_2xz + _2yw);
        result.m10 = (_2xy + _2zw);
        result.m11 = 1 - (_2xx + _2zz);
        result.m12 = (_2yz - _2xw);
        result.m20 = (_2xz - _2yw);
        result.m21 = (_2yz + _2xw);
        result.m22 = 1 - (_2xx + _2yy);
        
        return result;
    }
    
    /**
     * 四元数转4x4矩阵
     * @param result
     * @return
     */
    public Matrix4f toRotationMatrix(Matrix4f result) {
        
        Vector3f originalScale = new Vector3f();
        
        // 保存矩阵原来的比例变换
        result.toScaleVector(originalScale);
        result.setScale(1, 1, 1);

        // 先计算2x、2y、2z的值，可以节省多次乘法运算。
        float _2x = x * 2;
        float _2y = y * 2;
        float _sz = z * 2;
        float _2xx = x * _2x;
        float _2xy = x * _2y;
        float _2xz = x * _sz;
        float _2xw = w * _2x;
        float _2yy = y * _2y;
        float _2yz = y * _sz;
        float _2yw = w * _2y;
        float _2zz = z * _sz;
        float _2zw = w * _sz;

        result.m00 = 1 - (_2yy + _2zz);
        result.m01 = (_2xy - _2zw);
        result.m02 = (_2xz + _2yw);
        result.m10 = (_2xy + _2zw);
        result.m11 = 1 - (_2xx + _2zz);
        result.m12 = (_2yz - _2xw);
        result.m20 = (_2xz - _2yw);
        result.m21 = (_2yz + _2xw);
        result.m22 = 1 - (_2xx + _2yy);

        // 恢复矩阵的比例变换
        result.setScale(originalScale);
        
        return result;
    }
    
    /**
     * 旋转矩阵转四元数
     * @param mat3
     * @return
     */
    public Quaternion fromRotationMatrix(Matrix3f mat3) {
        return fromRotationMatrix(mat3.m00, mat3.m01, mat3.m02,
                mat3.m10, mat3.m11, mat3.m12, mat3.m20, mat3.m21, mat3.m22);
    }
    
    /**
     * 旋转矩阵转四元数
     * @param m00
     * @param m01
     * @param m02
     * @param m10
     * @param m11
     * @param m12
     * @param m20
     * @param m21
     * @param m22
     * @return
     */
    public Quaternion fromRotationMatrix(float m00, float m01, float m02,
            float m10, float m11, float m12, float m20, float m21, float m22) {
        // 先把矩阵的3个列向量规范化，避免缩放矩阵对四元数旋转的影响。
        float lengthSquared = m00 * m00 + m10 * m10 + m20 * m20;
        if (lengthSquared != 1f && lengthSquared != 0f) {
            lengthSquared = (float) (1.0 / Math.sqrt(lengthSquared));
            m00 *= lengthSquared;
            m10 *= lengthSquared;
            m20 *= lengthSquared;
        }
        lengthSquared = m01 * m01 + m11 * m11 + m21 * m21;
        if (lengthSquared != 1f && lengthSquared != 0f) {
            lengthSquared = (float) (1.0 / Math.sqrt(lengthSquared));
            m01 *= lengthSquared;
            m11 *= lengthSquared;
            m21 *= lengthSquared;
        }
        lengthSquared = m02 * m02 + m12 * m12 + m22 * m22;
        if (lengthSquared != 1f && lengthSquared != 0f) {
            lengthSquared = (float) (1.0 / Math.sqrt(lengthSquared));
            m02 *= lengthSquared;
            m12 *= lengthSquared;
            m22 *= lengthSquared;
        }
        
        // Use the Graphics Gems code, from 
        // ftp://ftp.cis.upenn.edu/pub/graphics/shoemake/quatut.ps.Z
        // *NOT* the "Matrix and Quaternions FAQ", which has errors!

        // the trace is the sum of the diagonal elements; see
        // http://mathworld.wolfram.com/MatrixTrace.html
        float t = m00 + m11 + m22;

        // we protect the division by s by ensuring that s>=1
        if (t >= 0) { // |w| >= .5
            float s = (float) Math.sqrt(t + 1); // |s|>=1 ...
            w = 0.5f * s;
            s = 0.5f / s;                 // so this division isn't bad
            x = (m21 - m12) * s;
            y = (m02 - m20) * s;
            z = (m10 - m01) * s;
        } else if ((m00 > m11) && (m00 > m22)) {
            float s = (float) Math.sqrt(1.0f + m00 - m11 - m22); // |s|>=1
            x = s * 0.5f; // |x| >= .5
            s = 0.5f / s;
            y = (m10 + m01) * s;
            z = (m02 + m20) * s;
            w = (m21 - m12) * s;
        } else if (m11 > m22) {
            float s = (float) Math.sqrt(1.0f + m11 - m00 - m22); // |s|>=1
            y = s * 0.5f; // |y| >= .5
            s = 0.5f / s;
            x = (m10 + m01) * s;
            z = (m21 + m12) * s;
            w = (m02 - m20) * s;
        } else {
            float s = (float) Math.sqrt(1.0f + m22 - m00 - m11); // |s|>=1
            z = s * 0.5f; // |z| >= .5
            s = 0.5f / s;
            x = (m02 + m20) * s;
            y = (m21 + m12) * s;
            w = (m10 - m01) * s;
        }
        return this;
    }
    
    /**
     * 设置四元数的四个分量
     * 
     * @param x
     * @param y
     * @param z
     * @param w
     * @return
     */
    public Quaternion set(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    /**
     * 设置四元数的四个分量
     * 
     * @param q
     * @return
     */
    public Quaternion set(Quaternion q) {
        this.x = q.x;
        this.y = q.y;
        this.z = q.z;
        this.w = q.w;
        return this;
    }
}
