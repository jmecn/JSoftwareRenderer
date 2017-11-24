package net.jmecn.scene;

import net.jmecn.geom.Drawable;
import net.jmecn.math.ColorRGBA;
import net.jmecn.math.Matrix4f;
import net.jmecn.math.Transform;
import net.jmecn.math.Vector3f;
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
}
