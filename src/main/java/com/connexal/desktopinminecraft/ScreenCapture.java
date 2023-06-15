package com.connexal.desktopinminecraft;

import java.awt.*;

public class ScreenCapture {
    private static final Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            throw new RuntimeException(e);
        }
    }

    public static void init() {
        // Do nothing, just initialize the static block
    }

    public static Image capture(Rectangle dimensions) {
        return robot.createScreenCapture(dimensions);
    }
}
