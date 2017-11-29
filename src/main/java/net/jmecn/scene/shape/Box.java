package net.jmecn.scene.shape;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.Vertex;

/**
 * 立方体网格
 * @author yanmaoyuan
 *
 */
public class Box extends Mesh {
    
    public Box() {
        
        // 顶点坐标
        float[] positions = {
                // back
                 1,-1,-1,  -1,-1,-1,   1, 1,-1,  -1, 1,-1,
                // front
                -1,-1, 1,   1,-1, 1,  -1, 1, 1,   1, 1, 1,
                // left
                 1,-1, 1,   1,-1,-1,   1, 1, 1,   1, 1,-1,
                // right
                -1,-1,-1,  -1,-1, 1,  -1, 1,-1,  -1, 1, 1,
                // top
                -1, 1,-1,  -1, 1, 1,   1, 1,-1,   1, 1, 1,
                // bottom
                 1,-1,-1,   1,-1, 1,  -1,-1,-1,  -1,-1, 1,
        };
        
        // 顶点法线
        float[] normals = {
                // back
                0, 0,-1,   0, 0,-1,   0, 0,-1,   0, 0,-1,
                //front
                0, 0, 1,   0, 0, 1,   0, 0, 1,   0, 0, 1,
                // left
               -1, 0, 0,  -1, 0, 0,  -1, 0, 0,  -1, 0, 0,
                // right
                1, 0, 0,   1, 0, 0,   1, 0, 0,   1, 0, 0,
                // top
                0, 1, 0,   0, 1, 0,   0, 1, 0,   0, 1, 0,
                // bottom
                0,-1, 0,   0,-1, 0,   0,-1, 0,   0,-1, 0,
        };
        
        // 纹理坐标
        float[] texCoords = {
                // back
                0, 0,  1, 0,  0, 1,  1, 1,
                // front
                0, 0,  1, 0,  0, 1,  1, 1,
                // left
                0, 0,  1, 0,  0, 1,  1, 1,
                // right
                0, 0,  1, 0,  0, 1,  1, 1,
                // top
                0, 0,  1, 0,  0, 1,  1, 1,
                // bottom
                0, 0,  1, 0,  0, 1,  1, 1,
        };
        
        // 顶点颜色
        float[] colors = {
                // back
                1, 0, 0, 1,   0, 0, 0, 1,   1, 1, 0, 1,   0, 1, 0, 1,
                // front
                0, 0, 1, 1,   1, 0, 1, 1,   0, 1, 1, 1,   1, 1, 1, 1,
                // left
                1, 0, 1, 1,   1, 0, 0, 1,   1, 1, 1, 1,   1, 1, 0, 1,
                // right
                0, 0, 0, 1,   0, 0, 1, 1,   0, 1, 0, 1,   0, 1, 1, 1,
                // top
                0, 1, 0, 1,   0, 1, 1, 1,   1, 1, 0, 1,   1, 1, 1, 1,
                // bottom
                1, 0, 0, 1,   1, 0, 1, 1,   0, 0, 0, 1,   0, 0, 1, 1,
        };
        
        // 顶点索引
        this.indexes = new int[]{
                // back
                0, 1, 3,  0, 3, 2,
                // front
                4, 5, 7,  4, 7, 6,
                // left
                8, 9, 11, 8, 11, 10,
                // right
                12, 13, 15,  12, 15, 14,
                // top
                16, 17, 19, 16, 19, 18,
                // bottom
                20, 21, 23, 20, 23, 22,
        };
        
        this.vertexes = new Vertex[positions.length];
        
        for(int i = 0; i < indexes.length; i++) {
            int index = indexes[i];
            
            vertexes[index] = new Vertex();
            vertexes[index].position = new Vector3f( positions[index*3], positions[index*3+1], positions[index*3+2]);
            vertexes[index].normal = new Vector3f( normals[index*3], normals[index*3+1], normals[index*3+2]);
            vertexes[index].color = new Vector4f( colors[index*4], colors[index*4+1], colors[index*4+2], colors[index*4+3]);
            vertexes[index].texCoord = new Vector2f(texCoords[index*2], texCoords[index*2+1]);
        }
    }
}
