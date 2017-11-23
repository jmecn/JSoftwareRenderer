package net.jmecn.math;

/**
 * 4*4矩阵
 * 
 * @author yanmaoyuan
 *
 */
public class Matrix4f {
    private float[][] m;

    public Matrix4f() {
        m = new float[4][4];
    }

    /**
     * 单位矩阵
     * @return
     */
    public Matrix4f initIdentity() {
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = 0;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = 0;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = 0;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

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

    public float get(int x, int y) {
        return m[x][y];
    }

    public void setM(float[][] m) {
        this.m = m;
    }

    public void set(int x, int y, float value) {
        m[x][y] = value;
    }
}
