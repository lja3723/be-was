package _support.mock_objects;

import java.io.IOException;
import java.io.OutputStream;

/**
 * MockDataOutputStream에 전달할 단순 OutputStream 구현체
 */
public class MockOutputStream extends OutputStream {

    @Override
    public void write(int b) throws IOException {
    }
}
