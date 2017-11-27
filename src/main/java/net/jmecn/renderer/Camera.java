package net.jmecn.renderer;

import net.jmecn.math.Matrix4f;
import net.jmecn.math.Quaternion;
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
     * 摄像机的UVN系统
     */
    private Vector3f uAxis = new Vector3f(1, 0, 0);
    private Vector3f vAxis = new Vector3f(0, 1, 0);
    private Vector3f nAxis = new Vector3f(0, 0, 1);
    
    /**
     * 观察变换矩阵
     */
    private Matrix4f viewMatrix = new Matrix4f();
    
    /**
     * 组成视锥的六个平面
     */
    private float near   = 1f;    // 近平面距离
    private float far    = 1000f; // 远平面距离
    private float left;           // 左平面距离
    private float right;          // 右平面距离
    private float top;            // 上平面距离
    private float bottom;         // 下平面距离
    
    /**
     * 视野范围默认为 70°
     */
    private float fov = (float) Math.toRadians(70);
    private float aspect;// 屏幕高宽比 width / height
    
    /**
     * 是否平行投影
     */
    private boolean parallel = false;
    
    /**
     * 投影变换矩阵
     */
    private Matrix4f projectionMatrix = new Matrix4f();
    
    /**
     * 观察-投影 变换矩阵
     */
    private Matrix4f viewProjectionMatrix = new Matrix4f();
    
    /**
     * 初始化摄像机
     * @param width
     * @param height
     */
    public Camera(int width, int height) {
        this.aspect = (float) width / height;// 屏幕宽高比
        
        // 计算观察-投影变换矩阵
        updateViewProjectionMatrix();
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
        return uAxis;
    }
    
    /**
     * 获取观察方向的正上方向量
     * @return
     */
    public Vector3f getUpVector() {
        return vAxis;
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
        direction.cross(up, uAxis);
        uAxis.cross(direction, vAxis);
        nAxis.set(-direction.x, -direction.y, -direction.z);

        // 计算摄像机旋转后的平移变换
        float x = uAxis.dot(location);
        float y = vAxis.dot(location);
        float z = nAxis.dot(location);
        
        // 计算观察变换矩阵
        float m00 = uAxis.x, m01 = uAxis.y, m02 = uAxis.z, m03 = -x;
        float m10 = vAxis.x, m11 = vAxis.y, m12 = vAxis.z, m13 = -y;
        float m20 = nAxis.x, m21 = nAxis.y, m22 = nAxis.z, m23 = -z;
        float m30 = 0f,      m31 = 0f,      m32 = 0f,      m33 = 1f;

        viewMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
    }
    
    /**
     * 投影变换矩阵
     */
    public void updateProjectionMatrix() {
        if (!parallel) {
            // 透视投影
            setPerspective(fov, aspect, near, far);
        } else {
            // 正交投影
            left = -0.5f;
            right = 0.5f;
            top = 0.5f;
            bottom = -0.5f;
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
     * 透视投影
     * @param left
     * @param right
     * @param bottom
     * @param top
     * @param near
     * @param far
     */
    public void setPerspective(float left, float right, float bottom, float top, float near, float far) {
        // X方向的缩放比
        float zoomX = 2f * near / (right - left);
        // Y方向的缩放比
        float zoomY = 2f * near / (top - bottom);

        float m00 = zoomX, m01 = 0,     m02 = 0,                      m03 = 0;
        float m10 = 0,     m11 = zoomY, m12 = 0,                      m13 = 0;
        float m20 = 0,     m21 = 0,     m22 = -(far+near)/(far-near), m23 = -2*far*near/(far-near);
        float m30 = 0,     m31 = 0,     m32 = -1,                     m33 = 0;

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
     * 使摄像机观察指定位置
     * @param target
     * @param up
     */
    public void lookAt(Vector3f target, Vector3f up) {
        target.subtract(location, direction);
        direction.normalizeLocal();
        
        this.up.set(up);
        this.up.normalizeLocal();
        
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
    
    /**
     * 使摄像机观察指定位置
     * @param location
     * @param target
     * @param up
     */
    public void lookAt(Vector3f location, Vector3f target, Vector3f up) {
        this.location.set(location);
        target.subtract(location, direction);
        this.direction.normalizeLocal();
        
        this.up.set(up);
        this.up.normalizeLocal();
        
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
    
    /**
     * 使摄像机观察指定方向
     * @param direction
     * @param up
     */
    public void lookAtDirection(Vector3f direction, Vector3f up) {
        this.direction.set(direction);
        this.direction.normalizeLocal();
        
        this.up.set(up);
        this.up.normalizeLocal();
        
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
    
    /**
     * 使摄像机观察指定方向
     * @param location
     * @param direction
     * @param up
     */
    public void lookAtDirection(Vector3f location, Vector3f direction, Vector3f up) {
        this.location.set(location);
        
        this.direction.set(direction);
        this.direction.normalizeLocal();
        
        this.up.set(up);
        this.up.normalizeLocal();
        
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
    
    /**
     * 使摄像机按欧拉角旋转（弧度制）
     * @param xAngle
     * @param yAngle
     * @param zAngle
     */
    public void rotate(float xAngle, float yAngle, float zAngle) {
        // 计算旋转后的uvn系统
        // 不能直接绕x、y、z轴旋转，而是应该绕uvn系统的三轴旋转。
        //Quaternion rot = new Quaternion().fromAngles(xAngle, yAngle, zAngle);
        
        Quaternion rot = new Quaternion(uAxis, xAngle);
        rot.multLocal(new Quaternion(vAxis, yAngle));
        rot.multLocal(new Quaternion(nAxis, zAngle));
        // 计算旋转后的视线方向
        rot.multLocal(direction);
        direction.normalizeLocal();
        
        updateViewMatrix();
        projectionMatrix.mult(viewMatrix, viewProjectionMatrix);
    }
}
