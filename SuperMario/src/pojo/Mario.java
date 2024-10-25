package pojo;

import util.BackGround;
import util.StaticValue;

import java.awt.image.BufferedImage;

/**
 * @description:
 * @author: liu jin
 * @create: 2024-10-22 22:22
 **/
public class Mario implements Runnable{
    //用于表示坐标
    private int x;
    private int y;
    //用于表示当前状态
    private String status;
    //用于显示当前状态所对应的图片
    private BufferedImage show = null;
    //定义一个Background对象，用来获取障碍物的信息
    private BackGround backGround = new BackGround();
    //用来实现马里奥的动作
    private Thread thread = null;

    //用于表示马里奥的移动速度
    private int xSpeed ;

    //用于表示马里奥的跳跃速度
    private int ySpeed ;
    //定义一个索引
    private int index;
    //表示马里奥的上升时间
    private int upTime = 0;
    //判断马里奥是否走到城堡口
    private boolean isOk;
    //记录玛丽奥是否死亡
    private boolean isDeath=false;
    //用来表示积分
    private int score = 0;
    public Mario() {
    }
    public Mario(int x, int y) {
        this.x = x;
        this.y = y;
        show = StaticValue.stand_R;
        this.status = "stand--right";
        thread = new Thread(this);
        thread.start();
    }
    //马里奥向左移动
    public void leftMove(){
        //改变速度
        xSpeed = -5;
        //判断马里奥是否碰到旗帜
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        }
        else
            status = "move--left";
    }
    //马里奥向右移动
    public void rightMove(){
        //改变速度
        xSpeed = 5;
        //判断马里奥是否碰到旗帜
        if (backGround.isReach()){
            xSpeed = 0;
        }
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        }
        else
            status = "move--right";
    }
    //马里奥向左停止
    public void leftStop(){
        //改变速度
        xSpeed = 0;
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--left";
        }
        else
            status = "stop--left";
    }
    //马里奥向右停止
    public void rightStop(){
        //改变速度
        xSpeed = 0;
        //判断马里奥是否在空中
        if (status.indexOf("jump") != -1) {
            status = "jump--right";
        }
        else
            status = "stop--right";
    }
    //马里奥的跳跃方法
    public void jump() {
        //-1不是跳跃状态
        if (status.indexOf("jump") == -1) {
            if (status.indexOf("left") != -1) {
                status = "jump--left";
            } else status = "jump--right";
            ySpeed = -10;
            upTime = 7;
        }

        if (backGround.isReach()) {
            ySpeed = 0;
        }
    }
    //马里奥下落
    public void fall(){
        if (status.indexOf("left") != -1) {
            status = "jump--left";
        }
        else{
            status = "jump--right";
        }
        ySpeed = 10;
    }
    //判断马里奥是否死亡
    public void Death(){
        isDeath = true;
    }

    @Override
    public void run() {
        while (true){
            //判断马里奥是否处于障碍物上
            boolean onObstacle = false;
            //判断是否可以向右走
            boolean canRight = true;
            //判断是否可以向左走
            boolean canLeft = true;

            //判断马里奥是否到达旗帜位置
            if (backGround.isFlag() && this.x >= 500) {
                this.backGround.setReach(true);
                //判断旗帜是否下落
                if (this.backGround.isBase()) {
                    status = "move--right";
                    if (x < 690) {
                        x += 5;
                        y = 395;
                    } else {
                        isOk = true;
                    }
                } else {
                    if (y < 395) {
                        xSpeed = 0;
                        this.y += 5;
                        status = "jump--right";
                    }
                    if (y > 395) {
                        this.y = 395;
                        status = "stop--right";
                    }
                }
            }else {
                //遍历当前场景的所有障碍物
                for (int i = 0; i < backGround.getObstacleList().size(); i++) {
                    Obstacle obstacle = backGround.getObstacleList().get(i);
                    //判断马里奥是否在障碍物上
                    if (obstacle.getY() == this.y + 25 && (obstacle.getX() > this.x - 30 && obstacle.getX() < this.x + 25)) {
                        onObstacle = true;
                    }

                    //判断马里奥是否可以顶到砖块
                    if ((obstacle.getY() >= this.y - 30 && obstacle.getY() <= this.y - 20) && (obstacle.getX() > this.x - 30 && obstacle.getX()
                            < this.x + 25)) {
                        if (obstacle.getType() == 0) {
                            backGround.getObstacleList().remove(obstacle);
                            score+=1;
                        }
                        upTime = 0;
                    }

                    //判断马里奥是否可以向右走
                    if (obstacle.getX() == this.x + 25 && (obstacle.getY() > this.y - 30 && obstacle.getY() < this.y + 25)) {
                        canRight = false;
                    }
                    //判断马里奥是否可以向左走
                    if (obstacle.getX() == this.x - 30 && (obstacle.getY() > this.y - 30 && obstacle.getY() < this.y + 25)) {
                        canLeft = false;
                    }

                }

                //判断
                for (int i = 0; i < backGround.getEnemyList().size(); i++) {
                    Enemy enemy = backGround.getEnemyList().get(i);
                    //检查马里奥是否踩死敌人
                    if (enemy.getY() == this.y + 20 && (enemy.getX() - 25 <= this.x && enemy.getX() + 35 >= this.x)) {
                        if (enemy.getType() == 1) {
                            enemy.Death();
                            score += 2;
                            upTime = 3;
                            ySpeed = -10;
                        } else if (enemy.getType() == 2) {
                            Death();
                        }
                    }

                    //马里奥是否碰到敌人
                    if ((enemy.getX() + 35 > this.x && enemy.getX() - 25 < this.x) && (enemy.getY() + 35 > this.y && enemy.getY() - 20 < this.y)) {
                        //马里奥死亡
                        Death();
                    }
                }


                //进行马里奥的跳跃操作
                if (onObstacle && upTime == 0) {
                    if (status.indexOf("left") != -1) {
                        if (xSpeed != 0) {
                            status = "move--left";
                        } else {
                            status = "stop--left";
                        }
                    }
                    //马里奥的向左运动
                    else {
                        if (xSpeed != 0) {
                            status = "move--right";
                        } else {
                            status = "stop--right";
                        }
                    }
                }
                //如果位于障碍物上和空中
                else {
                    if (upTime != 0) {
                        upTime--;
                    } else {
                        fall();
                    }
                    y += ySpeed;
                }
            }

            if((canLeft && xSpeed < 0) || ( canRight && xSpeed >0)){
                x+=xSpeed;
                //判断马里奥是否到达最左边
                if (x < 0){
                    x=0;
                }
            }
            //判断马里奥是否是移动状态
            if (status.contains("move")){
                index = index==0 ? 1 : 0;
            }
            //判断是否向左移动
            if ("move--left".equals(status)){
                show = StaticValue.run_L.get(index);
            }
            //判断是否向右移动
            if ("move--right".equals(status)){
                show = StaticValue.run_R.get(index);
            }
            //判断是否向左停止
            if ("stop--left".equals(status)){
                show = StaticValue.stand_L;
            }
            //判断是否向右停止
            if ("stop--right".equals(status)){
                show = StaticValue.stand_R;
            }
            //判断是否向左跳
            if ("jump--left".equals(status)){
                show = StaticValue.jump_L;
            }
            //判断是否向右跳
            if ("jump--right".equals(status)){
                show = StaticValue.jump_R;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public BufferedImage getShow() {
        return show;
    }

    public void setBackGround(BackGround backGround) {
        this.backGround = backGround;
    }

    public boolean isOk() {
        return isOk;
    }

    public boolean isDeath() {
        return isDeath;
    }

    public int getScore() {
        return score;
    }
}
