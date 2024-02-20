package com.ecfingerprint.ecfingerprint.util.httpUtil;


import java.io.IOException;
import java.io.InputStream;

interface StreamConverter {
    String convert(InputStream inputStream) throws IOException;
}
