package game.bird;

import java.awt.*;

/**
 * 点
 *
 * @author: adonis lau
 */
public final class Point {
    /**
     * 点x,y坐标
     */
    private int x, y;
    /**
     * 差值
     */
    private int var = 10;
    /**
     * 宽度
     */
    private static final int pointW = 5;
    /**
     * 高度
     */
    private static final int pointH = 5;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;

    }

    /**
     * 绘制点
     *
     * @param g
     * @return
     */
    public double draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillOval(x, y, pointW, pointH);
        x -= var;
        return x;
    }
}
