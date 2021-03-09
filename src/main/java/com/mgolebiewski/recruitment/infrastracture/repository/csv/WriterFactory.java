package com.mgolebiewski.recruitment.infrastracture.repository.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;

public class WriterFactory {

  public BufferedWriter getBufferedWriter(File file, StandardOpenOption... options) throws IOException {
    return Files.newBufferedWriter(file.toPath(), options);
  }
}
