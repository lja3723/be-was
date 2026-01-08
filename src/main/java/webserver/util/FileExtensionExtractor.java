package webserver.util;

public class FileExtensionExtractor {

    public static String get(String path) {
        int lastDotIndex = path.lastIndexOf('.');
        if (lastDotIndex == -1 || lastDotIndex == path.length() - 1) {
            return null;
        }
        return path.substring(lastDotIndex + 1);
    }

}
