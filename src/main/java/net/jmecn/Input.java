package net.jmecn;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 按键输入
 * 
 * @author yanmaoyuan
 *
 */
public class Input implements KeyListener {

    private boolean[] keys = new boolean[65536];

    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = true;
    }

    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = false;
    }

    public void keyTyped(KeyEvent e) {}

    public boolean getKey(int key) { return keys[key]; }

}