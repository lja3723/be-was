package app.db;

import app.model.Image;
import java.util.HashMap;
import java.util.Map;

public class ImageDatabase {

    private static final Map<String, Image> images = new HashMap<>();

    public static void addImage(Image image) {
        images.put(image.url(), image);
    }

    public static Image findImageByUrl(String url) {
        return images.get(url);
    }

    public static Map<String, Image> findAll() {
        return images;
    }
}


