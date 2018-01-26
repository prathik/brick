package rocks.thiscoder.brickimagebuilder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

class DecomposedImageWriter {
    private final int blockHeight;
    private final int blockWidth;
    private final String writeLocation;
    private final List<List<String>> images;

    DecomposedImageWriter(int blockHeight, int blockWidth, String writeLocation, List<List<String>> images) {
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        this.writeLocation = writeLocation;
        this.images = images;
    }

    void write() throws IOException {
        int imageHeight = blockHeight*images.size();
        int imageWidth = blockWidth*images.get(0).size();

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);

        for(int i = 0; i < images.size(); i++) {
            List<String> row = images.get(i);
            for(int j = 0; j < row.size(); j++) {
                String loc = row.get(j);
                File file = new File(loc);
                BufferedImage fileImg = ImageIO.read(file);
                writeToImage(i, j, fileImg, image);
            }
        }

        ImageIO.write(image, "png", new File(writeLocation));
    }

    private void writeToImage(int i, int j, BufferedImage fileImg, BufferedImage image) {
        int startRowPx = i*blockHeight;
        int startColPx = j*blockWidth;

        for(int k = 0; k < blockHeight; k++) {
            for(int l = 0; l < blockWidth; l++) {
                image.setRGB(startColPx + l, startRowPx + k, fileImg.getRGB(l, k));
            }
        }
    }
}
