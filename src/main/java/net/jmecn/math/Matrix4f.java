package net.jmecn.math;

/**
 * 4*4矩阵
 * 
 * @author yanmaoyuan
 *
 */
public class Matrix4f {
    
    protected float m00, m01, m02, m03;
    protected float m10, m11, m12, m13;
    protected float m20, m21, m22, m23;
    protected float m30, m31, m32, m33;

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
     * 4*4矩阵的第四列表示位移
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f initTranslation(float x, float y, float z) {
        m00 = 1; m01 = 0; m02 = 0; m03 = x;
        m10 = 0; m11 = 1; m12 = 0; m13 = y;
        m20 = 0; m21 = 0; m22 = 1; m23 = z;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;

        return this;
    }

    /**
     * 4*4矩阵的左上角3个Vector3f，表示缩放矩阵。
     * @param v
     * @return
     */
    public Matrix4f fromScale(Vector3f v) {
        m00 = v.x; m01 = 0;   m02 = 0;   m03 = 0;
        m10 = 0;   m11 = v.y; m12 = 0;   m13 = 0;
        m20 = 0;   m21 = 0;   m22 = v.z; m23 = 0;
        m30 = 0;   m31 = 0;   m32 = 0;   m33 = 1;
        return this;
    }
    
    /**
     * 4*4矩阵的左上角3个Vector3f，表示缩放矩阵。
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Matrix4f fromScale(float x, float y, float z) {
        m00 = x; m01 = 0; m02 = 0; m03 = 0;
        m10 = 0; m11 = y; m12 = 0; m13 = 0;
        m20 = 0; m21 = 0; m22 = z; m23 = 0;
        m30 = 0; m31 = 0; m32 = 0; m33 = 1;
        return this;
    }
    
    /**
     * 绕x轴旋转
     * @param angle
     * @return
     */
    public Matrix4f fromRotateX(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        
        m00 = 1; m11 = 0;   m12 = 0;
        m10 = 0; m11 = cos; m12 = -sin;
        m20 = 0; m21 = sin; m22 = cos;
        
        return this;
    }
    
    /**
     * 绕y轴旋转
     * @param angle
     * @return
     */
    public Matrix4f fromRotateY(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        
        m00 = cos;  m11 = 0; m12 = sin;
        m10 = 0;    m11 = 1; m12 = 0;
        m20 = -sin; m21 = 0; m22 = cos;
        
        return this;
    }
    
    /**
     * 绕z轴旋转
     * @param angle
     * @return
     */
    public Matrix4f fromRotateZ(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        
        m00 = cos; m11 = -sin; m12 = 0;
        m10 = sin; m11 = cos;  m12 = 0;
        m20 = 0;   m21 = 0;    m22 = 1;
        
        return this;
    }

    /**
     * 轴角对旋转矩阵
     * @param v
     * @param angle
     * @return
     */
    public Matrix4f fromAxisAngle(Vector3f v, float angle) {
        return fromAxisAngle(v.x, v.y, v.z, angle);
    }
    
    /**
     * 轴角对旋转矩阵。
     * @param vx
     * @param vy
     * @param vz
     * @param angle
     * @return
     */
    public Matrix4f fromAxisAngle(float vx, float vy, float vz, float angle) {
        zero();
        m33 = 1;
        
        float length = vx * vx + vy * vy + vz * vz;
        if (length == 0) {
            return this;
        }
        
        // 先把向量规范化
        if (Math.abs(length - 1.0) > 0.0001) {
            length = (float) (1.0 / Math.sqrt(length));
            vx *= length;
            vy *= length;
            vz *= length;
        }
        
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        
        // 节省5次减法运算
        float _1_minus_cos = 1f - cos;

        // 节省3次乘法运算
        float xSin = vx * sin;
        float ySin = vy * sin;
        float zSin = vz * sin;
        
        // 节省6次乘法运算
        float xyM = vx * vy * _1_minus_cos;
        float xzM = vx * vz * _1_minus_cos;
        float yzM = vy * vz * _1_minus_cos;
        
        m00 = vx * vx * _1_minus_cos + cos;
        m01 = xyM - zSin;
        m02 = xzM + ySin;
        m10 = xyM + zSin;
        m11 = vy * vy * _1_minus_cos + cos;
        m12 = yzM - xSin;
        m20 = xzM - ySin;
        m21 = yzM + xSin;
        m22 = vz * vz * _1_minus_cos + cos;

        return this;
    }
    
