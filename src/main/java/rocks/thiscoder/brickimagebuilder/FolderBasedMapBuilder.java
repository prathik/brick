package rocks.thiscoder.brickimagebuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import rocks.thiscoder.brickimagebuilder.model.JsonDescItem;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FolderBasedMapBuilder {
    private final File folder;
    private final int blockHeight;
    private final int blockWidth;

    public FolderBasedMapBuilder(String file, int blockHeight, int blockWidth) {
        folder = new File(file);

        if(!folder.isDirectory()) {
            throw new IllegalArgumentException("Invalid folder");
        }

        this.blockHeight = blockHeight;
        this.blockWidth = blockWidth;
    }

    private boolean isValidImage(BufferedImage image) throws IOException {
        if(image.getHeight() != blockHeight) {
            return false;
        }

        if(image.getWidth() != blockWidth) {
            return false;
        }

        return true;
    }

    Map<Integer, String> getPixelLocationMap() throws IOException {
        String jsonFile = folder.getAbsolutePath() + "/values.json";
        String json = FileUtils.readFileToString(new File(jsonFile), "utf-8");
        List<JsonDescItem> jsonDescItems;
        ObjectMapper mapper = new ObjectMapper();
        jsonDescItems = mapper.readValue(json, new TypeReference<List<JsonDescItem>>(){});
        Map<Integer, String> result = new HashMap<Integer, String>();
        for(JsonDescItem jsonDescItem: jsonDescItems) {
            Color color = new Color(jsonDescItem.getR(), jsonDescItem.getG(), jsonDescItem.getB());
            File file = new File(folder.getAbsolutePath() + "/" + jsonDescItem.getFile());
            BufferedImage image = ImageIO.read(file);
            if(isValidImage(image)) {
                result.put(color.getRGB(), file.getAbsolutePath());
            } else {
                throw new IllegalArgumentException(file.getAbsolutePath() + " is not a valid image. " +
                        "Check for image format and image size. Required dimensions is " + blockHeight + "*"
                        + blockHeight);
            }
        }
        return result;
    }
}
