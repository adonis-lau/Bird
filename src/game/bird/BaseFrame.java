package game.bird;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author: adonis lau
 */
public class BaseFrame extends Frame {

    BaseFrame(String name) {
        super(name);
    }

    /**
     * 窗口缓冲图像
     */
    private Image offScreenImage = null;

    /**
     * 双缓冲
     *
     * @param g
     */
    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        }
        Graphics goff = offScreenImage.getGraphics();
        paint(goff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    /**
     * 启动窗口
     */
    public void launchFrame() {
        setSize(GameConstant.GAME_WIDTH, GameConstant.GAME_HEIGHT);
        Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrSize.width - GameConstant.GAME_WIDTH) / 2, (scrSize.height - GameConstant.GAME_HEIGHT) / 2);
        setVisible(true);
        new PaintThread().start();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * 绘制线程
     */
    class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
