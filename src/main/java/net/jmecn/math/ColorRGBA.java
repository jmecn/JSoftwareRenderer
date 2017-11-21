package net.jmecn.math;

public class ColorRGBA {

    public byte r;
    public byte g;
    public byte b;
    public byte a;

    public static final ColorRGBA WHITE = new ColorRGBA(0xFFFFFFFF);
    public static final ColorRGBA BLACK = new ColorRGBA(0x000000FF);
    public static final ColorRGBA RED = new ColorRGBA(0xFF0000FF);
    public static final ColorRGBA GREEN = new ColorRGBA(0x00FF00FF);
    public static final ColorRGBA BLUE = new ColorRGBA(0x0000FFFF);
    public static final ColorRGBA DARKGRAY = new ColorRGBA(0x666666FF);

    public ColorRGBA() {
        r = g = b = a = (byte) 0xFF;
    }

    public ColorRGBA(int color) {
        r = (byte) ((color >> 24) & 0xFF);
        g = (byte) ((color >> 16) & 0xFF);
        b = (byte) ((color >> 8) & 0xFF);
        a = (byte) (color & 0xFF);
    }
}
