package app.db.adapter;

import app.db.ImageDatabase;
import app.model.Image;
import java.util.Collection;
import java.util.Optional;

public class ImageDatabaseAdapter implements DatabaseAdapter<String, Image> {

    @Override
    public void add(Image image) {
        ImageDatabase.addImage(image);
    }

    @Override
    public Optional<Image> findById(String url) {
        return Optional.ofNullable(ImageDatabase.findImageByUrl(url));
    }

    @Override
    public Collection<Image> findAll() {
        return ImageDatabase.findAll();
    }
}
