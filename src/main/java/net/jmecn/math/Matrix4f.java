package net.jmecn.math;

/**
 * 4*4矩阵
 * 
 * @author yanmaoyuan
 *
 */
public class Matrix4f {
    
    float[][] m;
    
    public float m00, m01, m02, m03;
    public float m10, m11, m12, m13;
    public float m20, m21, m22, m23;
    public float m30, m31, m32, m33;

    /**
     * 零矩阵
     */
    public static final Matrix4f ZERO = new Matrix4f(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);

    /**
     * 单位矩阵
     */
    public static final Matrix4f IDENTITY = new Matrix4f();
    
    /**
     * 初始化为单位矩阵
     */
    public Matrix4f() {
        loadIdentity();
    }
    
    public Matrix4f(float m00, float m01, float m02, float m03,
            float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23,
            float m30, float m31, float m32, float m33) {

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }

    /**
     * 使用数组来初始化矩阵，数组顺序为列优先格式。
     * @param array
     */
    public Matrix4f(float[] array) {
        set(array, false);
    }
    
    /**
     * 使用另一个矩阵来初始化矩阵
     * @param mat4
     */
    public Matrix4f(Matrix4f mat4) {
        if (mat4 == null) {
            loadIdentity();
        } else {
            set(mat4);
        }
    }
    
    /**
     * 单位矩阵
     */
    public void loadIdentity() {
        m01 = m02 = m03 = 0.0f;
        m10 = m12 = m13 = 0.0f;
        m20 = m21 = m23 = 0.0f;
        m30 = m31 = m32 = 0.0f;
        m00 = m11 = m22 = m33 = 1.0f;
    }

    /**
     * 零矩阵
     * @return
     */
    public Matrix4f zero() {
        m00 = m01 = m02 = m03 = 0.0f;
        m10 = m11 = m12 = m13 = 0.0f;
        m20 = m21 = m22 = m23 = 0.0f;
        m30 = m31 = m32 = m33 = 0.0f;
        return this;
    }
    
    /**
     * 求转置矩阵
     * @return
     */
    public Matrix4f transpose() {
        float[] tmp = new float[16];
        get(tmp, true);
        Matrix4f mat = new Matrix4f(tmp);
        return mat;
    }

    /**
     * 求转置矩阵
     * @return
     */
    public Matrix4f transposeLocal() {
        float tmp = m01;
        m01 = m10;
        m10 = tmp;

        tmp = m02;
        m02 = m20;
        m20 = tmp;

        tmp = m03;
        m03 = m30;
        m30 = tmp;

        tmp = m12;
        m12 = m21;
        m21 = tmp;

        tmp = m13;
        m13 = m31;
        m31 = tmp;

        tmp = m23;
        m23 = m32;
        m32 = tmp;

        return this;
    }
    
    /**
     * 标量乘法
     * @param scalar
     */
    public void multLocal(float scalar) {
        m00 *= scalar;
        m01 *= scalar;
        m02 *= scalar;
        m03 *= scalar;
        m10 *= scalar;
        m11 *= scalar;
        m12 *= scalar;
        m13 *= scalar;
        m20 *= scalar;
        m21 *= scalar;
        m22 *= scalar;
        m23 *= scalar;
        m30 *= scalar;
        m31 *= scalar;
        m32 *= scalar;
        m33 *= scalar;
    }

    /**
     * 标量乘法
     * @param scalar
     * @return
     */
    public Matrix4f mult(float scalar) {
        Matrix4f out = new Matrix4f();
        out.set(this);
        out.multLocal(scalar);
        return out;
    }

    /**
     * 标量乘法
     * @param scalar
     * @param store
     * @return
     */
    public Matrix4f mult(float scalar, Matrix4f store) {
        store.set(this);
        store.multLocal(scalar);
        return store;
    }
    
    /**
     * 矩阵乘法，结果将返回一个新的Matrix4f对象。
     * 
     * @param mat4
     * @return
     */
    public Matrix4f mult(Matrix4f mat4) {
        return mult(mat4, null);
    }

