package pojo;

import util.BackGround;
import util.StaticValue;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @description:
 * @author: liu jin
 * @create: 2024-10-22 23:51
 **/
public class Enemy implements Runnable {
    //存储当前坐标
    private int x;
    private int y;
    //存储敌人类型
    private int type;
    //判断敌人运动方向
    private boolean face_to = true;
    //用于显示当前敌人图像
    private BufferedImage show;
    //定义一个背景对象
    private BackGround bg;
    //食人花运动极限
    private int max_up = 0;
    private int max_down = 0;
    //定义一个线程对象，食人花和蘑菇的运动
    private Thread thread = new Thread(this);
    //用来表示当前图片的状态
    private int image_type = 0;

    //蘑菇敌人
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.bg = bg;
        show = StaticValue.mogu.get(0);
        thread.start();
    }

    //食人花敌人
    public Enemy(int x, int y, int type, boolean face_to, BackGround bg, int max_up, int max_down) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.face_to = face_to;
        this.bg = bg;
        this.max_up = max_up;
        this.max_down = max_down;
        show = StaticValue.flower.get(0);
        thread.start();
    }
    //死亡方法
    public void Death(){
        if (this.type == 1) {
            show = StaticValue.mogu.get(1);
            this.bg.getEnemyList().remove(this);
        }

    }

    @Override
    public void run() {
        while (true) {
            //蘑菇敌人
            if (type == 1) {
                if (face_to) {
                    this.x -= 2;
                } else this.x += 2;
                image_type = image_type == 1 ? 0 : 1;
                show = StaticValue.mogu.get(image_type);
            }

            //定义两个布尔值
            boolean canLeft = true;
            boolean canRight = true;

            for (int i = 0; i < bg.getObstacleList().size(); i++) {
                Obstacle obstacle = bg.getObstacleList().get(i);
                //判断是否可以向左右走
                if (obstacle.getX() == this.x - 36 && (obstacle.getY() + 65 > this.y && obstacle.getY() - 35 < this.y)) {
                    canLeft = false;
                }
                if (obstacle.getX() == this.x + 36 && (obstacle.getY() + 65 > this.y && obstacle.getY() - 35 < this.y)) {
                    canRight = false;
                }

            }
            //判断一下方向
            if (face_to && !canLeft || this.x == 0) {
                face_to = false;
            } else if ((!face_to) && (!canRight) || this.x == 764) {
                face_to = true;
            }

            //判断是否为食人花敌人
            if (type == 2) {
                if (face_to) {
                    //向上
                    this.y -= 2;
                } else {
                    //向下
                    this.y += 2;
                }
                image_type = image_type == 1 ? 0 : 1;
                //判断食人花是否到达极限位置
                if (face_to && (this.y == max_up)) {
                    face_to = false;
                }
                if ((!face_to) && (this.y == max_down)) {
                    face_to = true;
                }
                show = StaticValue.flower.get(image_type);
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public int getType() {
        return type;
    }
}
