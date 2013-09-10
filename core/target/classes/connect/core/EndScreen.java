package connect.core;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Mouse;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-10
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class EndScreen implements Screen {

    private GameEngine gameEngine;
    private GroupLayer groupLayer;

    public EndScreen(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
        init();
    }
    
    @Override
    public void init() {
        
    }

    @Override
    public void cleanup() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseUpEvent(Mouse.ButtonEvent buttonEvent) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void handleMouseDownEvent(Mouse.ButtonEvent buttonEvent) {

    }

    @Override
    public void handleMouseMovedEvent(Mouse.MotionEvent event) {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void draw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
