package com.mgolebiewski.recruitment.infrastracture.repository.csv;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;

public class ReaderFactory {

  public Reader getReader(File file) throws IOException {
    return Files.newBufferedReader(file.toPath());
  }
}
