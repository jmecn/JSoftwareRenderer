package net.jmecn.scene;

import net.jmecn.geom.Drawable;
import net.jmecn.math.ColorRGBA;
import net.jmecn.math.Matrix4f;
import net.jmecn.math.Transform;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Camera;
import net.jmecn.renderer.ImageRaster;

/**
 * 定义三角形网格
 * @author yanmaoyuan
 *
 */
public class Mesh implements Drawable {
    /**
     * 顶点数组
     */
    private Vector3f[] positions;
    /**
     * 顶点索引
     */
    private int[] indexes;
    /**
     * 空间变换
     */
    private Transform transform;

    /**
     * 初始化三角网格。
     * 
     * @param vertexes
     * @param indexes
     */
    public Mesh(Vector3f[] positions, int[] indexes) {
        this.positions = positions;
        this.indexes = indexes;
        this.transform = new Transform();
    }

    public Transform getTransform() {
        return transform;
    }

    @Override
    public void draw(ImageRaster imageRaster) {
        
        // 世界变换矩阵
        Matrix4f worldMat = transform.toTransformMatrix();

        // 用于保存变换后的向量坐标。
        Vector3f v1 = new Vector3f();
        Vector3f v2 = new Vector3f();
        Vector3f v3 = new Vector3f();
        
        // 遍历所有三角形
        for (int i = 0; i < indexes.length - 2; i += 3) {
            int a = indexes[i];
            int b = indexes[i + 1];
            int c = indexes[i + 2];
            
            // 画三角形
            Vector3f va = positions[a];
            Vector3f vb = positions[b];
            Vector3f vc = positions[c];
            
            // 世界空间变换
            worldMat.mult(va, v1);
            worldMat.mult(vb, v2);
            worldMat.mult(vc, v3);
            
            // 画三角形
            imageRaster.drawTriangle((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y, (int)v3.x, (int)v3.y, ColorRGBA.WHITE);
        }
    }

    /**
     * 渲染3D场景
     * @param imageRaster
     * @param camera
     */
    public void render(ImageRaster imageRaster, Camera camera) {
        // 屏幕空间矩阵
        Matrix4f screenSpaceMat = camera.getScreenSpaceMatrix();
        
        // 世界变换矩阵
        Matrix4f worldMat = transform.toTransformMatrix();
        // 观察变换矩阵
        Matrix4f viewMat = camera.getViewMatrix();
        // 投影矩阵
        Matrix4f projectionMat = camera.getProjectionMatrix();
        
        // 世界-观察-投影变换矩阵
        Matrix4f mvp = projectionMat.mult(viewMat).mult(worldMat);

        // 用于保存变换后的向量坐标。
        Vector4f v1 = new Vector4f();
        Vector4f v2 = new Vector4f();
        Vector4f v3 = new Vector4f();
        
        // 遍历所有三角形
        for (int i = 0; i < indexes.length - 2; i += 3) {
            int a = indexes[i];
            int b = indexes[i + 1];
            int c = indexes[i + 2];
            
            // 画三角形
            Vector3f va = positions[a];
            Vector3f vb = positions[b];
            Vector3f vc = positions[c];
            
            // 使用齐次坐标计算顶点。
            v1.set(va.x, va.y, va.z, 1);
            v2.set(vb.x, vb.y, vb.z, 1);
            v3.set(vc.x, vc.y, vc.z, 1);
            
            // 模型-观察-透视 变换
            mvp.mult(v1, v1);
            mvp.mult(v2, v2);
            mvp.mult(v3, v3);

            if (faceCullBack(v1, v2, v3)) {
                continue;
            }
            
            // 把顶点位置修正到屏幕空间。
            screenSpaceMat.mult(v1, v1);
            screenSpaceMat.mult(v2, v2);
            screenSpaceMat.mult(v3, v3);
            
            // 透视除法
            v1.multLocal(1f/v1.w);
            v2.multLocal(1f/v2.w);
            v3.multLocal(1f/v3.w);
            
            // 画三角形
            imageRaster.drawTriangle((int)v1.x, (int)v1.y, (int)v2.x, (int)v2.y, (int)v3.x, (int)v3.y, ColorRGBA.WHITE);
        }
    }
    
    /**
     * 剔除背面
     * @param v1
     * @param v2
     * @param v3
     * @return
     */
    private boolean faceCullBack(Vector4f v1, Vector4f v2, Vector4f v3) {
        
        Vector3f a = new Vector3f(v1.x, v1.y, v1.z);
        Vector3f b = new Vector3f(v2.x, v2.y, v2.z);
        Vector3f c = new Vector3f(v3.x, v3.y, v3.z);
        
        Vector3f ab = b.subtract(a, a);
        Vector3f bc = c.subtract(b, b);
        
        // 计算表面法线
        Vector3f faceNormal = ab.crossLocal(bc);
        
        return faceNormal.dot(c) < 0;
    }
}
