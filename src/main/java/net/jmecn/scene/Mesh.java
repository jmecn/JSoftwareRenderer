package net.jmecn.scene;

import net.jmecn.geom.Drawable;
import net.jmecn.math.ColorRGBA;
import net.jmecn.math.Matrix4f;
import net.jmecn.math.Transform;
import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.ImageRaster;

/**
 * 定义三角形网格
 * 
 * @author yanmaoyuan
 *
 */
public class Mesh implements Drawable {
    protected Vertex[] vertexes;
    protected int[] indexes;
    
    protected Texture texture;
    
    /**
     * 空间变换
     */
    protected Transform transform;

    protected boolean wireframe = false;

    public Mesh() {}
    
    public Mesh(Vector3f[] positions, int[] indexes) {
        this(positions, indexes, null, null, null);
    }
    
    /**
     * 初始化三角网格。
     * @param positions
     * @param indexes
     * @param texCoords
     * @param normals
     * @param colors
     */
    public Mesh(Vector3f[] positions, int[] indexes, Vector2f[] texCoords, Vector3f[] normals, Vector4f[] colors) {
        this.indexes = indexes;
        this.vertexes = new Vertex[positions.length];
        
        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            
            vertexes[index] = new Vertex();
            
            vertexes[index].position = positions[index];
            
            if (normals != null) {
                vertexes[index].normal = normals[index];
            }
            if (colors != null) {
                vertexes[index].color = colors[index];
            }
            if (texCoords != null) {
                vertexes[index].texCoord = texCoords[index];
            }
        }
        this.indexes = indexes;
        this.transform = new Transform();
    }

    public Transform getTransform() {
        return transform;
    }

    public Vertex[] getVertexes() {
        return vertexes;
    }
    
    public int[] getIndexes() {
        return indexes;
    }

    public boolean isWireframe() {
        return wireframe;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public void setWireframe(boolean wireframe) {
        this.wireframe = wireframe;
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
            Vector3f va = vertexes[a].position;
            Vector3f vb = vertexes[b].position;
            Vector3f vc = vertexes[c].position;

            // 世界空间变换
            worldMat.mult(va, v1);
            worldMat.mult(vb, v2);
            worldMat.mult(vc, v3);

            // 画三角形
            imageRaster.drawTriangle((int) v1.x, (int) v1.y, (int) v2.x, (int) v2.y, (int) v3.x, (int) v3.y,
                    ColorRGBA.WHITE);
        }
    }

}
