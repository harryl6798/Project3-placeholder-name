package com.base.engine;

import org.lwjgl.glfw.GLFWKeyCallback;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_1;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

/**
 * Adapted from LWJGL 3 tutorial: https://tutorialedge.net/java/lwjgl3/lwjgl-3-keyboard-input-handler-tutorial/
 */
public class InputHandler {
    private static boolean[] keys = new boolean[65536];

    private static boolean isMousePressed = false;
    private static boolean isMouseReleased = false;

    private static double mouseXPos = 0;
    private static double mouseYPos = 0;

    public void invokeKey(long window, int key, int scancode, int action, int mods) {
        if (key == -1) {
            return;
        }
        keys[key] = action != GLFW_RELEASE;
    }

    /**
     * Static boolean method to detect if a given key is pressed
     * @param keycode Key to test
     * @return true if key is pressed down
     */
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }

    public void invokeMouseButton(long window, int button, int action, int mods) {
        if (button != GLFW_MOUSE_BUTTON_1)
            return;

        if (action == GLFW_PRESS) {
            isMousePressed = true;
            isMouseReleased = false;
        }else if (action == GLFW_RELEASE) {
            isMousePressed = false;
            isMouseReleased = true;
        }
    }

    public void invokeMouseMovement(long window, double xPos, double yPos) {
        mouseXPos = xPos;
        mouseYPos = yPos;
    }

    public static boolean isMouseDown() {
        return isMousePressed;
    }

    public static boolean isMouseReleased() {
        return isMouseReleased;
    }

    public static Vector2f getMousePos() {
        return new Vector2f((float) mouseXPos, (float) mouseYPos);
    }
}
