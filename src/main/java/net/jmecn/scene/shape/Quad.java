package net.jmecn.scene.shape;

import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.Vertex;

public class Quad extends Mesh {

    float[] positions = {
            -1, -1,  0,
             1, -1,  0,
             1,  1,  0,
            -1,  1,  0
    };
    
    float[] colors = {
            1, 1, 0, 1,
            0, 1, 1, 1,
            1, 0, 1, 1,
            1, 1, 1, 1
    };
    
    float[] texCoords = {
            0, 0,
            1, 0,
            1, 1,
            0, 1
    };
    
    float[] normals = {
            0, 0, 1,
            0, 0, 1,
            0, 0, 1,
            0, 0, 1
    };
    
    int [] index = {
            0, 1, 2,
            0, 2, 3
    };
    
    public Quad() {
        this.indexes = index;
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
