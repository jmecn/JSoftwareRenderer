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
     * 观察的方向。默认为Z轴负方向
     */
    private Vector3f direction = new Vector3f(0, 0, -1);
    /**
     * 观察向上向量。默认为Y轴正方向
     */
    private Vector3f up = new Vector3f(0, 1, 0);
    
    /**
     * 摄像机旋转矩阵的单位向量。
     */
    private Vector3f xAxis = new Vector3f(1, 0, 0);
    private Vector3f yAxis = new Vector3f(0, 1, 0);
    private Vector3f zAxis = new Vector3f(0, 0, 1);
    
    // 平行投影
    private boolean parallel = false;
    
    /**
     * 观察变换矩阵
     */
    private Matrix4f viewMatrix = new Matrix4f();
    /**
     * 投影变换矩阵
     */
    private Matrix4f projectionMatrix = new Matrix4f();
    /**
     * 观察-投影 变换矩阵
     */
    private Matrix4f viewProjectionMatrix = new Matrix4f();
    
    /**
     * 屏幕的宽度和高度
     */
    private int width;
    private int height;
    /**
     * 屏幕空间变换矩阵
     */
    private Matrix4f viewportMatrix = new Matrix4f();
    
    /**
     * 初始化摄像机
     * @param width
     * @param height
     */
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
        
        // 计算观察-投影变换矩阵
        updateViewProjectionMatrix();
        
        // 计算屏幕空间变换矩阵
        updateViewportMatrix();
    }
    
    /**
     * 获取位置
     * @return
     */
    public Vector3f getLocation() {
        return location;
    }

    /**
     * 设置观察位置
     * @param location
     */
    public void setLocation(Vector3f location) {
        this.location.set(location);

        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }

    /**
     * 获取观察方向
     * @return
     */
    public Vector3f getDirection() {
        return direction;
    }

    /**
     * 设置观察方向
     * @param direction
     */
    public void setDirection(Vector3f direction) {
        this.direction.set(direction);
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }

    /**
     * 获取观察方向的正右方向量
     * @return
     */
    public Vector3f getRightVector() {
        return xAxis;
    }
    
    /**
     * 获取观察方向的正上方向量
     * @return
     */
    public Vector3f getUpVector() {
        return yAxis;
    }
    
    /**
     * 是否平行投影
     * @return
     */
    public boolean isParallel() {
        return parallel;
    }

    /**
     * 设置平行投影
     * @param parallel
     */
    public void setParallel(boolean parallel) {
        this.parallel = parallel;
    }
    
    /**
     * 获取观察变换矩阵
     * @return
     */
    public Matrix4f getViewMatrix() {
        return viewMatrix;
    }
    
    /**
     * 获取投影变换矩阵
     * @return
     */
    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    /**
     * 获取观察投影变换矩阵
     * @return
     */
    public Matrix4f getViewProjectionMatrix() {
        return viewProjectionMatrix;
    }

    /**
     * 获得视口变换矩阵
     * @return
     */
    public Matrix4f getViewportMatrix() {
        return viewportMatrix;
    }
    
    /**
     * 观察-投影 变换矩阵
     */
    public void updateViewProjectionMatrix() {
        updateViewMatrix();
        updateProjectionMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
    
    /**
     * 观察变换矩阵
     */
    public void updateViewMatrix() {
        // 计算摄像机的旋转矩阵
        direction.cross(up, xAxis);
        xAxis.cross(direction, yAxis);
        zAxis.set(-direction.x, -direction.y, -direction.z);

        // 计算摄像机旋转后的平移变换
        float x = xAxis.dot(location);
        float y = yAxis.dot(location);
        float z = zAxis.dot(location);
        
        // 计算观察变换矩阵
        float m00 = xAxis.x, m01 = xAxis.y, m02 = xAxis.z, m03 = -x;
        float m10 = yAxis.x, m11 = yAxis.y, m12 = yAxis.z, m13 = -y;
        float m20 = zAxis.x, m21 = zAxis.y, m22 = zAxis.z, m23 = -z;
        float m30 = 0f,      m31 = 0f,      m32 = 0f,      m33 = 1f;

        viewMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 投影变换矩阵
     */
    public void updateProjectionMatrix() {
        if (!parallel) {
            // 透视投影
            float fov = (float) Math.toRadians(70);// 视野范围 70°
            float aspect = (float) width / height;// 屏幕高宽比
            float near = 1f;
            float far = 2f;
            
            setPerspective(fov, aspect, near, far);
        } else {
            // 正交投影
            float near = 1.0f;
            float far = 2.0f;
            float left = -0.5f;
            float right = 0.5f;
            float top = 0.5f;
            float bottom = -0.5f;
            
            setOrthographic(left, right, bottom, top, near, far);
        }
    }
    

    /**
     * 透视投影
     * @param fov 视野范围（弧度制）
     * @param aspect 视锥平面的宽高比（w/h）
     * @param near 近平面距离
     * @param far 远平面距离
     */
    public void setPerspective(float fov, float aspect, float near, float far) {
        // X方向的缩放比
        float zoomX = 1f / (float)Math.tan(fov * 0.5f);
        // Y方向的缩放比
        float zoomY = zoomX * aspect;

        float m00 = zoomX, m01 = 0,     m02 = 0,                      m03 = 0;
        float m10 = 0,     m11 = zoomY, m12 = 0,                      m13 = 0;
        float m20 = 0,     m21 = 0,     m22 = -(far+near)/(far-near), m23 = -2*far*near/(far-near);
        float m30 = 0,     m31 = 0,     m32 = -1,                     m33 = 0;

        projectionMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 透视投影（无穷远）
     * @param fov 视野范围（弧度制）
     * @param aspect 视锥平面的宽高比（w/h）
     * @param near 近平面距离
     */
    public void setPerspective(float fov, float aspect, float near) {
        // X方向的缩放比
        float zoomX = 1f / (float)Math.tan(fov * 0.5f);
        // Y方向的缩放比
        float zoomY = zoomX * aspect;

        float m00 = zoomX, m01 = 0,     m02 = 0,  m03 = 0;
        float m10 = 0,     m11 = zoomY, m12 = 0,  m13 = 0;
        float m20 = 0,     m21 = 0,     m22 = -1, m23 = -2 * near;
        float m30 = 0,     m31 = 0,     m32 = -1, m33 = 0;

        projectionMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 正交投影
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public void setOrthographic(float left, float right, float bottom, float top, float near, float far) {
        
        float w = right - left;
        float h = top - bottom;
        float depth = far - near;
        
        // 计算矩阵
        float m00 = 2 / w, m01 = 0,     m02 = 0,        m03 = -(right + left)/w;
        float m10 = 0,     m11 = 2 / h, m12 = 0,        m13 = -(top + bottom)/h;
        float m20 = 0,     m21 = 0,     m22 = -2/depth, m23 = -(far + near)/depth;
        float m30 = 0,     m31 = 0,     m32 = 0,        m33 = 1;
        
        projectionMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 视口变换矩阵
     */
    public void updateViewportMatrix(float xmin, float ymin, float xmax, float ymax, float near, float far) {
        // 把模型移到屏幕中心，并且按屏幕比例放大。
        float m00 = (xmax - xmin) * 0.5f, m01 = 0,                     m02 = 0,                 m03 = (xmax + xmin) * 0.5f;
        float m10 = 0,                    m11 = -(ymax - ymin) * 0.5f, m12 = 0,                 m13 = (ymax + ymin) * 0.5f;
        float m20 = 0,                    m21 = 0,                     m22 = (far-near) * 0.5f, m23 = (far + near) * 0.5f;
        float m30 = 0,                    m31 = 0,                     m32 = 0,                 m33 = 1f;
        
        viewportMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 视口变换矩阵
     */
    public void updateViewportMatrix() {
        float w = width * 0.5f;
        float h = height * 0.5f;
        
        // 把模型移到屏幕中心，并且按屏幕比例放大。
        float m00 = w, m01 = 0,  m02 = 0,    m03 = w;
        float m10 = 0, m11 = -h, m12 = 0,    m13 = h;
        float m20 = 0, m21 = 0,  m22 = 0.5f, m23 = 0.5f;
        float m30 = 0, m31 = 0,  m32 = 0,    m33 = 1;
        
        viewportMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }

}
