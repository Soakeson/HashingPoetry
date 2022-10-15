import java.util.ArrayList;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class WritePoetry {
    public String WritePoem(String fileName, String seedWord, int wordCount, boolean printHash) {
        HashTable<String, WordFreqInfo> table = parseFile(fileName);
        StringBuilder sb = new StringBuilder();
        if (printHash) table.printTable();
        for (int i=0; i < wordCount; i++) {
            if (seedWord != "?" && seedWord != "!" && seedWord != ".")
                sb.append(seedWord + " ");
            else 
                sb.append(seedWord + "\n");
            //******************************************************** DELETE ME **************************************
            // System.out.println(seedWord + " " + table.find(seedWord));
            //******************************************************* */
            seedWord = nextWord(table.find(seedWord));
        }
        return sb.toString();
    }

    /**
     * Parse a text file into an HashTable by entering each word as a WordFreqInfo to keep track of uses and following words. 
     * @param fileName the name of the text file to be parsed
     * @return a HashTable<String, WordFreqInfo>
     * @throws FileNotFoundException if the file is not found throws an exception
     */
    private HashTable<String, WordFreqInfo> parseFile(String fileName) {
        try {
            Path filePath = Path.of(fileName);
            String poem = Files.readString(filePath);
            poem = poem.replaceAll("\\n|\\r", " ");
            String[] wordArray = poem.split(" ");
            HashTable<String, WordFreqInfo> table = new HashTable<>();
            
            for (int i=0; i < wordArray.length - 1; i++) {
                String word = wordArray[i].toLowerCase();
                WordFreqInfo info = table.find(word);

                if (info != null && i < wordArray.length) { // If the word has already been entered update the word.
                    info.updateFollows(wordArray[i+1].toLowerCase());
                } else { // If the current word hasn't been entered into the table enter it and update follows.
                    WordFreqInfo wordInfo = new WordFreqInfo(word, 0);
                    wordInfo.updateFollows(wordArray[i+1].toLowerCase());
                    table.insert(word, wordInfo);
                }
            }
            return table;
        } catch(Exception e) {
            System.out.println(e);
            System.exit(1);
        }
        return null;
    }

    private String nextWord(WordFreqInfo wordInfo) {
        Random rand = new Random();
        int r = rand.nextInt(wordInfo.occurCt);
        for (WordFreqInfo.Freq follow: wordInfo.followList) {
            r = r - follow.followCt;
            if (r <= 0) {
                WordFreqInfo.Freq next = follow;
                return next.follow;
            }
        }
        return "";
    }
}
