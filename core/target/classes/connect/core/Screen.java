package connect.core;

import playn.core.Mouse;

public interface Screen {

    // Setup and destroy the state
    void init();
    void cleanup();

    // Used when temporarily transitioning to another state
    void pause();
    void resume();

    // Handle different events
    void handleMouseUpEvent(Mouse.ButtonEvent buttonEvent);
    void handleMouseDownEvent(Mouse.ButtonEvent buttonEvent);
    void handleMouseMovedEvent(Mouse.MotionEvent event);

    // Update
    void update(float delta);
    void draw();

}