    /**
     * 矩阵乘法，乘积保存在store对象中。
     * 
     * @param mat4
     * @param store
     * @return
     */
    public Matrix4f mult(Matrix4f mat4, Matrix4f store) {
        if (store == null) {
            store = new Matrix4f();
        }

        float temp00, temp01, temp02, temp03;
        float temp10, temp11, temp12, temp13;
        float temp20, temp21, temp22, temp23;
        float temp30, temp31, temp32, temp33;

        temp00 = m00 * mat4.m00
                + m01 * mat4.m10
                + m02 * mat4.m20
                + m03 * mat4.m30;
        temp01 = m00 * mat4.m01
                + m01 * mat4.m11
                + m02 * mat4.m21
                + m03 * mat4.m31;
        temp02 = m00 * mat4.m02
                + m01 * mat4.m12
                + m02 * mat4.m22
                + m03 * mat4.m32;
        temp03 = m00 * mat4.m03
                + m01 * mat4.m13
                + m02 * mat4.m23
                + m03 * mat4.m33;

        temp10 = m10 * mat4.m00
                + m11 * mat4.m10
                + m12 * mat4.m20
                + m13 * mat4.m30;
        temp11 = m10 * mat4.m01
                + m11 * mat4.m11
                + m12 * mat4.m21
                + m13 * mat4.m31;
        temp12 = m10 * mat4.m02
                + m11 * mat4.m12
                + m12 * mat4.m22
                + m13 * mat4.m32;
        temp13 = m10 * mat4.m03
                + m11 * mat4.m13
                + m12 * mat4.m23
                + m13 * mat4.m33;

        temp20 = m20 * mat4.m00
                + m21 * mat4.m10
                + m22 * mat4.m20
                + m23 * mat4.m30;
        temp21 = m20 * mat4.m01
                + m21 * mat4.m11
                + m22 * mat4.m21
                + m23 * mat4.m31;
        temp22 = m20 * mat4.m02
                + m21 * mat4.m12
                + m22 * mat4.m22
                + m23 * mat4.m32;
        temp23 = m20 * mat4.m03
                + m21 * mat4.m13
                + m22 * mat4.m23
                + m23 * mat4.m33;

        temp30 = m30 * mat4.m00
                + m31 * mat4.m10
                + m32 * mat4.m20
                + m33 * mat4.m30;
        temp31 = m30 * mat4.m01
                + m31 * mat4.m11
                + m32 * mat4.m21
                + m33 * mat4.m31;
        temp32 = m30 * mat4.m02
                + m31 * mat4.m12
                + m32 * mat4.m22
                + m33 * mat4.m32;
        temp33 = m30 * mat4.m03
                + m31 * mat4.m13
                + m32 * mat4.m23
                + m33 * mat4.m33;

        store.m00 = temp00;
        store.m01 = temp01;
        store.m02 = temp02;
        store.m03 = temp03;
        store.m10 = temp10;
        store.m11 = temp11;
        store.m12 = temp12;
        store.m13 = temp13;
        store.m20 = temp20;
        store.m21 = temp21;
        store.m22 = temp22;
        store.m23 = temp23;
        store.m30 = temp30;
        store.m31 = temp31;
        store.m32 = temp32;
        store.m33 = temp33;

        return store;
    }

    /**
     * 矩阵乘法，乘积保存在当前矩阵中。
     * 
     * @param mat4
     * @return
     */
    public Matrix4f multLocal(Matrix4f mat4) {
        return mult(mat4, this);
    }
    
    /**
     * 4x4矩阵与Vector3f向量相乘，结果保存在一个新的Vector3f对象中。
     * 
     * @param vec
     * @return
     */
    public Vector3f mult(Vector3f vec) {
        return mult(vec, null);
    }

    /**
     * 4x4矩阵与Vector3f向量相乘，结果保存在一个新的store对象中。
     * 
     * @param vec
     * @param store
     * @return
     */
    public Vector3f mult(Vector3f vec, Vector3f store) {
        if (store == null) {
            store = new Vector3f();
        }

        float vx = vec.x, vy = vec.y, vz = vec.z;
        store.x = m00 * vx + m01 * vy + m02 * vz + m03;
        store.y = m10 * vx + m11 * vy + m12 * vz + m13;
        store.z = m20 * vx + m21 * vy + m22 * vz + m23;

        return store;
    }

    /**
     * 矩阵与四位向量相乘，结果返回一个新的Vector4f对象。
     *
     * @param vec
     * @return
     */
    public Vector4f mult(Vector4f vec) {
        return mult(vec, null);
    }

