package net.jmecn.examples;

import java.util.Random;

import net.jmecn.Application;
import net.jmecn.geom.Line2D;
import net.jmecn.math.ColorRGBA;

/**
 * 绘制2D线段
 * 
 * @author yanmaoyuan
 *
 */
public class Test2DLines extends Application {

    public static void main(String[] args) {
        Test2DLines app = new Test2DLines();
        app.setResolution(1080, 720);
        app.setTitle("2D Lines");
        app.setFrameRate(0);
        app.start();
    }

    /**
     * 初始化
     */
    @Override
    protected void initialize() {
        Random rand = new Random();
        /**
         * 随机生成线段
         */
        for(int i=0; i<100; i++) {
            Line2D line = new Line2D();
            line.x0 = rand.nextInt(width);
            line.y0 = rand.nextInt(height);
            line.x1 = rand.nextInt(width);
            line.y1 = rand.nextInt(height);
            line.color = new ColorRGBA(rand.nextInt(0x4FFFFFFF));
            
            // 添加到场景中
            scene.add(line);
        }
    }

    @Override
    protected void update(float delta) {
    }

}
