package test;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import original.LinesOfCode;
//import refactored.LinesOfCodeRefactored;

public class LinesOfCodeTest {

    private static final String TEST_FILE_NAME = "JavaDemoFile.java";

    @Test
    public void testOriginalVersion() throws IOException {
        assertEquals(10, LinesOfCode.countLines(TEST_FILE_NAME));
    }

    /**
    @Test
    public void testRefactoredVersion() throws IOException {
        assertEquals(10, LinesOfCodeRefactored.countLines(TEST_FILE_NAME));
    }
    **/
}

