package net.jmecn.renderer;

import net.jmecn.math.Matrix4f;
import net.jmecn.math.Vector3f;

/**
 * 摄像机
 * 
 * @author yanmaoyuan
 *
 */
public class Camera {

    /**
     * 观察位置。初始位置位于Z轴的正方向，离世界空间中心点10个单位距离。
     */
    private Vector3f location = new Vector3f(0, 0, 10);
    /**
     * 观察向上向量。默认为Y轴正方向
     */
    private Vector3f up = new Vector3f(0, 1, 0);
    /**
     * 观察的方向。默认为Z轴负方向
     */
    private Vector3f direction = new Vector3f(0, 0, -1);

    private int width;
    private int height;
    
    // 平行投影
    private boolean parallel = false;
    
    // 观察变换矩阵
    private Matrix4f viewMatrix = new Matrix4f();
    // 投影变换矩阵
    private Matrix4f projectionMatrix = new Matrix4f();
    // 屏幕空间变换矩阵
    private Matrix4f screenSpaceMatrix = new Matrix4f();
    
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        
        initViewMatrix();
        initProjectionMatrix();
        initScreenSpaceMatrix();
    }
    
    public Vector3f getLocation() {
        return location;
    }

    public void setLocation(Vector3f location) {
        this.location.set(location);
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction.set(direction);
    }

    public boolean isParallel() {
        return parallel;
    }

    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }
    /**
     * 观察变换矩阵
     */
    public void initViewMatrix() {
        // 单位矩阵
        float m00 = 1, m01 = 0, m02 = 0, m03 = 0;
        float m10 = 0, m11 = 1, m12 = 0, m13 = 0;
        float m20 = 0, m21 = 0, m22 = 1, m23 = 0;
        float m30 = 0, m31 = 0, m32 = 0, m33 = 1;

        Vector3f leftVector = direction.cross(up);
        Vector3f upVector = leftVector.cross(direction);

        m00 = leftVector.x; m01 = leftVector.y; m02 = leftVector.z; m03 = -leftVector.dot(location);

        m10 = upVector.x; m11 = upVector.y; m12 = upVector.z; m13 = -upVector.dot(location);

        m20 = -direction.x; m21 = -direction.y; m22 = -direction.z; m23 = direction.dot(location);

        m30 = 0f; m31 = 0f; m32 = 0f; m33 = 1f;

        viewMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 投影变换矩阵
     */
    public void initProjectionMatrix() {
        if (parallel) {
            // 正交投影
            float near = 1.0f;
            float far = 2.0f;
            float left = -0.5f;
            float right = 0.5f;
            float top = 0.5f;
            float bottom = -0.5f;
            
            initOrthographic(left, right, bottom, top, near, far);
        } else {
            // 透视投影
            float fov = (float) Math.toRadians(70);// 视野范围 70°
            float aspectRatio = (float) height / width;// 屏幕高宽比
            float near = 0.1f;
            float far = 1000f;
            
            initPerspective(fov, aspectRatio, near, far);
        }
    }
    
    /**
     * 透视投影
     */
    public void initPerspective(float fov, float aspect, float near, float far) {
        float e = 1f / (float)Math.tan(fov * 0.5f);// 焦距
        float a = aspect;
        float range = near - far;

        float m00 = e, m01 = 0,     m02 = 0,                 m03 = 0;
        float m10 = 0, m11 = e * a, m12 = 0,                 m13 = 0;
        float m20 = 0, m21 = 0,     m22 = (far+near)/range, m23 = 2 * far * near / range;
        float m30 = 0, m31 = 0,     m32 = -1,                 m33 = 0;

        projectionMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 正交投影
     */
    public void initOrthographic(float left, float right, float bottom, float top, float near, float far) {
        
        float width = right - left;
        float height = top - bottom;
        float depth = far - near;
        
        // 计算矩阵
        float m00 = 2 / width, m01 = 0,        m02 = 0,        m03 = -(right + left)/width;
        float m10 = 0,         m11 = 2/height, m12 = 0,        m13 = -(top + bottom)/height;
        float m20 = 0,         m21 = 0,        m22 = -2/depth, m23 = -(far + near)/depth;
        float m30 = 0,         m31 = 0,        m32 = 0,        m33 = 1;
        
        projectionMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 屏幕空间变换矩阵
     */
    public void initScreenSpaceMatrix() {
        float w = width * 0.5f;
        float h = height * 0.5f;
        
        // 把模型移到屏幕中心，并且按屏幕比例放大。
        float m00 = h, m01 = 0,  m02 = 0, m03 = w;
        float m10 = 0, m11 = -w, m12 = 0, m13 = h;
        float m20 = 0, m21 = 0,  m22 = 1, m23 = 0;
        float m30 = 0, m31 = 0,  m32 = 0, m33 = 1;
        
        screenSpaceMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    public Matrix4f getScreenSpaceMatrix() {
        return screenSpaceMatrix;
    }
}
