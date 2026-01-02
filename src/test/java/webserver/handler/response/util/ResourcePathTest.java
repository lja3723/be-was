package webserver.handler.response.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test_support.Pair;
import webserver.httpheader.response.ContentType;

class ResourcePathTest {

    private ResourcePath resourcePath;

    @BeforeEach
    void setUp() {
        resourcePath = new ResourcePath("/img/myimage.png");
    }

    @Test
    void getResourcePath() {
        // given
        String expected = "static/img/myimage.png";
        // when
        String actual = resourcePath.getResourcePath();
        // then
        assertEquals(expected, actual);
    }

    @Test
    void getResourcePath_RootPath() {
        // given
        ResourcePath rootResourcePath = new ResourcePath("/");
        String expected = "static/index.html";
        // when
        String actual = rootResourcePath.getResourcePath();
        // then
        assertEquals(expected, actual);
    }

    @Test
    void getFileName() {
        // given
        String expected = "myimage.png";
        // when
        String actual = resourcePath.getFileName();
        // then
        assertEquals(expected, actual);
    }

    @Test
    void getContentType() {
        // given
        List<Pair<String, ContentType>> inputOutputPair = List.of(
            new Pair<>("/txt/file.txt", ContentType.TEXT_PLAIN),
            new Pair<>("/index.html", ContentType.TEXT_HTML),
            new Pair<>("/styles/main.css", ContentType.TEXT_CSS),
            new Pair<>("/scripts/app.js", ContentType.APPLICATION_JAVASCRIPT),
            new Pair<>("/images/logo.png", ContentType.IMAGE_PNG),
            new Pair<>("/images/photo.jpg", ContentType.IMAGE_JPG),
            new Pair<>("/images/graphic.gif", ContentType.IMAGE_GIF),
            new Pair<>("/images/vector.svg", ContentType.IMAGE_SVG_XML),
            new Pair<>("/docs/readme.pdf", ContentType.TEXT_PLAIN), // unknown extension
            new Pair<>("/data/data.json", ContentType.APPLICATION_JSON),
            new Pair<>("/files/archive.zip", ContentType.TEXT_PLAIN), // unknown extension
            new Pair<>("/audio/song.mp3", ContentType.TEXT_PLAIN), // unknown extension
            new Pair<>("/video/movie.mp4", ContentType.TEXT_PLAIN), // unknown extension
            new Pair<>("/unknown/file.unknown", ContentType.TEXT_PLAIN)
        );

        inputOutputPair.forEach(pair -> {
            //when
            ResourcePath resourcePath = new ResourcePath(pair.first());

            //then
            ContentType actualContentType = resourcePath.getContentType();
            assertEquals(pair.second(), actualContentType);
        });
    }
}