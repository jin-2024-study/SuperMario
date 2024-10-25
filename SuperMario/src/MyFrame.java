import javazoom.jl.decoder.JavaLayerException;
import pojo.Enemy;
import pojo.Mario;
import pojo.Obstacle;
import util.BackGround;
import util.Music;
import util.StaticValue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: liu jin
 * @create: 2024-10-22 21:16
 **/
public class MyFrame extends JFrame implements KeyListener,Runnable {
    //存储所以的背景
    private List<BackGround> allBg = new ArrayList<>();
    //存储当前背景
    private BackGround nowBg = new BackGround();
    //用于双缓存
    private Image offSceenImage = null;
    //马里奥对象
    private Mario mario= new Mario();

    //实现马里奥的移动
    private Thread thread = new Thread(this);
    public MyFrame(){
        this.setSize(800,600);
        //设置窗口居中显示
        this.setLocationRelativeTo(null);
        //设置窗口可见
        this.setVisible(true);
        //设置窗口上关闭键的结束程序
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        //添加键盘监听
        this.addKeyListener(this);
        //设置窗口名称
        this.setTitle("超级玛丽@Version1.0");
        //初始化所以的图片
        StaticValue.init();
        //初始化马里奥对象
        mario = new Mario(10,355);

        //创建所有的场景
        for (int i = 1; i <=3 ; i++) {
            allBg.add(new BackGround(i,i==3 ? true:false));
        }
        //将第一个场景设置为当前场景
        nowBg = allBg.get(0);
        mario.setBackGround(nowBg);
        //绘制图像
        repaint();
        thread.start();

        try {
            new Music();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        MyFrame myFrame = new MyFrame();
    }

    @Override
    public void paint(Graphics g) {
        if (offSceenImage == null) {
            offSceenImage = createImage(800, 600);
        }
        Graphics graphics = offSceenImage.getGraphics();
        graphics.fillRect(0, 0, 800, 600);

        //绘制背景
        graphics.drawImage(nowBg.getBgImage(), 0, 0, this);

        //绘制敌人
        for (Enemy enemy : nowBg.getEnemyList()){
            graphics.drawImage(enemy.getShow(),enemy.getX(),enemy.getY(),this);
        }

        //绘制障碍物
        for (Obstacle ob:
                nowBg.getObstacleList()) {
            graphics.drawImage(ob.getShow(), ob.getX(), ob.getY(),this);
        }


        //绘制城堡
        graphics.drawImage(nowBg.getTower(),620,270,this);
        //绘制旗杆
        graphics.drawImage(nowBg.getGan(),500,220,this);

        //绘制马里奥
        graphics.drawImage(mario.getShow(),mario.getX(),mario.getY(),this);

        //添加积分
        Color color = graphics.getColor();
        graphics.setColor(Color.CYAN);
        graphics.setFont(new Font("黑体",Font.BOLD,25));
        graphics.drawString("当前分数："+ mario.getScore(),300,100);
        graphics.setColor(color);

        //将图像绘制到窗口中
        g.drawImage(offSceenImage,0,0,this);
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        //向左
        if (e.getKeyCode() == 37){
            mario.leftMove();
        }
        //向右
        if (e.getKeyCode() == 39){
            mario.rightMove();
        }
        //跳跃
        if (e.getKeyCode() == 38){
            mario.jump();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == 37){
            mario.leftStop();
        }
        if (e.getKeyCode() == 39){
            mario.rightStop();
        }
    }

    @Override
    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(50);
                //判断马里奥是否到达下一关
                if (mario.getX() >= 775) {
                    nowBg = allBg.get(nowBg.getSort());
                    mario.setBackGround(nowBg);
                    mario.setX(10);
                    mario.setY(395);
                }
                //判断马里奥是否死亡
                if (mario.isDeath()){
                    JOptionPane.showMessageDialog(this,"马里奥死亡!");
                    System.exit(0);
                }

                //判断游戏是否结束
                if (mario.isOk()){
                    JOptionPane.showMessageDialog(this,"恭喜你成功通关！");
                    System.exit(0);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
