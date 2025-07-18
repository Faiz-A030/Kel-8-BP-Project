import java.awt.Image;
import java.io.File;
import java.net.URL;
import javax.swing.ImageIcon;
/**
 * This enum is used by:
 * 1. Player: takes value of CROSS or NOUGHT
 * 2. Cell content: takes value of CROSS, NOUGHT, or NO_SEED.
 *
 * We also attach a display image icon (text or image) for the items.
 *   and define the related variable/constructor/getter.
 * To draw the image:
 *   g.drawImage(content.getImage(), x, y, width, height, null);
 *
 * Ideally, we should define two enums with inheritance, which is,
 *  however, not supported.
 */
public enum Seed {   // to save as "Seed.java"
    CROSS("X", "images/cross.png"),   // displayName, imageFilename
    NOUGHT("O", "images/not.png"),
    NO_SEED(" ", null);

    // Private variables
    private String displayName;
    private Image img = null;

    // Constructor (must be private)
    private Seed(String name, String imageFilename) {
        this.displayName = name;

        if (imageFilename != null) {
            URL imgURL = getClass().getClassLoader().getResource(imageFilename);
            ImageIcon icon = null;
            if (imgURL != null) {
                icon = new ImageIcon(imgURL);
                //System.out.println(icon);  // debugging
            } else {
                System.err.println("Couldn't find file " + imageFilename);
            }
            img = icon.getImage();
        }
    }

    public void changeIcon(String imagePath) {
        ImageIcon icon = null;

        URL imgURL = getClass().getClassLoader().getResource(imagePath);
        if (imgURL != null) {
            icon = new ImageIcon(imgURL);
        } else {
            File file = new File(imagePath);
            if (file.exists()) {
                icon = new ImageIcon(imagePath); // absolute path
            } else {
                System.err.println("Couldn't find file: " + imagePath);
            }
        }

        if (icon != null) {
            img = icon.getImage();
        } else {
            System.err.println("Failed to load icon from: " + imagePath);
        }
    }


    // Public getters
    public String getDisplayName() {
        return displayName;
    }
    public Image getImage() {
        return img;
    }
}
