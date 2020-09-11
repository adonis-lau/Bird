package game.bird;

import java.awt.*;

/**
 * 图片类
 *
 * @author: adonis lau
 */
public final class MyImage {

    /**
     * 图片
     */
    private final Image image;
    /**
     * 宽度
     */
    private final int width;
    /**
     * 高度
     */
    private final int height;

    public MyImage(String imagePath) {
        this.image = GameUtil.getImage(imagePath);
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    public Image getImage() {
        return image;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
