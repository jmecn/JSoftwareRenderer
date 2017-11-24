package net.jmecn.math;

/**
 * 3*3矩阵
 * 
 * @author yanmaoyuan
 *
 */
public class Matrix3f {

    protected float m00, m01, m02;
    protected float m10, m11, m12;
    protected float m20, m21, m22;
    
    /**
     * 零矩阵
     */
    public static final Matrix3f ZERO = new Matrix3f(0, 0, 0, 0, 0, 0, 0, 0, 0);
    
    /**
     * 单位矩阵
     */
    public final static Matrix3f IDENTITY = new Matrix3f();
    
    /**
     * 初始化为单位矩阵。
     */
    public Matrix3f() {
        loadIdentity();
    }
    
    /**
     * 初始化矩阵
     * @param m00
     * @param m01
     * @param m02
     * @param m10
     * @param m11
     * @param m12
     * @param m20
     * @param m21
     * @param m22
     */
    public Matrix3f(float m00, float m01, float m02, float m10, float m11,
            float m12, float m20, float m21, float m22) {

        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }
    
    /**
     * 使用另一个矩阵来初始化矩阵
     * @param mat3
     */
    public Matrix3f(Matrix3f mat3) {
        set(mat3);
    }
    
    /**
     * 复制另一个矩阵的值
     * @param matrix
     * @return
     */
    public Matrix3f set(Matrix3f matrix) {
        if (null == matrix) {
            loadIdentity();
        } else {
            m00 = matrix.m00;
            m01 = matrix.m01;
            m02 = matrix.m02;
            m10 = matrix.m10;
            m11 = matrix.m11;
            m12 = matrix.m12;
            m20 = matrix.m20;
            m21 = matrix.m21;
            m22 = matrix.m22;
        }
        return this;
    }
    
    /**
     * 单位矩阵
     */
    public void loadIdentity() {
        m01 = m02 = m10 = m12 = m20 = m21 = 0;
        m00 = m11 = m22 = 1;
    }
    
    /**
     * 零矩阵
     */
    public Matrix3f zero() {
        m00 = m01 = m02 = m10 = m11 = m12 = m20 = m21 = m22 = 0.0f;
        return this;
    }
    
    /**
     * 求转置矩阵
     * @return
     */
    public Matrix3f transpose() {
        return new Matrix3f(m00, m10, m20, m01, m11, m21, m02, m12, m22);
    }
    
    /**
     * 求转置矩阵
     * @return
     */
    public Matrix3f transposeLocal() {
        float tmp = m01;
        m01 = m10;
        m10 = tmp;

        tmp = m02;
        m02 = m20;
        m20 = tmp;

        tmp = m12;
        m12 = m21;
        m21 = tmp;
        
        return this;
    }
    
    /**
     * 标量乘法
     * @param scalor
     * @return
     */
    public Matrix3f multLocal(float scalor) {
        m00 *= scalor;
        m01 *= scalor;
        m02 *= scalor;
        m10 *= scalor;
        m11 *= scalor;
        m12 *= scalor;
        m20 *= scalor;
        m21 *= scalor;
        m22 *= scalor;
        return this;
    }
    
