package connect.core;

/**
 * Created by IntelliJ IDEA.
 * User: Ingemar
 * Date: 2012-06-12
 * Time: 22:41
 * To change this template use File | Settings | File Templates.
 */
public enum DiscType {
    EMPTY("empty"), RED("images/red_disc.png"), YELLOW("images/yellow_disc.png");

    private String imageURL;

    DiscType(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getImageURL() {
        return imageURL;
    }
}
