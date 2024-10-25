package util;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @description:
 * @author: liu jin
 * @create: 2024-10-23 08:53
 **/
public class Music {
    public Music() throws FileNotFoundException, JavaLayerException {
        Player player ;
        String str = System.getProperty("user.dir") + "/src/music/music.wav";
        BufferedInputStream name = new BufferedInputStream(new FileInputStream(str));
        player = new Player(name);
        player.play();
    }
}
