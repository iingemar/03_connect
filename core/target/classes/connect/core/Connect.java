package connect.core;

import playn.core.Game;

public class Connect implements Game {
    private GameEngine gameEngine;

    @Override
    public void init() {
        gameEngine = new GameEngine();
    }

    @Override
    public void paint(float alpha) {
        // the background automatically paints itself, so no need to do anything here!
    }

    @Override
    public void update(float delta) {
        gameEngine.update(delta);
    }

    @Override
    public int updateRate() {
        return 25;
    }
}
