package game.bird;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * @author: adonis lau
 */
public final class BirdGame extends BaseFrame {
    /**
     * 鸟贴图
     */
    private MyImage birdImage = new MyImage("images/birds.png");
    /**
     * 背景贴图
     */
    private MyImage backImage = new MyImage("images/back.png");
    /**
     * 地面贴图
     */
    private MyImage groundImage = new MyImage("images/Ground.png");
    /**
     * 上管道贴图
     */
    private MyImage pipeUpImage = new MyImage("images/Pipe_Up.png");
    /**
     * 下管道贴图
     */
    private MyImage pipeDownImage = new MyImage("images/Pipe_Down.png");
    /**
     * 死亡展示贴图
     */
    private MyImage overImage = new MyImage("images/Over.png");
    /**
     * 文字
     */
    private Font scoreFont = new Font("隶书", Font.BOLD, 35);
    /**
     * 点集合
     */
    private static ArrayList<Point> points = new ArrayList<Point>();
    /**
     * 停止
     */
    private boolean stop = true;
    /**
     * 死亡
     */
    private boolean dead = false;
    /**
     * 鸟x坐标
     */
    private int birdX = 0;
    /**
     * 鸟y坐标
     */
    private int birdY = 0;
    private int sum = 0;
    /**
     * 分数
     */
    private int score = 0;
    private int x, x1;
    /**
     * 管子x坐标集
     */
    private int pipeX[] = {600, 850, 1100, 1350};
    /**
     * 管子y坐标集
     */
    private int pipeY[] = {-300, -300, -300, -300};
    /**
     * 上下管道高度差
     */
    private static final int spaceHeight = 560;

    public BirdGame() {
        super("FlayBirdInJava");
        birdX = GameConstant.GAME_WIDTH / 8;
        birdY = (GameConstant.GAME_HEIGHT - groundImage.getHeight()) / 2;
        for (int i = 0; i != 4; i++) {
            pipeY[i] = produce();
        }
        setListener();
    }

    /**
     * 初始化
     */
    public void init() {
        int k = 0;
        for (int i = 0; i != 4; i++) {
            pipeX[i] = k + 600;
            k += 250;
        }
        dead = false;
        score = 0;
        birdX = 100;
        birdY = 250;
        for (int i = 0; i != 4; i++) {
            pipeY[i] = produce();
        }
    }

    /**
     * 随机产生高度
     *
     * @return
     */
    public int produce() {
        return (int) (Math.random() * 230 + 80) - pipeUpImage.getHeight();
    }