    /**
     * 矩阵乘法
     * 
     * @param mat
     * @param product 结果矩阵
     * @return 返回一个新的Matrix3f对象，携带计算结果。
     */
    public Matrix3f mult(Matrix3f mat, Matrix3f product) {

        float temp00, temp01, temp02;
        float temp10, temp11, temp12;
        float temp20, temp21, temp22;

        if (product == null) {
            product = new Matrix3f();
        }
        temp00 = m00 * mat.m00 + m01 * mat.m10 + m02 * mat.m20;
        temp01 = m00 * mat.m01 + m01 * mat.m11 + m02 * mat.m21;
        temp02 = m00 * mat.m02 + m01 * mat.m12 + m02 * mat.m22;
        temp10 = m10 * mat.m00 + m11 * mat.m10 + m12 * mat.m20;
        temp11 = m10 * mat.m01 + m11 * mat.m11 + m12 * mat.m21;
        temp12 = m10 * mat.m02 + m11 * mat.m12 + m12 * mat.m22;
        temp20 = m20 * mat.m00 + m21 * mat.m10 + m22 * mat.m20;
        temp21 = m20 * mat.m01 + m21 * mat.m11 + m22 * mat.m21;
        temp22 = m20 * mat.m02 + m21 * mat.m12 + m22 * mat.m22;

        product.m00 = temp00;
        product.m01 = temp01;
        product.m02 = temp02;
        product.m10 = temp10;
        product.m11 = temp11;
        product.m12 = temp12;
        product.m20 = temp20;
        product.m21 = temp21;
        product.m22 = temp22;

        return product;
    }
    
    /**
     * 矩阵乘法
     * @param mat3
     * @return
     */
    public Matrix3f mult(Matrix3f mat) {
        return mult(mat, null);
    }

    /**
     * 矩阵乘法
     * @param mat
     * @return
     */
    public Matrix3f multLocal(Matrix3f mat) {
        return mult(mat, this);
    }
    
    /**
     * 计算矩阵与vec的乘积，结果保存在新的Vector3f对象中。
     * 
     * @param vec
     * @return
     */
    public Vector3f mult(Vector3f vec) {
        return mult(vec, null);
    }

    /**
     * 计算矩阵与vec的乘积，结果保存在product对象中。
     * 
     * @param vec
     * @param product
     * @return
     */
    public Vector3f mult(Vector3f vec, Vector3f product) {

        if (null == product) {
            product = new Vector3f();
        }

        float x = vec.x;
        float y = vec.y;
        float z = vec.z;

        product.x = m00 * x + m01 * y + m02 * z;
        product.y = m10 * x + m11 * y + m12 * z;
        product.z = m20 * x + m21 * y + m22 * z;
        return product;
    }

    /**
     * 计算矩阵与向量vec的乘积，结果保存在该vec中。
     * 
     * @param vec
     * @return
     */
    public Vector3f multLocal(Vector3f vec) {
        if (vec == null) {
            return null;
        }
        float x = vec.x;
        float y = vec.y;
        vec.x = m00 * x + m01 * y + m02 * vec.z;
        vec.y = m10 * x + m11 * y + m12 * vec.z;
        vec.z = m20 * x + m21 * y + m22 * vec.z;
        return vec;
    }
    
    /**
     * 计算行列式
     * @return
     */
    public float determinant() {
        float fCo00 = m11 * m22 - m12 * m21;
        float fCo10 = m12 * m20 - m10 * m22;
        float fCo20 = m10 * m21 - m11 * m20;
        float fDet = m00 * fCo00 + m01 * fCo10 + m02 * fCo20;
        return fDet;
    }
    
    /**
     * 求Matrix3f的标准伴随矩阵。
     * 
     * @return
     */
    public Matrix3f adjoint() {
        return adjoint(null);
    }

    /**
     * 求Matrix3f的标准伴随矩阵，结果保存为store对象。
     * 
     * @param store
     * @return
     */
    public Matrix3f adjoint(Matrix3f store) {
        if (store == null) {
            store = new Matrix3f();
        }

        store.m00 = m11 * m22 - m12 * m21;
        store.m01 = m02 * m21 - m01 * m22;
        store.m02 = m01 * m12 - m02 * m11;
        store.m10 = m12 * m20 - m10 * m22;
        store.m11 = m00 * m22 - m02 * m20;
        store.m12 = m02 * m10 - m00 * m12;
        store.m20 = m10 * m21 - m11 * m20;
        store.m21 = m01 * m20 - m00 * m21;
        store.m22 = m00 * m11 - m01 * m10;

        return store;
    }
    
    /**
     * 求矩阵的逆
     * 
     * @return
     */
    public Matrix3f invert() {
        return invert(null);
    }

