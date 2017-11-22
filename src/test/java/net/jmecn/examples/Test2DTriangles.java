package net.jmecn.examples;

import java.util.Random;

import net.jmecn.Application;
import net.jmecn.geom.Triangle2D;
import net.jmecn.math.ColorRGBA;

/**
 * 绘制2D三角形
 * 
 * @author yanmaoyuan
 *
 */
public class Test2DTriangles extends Application {

    public static void main(String[] args) {
        Test2DTriangles app = new Test2DTriangles();
        app.setResolution(720, 405);
        app.setTitle("2D Triangles");
        app.setFrameRate(120);
        app.start();
    }

    /**
     * 初始化
     */
    @Override
    protected void initialize() {
        Random rand = new Random();
        
        /**
         * 随机生成三角形
         */
        for(int i=0; i<10; i++) {
            Triangle2D tri = new Triangle2D();
            tri.x0 = rand.nextInt(width);
            tri.y0 = rand.nextInt(height);
            tri.x1 = rand.nextInt(width);
            tri.y1 = rand.nextInt(height);
            tri.x2 = rand.nextInt(width);
            tri.y2 = rand.nextInt(height);
            tri.color = new ColorRGBA(rand.nextInt(0x4FFFFFFF));
            tri.isSolid = rand.nextFloat() > 0.5f;
            // 添加到场景中
            scene.add(tri);
        }
    }

    @Override
    protected void update(float delta) {
    }

}