    /**
     * 矩阵与四位向量相乘，结果返保存在store对象中。
     *
     * @param vec
     * @param store
     * @return
     */
    public Vector4f mult(Vector4f vec, Vector4f store) {
        if (null == vec) {
            return null;
        }
        if (store == null) {
            store = new Vector4f();
        }

        float vx = vec.x, vy = vec.y, vz = vec.z, vw = vec.w;
        store.x = m00 * vx + m01 * vy + m02 * vz + m03 * vw;
        store.y = m10 * vx + m11 * vy + m12 * vz + m13 * vw;
        store.z = m20 * vx + m21 * vy + m22 * vz + m23 * vw;
        store.w = m30 * vx + m31 * vy + m32 * vz + m33 * vw;

        return store;
    }
    
    /**
     * 计算行列式
     * @return
     */
    public float determinant() {
        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;
        return fDet;
    }
    
    /**
     * 求Matrix4f的标准伴随矩阵。
     * 
     * @return
     */
    public Matrix4f adjoint() {
        return adjoint(null);
    }
    
    /**
     * 求Matrix4f的标准伴随矩阵，结果保存在store对象中。
     * 
     * @param store
     * @return
     */
    public Matrix4f adjoint(Matrix4f store) {
        if (store == null) {
            store = new Matrix4f();
        }

        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;

        store.m00 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
        store.m10 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
        store.m20 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
        store.m30 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
        store.m01 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
        store.m11 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
        store.m21 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
        store.m31 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
        store.m02 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
        store.m12 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
        store.m22 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
        store.m32 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
        store.m03 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
        store.m13 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
        store.m23 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
        store.m33 = +m20 * fA3 - m21 * fA1 + m22 * fA0;

        return store;
    }
    
    /**
     * 求矩阵的逆
     * 
     * @return 
     */
    public Matrix4f invert() {
        return invert(null);
    }

    /**
     * 求矩阵的逆
     * 
     * @return
     */
    public Matrix4f invert(Matrix4f store) {
        if (store == null) {
            store = new Matrix4f();
        }

        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;

        if (Math.abs(fDet) <= 0f) {
            throw new ArithmeticException("This matrix cannot be inverted");
        }

        store.m00 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
        store.m10 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
        store.m20 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
        store.m30 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
        store.m01 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
        store.m11 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
        store.m21 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
        store.m31 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
        store.m02 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
        store.m12 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
        store.m22 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
        store.m32 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
        store.m03 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
        store.m13 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
        store.m23 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
        store.m33 = +m20 * fA3 - m21 * fA1 + m22 * fA0;

        float fInvDet = 1.0f / fDet;
        store.multLocal(fInvDet);

        return store;
    }

    /**
     * 求矩阵的逆
     * 
     * @return
     */
    public Matrix4f invertLocal() {

        float fA0 = m00 * m11 - m01 * m10;
        float fA1 = m00 * m12 - m02 * m10;
        float fA2 = m00 * m13 - m03 * m10;
        float fA3 = m01 * m12 - m02 * m11;
        float fA4 = m01 * m13 - m03 * m11;
        float fA5 = m02 * m13 - m03 * m12;
        float fB0 = m20 * m31 - m21 * m30;
        float fB1 = m20 * m32 - m22 * m30;
        float fB2 = m20 * m33 - m23 * m30;
        float fB3 = m21 * m32 - m22 * m31;
        float fB4 = m21 * m33 - m23 * m31;
        float fB5 = m22 * m33 - m23 * m32;
        float fDet = fA0 * fB5 - fA1 * fB4 + fA2 * fB3 + fA3 * fB2 - fA4 * fB1 + fA5 * fB0;

        if (Math.abs(fDet) <= 0f) {
            return zero();
        }

        float f00 = +m11 * fB5 - m12 * fB4 + m13 * fB3;
        float f10 = -m10 * fB5 + m12 * fB2 - m13 * fB1;
        float f20 = +m10 * fB4 - m11 * fB2 + m13 * fB0;
        float f30 = -m10 * fB3 + m11 * fB1 - m12 * fB0;
        float f01 = -m01 * fB5 + m02 * fB4 - m03 * fB3;
        float f11 = +m00 * fB5 - m02 * fB2 + m03 * fB1;
        float f21 = -m00 * fB4 + m01 * fB2 - m03 * fB0;
        float f31 = +m00 * fB3 - m01 * fB1 + m02 * fB0;
        float f02 = +m31 * fA5 - m32 * fA4 + m33 * fA3;
        float f12 = -m30 * fA5 + m32 * fA2 - m33 * fA1;
        float f22 = +m30 * fA4 - m31 * fA2 + m33 * fA0;
        float f32 = -m30 * fA3 + m31 * fA1 - m32 * fA0;
        float f03 = -m21 * fA5 + m22 * fA4 - m23 * fA3;
        float f13 = +m20 * fA5 - m22 * fA2 + m23 * fA1;
        float f23 = -m20 * fA4 + m21 * fA2 - m23 * fA0;
        float f33 = +m20 * fA3 - m21 * fA1 + m22 * fA0;

        m00 = f00;
        m01 = f01;
        m02 = f02;
        m03 = f03;
        m10 = f10;
        m11 = f11;
        m12 = f12;
        m13 = f13;
        m20 = f20;
        m21 = f21;
        m22 = f22;
        m23 = f23;
        m30 = f30;
        m31 = f31;
        m32 = f32;
        m33 = f33;

        float fInvDet = 1.0f / fDet;
        multLocal(fInvDet);

        return this;
    }
    