    /**
     * 求矩阵的逆
     * 
     * @return The store
     */
    public Matrix3f invert(Matrix3f store) {
        if (store == null) {
            store = new Matrix3f();
        }

        float det = determinant();
        if (Math.abs(det) <= 0f) {
            return store.zero();
        }

        store.m00 = m11 * m22 - m12 * m21;
        store.m01 = m02 * m21 - m01 * m22;
        store.m02 = m01 * m12 - m02 * m11;
        store.m10 = m12 * m20 - m10 * m22;
        store.m11 = m00 * m22 - m02 * m20;
        store.m12 = m02 * m10 - m00 * m12;
        store.m20 = m10 * m21 - m11 * m20;
        store.m21 = m01 * m20 - m00 * m21;
        store.m22 = m00 * m11 - m01 * m10;

        store.multLocal(1f / det);
        return store;
    }

    /**
     * 求矩阵的逆
     * 
     * @return this
     */
    public Matrix3f invertLocal() {
        float det = determinant();
        if (Math.abs(det) <= 0f) {
            return zero();
        }

        float f00 = m11 * m22 - m12 * m21;
        float f01 = m02 * m21 - m01 * m22;
        float f02 = m01 * m12 - m02 * m11;
        float f10 = m12 * m20 - m10 * m22;
        float f11 = m00 * m22 - m02 * m20;
        float f12 = m02 * m10 - m00 * m12;
        float f20 = m10 * m21 - m11 * m20;
        float f21 = m01 * m20 - m00 * m21;
        float f22 = m00 * m11 - m01 * m10;

        m00 = f00;
        m01 = f01;
        m02 = f02;
        m10 = f10;
        m11 = f11;
        m12 = f12;
        m20 = f20;
        m21 = f21;
        m22 = f22;

        multLocal(1f / det);
        return this;
    }
    
    /**
     * 比例变换矩阵
     * @param v
     * @return
     */
    public Matrix3f fromScale(Vector3f v) {
        m00 = v.x; m01 = 0;   m02 = 0;
        m10 = 0;   m11 = v.y; m12 = 0;
        m20 = 0;   m21 = 0;   m22 = v.z;
        return this;
    }
    
    /**
     * 比例变换矩阵
     * @param v
     * @return
     */
    public Matrix3f fromScale(float x, float y, float z) {
        m00 = x; m01 = 0; m02 = 0;
        m10 = 0; m11 = y; m12 = 0;
        m20 = 0; m21 = 0; m22 = z;
        return this;
    }
    
    /**
     * 绕x轴旋转
     * @param angle
     * @return
     */
    public Matrix3f fromRotateX(float angle) {
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
    public Matrix3f fromRotateY(float angle) {
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
    public Matrix3f fromRotateZ(float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        
        m00 = cos; m11 = -sin; m12 = 0;
        m10 = sin; m11 = cos;  m12 = 0;
        m20 = 0;   m21 = 0;    m22 = 1;
        
        return this;
    }
    
    /**
     * 欧拉角旋转
     * @param xAngle
     * @param yAngle
     * @param zAngle
     * @return
     */
    public Matrix3f fromRotate(float xAngle, float yAngle, float zAngle) {
        // FIXME 这个计算方法的性能很差，可以直接根据矩阵乘法规则算出结果矩阵，然后化简。
        Matrix3f rotateX = new Matrix3f().fromRotateX(xAngle);
        Matrix3f rotateY = new Matrix3f().fromRotateX(yAngle);
        Matrix3f rotateZ = new Matrix3f().fromRotateX(zAngle);
        
        Matrix3f result = rotateZ.mult(rotateY).mult(rotateX);
        return result;
    }
    
    /**
     * 轴角对旋转矩阵
     * @param v
     * @param angle
     * @return
     */
    public Matrix3f fromAxisAngle(Vector3f v, float angle) {
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
    public Matrix3f fromAxisAngle(float vx, float vy, float vz, float angle) {
        zero();
        
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
}
