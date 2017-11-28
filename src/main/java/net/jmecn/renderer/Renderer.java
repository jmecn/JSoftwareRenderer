package net.jmecn.renderer;

import java.util.List;

import net.jmecn.math.ColorRGBA;
import net.jmecn.math.Matrix4f;
import net.jmecn.math.Vector3f;
import net.jmecn.scene.Mesh;

/**
 * 渲染器
 * @author yanmaoyuan
 *
 */
public class Renderer {

    // 渲染图像
    private Image image;
    // 光栅器
    private VertexRaster raster;
    // 清屏颜色
    private ColorRGBA clearColor = ColorRGBA.WHITE;
    
    /**
     * 初始化渲染器
     * @param width
     * @param height
     */
    public Renderer(int width, int height) {
        image = new Image(width, height);
        raster = new VertexRaster(image);
        
        // 计算视口变换矩阵
        updateViewportMatrix(width, height);
    }

    /**
     * 设置背景色
     * @param color
     */
    public void setBackgroundColor(ColorRGBA color) {
        if (color != null) {
            this.clearColor = color;
        }
    }
    
    /**
     * 使用背景色填充图像数据
     */
    public void clear() {
        raster.fill(clearColor);
        raster.clearDepthBuffer();
    }

    /**
     * 获得渲染好的图像
     * @return
     */
    public Image getRenderContext() {
        return image;
    }

    /**
     * 获得光栅器
     * @return
     */
    public ImageRaster getImageRaster() {
        return raster;
    }

    private Matrix4f worldMatrix = new Matrix4f();
    private Matrix4f viewMatrix = new Matrix4f();
    private Matrix4f projectionMatrix = new Matrix4f();
    private Matrix4f viewProjectionMatrix = new Matrix4f();
    
    private Matrix4f worldViewMatrix = new Matrix4f();
    private Matrix4f worldViewProjectionMatrix = new Matrix4f();
    
    private Matrix4f viewportMatrix = new Matrix4f();
    
    /**
     * 视口变换矩阵
     */
    public void updateViewportMatrix(float width, float height) {
        float w = width * 0.5f;
        float h = height * 0.5f;
        
        // 把模型移到屏幕中心，并且按屏幕比例放大。
        float m00 = w, m01 = 0,  m02 = 0,  m03 = w;
        float m10 = 0, m11 = -h, m12 = 0,  m13 = h;
        float m20 = 0, m21 = 0,  m22 = 1f, m23 = 0;
        float m30 = 0, m31 = 0,  m32 = 0,  m33 = 1;
        
        viewportMatrix.set(m00, m01, m02, m03, m10, m11, m12, m13, m20, m21, m22, m23, m30, m31, m32, m33);
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
     * 渲染场景
     * @param meshes
     * @param camera
     */
    public void render(List<Mesh> meshes, Camera camera) {
        
        // 根据Camera初始化观察变换矩阵。
        viewMatrix.set(camera.getViewMatrix());
        projectionMatrix.set(camera.getProjectionMatrix());
        viewProjectionMatrix.set(camera.getViewProjectionMatrix());

        // 遍历场景中的Mesh
        for(int i=0; i<meshes.size(); i++) {
            Mesh mesh = meshes.get(i);
            
            // 根据Mesh的世界变换，计算MVP等变换矩阵。
            worldMatrix.set(mesh.getTransform().toTransformMatrix());
            viewMatrix.mult(worldMatrix, worldViewMatrix);
            viewProjectionMatrix.mult(worldMatrix, worldViewProjectionMatrix);
            
            // TODO 剔除不可见的Mesh
            
            // 渲染
            render(mesh);
        }
    }

    /**
     * 渲染单个Mesh
     * @param mesh
     */
    protected void render(Mesh mesh) {
        
        // 设置采样用的纹理
        raster.setTexture(mesh.getTexture());
        
        // 用于保存变换后的向量坐标。
        Vector3f a = new Vector3f();
        Vector3f b = new Vector3f();
        Vector3f c = new Vector3f();
        
        // 遍历所有三角形
        int[] indexes = mesh.getIndexes();
        Vertex[] vertexes = mesh.getVertexes();
        
        for (int i = 0; i < indexes.length; i += 3) {

            Vertex v0 = vertexes[indexes[i]];
            Vertex v1 = vertexes[indexes[i+1]];
            Vertex v2 = vertexes[indexes[i+2]];
            
            // 在观察空间进行背面消隐
            worldViewMatrix.mult(v0.position, a);
            worldViewMatrix.mult(v1.position, b);
            worldViewMatrix.mult(v2.position, c);
            
            if (cullBackFace(a, b, c))
                continue;

            // 执行顶点着色器
            VertexOut out0 = vertexShader(v0);
            VertexOut out1 = vertexShader(v1);
            VertexOut out2 = vertexShader(v2);

            // TODO 视锥裁剪
            
            
            // x,y,z除以w
            out0.perspectiveDivide();
            out1.perspectiveDivide();
            out2.perspectiveDivide();
            
            // 把顶点位置修正到屏幕空间。
            viewportMatrix.mult(out0.position, out0.position);
            viewportMatrix.mult(out1.position, out1.position);
            viewportMatrix.mult(out2.position, out2.position);
            
            if (mesh.isWireframe()) {
                raster.drawTriangle(out0, out1, out2);
            } else {
                raster.rasterizeTriangle(out0, out1, out2);
            }
        }
    }
    
    /**
     * 剔除背面
     * 
     * @param a
     * @param b
     * @param c
     * @return
     */
    protected boolean cullBackFace(Vector3f a, Vector3f b, Vector3f c) {

        // 计算ab向量
        Vector3f ab = b.subtract(a, a);

        // 计算bc向量
        Vector3f bc = c.subtract(b, b);

        // 计算表面法线
        Vector3f faceNormal = ab.crossLocal(bc);

        return faceNormal.dot(c) >= 0;
    }
    
    /**
     * 顶点着色
     * @param vert
     * @return
     */
    protected VertexOut vertexShader(Vertex vert) {
        VertexOut out = new VertexOut();
        // 顶点位置
        out.position.set(vert.position, 1f);
        // 顶点法线
        if (vert.normal != null) {
            out.normal.set(vert.normal);
            out.hasNormal = true;
        }
        // 纹理坐标
        if (vert.texCoord != null) {
            out.texCoord.set(vert.texCoord);
            out.hasTexCoord = true;
        }
        // 顶点颜色
        if (vert.color != null) {
            out.color.set(vert.color);
            out.hasVertexColor = true;
        }
        
        // 顶点着色器
        // 模型-观察-透视 变换
        worldViewProjectionMatrix.mult(out.position, out.position);
        
        return out;
    }
    
}
