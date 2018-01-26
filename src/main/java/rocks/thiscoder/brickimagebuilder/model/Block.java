package rocks.thiscoder.brickimagebuilder.model;

public class Block {
    private final int[][] pixels;
    private final int width;
    private final int height;

    public Block(int[][] pixels, int height, int width) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public boolean isValidPixel(int i, int j) {
        return i >= 0 && i < height && j >= 0 && j < width;

    }

    public int getPixel(int i, int j) {
        if(!isValidPixel(i, j)) {
            throw new IllegalArgumentException("Invalid pixel");
        }

        return pixels[i][j];
    }

    int getMaxRow() {
        return (height - 1);
    }

    int getMaxColumn() {
        return (width - 1);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