    /**
     * 屏幕空间变换
     * @param halfWidth
     * @param halfHeight
     * @return
     */
    public Matrix4f initScreenSpaceTransform(float halfWidth, float halfHeight) {
        m[0][0] = halfWidth; m[0][1] = 0;           m[0][2] = 0; m[0][3] = halfWidth - 0.5f;
        m[1][0] = 0;         m[1][1] = -halfHeight; m[1][2] = 0; m[1][3] = halfHeight - 0.5f;
        m[2][0] = 0;         m[2][1] = 0;           m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0;         m[3][1] = 0;           m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * 4*4矩阵的第四列表示位移
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initTranslation(float x, float y, float z) {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * 计算绕轴旋转矩阵。
     * @param x
     * @param y
     * @param z
     * @param angle
     * @return
     */
    public Matrix4f initRotation(float x, float y, float z, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        m[0][0] = cos + x * x * (1 - cos);     m[0][1] = x * y * (1 - cos) - z * sin; m[0][2] = x * z * (1 - cos) + y * sin; m[0][3] = 0;
        m[1][0] = y * x * (1 - cos) + z * sin; m[1][1] = cos + y * y * (1 - cos);     m[1][2] = y * z * (1 - cos) - x * sin; m[1][3] = 0;
        m[2][0] = z * x * (1 - cos) - y * sin; m[2][1] = z * y * (1 - cos) + x * sin; m[2][2] = cos + z * z * (1 - cos);     m[2][3] = 0;
        m[3][0] = 0;                           m[3][1] = 0;                           m[3][2] = 0;                           m[3][3] = 1;

        return this;
    }

    /**
     * 利用矩阵乘法来计算欧拉角旋转矩阵。
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initRotation(float x, float y, float z) {
        Matrix4f rx = new Matrix4f();
        Matrix4f ry = new Matrix4f();
        Matrix4f rz = new Matrix4f();

        rz.m[0][0] = (float) Math.cos(z); rz.m[0][1] = -(float) Math.sin(z); rz.m[0][2] = 0;  rz.m[0][3] = 0;
        rz.m[1][0] = (float) Math.sin(z); rz.m[1][1] = (float) Math.cos(z);  rz.m[1][2] = 0;  rz.m[1][3] = 0;
        rz.m[2][0] = 0;                   rz.m[2][1] = 0;                    rz.m[2][2] = 1;  rz.m[2][3] = 0;
        rz.m[3][0] = 0;                   rz.m[3][1] = 0;                    rz.m[3][2] = 0;  rz.m[3][3] = 1;

        rx.m[0][0] = 1; rx.m[0][1] = 0;                   rx.m[0][2] = 0;                    rx.m[0][3] = 0;
        rx.m[1][0] = 0; rx.m[1][1] = (float) Math.cos(x); rx.m[1][2] = -(float) Math.sin(x); rx.m[1][3] = 0;
        rx.m[2][0] = 0; rx.m[2][1] = (float) Math.sin(x); rx.m[2][2] = (float) Math.cos(x);  rx.m[2][3] = 0;
        rx.m[3][0] = 0; rx.m[3][1] = 0;                   rx.m[3][2] = 0;                    rx.m[3][3] = 1;

        ry.m[0][0] = (float) Math.cos(y); ry.m[0][1] = 0; ry.m[0][2] = -(float) Math.sin(y); ry.m[0][3] = 0;
        ry.m[1][0] = 0;                   ry.m[1][1] = 1; ry.m[1][2] = 0;                    ry.m[1][3] = 0;
        ry.m[2][0] = (float) Math.sin(y); ry.m[2][1] = 0; ry.m[2][2] = (float) Math.cos(y);  ry.m[2][3] = 0;
        ry.m[3][0] = 0;                   ry.m[3][1] = 0; ry.m[3][2] = 0;                    ry.m[3][3] = 1;

        m = rz.mul(ry.mul(rx)).getM();

        return this;
    }

    /**
     * 4*4矩阵的左上角3个Vector3f，表示缩放矩阵。
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initScale(float x, float y, float z) {
        m[0][0] = x; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = y; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = z; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    /**
     * 正交投影矩阵
     * @param fovY
     * @param aspectRatio
     * @param near
     * @param far
     * @return
     */
    public Matrix4f initPerspective(float fovY, float aspectRatio, float near, float far) {
        float zoomY = (float) Math.tan(fovY / 2);
        float zoomX = zoomY * aspectRatio;

        m[0][0] = 1.0f / zoomX; m[0][1] = 0;            m[0][2] = 0;                           m[0][3] = 0;
        m[1][0] = 0;            m[1][1] = 1.0f / zoomY; m[1][2] = 0;                           m[1][3] = 0;
        m[2][0] = 0;            m[2][1] = 0;            m[2][2] = (far + near) / (far - near); m[2][3] = 2 * far * near / (near - far);
        m[3][0] = 0;            m[3][1] = 0;            m[3][2] = 1;                           m[3][3] = 0;

        return this;
    }

    /**
     * 平行投影矩阵
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     * @return
     */
    public Matrix4f initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;

        m[0][0] = 2 / width; m[0][1] = 0;          m[0][2] = 0;          m[0][3] = -(right + left) / width;
        m[1][0] = 0;         m[1][1] = 2 / height; m[1][2] = 0;          m[1][3] = -(top + bottom) / height;
        m[2][0] = 0;         m[2][1] = 0;          m[2][2] = -2 / depth; m[2][3] = -(far + near) / depth;
        m[3][0] = 0;         m[3][1] = 0;          m[3][2] = 0;          m[3][3] = 1;

        return this;
    }

    /**
     * 初始化旋转矩阵
     * @param forward
     * @param up
     * @return
     */
    public Matrix4f initRotation(Vector3f forward, Vector3f up) {
        Vector3f f = forward.normalize();

        Vector3f r = up.normalize();
        r = r.cross(f);

        Vector3f u = f.cross(r);

        return initRotation(f, u, r);
    }

    /**
     * 初始化旋转矩阵
     * @param forward
     * @param up
     * @param right
     * @return
     */
    public Matrix4f initRotation(Vector3f forward, Vector3f up, Vector3f right) {
        Vector3f f = forward;
        Vector3f r = right;
        Vector3f u = up;

        m[0][0] = r.x; m[0][1] = r.y; m[0][2] = r.z; m[0][3] = 0;
        m[1][0] = u.x; m[1][1] = u.y; m[1][2] = u.z; m[1][3] = 0;
        m[2][0] = f.x; m[2][1] = f.y; m[2][2] = f.z; m[2][3] = 0;
        m[3][0] = 0;   m[3][1] = 0;   m[3][2] = 0;   m[3][3] = 1;

        return this;
    }

    /**
     * 对象量进行线性变换
     * @param r
     * @return
     */
    public Vector3f transform(Vector3f r) {
        return new Vector3f(m[0][0] * r.x + m[0][1] * r.y + m[0][2] * r.z + m[0][3],
                m[1][0] * r.x + m[1][1] * r.y + m[1][2] * r.z + m[1][3],
                m[2][0] * r.x + m[2][1] * r.y + m[2][2] * r.z + m[2][3]);
    }

    /**
     * 矩阵乘法
     * @param r
     * @return
     */
    public Matrix4f mul(Matrix4f r) {
        Matrix4f res = new Matrix4f();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res.set(i, j,
                        m[i][0] * r.get(0, j) + m[i][1] * r.get(1, j) + m[i][2] * r.get(2, j) + m[i][3] * r.get(3, j));
            }
        }

