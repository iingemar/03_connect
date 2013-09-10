package connect.core;

import playn.core.GroupLayer;
import playn.core.Mouse;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-12
 * Time: 22:10
 * To change this template use File | Settings | File Templates.
 */
public class Disc extends Sprite {

    public static int SIZE = 60;
    public static int Y_POSITION = 65;
    public static int BOARD_X = 117;

    private int currentColumn;
    private int destinationY;
    private DiscState state;

    public Disc(GroupLayer groupLayer, float x, float y, DiscType discType) {
        super(groupLayer, x, y);
        loadImage(discType.getImageURL());
        setColumn(getX());
        state = DiscState.READY;
    }

    public void setColumn(float x) {
        currentColumn = (int)Math.floor((x - 120) / SIZE);
        // Check min max
        if (currentColumn < 0) {
            currentColumn = 0;
        }
        if (currentColumn > 6) {
            currentColumn = 6;
        }
        
        setPosition(currentColumn * SIZE + 122, Y_POSITION);
    }

    public void followMouse(Mouse.MotionEvent event) {
        setColumn(event.x());
    }

    public void drop(int row) {
        destinationY = row * SIZE + BOARD_X;
        state = DiscState.MOVING;
    }

    public void update(float delta) {
        updatePosition();
        updateGraphics();
    }

    private void updatePosition() {
        if (destinationY > 0) {
            for (int i=0; i<7; i++) {
                // Move X steps, one pixel each step
                setY(getY() + 1);
                if (destinationY == getY()) {
                    destinationY = 0;
                    // Done animating
                    state = DiscState.READY;
                    break;
                }
            }
        }
    }

    public int getCurrentColumn() {
        return currentColumn;
    }

    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
        setPosition(currentColumn * SIZE + 122, Y_POSITION);
    }

    public DiscState getState() {
        return state;
    }

    public void setState(DiscState state) {
        this.state = state;
    }
}
