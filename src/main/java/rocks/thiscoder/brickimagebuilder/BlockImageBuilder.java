package rocks.thiscoder.brickimagebuilder;

import rocks.thiscoder.brickimagebuilder.model.Block;
import rocks.thiscoder.brickimagebuilder.model.BlockImage;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

class BlockImageBuilder {
    private final BufferedImage image;
    private final int blockWidth;
    private final int blockHeight;

    BlockImageBuilder(BufferedImage image, int blockHeight, int blockWidth) {
        this.image = image;
        this.blockWidth = blockWidth;
        this.blockHeight = blockHeight;
    }

    private int getTotalColumns() {
        return image.getWidth()/blockWidth;
    }

    private int getTotalRows() {
        return image.getHeight()/blockHeight;
    }

    private boolean isValidBlock(int row, int column) {
        return column >= 0 && column < getTotalColumns() && row >= 0 && row < getTotalRows();

    }

    private boolean isValidPixel(int x, int y) {
        return x >= 0 && x < image.getHeight() && y >= 0 && y < image.getWidth();
    }

    private Block getBlock(int row, int column) {
        if(!isValidBlock(row, column)) {
            throw new IllegalArgumentException("Invalid block specified!");
        }

        int colPxStart = column*blockWidth;
        int rowPxStart = row*blockHeight;

        int[][] pixels = new int[blockHeight][blockWidth];

        for(int i = 0; i < blockHeight; i++) {
            for(int j = 0; j < blockWidth; j++) {
                if(isValidPixel(rowPxStart + i, colPxStart + j)) {
                    pixels[i][j] = image.getRGB(colPxStart + j, rowPxStart + i);
                } else {
                    pixels[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        return new Block(pixels, blockHeight, blockWidth);
    }

    BlockImage getBlockImage() {
        List<List<Block>> allBlocks = new LinkedList<List<Block>>();
        for(int i = 0; i < getTotalRows(); i++) {
            List<Block> blockRow = new LinkedList<Block>();
            for(int j = 0; j < getTotalColumns(); j++) {
                blockRow.add(getBlock(i, j));
            }
            allBlocks.add(blockRow);
        }

        return new BlockImage(allBlocks, getTotalRows(), getTotalColumns());
    }
}