        return res;
    }

    public float[][] getM() {
        float[][] res = new float[4][4];

        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                res[i][j] = m[i][j];

        return res;
    }

    public void setM(float[][] m) {
        this.m = m;
    }
    
    /**
     * 以行向量模式，把矩阵的值复制到一个float数组中。
     * 
     * @param matrix
     */
    public void get(float[] matrix) {
        get(matrix, true);
    }

    /**
     * 将矩阵数据保存到一个float数组中。
     * 
     * @param matrix
     * @param rowMajor 判断输出数据是行优先还是列优先
     */
    public void get(float[] matrix, boolean rowMajor) {
        if (matrix.length != 16) {
            throw new IllegalArgumentException("Array must be of size 16.");
        }

        if (rowMajor) {
            matrix[0] = m00;
            matrix[1] = m01;
            matrix[2] = m02;
            matrix[3] = m03;
            matrix[4] = m10;
            matrix[5] = m11;
            matrix[6] = m12;
            matrix[7] = m13;
            matrix[8] = m20;
            matrix[9] = m21;
            matrix[10] = m22;
            matrix[11] = m23;
            matrix[12] = m30;
            matrix[13] = m31;
            matrix[14] = m32;
            matrix[15] = m33;
        } else {
            matrix[0] = m00;
            matrix[4] = m01;
            matrix[8] = m02;
            matrix[12] = m03;
            matrix[1] = m10;
            matrix[5] = m11;
            matrix[9] = m12;
            matrix[13] = m13;
            matrix[2] = m20;
            matrix[6] = m21;
            matrix[10] = m22;
            matrix[14] = m23;
            matrix[3] = m30;
            matrix[7] = m31;
            matrix[11] = m32;
            matrix[15] = m33;
        }
    }

    /**
     * 获取第i行第j列元素
     * 
     * @param i
     * @param j
     * @return
     */
    public float get(int i, int j) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    case 3:
                        return m03;
                }
            case 1:
                switch (j) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    case 3:
                        return m13;
                }
            case 2:
                switch (j) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    case 3:
                        return m23;
                }
            case 3:
                switch (j) {
                    case 0:
                        return m30;
                    case 1:
                        return m31;
                    case 2:
                        return m32;
                    case 3:
                        return m33;
                }
        }

        throw new IllegalArgumentException("Invalid indices into matrix.");
    }

    /**
     * 获取矩阵第i列数据
     * 
     * @param i
     * @return
     */
    public float[] getColumn(int i) {
        return getColumn(i, null);
    }

    /**
     * 将第i列数据保存到一个float数组中。
     * 
     * @param i
     * @param store
     * @return
     */
    public float[] getColumn(int i, float[] store) {
        if (store == null) {
            store = new float[4];
        }
        switch (i) {
            case 0:
                store[0] = m00;
                store[1] = m10;
                store[2] = m20;
                store[3] = m30;
                break;
            case 1:
                store[0] = m01;
                store[1] = m11;
                store[2] = m21;
                store[3] = m31;
                break;
            case 2:
                store[0] = m02;
                store[1] = m12;
                store[2] = m22;
                store[3] = m32;
                break;
            case 3:
                store[0] = m03;
                store[1] = m13;
                store[2] = m23;
                store[3] = m33;
                break;
            default:
                throw new IllegalArgumentException("Invalid column index. " + i);
        }
        return store;
    }

    /**
     * 设置矩阵第i列的值
     * 
     * @param i
     * @param column
     */
    public void setColumn(int i, float[] column) {

        if (column == null) {
            return;
        }
        switch (i) {
            case 0:
                m00 = column[0];
                m10 = column[1];
                m20 = column[2];
                m30 = column[3];
                break;
            case 1:
                m01 = column[0];
                m11 = column[1];
                m21 = column[2];
                m31 = column[3];
                break;
            case 2:
                m02 = column[0];
                m12 = column[1];
                m22 = column[2];
                m32 = column[3];
                break;
            case 3:
                m03 = column[0];
                m13 = column[1];
                m23 = column[2];
                m33 = column[3];
                break;
            default:
                throw new IllegalArgumentException("Invalid column index. " + i);
        }
    }

    /**
     * 设置矩阵第i行第j列的值。
     * 
     * @param i
     * @param j
     * @param value
     */
    public void set(int i, int j, float value) {
        switch (i) {
            case 0:
                switch (j) {
                    case 0:
                        m00 = value;
                        return;
                    case 1:
                        m01 = value;
                        return;
                    case 2:
                        m02 = value;
                        return;
                    case 3:
                        m03 = value;
                        return;
                }
            case 1:
                switch (j) {
                    case 0:
                        m10 = value;
                        return;
                    case 1:
                        m11 = value;
                        return;
                    case 2:
                        m12 = value;
                        return;
                    case 3:
                        m13 = value;
                        return;
                }
            case 2:
                switch (j) {
                    case 0:
                        m20 = value;
                        return;
                    case 1:
                        m21 = value;
                        return;
                    case 2:
                        m22 = value;
                        return;
                    case 3:
                        m23 = value;
                        return;
                }
            case 3:
                switch (j) {
                    case 0:
                        m30 = value;
                        return;
                    case 1:
                        m31 = value;
                        return;
                    case 2:
                        m32 = value;
                        return;
                    case 3:
                        m33 = value;
                        return;
                }
        }

        throw new IllegalArgumentException("Invalid indices into matrix.");
    }

    /**
     * 使用二维数组来为矩阵赋值。
     * 
     * @param matrix
     */
    public void set(float[][] matrix) {
        if (matrix.length != 4 || matrix[0].length != 4) {
            throw new IllegalArgumentException("Array must be of size 16.");
        }

        m00 = matrix[0][0];
        m01 = matrix[0][1];
        m02 = matrix[0][2];
        m03 = matrix[0][3];
        m10 = matrix[1][0];
        m11 = matrix[1][1];
        m12 = matrix[1][2];
        m13 = matrix[1][3];
        m20 = matrix[2][0];
        m21 = matrix[2][1];
        m22 = matrix[2][2];
        m23 = matrix[2][3];
        m30 = matrix[3][0];
        m31 = matrix[3][1];
        m32 = matrix[3][2];
        m33 = matrix[3][3];
    }
    
    
    /**
     * 根据给定的值来为矩阵赋值
     */
    public void set(float m00, float m01, float m02, float m03,
            float m10, float m11, float m12, float m13,
            float m20, float m21, float m22, float m23,
            float m30, float m31, float m32, float m33) {

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }
    
    /**
     * 复制另一个Matrix4f的值
     * @param matrix
     */
    public void set(Matrix4f matrix) {
        m00 = matrix.m00;
        m01 = matrix.m01;
        m02 = matrix.m02;
        m03 = matrix.m03;
        m10 = matrix.m10;
        m11 = matrix.m11;
        m12 = matrix.m12;
        m13 = matrix.m13;
        m20 = matrix.m20;
        m21 = matrix.m21;
        m22 = matrix.m22;
        m23 = matrix.m23;
        m30 = matrix.m30;
        m31 = matrix.m31;
        m32 = matrix.m32;
        m33 = matrix.m33;
    }

    /**
     * 按行优先规则，把数组中的值赋给矩阵。
     * 
     * @param matrix
     */
    public void set(float[] matrix) {
        set(matrix, true);
    }

    /**
     * 使用给定数组来为矩阵赋值
     * 
     * @param matrix
     * @param rowMajor 判断输入数据是否为行优先，还是列优先。
     */
    public void set(float[] matrix, boolean rowMajor) {
        if (matrix.length != 16) {
            throw new IllegalArgumentException(
                    "Array must be of size 16.");
        }

        if (rowMajor) {
            m00 = matrix[0];
            m01 = matrix[1];
            m02 = matrix[2];
            m03 = matrix[3];
            m10 = matrix[4];
            m11 = matrix[5];
            m12 = matrix[6];
            m13 = matrix[7];
            m20 = matrix[8];
            m21 = matrix[9];
            m22 = matrix[10];
            m23 = matrix[11];
            m30 = matrix[12];
            m31 = matrix[13];
            m32 = matrix[14];
            m33 = matrix[15];
        } else {
            m00 = matrix[0];
            m01 = matrix[4];
            m02 = matrix[8];
            m03 = matrix[12];
            m10 = matrix[1];
            m11 = matrix[5];
            m12 = matrix[9];
            m13 = matrix[13];
            m20 = matrix[2];
            m21 = matrix[6];
            m22 = matrix[10];
            m23 = matrix[14];
            m30 = matrix[3];
            m31 = matrix[7];
            m32 = matrix[11];
            m33 = matrix[15];
        }
    }
}
