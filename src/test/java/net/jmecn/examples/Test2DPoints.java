package net.jmecn.examples;

import java.util.ArrayList;
import java.util.List;
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
        app.setResolution(1080, 720);
        app.setTitle("2D Points");
        app.setFrameRate(0);
        app.start();
    }

    private List<Point2D> points = new ArrayList<Point2D>();
    
    /**
     * 初始化
     */
    @Override
    protected void initialize() {
        Random rand = new Random();
        /**
         * 随机生成10000个点
         */
        for(int i=0; i<10000; i++) {
            Point2D point = new Point2D();
            point.x = rand.nextInt(width);
            point.y = rand.nextInt(height);
            point.color = new ColorRGBA(rand.nextInt(0x4FFFFFFF));
            
            points.add(point);
            
            // 添加到场景中
            scene.add(point);
        }
    }

    @Override
    protected void update(float delta) {
        for(Point2D p : points) {
            p.x--;
            if (p.x <= 0)
                p.x = 1080;
        }
    }

}
