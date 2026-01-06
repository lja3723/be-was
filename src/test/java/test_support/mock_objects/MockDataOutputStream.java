package test_support.mock_objects;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 메서드 호출 횟수 및 작성된 바이트 수를 추적하는 Mock DataOutputStream
 */
public class MockDataOutputStream extends DataOutputStream {

    private int flushCallCount;
    private int write3ArgCallCount;

    public MockDataOutputStream(OutputStream out) {
        super(out);
        this.flushCallCount = 0;
        this.write3ArgCallCount = 0;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        write3ArgCallCount++;
    }

    @Override
    public void flush() {
        flushCallCount++;
    }

    public int getWrittenBytes() {
        // 부모 클래스의 written 필드에 접근하여 작성된 바이트 수 반환
        return super.written;
    }

    public int getFlushCallCount() {
        return flushCallCount;
    }

    public int getWrite3ArgCallCount() {
        return write3ArgCallCount;
    }
}
