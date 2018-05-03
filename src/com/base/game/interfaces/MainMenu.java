package com.base.game.interfaces;

import com.base.engine.*;
import com.base.game.Game;
import com.base.game.gameobject.button.GameButton;
import com.base.game.levels.LevelManager;
import com.test.TestDriver;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.lwjgl.opengl.GL11.*;

public class MainMenu extends Interface {
    private GameButton startButton;
    private GameButton quitButton;

    private TextRenderer testOptions;
    private GameButton runTestSuite;
    private GameButton loadTestLevelManual;
    private GameButton loadTestLevelAuto;

    private int music;
    private boolean startedAudio;

    /**
     * Initialize the main menu screen
     */
    @Override
    public void init(String fileName, int widthOfImage, int heightOfImage) {
        super.init(fileName, widthOfImage, heightOfImage);

        startedAudio = false;
        music = Audio.loadSound("res/audio/Ove_Melaa_Times.ogg");

        startButton = new GameButton((float)(Display.getWidth()/2 - 200), (float)(Display.getHeight()/2 + 100), 400, 80, "Start",
                () -> {
                    LevelManager.loadGameLevel();
                    start();
                });
        quitButton = new GameButton((float)(Display.getWidth()/2 - 200), (float)(Display.getHeight()/2 - 300), 400, 80, "Quit", Display::quit);

        runTestSuite = new GameButton((float)(Display.getWidth()/2 - 640), (float)(Display.getHeight()/2 - 100), 400, 80, "Suite",
                () -> {
                    try {
                        TestDriver.runTests();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                });

        loadTestLevelManual = new GameButton((float)(Display.getWidth()/2 - 200), (float)(Display.getHeight()/2 - 100), 400, 80, "Manual",
                () -> {
                    LevelManager.loadTestLevel(false);
                    start();
                });

        loadTestLevelAuto = new GameButton((float)(Display.getWidth()/2 + 240), (float)(Display.getHeight()/2 - 100), 400, 80, "Auto",
                () -> {
                    LevelManager.loadTestLevel(true);
                    start();
                });

        testOptions = new TextRenderer("Test Options", 400, 40, true, 2, false, (float)(Display.getWidth()/2 - 200), (float)(Display.getHeight()/2));
    }

    public void startAudio() {
        Audio.playBuffer(music);
        Audio.loopBuffer(music);

        startedAudio = true;
    }

    /**
     * Perform actions based off of the user clicking the buttons
     */
    public void update() {
        if (!startedAudio) {
            startAudio();
        }

        startButton.update();
        quitButton.update();

        runTestSuite.update();
        loadTestLevelManual.update();
        loadTestLevelAuto.update();
    }

    /**
     * Render the main menu
     */
    @Override
    public void render() {

        super.render();
        startButton.render();
        quitButton.render();

        renderTestOptions();

    }

    /**
     * Renders the test options
     */
    private void renderTestOptions()
    {
        // Outline of a box that holds the test options
        glLineWidth(4);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glColor4f(0.5f, 0.5f, 0.5f, 0.0f);
        glBegin(GL11.GL_QUADS);
            glVertex2f((float)(Display.getWidth()/2 - 680), (float)(Display.getHeight()/2 - 140));
            glVertex2f((float)(Display.getWidth()/2 + 680), (float)(Display.getHeight()/2 - 140));
            glVertex2f((float)(Display.getWidth()/2 + 680), (float)(Display.getHeight()/2 + 20));
            glVertex2f((float)(Display.getWidth()/2 - 680), (float)(Display.getHeight()/2 + 20));
        glEnd();
        glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);

        // Covers up the line
        glColor4f(0.721568627f, 0.717647059f, 0.749019608f, 0.0f);
        glBegin(GL11.GL_QUADS);
            glVertex2f((float)(Display.getWidth()/2 - 150), (float)(Display.getHeight()/2));
            glVertex2f((float)(Display.getWidth()/2 + 150), (float)(Display.getHeight()/2));
            glVertex2f((float)(Display.getWidth()/2 + 150), (float)(Display.getHeight()/2 + 40));
            glVertex2f((float)(Display.getWidth()/2 - 150), (float)(Display.getHeight()/2 + 40));
        glEnd();

        testOptions.render(); // The text
        // The buttons
        runTestSuite.render();
        loadTestLevelManual.render();
        loadTestLevelAuto.render();
    }

    private void reset() {
        startedAudio = false;
        Audio.stopBuffer(music);
    }

    private void start() {
        Game.start();
        reset();
    }
}
