package com.sheandstud.processing;

import java.io.Closeable;
import java.io.IOException;

public interface DataHandler extends Closeable {
    boolean handle(String data) throws IOException;
    Statistics getStatistics();
}
