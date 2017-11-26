package net.jmecn;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener,
        FocusListener, MouseListener, MouseMotionListener {
    private boolean[] keys = new boolean[65536];
    private boolean[] mouseButtons = new boolean[4];
    private int mouseX = 0;
    private int mouseY = 0;

    /** Updates state when the mouse is dragged */
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /** Updates state when the mouse is moved */
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    /** Updates state when the mouse is clicked */
    public void mouseClicked(MouseEvent e) {
    }

    /** Updates state when the mouse enters the screen */
    public void mouseEntered(MouseEvent e) {
    }

    /** Updates state when the mouse exits the screen */
    public void mouseExited(MouseEvent e) {
    }

    /** Updates state when a mouse button is pressed */
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if (code > 0 && code < mouseButtons.length)
            mouseButtons[code] = true;
    }

    /** Updates state when a mouse button is released */
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if (code > 0 && code < mouseButtons.length)
            mouseButtons[code] = false;
    }

    /** Updates state when the window gains focus */
    public void focusGained(FocusEvent e) {
    }

    /** Updates state when the window loses focus */
    public void focusLost(FocusEvent e) {
        for (int i = 0; i < keys.length; i++)
            keys[i] = false;
        for (int i = 0; i < mouseButtons.length; i++)
            mouseButtons[i] = false;
    }

    /** Updates state when a key is pressed */
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = true;
    }

    /** Updates state when a key is released */
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code > 0 && code < keys.length)
            keys[code] = false;
    }

    /** Updates state when a key is typed */
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Gets whether or not a particular key is currently pressed.
     * 
     * @param key
     *            The key to test
     * @return Whether or not key is currently pressed.
     */
    public boolean getKey(int key) {
        return keys[key];
    }

    /**
     * Gets whether or not a particular mouse button is currently pressed.
     * 
     * @param button
     *            The button to test
     * @return Whether or not the button is currently pressed.
     */
    public boolean getMouse(int button) {
        return mouseButtons[button];
    }

    /**
     * Gets the location of the mouse cursor on x, in pixels.
     * 
     * @return The location of the mouse cursor on x, in pixels
     */
    public int getMouseX() {
        return mouseX;
    }

    /**
     * Gets the location of the mouse cursor on y, in pixels.
     * 
     * @return The location of the mouse cursor on y, in pixels
     */
    public int getMouseY() {
        return mouseY;
    }
}