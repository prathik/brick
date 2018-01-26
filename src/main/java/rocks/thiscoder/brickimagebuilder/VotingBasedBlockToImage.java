package rocks.thiscoder.brickimagebuilder;

import rocks.thiscoder.brickimagebuilder.model.Block;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VotingBasedBlockToImage {
    private final Map<Integer, String> rgbImage;
    private final Block block;

    public VotingBasedBlockToImage(Map<Integer, String> rgbImage, Block block) {
        this.rgbImage = rgbImage;
        this.block = block;
    }

    private double eucledianDist(int defaultPixel, int currentPixel) {
        int cAlpha = (currentPixel>>24) & 0xff;
        int cR = (currentPixel>>16) & 0xff;
        int cG = (currentPixel>>8) & 0xff;
        int cB = (currentPixel) & 0xff;

        int dAlpha = (defaultPixel>>24) & 0xff;
        int dR = (defaultPixel>>16) & 0xff;
        int dG = (defaultPixel>>8) & 0xff;
        int dB = (defaultPixel) & 0xff;

        return Math.sqrt(Math.pow(cAlpha - dAlpha, 2) +
                Math.pow(cR - dR, 2) +
                Math.pow(cG - dG, 2) +
                Math.pow(cB - dB, 2) );
    }

    private String getClosestImageForPixel(int i, int j) {
        if(!block.isValidPixel(i, j)) {
            throw new IllegalArgumentException("Invalid pixel for block.");
        }
        Integer pixel = block.getPixel(i, j);

        if(pixel == Integer.MAX_VALUE) {
            return null;
        }

        Set<Integer> integers = rgbImage.keySet();
        Double min = Double.MAX_VALUE;
        Integer minDefPixel = Integer.MAX_VALUE;

        for(Integer possibleDefaultPixelCandidate: integers) {
            Double eucledianDist = eucledianDist(possibleDefaultPixelCandidate, pixel);
            if(eucledianDist < min) {
                min = eucledianDist;
                minDefPixel = possibleDefaultPixelCandidate;
            }
        }

        return rgbImage.get(minDefPixel);
    }

    String getMaxVotedImage() {
        Map<String, Integer> votes = new HashMap<String, Integer>();
        for(int i = 0; i < block.getHeight(); i++) {
            for(int j = 0; j < block.getWidth(); j++) {
                String voteFromPixel = getClosestImageForPixel(i, j);

                if(voteFromPixel != null) {
                    if (votes.get(voteFromPixel) == null) {
                        votes.put(voteFromPixel, 1);
                    } else {
                        Integer count = votes.get(voteFromPixel);
                        count = count + 1;
                        votes.put(voteFromPixel, count);
                    }
                }
            }
        }

        Integer maxVotes = Integer.MIN_VALUE;
        String maxVoteImage = null;

        for(Map.Entry<String, Integer> vote: votes.entrySet()) {
            if(vote.getValue() > maxVotes) {
                maxVoteImage = vote.getKey();
            }
        }

        if(maxVoteImage == null) {
            throw new RuntimeException("This cannot be null. Shows logic flaw. Check code.");
        }

        return maxVoteImage;
    }
}
