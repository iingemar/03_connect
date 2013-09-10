package connect.core;

import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.graphics;

/**
 * Sprite
 */
public class Sprite {
    private GroupLayer groupLayer;
    private ImageLayer imageLayer;
    private Image image;
    private float x;
    private float y;
    private float width;
    private float height;

    public Sprite(GroupLayer groupLayer, float x, float y) {
        this.groupLayer = groupLayer;
        this.x = x;  // position
        this.y = y;
    }

    public void loadImage(String imageURL) {
        image = assets().getImage(imageURL);
        width = image.width();
        height = image.height();
        imageLayer = graphics().createImageLayer(image);
        imageLayer.setTranslation(x, y);
        groupLayer.add(imageLayer);
    }

    public GroupLayer getGroupLayer() {
        return groupLayer;
    }

    public void setGroupLayer(GroupLayer groupLayer) {
        this.groupLayer = groupLayer;
    }

    public ImageLayer getImageLayer() {
        return imageLayer;
    }

    public void setImageLayer(ImageLayer imageLayer) {
        this.imageLayer = imageLayer;
    }

    public void removeImageLayer() {
        groupLayer.remove(imageLayer);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void updateGraphics() {
        imageLayer.setTranslation(x, y);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setHitBox(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public void setVisible(boolean show) {
        getImageLayer().setVisible(show);
    }
}
