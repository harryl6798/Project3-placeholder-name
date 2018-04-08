package com.base.game;

import com.base.engine.*;
import com.base.game.gameobject.entity.Player;
import com.base.game.gameobject.entity.Boss;
import com.base.game.gameobject.item.ConsumableItem;
import com.base.game.interfaces.UI;
import com.base.game.levels.Level;
import com.base.game.levels.LevelTransition;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static Game game;

    private ArrayList<Level> levels;
    private int currLevel;

    public Game() {
        //TEST AUDIO
        try {
            Clip bSound = AudioSystem.getClip();
            File audio = new File("./res/audio/Fighting_is_not_an_option.wav");
            bSound.open(AudioSystem.getAudioInputStream(audio.toURI().toURL()));
            bSound.start();
            bSound.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        currLevel = 0;
        levels = new ArrayList();

        Level level1 = new Level("./res/bricks.jpg", null);
      
        Boss boss = new Boss(Display.getWidth() / 2 - 35, Display.getHeight() - 150, 70, 70, "", 2f,67, 5);
        Level level2 = new Level("./res/bricks.jpg", boss);

        levels.add(level1);
        levels.add(level2);
    }

    private Level getCurrLevel() {
        return levels.get(currLevel);
    }

    public void update() {
        levels.get(currLevel).update();
    }

    public void render() {
        levels.get(currLevel).render();
    }

    public void addObj(GameObject obj) {
        getCurrLevel().addObj(obj);
    }

    public void endLevel() {
        getCurrLevel().endLevel();
    }

    public ArrayList<GameObject> getCloseObjects(GameObject object, float range) {
        return getCurrLevel().getCloseObjects(object, range);
    }

    public void levelOver(boolean lose) {
        getCurrLevel().levelOver(lose);
    }

    public float getPlayerX() {
        return getCurrLevel().getPlayerX();
    }

    public float getPlayerY() {
        return getCurrLevel().getPlayerY();
    }

    public int getHealth(boolean isPlayer)
    {
        return getCurrLevel().getHealth(isPlayer);
    }

    public void nextLevel() {
        if (currLevel < levels.size() - 1)
            currLevel++;
    }
}
