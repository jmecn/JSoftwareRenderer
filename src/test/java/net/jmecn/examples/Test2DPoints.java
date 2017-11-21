package net.jmecn.examples;

import java.util.Random;

import net.jmecn.Application;
import net.jmecn.geom.Point2D;
import net.jmecn.math.ColorRGBA;

/**
 * 绘制2D点
 * 
 * @author yanmaoyuan
 *
 */
public class Test2DPoints extends Application {

    public static void main(String[] args) {
        Test2DPoints app = new Test2DPoints();
        // app.setFrameRate(30);
        app.start();
    }

    /**
     * 初始化
     */
    @Override
    protected void initialize() {

        Random rand = new Random();
        /**
         * 随机生成100个点
         */
        for(int i=0; i<100; i++) {
            Point2D point = new Point2D();
            point.x = rand.nextInt(width);
            point.y = rand.nextInt(height);
            point.color = ColorRGBA.RED;
            
            scene.add(point);
        }
    }

    @Override
    protected void update(float delta) {
        // TODO Auto-generated method stub
        
    }

}
