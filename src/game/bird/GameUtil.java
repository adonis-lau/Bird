package game.bird;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 工具类
 *
 * @author: adonis lau
 */
public final class GameUtil {

    /**
     * 获取图片资源
     *
     * @param path
     * @return
     */
    public static Image getImage(String path) {
        URL url = GameUtil.class.getClassLoader().getResource(path);
        BufferedImage img = null;
        try {
            img = ImageIO.read(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return img;
    }

}