    /**
     * 添加事件监听
     */
    public void setListener() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                birdJump();
            }
        });
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    birdJump();
                }
            }
        });
    }

    private void birdJump() {
        if (stop) {
            stop = false;
            init();
        }
        if (!dead) {
            jump();
        }
    }

    /**
     * 跳跃
     */
    public void jump() {
        int y = birdY;
        sum = 11;
        while (birdY < y + 1) {
            birdY -= sum / 2;
            sum -= 5;
        }
    }

    /**
     * 死亡判断
     *
     * @param pipeX 管子x坐标
     * @param pipeY 管子y坐标
     * @return
     */
    public boolean isDead(int pipeX, int pipeY) {
        final int birdL = birdX;
        final int birdT = birdY;
        final int birdR = birdL + birdImage.getWidth() / 3;
        final int birdB = birdT + birdImage.getHeight();
        final int pipeUpL = pipeX;
        final int pipeUpT = pipeY;
        final int pipeUpR = pipeX + pipeUpImage.getWidth();
        final int pipeUpB = pipeY + pipeUpImage.getHeight();
        final int pipeDoL = pipeUpL;
        final int pipeDoT = pipeUpT + spaceHeight;
        final int pipeDoR = pipeUpR;
        final int floorY = GameConstant.GAME_HEIGHT - groundImage.getHeight();
        //左下角
        if (birdL < pipeDoR && birdB > pipeDoT && birdL > pipeDoL) {
            System.out.println("左下角");
            return true;
        }
        //左上角
        if (birdL < pipeUpR && birdT < pipeUpB && birdL > pipeUpL) {
            System.out.println("左上角");
            return true;
        }
        //右上角
        if (birdR > pipeUpL && birdT < pipeUpB && birdR < pipeUpR) {
            System.out.println("右上角");
            return true;
        }
        //右下角
        if (birdR > pipeDoL && birdB > pipeDoT && birdR < pipeDoR) {
            System.out.println("右下角");
            return true;
        }
        //地板
        if (birdB > floorY) {
            return true;
        }
        return false;
    }

    /**
     * 鸟落下
     */
    public void birdDown() {
        if (birdY < 0) {
            birdY = 40;
        }
        birdY += sum;
        sum += 3;
    }

    private void birdDead() {
        dead = true;
    }

    /**
     * 绘制路径点
     *
     * @param g
     */
    public void drawPoints(Graphics g) {
        Point point = new Point(birdX + birdImage.getWidth() / 6, birdY + birdImage.getHeight() / 2);
        points.add(point);
        for (int i = 0; i < points.size(); i++) {
            if ((points.get(i)).draw(g) < 0) {
                points.remove(i);
            }
        }
    }

    /**
     * 绘制管道
     *
     * @param g
     */
    private void drawPipe(Graphics g) {
        if (birdY > GameConstant.GAME_HEIGHT + spaceHeight) {
            birdY = GameConstant.GAME_HEIGHT + spaceHeight;
            stop = true;
        }
        for (int i = 0; i != 4; i++) {
            g.drawImage(pipeUpImage.getImage(), pipeX[i], pipeY[i], pipeUpImage.getWidth(), pipeUpImage.getHeight(), null);
            g.drawImage(pipeDownImage.getImage(), pipeX[i], pipeY[i] + spaceHeight, pipeDownImage.getWidth(), pipeDownImage.getHeight(), null);
            pipeX[i] -= 5;
            if (pipeX[i] == GameConstant.GAME_WIDTH + 50) {
                pipeY[i] = produce();
            }
            if (pipeX[i] < -pipeUpImage.getWidth()) {
                pipeX[i] = GameConstant.GAME_WIDTH + 100;
            }
            if (isDead(pipeX[i], pipeY[i])) {
                birdDead();
            } else {
                if (pipeX[i] == birdX) {
                    score++;
                }
            }
        }
    }

    /**
     * 绘制地面
     *
     * @param g
     */
    private void drawGround(Graphics g) {
        final int groundW = groundImage.getWidth();
        final int groundH = groundImage.getHeight();
        final int groundY = GameConstant.GAME_HEIGHT - groundH;
        g.drawImage(groundImage.getImage(), 0, groundY, groundW, GameConstant.GAME_HEIGHT, x1, 0, x1 + groundW, groundH, null);
        g.drawImage(groundImage.getImage(), groundW - 25, groundY, groundW * 2 - 25, GameConstant.GAME_HEIGHT, x1, 0, x1 + groundW, groundH, null);
        g.drawImage(groundImage.getImage(), groundW * 2 - 50, groundY, groundW * 3 - 50, GameConstant.GAME_HEIGHT, x1, 0, x1 + groundW, groundH, null);
        x1 += 5;
        if (x1 == 20) {
            x1 = 0;
        }
    }

    /**
     * 绘制
     *
     * @param g
     */
    private void draw(Graphics g) {
        g.drawImage(backImage.getImage(), 0, 0, null);
        g.setFont(scoreFont);
        drawPipe(g);
        drawGround(g);
        g.drawString("" + score, GameConstant.GAME_WIDTH / 2, GameConstant.GAME_HEIGHT / 5);
        if (stop) {
            g.drawImage(overImage.getImage(), (GameConstant.GAME_WIDTH - overImage.getWidth()) / 2, (GameConstant.GAME_HEIGHT - overImage.getHeight()) / 2, null);
        }
        if (!stop) {
            g.drawImage(birdImage.getImage(), birdX, birdY, birdX + birdImage.getWidth() / 3, birdY + birdImage.getHeight(), x, 0, x + birdImage.getWidth() / 3, birdImage.getHeight(), null);
            x += birdImage.getWidth() / 3;
            if (x > birdImage.getWidth() - 20) {
                x = 0;
            }
        }
        if (!dead) {
            drawPoints(g);
        }
    }

    /**
     * 绘制
     *
     * @param g
     */
    @Override
    public void paint(Graphics g) {
        g.setColor(Color.WHITE);
        birdDown();
        draw(g);
    }

    public static void main(String[] args) {
        new BirdGame().launchFrame();
    }

}
