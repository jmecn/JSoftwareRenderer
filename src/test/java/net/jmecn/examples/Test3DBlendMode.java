package net.jmecn.examples;

import java.io.IOException;

import net.jmecn.Application;
import net.jmecn.material.Material;
import net.jmecn.material.RenderState.BlendMode;
import net.jmecn.material.Texture;
import net.jmecn.math.Vector3f;
import net.jmecn.renderer.Image;
import net.jmecn.scene.Geometry;
import net.jmecn.scene.Mesh;
import net.jmecn.scene.shape.Quad;

/**
 * 测试颜色混合
 * @author yanmaoyuan
 *
 */
public class Test3DBlendMode extends Application {

    public static void main(String[] args) {
        Test3DBlendMode app = new Test3DBlendMode();
        app.setResolution(1080, 720);
        app.setTitle("Test BlendMode");
        app.setFrameRate(60);
        app.start();
    }
    
    private Mesh mesh = new Quad();

    @Override
    protected void initialize() {
        // 初始化摄像机
        getCamera().lookAt(new Vector3f(2, 3, 4), Vector3f.ZERO, Vector3f.UNIT_Y);

        makeOpaque();
        makeTransparent();
        makeTranslucent();
    }
    
    /**
     * 不透明的物体
     * @return
     */
    private void makeOpaque() {
        Material material = new Material();
        
        // 使用默认的程序纹理
        Texture texture = new Texture();
        material.setDiffuseMap(texture);
        
        // 关闭Alpha测试
        material.getRenderState().setAlphaTest(false);
        
        // 关闭颜色混合
        material.getRenderState().setBlendMode(BlendMode.OFF);
        
        Geometry geom = new Geometry(mesh, material);
        rootNode.attachChild(geom);
    }
    
    /**
     * 透明物体，应该使用AlphaTest，剔除透明部分。
     * @return
     */
    private void makeTransparent() {
        Material material = new Material();
        
        try {
            Image image = new Image("res/grass.png");
            Texture texture = new Texture(image);
            material.setDiffuseMap(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 开启Alpha测试
        material.getRenderState().setAlphaTest(true);
        material.getRenderState().setAlphaFalloff(0.75f);
        
        Geometry geom = new Geometry(mesh, material);
        rootNode.attachChild(geom);
        
        geom.getLocalTransform().setTranslation(0, 0, 0.2f);
    }
    
    /**
     * 半透明物体，应该最后被渲染，并且不写深度缓冲。
     * @return
     */
    private void makeTranslucent() {
        Material material = new Material();
        
        try {
            Image image = new Image("res/blending_transparent_window.png");
            Texture texture = new Texture(image);
            material.setDiffuseMap(texture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // 开启Alpha测试，剔除掉窗户边缘Alpha为0的像素。
        material.getRenderState().setAlphaTest(true);
        material.getRenderState().setAlphaFalloff(0.01f);
        
        // 设置颜色混合模式
        material.getRenderState().setBlendMode(BlendMode.ALPHA_BLEND);
        
        // 深度缓冲设为只读模式
        material.getRenderState().setDepthWrite(false);

        Geometry geom = new Geometry(mesh, material);
        rootNode.attachChild(geom);
        
        geom.getLocalTransform().setTranslation(1, 0, 0.4f);
    }

    @Override
    protected void update(float delta) {}

}
