package rocks.thiscoder.brickimagebuilder;

import org.apache.commons.cli.*;
import rocks.thiscoder.brickimagebuilder.model.BlockImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BrickImageController {
    private final BufferedImage image;
    private final int blockHeight;
    private final int blockWidth;
    private final Map<Integer, String> pixelToImage;
    private final String writeLocation;

    public BrickImageController(BufferedImage image,
                                int blockHeight,
                                int blockWidth,
                                Map<Integer, String> pixelToImage,
                                String writeLocation) {
        this.image = image;
        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
        this.pixelToImage = pixelToImage;
        this.writeLocation = writeLocation;
    }

    void generateBrickImage() throws IOException {
        BlockImageBuilder blockImageBuilder = new BlockImageBuilder(image, blockHeight, blockWidth);
        BlockImage blockImage = blockImageBuilder.getBlockImage();
        BlockImageDecomposer blockImageDecomposer = new BlockImageDecomposer(blockImage, pixelToImage);
        List<List<String>> decomposedImage = blockImageDecomposer.getDecomposedImage();
        DecomposedImageWriter decomposedImageWriter = new DecomposedImageWriter(blockHeight, blockWidth, writeLocation,
                decomposedImage);
        decomposedImageWriter.write();
    }

    public static void main(String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();
        Options options = new Options();

        Option inputFile = new Option("i", "input-file", true, "Input file");
        Option outputFile = new Option("o", "output-file", true, "Output file");
        Option imageFolder = new Option("f", "image-folder", true, "Location of" +
                " the folder with images and values.json file.");
        options.addOption(inputFile);
        options.addOption(outputFile);
        options.addOption(imageFolder);

        try {
            CommandLine commandLine = commandLineParser.parse(options, args);

            if(!commandLine.hasOption('i') || !commandLine.hasOption('o') ||
                    !commandLine.hasOption('f')) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar brick.jar", options);
                return;
            }

            String iFile = commandLine.getOptionValue('i');
            String oFile = commandLine.getOptionValue('o');
            String iFolder = commandLine.getOptionValue('f');

            BufferedImage image = ImageIO.read(new File(iFile));
            FolderBasedMapBuilder folderBasedMapBuilder = new FolderBasedMapBuilder(
                    iFolder,
                    16, 16);

            Map<Integer, String> pixelToImage = folderBasedMapBuilder.getPixelLocationMap();
            BrickImageController brickImageController = new BrickImageController(image, 16, 16,
                    pixelToImage, oFile);

            brickImageController.generateBrickImage();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
