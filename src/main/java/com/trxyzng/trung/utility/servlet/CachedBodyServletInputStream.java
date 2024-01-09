package com.trxyzng.trung.utility.servlet;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyServletInputStream extends ServletInputStream {

    private InputStream cachedBodyInputStream;
    public int read() throws IOException {
        return cachedBodyInputStream.read();
    }
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener readListener) {}
}
