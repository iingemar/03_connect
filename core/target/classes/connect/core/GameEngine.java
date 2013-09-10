package connect.core;

import playn.core.Mouse;

import static playn.core.PlayN.mouse;

public class GameEngine {
    private Screen screen = null;

    public GameEngine() {
        init();
    }

    public void init() {
        Screen gameScreen = new GameScreen(this);
        setScreen(gameScreen);

        // Input handler for mouse
        mouse().setListener(new Mouse.Listener() {
            @Override
            public void onMouseDown(Mouse.ButtonEvent buttonEvent) {
                screen.handleMouseDownEvent(buttonEvent);
            }
            @Override
            public void onMouseUp(Mouse.ButtonEvent buttonEvent) {
                screen.handleMouseUpEvent(buttonEvent);
            }
            @Override
            public void onMouseMove(Mouse.MotionEvent motionEvent) {
                screen.handleMouseMovedEvent(motionEvent);
            }
            @Override
            public void onMouseWheelScroll(Mouse.WheelEvent wheelEvent) {
                // Not used
            }
        });
    }

    public void update(final float delta) {
        screen.update(delta);
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
