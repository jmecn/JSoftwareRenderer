package net.jmecn.renderer;

import net.jmecn.math.Matrix4f;
import net.jmecn.math.Vector4f;
import net.jmecn.scene.Vertex;

public class RenderContext extends ImageRaster {

    protected float[] depthBuffer;
    
    private Matrix4f viewportMatrix;
    
    public RenderContext(Image image) {
        super(image);
        depthBuffer = new float[width * height];
    }

    /**
     * 设置视口变换矩阵
     * @param mat
     */
    public void setViewportMatrix(Matrix4f mat) {
        this.viewportMatrix = mat;
    }
    
    /**
     * 画点
     * 
     * @param x
     * @param y
     * @param color
     */
    public void drawPixel(int x, int y, Vector4f color) {
        if (x < 0 || y < 0 || x >= width || y >= height) {
            return;
        }

        int index = (x + y * width) * 4;

        components[index] = (byte)(color.x * 0xFF);
        components[index + 1] = (byte)(color.y * 0xFF);
        components[index + 2] = (byte)(color.z * 0xFF);
        components[index + 3] = (byte)(color.w * 0xFF);
    }

    public void drawTriangle(Vertex v0, Vertex v1, Vertex v2) {
        fragmentShader(v0);
        fragmentShader(v1);
        fragmentShader(v2);
        
        drawLineBresenham(v0, v1, 0);
        drawLineBresenham(v0, v2, 0);
        drawLineBresenham(v1, v2, 0);
    }
    
    public void fillTriangle(Vertex v0, Vertex v1, Vertex v2) {
        fragmentShader(v0);
        fragmentShader(v1);
        fragmentShader(v2);
        
        float y0 = v0.fragCoord.y;
        float y1 = v1.fragCoord.y;
        float y2 = v2.fragCoord.y;
        
        if (y0 == y1) {
            if (y2 <= y0) // 平底
            {
                fillBottomLineTriangle(v2, v0, v1);
            } else // 平顶
            {
                fillTopLineTriangle(v0, v1, v2);
            }
        } else if (y0 == y2) {
            if (y1 <= y0) // 平底
            {
                fillBottomLineTriangle(v1, v0, v2);
            } else // 平顶
            {
                fillTopLineTriangle(v0, v2, v1);
            }
        } else if (y1 == y2) {
            if (y0 <= y1) // 平底
            {
                fillBottomLineTriangle(v0, v1, v2);
            } else // 平顶
            {
                fillTopLineTriangle(v1, v2, v0);
            }
        } else {
            // 分割三角形
            Vertex top = null;
            Vertex middle = null;
            Vertex bottom = null;
            
            if (y0 < y1 && y1 < y2)
            {
                top = v0;
                middle = v1;
                bottom = v2;
            } else if (y0 < y2 && y2 < y1)
            {
                top = v0;
                middle = v2;
                bottom = v1;
            } else if (y1 < y0 && y0 < y2)
            {
                top = v1;
                middle = v0;
                bottom = v2;
            } else if (y1 < y2 && y2 < y0)
            {
                top = v1;
                middle = v2;
                bottom = v0;
            } else if (y2 < y0 && y0 < y1)
            {
                top = v2;
                middle = v0;
                bottom = v1;
            } else if (y2 < y1 && y1 < y0)
            {
                top = v2;
                middle = v1;
                bottom = v0;
            }
            
            float changeAmnt = (middle.fragCoord.y - top.fragCoord.y) / (bottom.fragCoord.y - top.fragCoord.y);
            // 长边在ymiddle时的x，来决定长边是在左边还是右边
            float middleX = (float)(changeAmnt * (bottom.fragCoord.x - top.fragCoord.x) + top.fragCoord.x + 0.5f);
            
            Vertex newVert = new Vertex();
            newVert.interpolateLocal(top, bottom, changeAmnt);
            
            if (middleX <= middle.fragCoord.x) // 左三角形
            {
                // 画平底
                fillBottomLineTriangle(top, newVert, middle);

                // 画平顶
                fillTopLineTriangle(newVert, middle, bottom);
            } else // 右三角形
            {
                // 画平底
                fillBottomLineTriangle(top, middle, newVert);

                // 画平顶
                fillTopLineTriangle(middle, newVert, bottom);
            }
        }
    }

