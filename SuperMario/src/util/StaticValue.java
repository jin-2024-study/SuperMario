package util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:常量类
 * @author: liu jin
 * @create: 2024-10-22 21:26
 **/
public class StaticValue {
    //背景
    public static BufferedImage bg = null;
    public static BufferedImage bg2 = null;
    //马里奥有四种状态
    //奥利奥的向左跳跃
    public static BufferedImage jump_L = null;
    //奥利奥的向右跳跃
    public static BufferedImage jump_R = null;
    //奥利奥的向左站立
    public static BufferedImage stand_L = null;
    //奥利奥的向右站立
    public static BufferedImage stand_R = null;
    //城堡
    public static BufferedImage tower= null;
    //旗杆
    public static BufferedImage gan = null;
    //障碍物
    public static List<BufferedImage> obstacle = new ArrayList<>();
    //马里奥向左跑
    public static List<BufferedImage> run_L = new ArrayList<>();
    //马里奥向右跑
    public static List<BufferedImage> run_R = new ArrayList<>();
    //蘑菇敌人
    public static List<BufferedImage>  mogu = new ArrayList<>();
    //食人花敌人
    public static List<BufferedImage>  flower = new ArrayList<>();
    //路径前缀，方便调用
    public static String path = System.getProperty("user.dir") + "/src/images/";
    //初始化方法
    public static void init() {
        try {
            //加载背景图片
            bg = ImageIO.read(new File(path + "bg.png"));
            bg2 = ImageIO.read(new File(path + "bg2.png"));
            //加载马里奥向左站立
            stand_L = ImageIO.read(new File(path + "stand_L.png"));
            //加载马里奥向右站立
            stand_R = ImageIO.read(new File(path + "stand_R.png"));
            //加载马里奥向左跳跃
            jump_L = ImageIO.read(new File(path + "jump1_L.png"));
            //加载马里奥向右跳跃
            jump_R = ImageIO.read(new File(path + "jump1_R.png"));
            //加载城堡
            tower = ImageIO.read(new File(path + "tower.png"));
            //加载旗杆
            gan = ImageIO.read(new File(path + "gan.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载马里奥向左跑
        for (int i = 1; i <= 2; i++) {
            try {
                run_L.add(ImageIO.read(new File(path + "run" + i + "_L.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载马里奥向右跑
        for (int i = 1; i <= 2; i++) {
            try {
                run_R.add(ImageIO.read(new File(path + "run" + i + "_R.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载障碍物
        try {
            //加载砖块，下地面，上地面
            obstacle.add(ImageIO.read(new File(path + "brick.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_base.png")));
            obstacle.add(ImageIO.read(new File(path + "soil_up.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载水管
        for (int i = 1; i <= 4; i++) {
            try {
                obstacle.add(ImageIO.read(new File(path + "pipe" +i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载不可破坏板砖和旗帜
        try {
            obstacle.add(ImageIO.read(new File(path + "brick2.png")));
            obstacle.add(ImageIO.read(new File(path + "flag.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //加载蘑菇敌人
        for (int i = 1; i <=3 ; i++) {
            try {
                mogu.add(ImageIO.read(new File(path + "fungus" +i+".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //加载食人花敌人
        for (int i = 1; i <=2 ; i++) {
            try {
                flower.add(ImageIO.read(new File(path + "flower" +i+".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
