package com.base.engine;

import com.base.game.utilities.Delay;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Animation {
    private Sprite[] frames;
    private int currFrame;
    private Delay frameDelay;
    private boolean isStopped;

    public Animation(String fileURI, int nFrames) {
        frames = new Sprite[nFrames];
        currFrame = 0;
        frameDelay = new Delay(200);
        frameDelay.restart();

        isStopped = false;
        loadSprites(fileURI);
    }

    private void loadSprites(String fileURI) {
        BufferedImage img = null;
        for (int i = 0; i < frames.length; i++) {
            try {
                img = ImageIO.read(new File("./res/" + fileURI + "_" + i + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            frames[i] = new Sprite(img.getWidth(), img.getHeight(), "./res/" + fileURI + "_" + i + ".png");
        }
    }

    public void start() {
        if (frameDelay.isOver()) {
            nextFrame();
            frameDelay.start();
        }
    }

    public void render(float xPos, float yPos) {
        frames[currFrame].render(xPos, yPos);
    }

    public void stop() {
        frameDelay.stop();
        isStopped = true;
    }

    public boolean isStopped() {
        return isStopped;
    }

    public void restart() {
        frameDelay.restart();
        isStopped = false;
    }

    private void nextFrame() {
        currFrame++;
        if (currFrame == frames.length) {
            currFrame = 0;
        }
    }
}
