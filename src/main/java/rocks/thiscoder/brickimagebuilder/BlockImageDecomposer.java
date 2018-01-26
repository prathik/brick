package rocks.thiscoder.brickimagebuilder;

import rocks.thiscoder.brickimagebuilder.model.Block;
import rocks.thiscoder.brickimagebuilder.model.BlockImage;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class BlockImageDecomposer {
    private final BlockImage blockImage;
    private final Map<Integer, String> rgbImages;

    BlockImageDecomposer(BlockImage blockImage, Map<Integer, String> rgbImages) {
        this.blockImage = blockImage;
        this.rgbImages = rgbImages;
    }

    List<List<String>> getDecomposedImage() {
        List<List<String>> result = new LinkedList<List<String>>();

        for(List<Block> row: blockImage.getImage()) {
            List<String> decomposedRow = new LinkedList<String>();
            for(Block b: row) {
                VotingBasedBlockToImage votingBasedBlockToImage = new VotingBasedBlockToImage(rgbImages, b);
                String mostEligibleImage = votingBasedBlockToImage.getMaxVotedImage();
                if(mostEligibleImage == null) {
                    throw new RuntimeException("Image cannot be null. Check logic.");
                }
                decomposedRow.add(mostEligibleImage);
            }
            result.add(decomposedRow);
        }

        return result;
    }
}
