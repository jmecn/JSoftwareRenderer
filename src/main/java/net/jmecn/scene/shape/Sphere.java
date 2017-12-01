package net.jmecn.scene.shape;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.Vertex;

/**
 * 球体网格
 * @author yanmaoyuan
 *
 */
public class Sphere extends Mesh {

    private final static float HALF_PI = (float)(0.5 * Math.PI);
    private final static float TWO_PI = (float)(2.0 * Math.PI);
    
    // 半径
    private float radius;
    // 经线数量 longitude
    private int lonCount;
    // 纬线数量 latitude
    private int latCount;
    // 法线方向是否朝内？
    private boolean interior = false;
    
    private int vertCount;// 顶点数量
    private int triCount; // 三角形数量
    
    public Sphere() {
        this(1f);
    }
    
    public Sphere(float radius) {
        this(radius, 36, 21);
    }
    
    public Sphere(float radius, int lonCount, int latCount) {
        this(radius, lonCount, latCount, false);
    }
    
    public Sphere(float radius, int lonCount, int latCount, boolean interior) {
        this.radius = radius;
        this.lonCount = lonCount;
        this.latCount =latCount;
        this.interior = interior;
        
        createVertexBuffer();
        createIndexBuffer();
    }
    
    /**
     * 生成球体网格
     */
    private void createVertexBuffer() {
        this.vertCount = (latCount - 2) * (lonCount + 1) + 2;
        this.vertexes = new Vertex[vertCount];
        
        // 生成球体
        
        // 计算圆截面上每根经线的坐标
        float[] sin = new float[(lonCount + 1)];
        float[] cos = new float[(lonCount + 1)];
        
        float invLonCount = 1.0f / lonCount;
        for (int i = 0; i < lonCount; i++) {
            float angle = TWO_PI * invLonCount * i;
            cos[i] = (float)Math.cos(angle);
            sin[i] = (float)Math.sin(angle);
        }
        sin[lonCount] = sin[0];
        cos[lonCount] = cos[0];
        
        // 生成Sphere顶点数据
        Vertex v;
        float factor = 2.0f / (latCount - 1);
        int i = 0;
        for (int iY = 1; iY < (latCount - 1); iY++) {
            float fAFraction = HALF_PI * (-1.0f + factor * iY); // in (-pi/2, pi/2)
            
            float sinZ = (float) Math.sin(fAFraction);

            // 计算圆截面高度和半径
            float sliceHeight = (float) sinZ * radius;
            float sliceRadius = (float) Math.cos(fAFraction) * radius;

            // 计算圆截面上的顶点坐标，首位两个顶点共用相同的位置和法线。
            int iSave = i;
            for (int iR = 0; iR < lonCount; iR++) {
                v = vertexes[i] = new Vertex();
                
                // 顶点坐标
                v.position = new Vector3f(cos[iR] * sliceRadius, sliceHeight, sin[iR] * sliceRadius);
                
                // 法线方向
                v.normal = new Vector3f(v.position);
                v.normal.normalizeLocal();
                if (interior) v.normal.negateLocal();

                // 纹理坐标
                v.texCoord = new Vector2f(1f - iR * invLonCount, 0.5f * (factor * iY));

                i++;
            }

            v = vertexes[i] = new Vertex();
            v.position = vertexes[iSave].position;
            v.normal = vertexes[iSave].normal;
            v.texCoord = new Vector2f(0f, 0.5f * (factor * iY));
            i++;
        }

        // 南极点
        v = vertexes[i] = new Vertex();
        v.position = new Vector3f(0, -radius, 0);
        v.normal = new Vector3f(0, interior ? 1 : -1, 0);
        v.texCoord = new Vector2f(0.5f, 0.0f);

        i++;

        // 北极点
        v = vertexes[i] = new Vertex();
        v.position = new Vector3f(0, radius, 0);
        v.normal = new Vector3f(0, interior ? -1 : 1, 0);
        v.texCoord = new Vector2f(0.5f, 1.0f);
    }
    
    /**
     * 计算顶点索引
     */
    private void createIndexBuffer() {
        this.triCount = 2 * (latCount - 2) * lonCount;
        this.indexes = new int[3 * triCount];

        // 生成三角形
        int index = 0;
        for (int y = 0, yStart = 0; y < (latCount - 3); y++) {
            int i0 = yStart;
            int i1 = i0 + 1;
            yStart += (lonCount + 1);
            int i2 = yStart;
            int i3 = i2 + 1;
            for (int i = 0; i < lonCount; i++, index += 6) {
                if (!interior) {
                    indexes[index] = i0++;
                    indexes[index+1] = i2;
                    indexes[index+2] = i1;
                    indexes[index+3] = i1++;
                    indexes[index+4] = i2++;
                    indexes[index+5] = i3++;
                } else { // 内部
                    indexes[index] = i0++;
                    indexes[index+1] = i1;
                    indexes[index+2] = i2;
                    indexes[index+3] = i1++;
                    indexes[index+4] = i3++;
                    indexes[index+5] = i2++;
                }
            }
        }

        // 南极点
        for (int i = 0; i < lonCount; i++, index += 3) {
            if (!interior) {
                indexes[index] = i;
                indexes[index+1] = i + 1;
                indexes[index+2] = vertCount - 2;
            } else { // 内部
                indexes[index] = i;
                indexes[index+1] = vertCount - 2;
                indexes[index+2] = i + 1;
            }
        }

        // 北极点
        int iOffset = (latCount - 3) * (lonCount + 1);
        for (int i = 0; i < lonCount; i++, index += 3) {
            if (!interior) {
                indexes[index] = i + iOffset;
                indexes[index+1] = vertCount - 1;
                indexes[index+2] = i + 1 + iOffset;
            } else { // 内部
                indexes[index] = i + iOffset;
                indexes[index+1] = i + 1 + iOffset;
                indexes[index+2] = vertCount - 1;
            }
        }
    }
}
