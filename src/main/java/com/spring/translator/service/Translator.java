package com.spring.translator.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

@Service
public class Translator {
    public void getStatistics(String inputFile, String outputFile){
        String text = this.readFile(inputFile);
        int numberOfStanzas = text.split("\\n\\n").length;

        String[] words = text.split("[\\n\\n ]");
        String[] wordsFormatted = Arrays.stream(words).map(
                w -> w.replaceAll("[,']", "").toLowerCase()
        ).toArray(String[]::new);
        String[] wordsDebugged = Arrays.stream(wordsFormatted).filter(
                w -> !w.equals("")
        ).toArray(String[]::new);
        String wordMostRepeated = this.wordMostRepeated(wordsDebugged);

        String statistics = String.format("wmr=%s\nls=%o", wordMostRepeated, numberOfStanzas);
        this.copyToFile(outputFile, statistics);
    }

    public void reverseText(String inputFile, String outputFile){
        String content = this.readFile(inputFile);
        String[] rows = content.split("\\n");
        Collections.reverse(Arrays.asList(rows));
        String reverseContent = String.join("\n", rows);
        this.copyToFile(outputFile, reverseContent);
    }

    public void setWordsMostRepeated(String inputFile, String outputFile, String replaceWord, String statisticsFile){
        String content = this.readFile(inputFile);
        String statisticsContent = this.readFile(statisticsFile);
        String wordToReplace = statisticsContent.
                substring(statisticsContent.indexOf('=') + 1, statisticsContent.indexOf("\n"));

        String contentReplaced = content.replaceAll(wordToReplace , replaceWord);
        this.copyToFile(outputFile, contentReplaced);
    }

    private String readFile(String inputFileName){
        try{
            InputStream is = new FileInputStream(inputFileName);
            String content = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
            return content;
        } catch (Exception exception){
            System.out.println(exception);
            return null;
        }
    }

    private void copyToFile(String outPutName, String text){
        try{
            OutputStream out = new FileOutputStream(outPutName);
            StreamUtils.copy(text, StandardCharsets.UTF_8, out);
        } catch (Exception exception){
            System.out.println(exception);
        }
    }

    private String wordMostRepeated(String[] words){
        HashMap<String, Integer> wordsRepeated = new HashMap<>();

        for (String word: words) {
            if (wordsRepeated.containsKey(word)) {
                int count = wordsRepeated.get(word);
                wordsRepeated.replace(word, count + 1);
            } else {
                wordsRepeated.put(word, 1);
            }
        }

        String wordRepeated = words[0];
        for (String k: wordsRepeated.keySet()) {
            if (wordsRepeated.get(k) > wordsRepeated.get(wordRepeated)) {
                wordRepeated = k;
            }
        };

        return wordRepeated;
    }
}
