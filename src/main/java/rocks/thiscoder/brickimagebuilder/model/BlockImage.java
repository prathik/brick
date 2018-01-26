package rocks.thiscoder.brickimagebuilder.model;

import java.util.List;

public class BlockImage {
    private final List<List<Block>> image;
    private final int totalColumns;
    private final int totalRows;

    public BlockImage(List<List<Block>> image, int totalRows, int totalColumns) {
        this.image = image;
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
    }

    public List<List<Block>> getImage() {
        return image;
    }

    public int getTotalColumns() {
        return totalColumns;
    }

    public int getTotalRows() {
        return totalRows;
    }
}