    /**
     * 画平底实心三角形
     * 
     * v1 v2 是底边
     */
    private void fillBottomLineTriangle(Vertex v0, Vertex v1, Vertex v2) {
        for (float y = v0.fragCoord.y; y <= v1.fragCoord.y; y++)
        {
            int yIndex = (int)(Math.round(y)); 
            if (yIndex >= 0 && yIndex < this.height)
            {
                float xl = (y - v0.fragCoord.y) * (v1.fragCoord.x - v0.fragCoord.x) / (v1.fragCoord.y - v0.fragCoord.y) + v0.fragCoord.x + 0.5f;
                float xr = (y - v0.fragCoord.y) * (v2.fragCoord.x - v0.fragCoord.x) / (v2.fragCoord.y - v0.fragCoord.y) + v0.fragCoord.x + 0.5f;

                float dy = y - v0.fragCoord.y;
                float t = dy / (v1.fragCoord.y - v0.fragCoord.y);
                //插值生成左右顶点
                Vertex new1 = new Vertex();
                new1.fragCoord.x = xl;
                new1.fragCoord.y = y;
                new1.interpolateLocal(v0, v1, t);
                //
                Vertex new2 = new Vertex();
                new2.fragCoord.x = xr;
                new2.fragCoord.y = y;
                new2.interpolateLocal(v0, v2, t);
                //扫描线填充
                if(new1.fragCoord.x < new2.fragCoord.x)
                {
                    drawLineBresenham(new1, new2, yIndex);
                }
                else
                {
                    drawLineBresenham(new2, new1, yIndex);
                }
            }
        }
    }

    /**
     * 画平顶实心三角形
     * v0 v1 是底边
     */
    private void fillTopLineTriangle(Vertex v0, Vertex v1, Vertex v2) {
        for (float y = v0.fragCoord.y; y <= v2.fragCoord.y; y++)
        {
            int yIndex = (int)(Math.round(y)); 
            if (yIndex >= 0 && yIndex < this.height)
            {
                float xl = (y - v0.fragCoord.y) * (v2.fragCoord.x - v0.fragCoord.x) / (v2.fragCoord.y - v0.fragCoord.y) + v0.fragCoord.x + 0.5f;
                float xr = (y - v1.fragCoord.y) * (v2.fragCoord.x - v1.fragCoord.x) / (v2.fragCoord.y - v1.fragCoord.y) + v1.fragCoord.x + 0.5f;

                float dy = y - v0.fragCoord.y;
                float t = dy / (v2.fragCoord.y - v0.fragCoord.y);
                //插值生成左右顶点
                Vertex new1 = new Vertex();
                new1.fragCoord.x = xl;
                new1.fragCoord.y = y;
                new1.interpolateLocal(v0, v2, t);
                //
                Vertex new2 = new Vertex();
                new2.fragCoord.x = xr;
                new2.fragCoord.y = y;
                new2.interpolateLocal(v1, v2, t);
                //扫描线填充
                if (new1.fragCoord.x < new2.fragCoord.x)
                {
                    drawLineBresenham(new1, new2, yIndex);
                }
                else
                {
                    drawLineBresenham(new2, new1, yIndex);
                }
            }
        }
    }
    
    /**
     * 画线
     * 
     * @param yIndex 
     */
    public void drawLineBresenham(Vertex v0, Vertex v1, int yIndex) {
        int x = (int) v0.fragCoord.x;
        int y = (int) v0.fragCoord.y;

        int w = (int) (v1.fragCoord.x - v0.fragCoord.x);
        int h = (int) (v1.fragCoord.y - v0.fragCoord.y);

        Vector4f c0 = v0.fragColor;
        Vector4f c1 = v1.fragColor;
        Vector4f color = new Vector4f();
        
        int dx1 = w < 0 ? -1 : (w > 0 ? 1 : 0);
        int dy1 = h < 0 ? -1 : (h > 0 ? 1 : 0);

        int dx2 = w < 0 ? -1 : (w > 0 ? 1 : 0);
        int dy2 = 0;

        int fastStep = Math.abs(w);
        int slowStep = Math.abs(h);
        if (fastStep <= slowStep) {
            fastStep = Math.abs(h);
            slowStep = Math.abs(w);

            dx2 = 0;
            dy2 = h < 0 ? -1 : (h > 0 ? 1 : 0);
        }
        int numerator = fastStep >> 1;

        for (int i = 0; i <= fastStep; i++) {
            // 颜色线性插值
            color.interpolateLocal(c0, c1, (float)i/(fastStep-1));
            
            drawPixel(x, y, color);
            numerator += slowStep;
            if (numerator >= fastStep) {
                numerator -= fastStep;
                x += dx1;
                y += dy1;
            } else {
                x += dx2;
                y += dy2;
            }
            drawPixel(x, y, color);
        }
    }
    
    protected void fragmentShader(Vertex vert) {
        // 把顶点位置修正到屏幕空间。
        viewportMatrix.mult(vert.fragCoord, vert.fragCoord);
        // 透视除法
        vert.fragCoord.multLocal(1f / vert.fragCoord.w);
        
        if (vert.color == null) {
            vert.fragColor.set(1, 1, 1, 1);
        } else {
            vert.fragColor.set(vert.color);
        }
    }
}
