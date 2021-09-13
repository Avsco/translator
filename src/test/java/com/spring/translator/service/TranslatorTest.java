package com.spring.translator.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StreamUtils;


import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TranslatorTest {

    @Autowired
    private Translator translator;

    private final String BASE = "src/test/resources/";

    private final String ORIGINAL_FILE = BASE + "Original.txt";
    private final String STATISTICS_FILE = BASE + "statistics.txt";
    private final String REVERSE_TEXT_FILE = BASE + "estrofasEnOrdenInverso.txt";
    private final String REVERSE_TEXT_TEST_FILE = BASE + "estrofasEnOrdenInversoTest.txt";
    private final String FINAL_OUTPUT_FILE = BASE + "finaloutput.txt";
    private final String FINAL_OUTPUT_TEST_FILE = BASE + "finaloutputTest.txt";

    @Test
    void reverseText() {
        this.translator.reverseText(ORIGINAL_FILE, REVERSE_TEXT_FILE);
        String test;
        String content;

        try {
            InputStream is = new FileInputStream(REVERSE_TEXT_FILE);
            content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);

            InputStream ist = new FileInputStream(REVERSE_TEXT_TEST_FILE);
            test = StreamUtils.copyToString(ist, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            test = "1";
            content = "2";
        }

        assertEquals(test, content);
    }

    @Test
    void getStatistics() {
        String content;

        this.translator.getStatistics(REVERSE_TEXT_FILE, STATISTICS_FILE);
        try {
            InputStream is = new FileInputStream(STATISTICS_FILE);
            content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            content = "";
        }

        assertEquals("wmr=beggin\nls=21", content);
    }

    @Test
    void setWordsMostRepeated() {
        String content;
        String test;

        this.translator.setWordsMostRepeated(REVERSE_TEXT_FILE, FINAL_OUTPUT_FILE, "you" , STATISTICS_FILE);
        try {
            InputStream is = new FileInputStream(FINAL_OUTPUT_FILE);
            content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);

            InputStream ist = new FileInputStream(FINAL_OUTPUT_TEST_FILE);
            test = StreamUtils.copyToString(ist, StandardCharsets.UTF_8);
        } catch (Exception exception) {
            test = "1";
            content = "2";
        }

        assertEquals(test, content);
    }
}