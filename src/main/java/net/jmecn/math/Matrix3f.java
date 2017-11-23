package net.jmecn.math;

/**
 * 3*3矩阵
 * 
 * @author yanmaoyuan
 *
 */
public class Matrix3f {

    private float[][] m;
    
    public Matrix3f() {
        m = new float[3][3];
    }
    
    /**
     * 单位矩阵
     * @return
     */
    public Matrix3f initIdentity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1;
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
    public Matrix3f initRotation(float x, float y, float z, float angle) {
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        m[0][0] = cos + x * x * (1 - cos);     m[0][1] = x * y * (1 - cos) - z * sin; m[0][2] = x * z * (1 - cos) + y * sin;
        m[1][0] = y * x * (1 - cos) + z * sin; m[1][1] = cos + y * y * (1 - cos);     m[1][2] = y * z * (1 - cos) - x * sin;
        m[2][0] = z * x * (1 - cos) - y * sin; m[2][1] = z * y * (1 - cos) + x * sin; m[2][2] = cos + z * z * (1 - cos);

        return this;
    }
}
