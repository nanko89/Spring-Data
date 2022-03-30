package hiberspring.util.impl;

import hiberspring.util.FileUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtilImpl implements FileUtil {
    @Override
    public String readFile(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
