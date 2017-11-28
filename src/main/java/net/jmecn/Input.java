package net.jmecn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import net.jmecn.math.Vector2f;

/**
 * 按键输入
 * 
 * @author yanmaoyuan
 *
 */
public class Input implements KeyListener, MouseListener, MouseMotionListener {

    private boolean[] keys = new boolean[65536];
    private boolean[] mouse = new boolean[4];

    private Vector2f start = new Vector2f();
    private Vector2f current = new Vector2f();
    private Vector2f delta = new Vector2f();
    
    public boolean getKey(int key) {
        return keys[key];
    }
    
    /**
     * 返回鼠标的按键状态
     * @param button
     * @return
     */
    public boolean getMouseButton(int button) {
        return mouse[button];
    }
    /**
     * 获得鼠标的起点坐标
     * @return
     */
    public Vector2f getStart() {
        return start;
    }
    /**
     * 获得鼠标的当前坐标
     * @return
     */
    public Vector2f getCurrent() {
        return current;
    }
    /**
     * 返回鼠标的相对位移
     * @return
     */
    public Vector2f getDelta() {
        return delta;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        current.set(e.getX(), e.getY());
        current.subtract(start, delta);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        mouse[e.getButton()] = true;
        start.set(e.getX(), e.getY());
        current.set(e.getX(), e.getY());
        delta.set(0, 0);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouse[e.getButton()] = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

}