    /**
     * 获得平移变换向量
     * @return
     */
    public Vector3f toTranslationVector() {
        return new Vector3f(m03, m13, m23);
    }

    /**
     * 获得平移变换向量
     * @param vector
     */
    public void toTranslationVector(Vector3f vector) {
        vector.set(m03, m13, m23);
    }
    
    /**
     * 旋转矩阵转为四元数
     * @return
     */
    public Quaternion toRotationQuat() {
        Quaternion quat = new Quaternion();
        quat.fromRotationMatrix(toRotationMatrix());
        return quat;
    }

    /**
     * 旋转矩阵转为四元数
     * @param q
     */
    public void toRotationQuat(Quaternion q) {
        q.fromRotationMatrix(toRotationMatrix());
    }

    /**
     * 获得旋转矩阵
     * @return
     */
    public Matrix3f toRotationMatrix() {
        return new Matrix3f(m00, m01, m02, m10, m11, m12, m20, m21, m22);
    }

    /**
     * 获得旋转矩阵
     * @param mat
     */
    public void toRotationMatrix(Matrix3f mat) {
        mat.m00 = m00;
        mat.m01 = m01;
        mat.m02 = m02;
        mat.m10 = m10;
        mat.m11 = m11;
        mat.m12 = m12;
        mat.m20 = m20;
        mat.m21 = m21;
        mat.m22 = m22;
    }
    
    /**
     * 获得比例变换向量
     * 
     * @return
     */
    public Vector3f toScaleVector() {
        Vector3f result = new Vector3f();
        this.toScaleVector(result);
        return result;
    }

    /**
     * 设置比例变换向量
     * 
     * @param the
     */
    public void toScaleVector(Vector3f vector) {
        float scaleX = (float) Math.sqrt(m00 * m00 + m10 * m10 + m20 * m20);
        float scaleY = (float) Math.sqrt(m01 * m01 + m11 * m11 + m21 * m21);
        float scaleZ = (float) Math.sqrt(m02 * m02 + m12 * m12 + m22 * m22);
        vector.set(scaleX, scaleY, scaleZ);
    }

    /**
     * 设置比例变换
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setScale(float x, float y, float z) {
        Vector3f tmp = new Vector3f();
        
        tmp.set(m00, m10, m20);
        tmp.normalizeLocal().multLocal(x);
        m00 = tmp.x;
        m10 = tmp.y;
        m20 = tmp.z;

        tmp.set(m01, m11, m21);
        tmp.normalizeLocal().multLocal(y);
        m01 = tmp.x;
        m11 = tmp.y;
        m21 = tmp.z;

        tmp.set(m02, m12, m22);
        tmp.normalizeLocal().multLocal(z);
        m02 = tmp.x;
        m12 = tmp.y;
        m22 = tmp.z;
    }

    /**
     * 设置比例变换
     * 
     * @param scale
     */
    public void setScale(Vector3f scale) {
        this.setScale(scale.x, scale.y, scale.z);
    }

    /**
     * 设置平移变换
     * 
     * @param translation
     */
    public void setTranslation(float[] translation) {
        if (translation.length != 3) {
            throw new IllegalArgumentException("Translation size must be 3.");
        }
        m03 = translation[0];
        m13 = translation[1];
        m23 = translation[2];
    }

    /**
     * 设置平移变换
     * 
     * @param x
     * @param y
     * @param z
     */
    public void setTranslation(float x, float y, float z) {
        m03 = x;
        m13 = y;
        m23 = z;
    }

    /**
     * 设置平移变换
     *
     * @param translation
     */
    public void setTranslation(Vector3f translation) {
        m03 = translation.x;
        m13 = translation.y;
        m23 = translation.z;
    }
    
    /**
     * 设置旋转变换
     * @param quat
     */
    public void setRotationQuaternion(Quaternion quat) {
        quat.toRotationMatrix(this);
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
