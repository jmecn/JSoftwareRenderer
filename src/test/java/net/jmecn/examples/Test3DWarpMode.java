package net.jmecn.examples;

import java.io.IOException;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.Texture;
import net.jmecn.material.Texture.WarpAxis;
import net.jmecn.material.Texture.WarpMode;
import net.jmecn.math.Vector2f;
import net.jmecn.math.Vector3f;
import net.jmecn.math.Vector4f;
import net.jmecn.renderer.Image;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;

/**
 * 测试纹理包裹模式
 * @author yanmaoyuan
 *
 */
public class Test3DWarpMode extends Application {

    public static void main(String[] args) {
        Test3DWarpMode app = new Test3DWarpMode();
        app.setResolution(1080, 720);
        app.setTitle("Test WarpMode");
        app.setFrameRate(60);
        app.start();
    }

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(1, 2, 3),
                Vector3f.ZERO, Vector3f.UNIT_Y);
        
        // 定义材质
        Material material = new Material();
        
        try {
            Image image = new Image("res/yan.bmp");
            Texture texture = new Texture(image);
            texture.setBorderColor(new Vector4f(0));
            texture.setWarpMode(WarpAxis.S, WarpMode.REPEAT);
            texture.setWarpMode(WarpAxis.T, WarpMode.MIRRORED_REPEAT);
            material.setDiffuseMap(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        
        // 定义四边形网格
        Vector3f[] positions = {
            new Vector3f(0, 0, 0),
            new Vector3f(1, 0, 0),
            new Vector3f(1, 1, 0),
            new Vector3f(0, 1, 0),
        };
        Vector3f[] normals = {
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1),
            new Vector3f(0, 0, 1),
        };
        Vector4f[] colors = {
            new Vector4f(1),      
            new Vector4f(1),      
            new Vector4f(1),      
            new Vector4f(1),
        };
        Vector2f[] texCoords = {
            new Vector2f(-2, -2),
            new Vector2f(2, -2),
            new Vector2f(2, 2),
            new Vector2f(-2, 2),
        };
        int[] indexes = {
            0, 1, 2,
            0, 2, 3,
        };
        
        Mesh mesh = new Mesh(positions, indexes, texCoords, normals, colors);
        
        // 添加到场景中
        Geometry geom = new Geometry(mesh, material);
        rootNode.attachChild(geom);
    }
    
    @Override
    protected void update(float delta) {}

}
