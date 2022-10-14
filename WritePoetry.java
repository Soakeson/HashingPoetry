import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.print.event.PrintEvent;

public class WritePoetry {
    public void WritePoem(String fileName, String seedWord, int wordCount, boolean bool) {
        try {
            HashTable<String, WordFreqInfo> table = parseFile(fileName);
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
            System.exit(404);
        }
        System.out.println("hello");
    }

    /**
     * Parse a text file into an HashTable by entering each word as a WordFreqInfo to keep track of uses and following words. 
     * @param fileName the name of the text file to be parsed
     * @return a HashTable<String, WordFreqInfo>
     * @throws FileNotFoundException if the file is not found throws an exception
     */
    private HashTable<String, WordFreqInfo> parseFile(String fileName) throws FileNotFoundException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        HashTable<String, WordFreqInfo> table = new HashTable<>();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] wordArray = line.split(" ");

            for (int i=0; i < wordArray.length; i++) {
                String word = wordArray[i].toLowerCase();
                WordFreqInfo info = table.find(word);

                if (info != null && i < wordArray.length - 1) { // If the word has already been entered update the word.
                    info.updateFollows(wordArray[i+1].toLowerCase());
                } else if (i < wordArray.length - 1) { // If the current word hasn't been entered into the table enter it and update follows.
                    WordFreqInfo wordInfo = new WordFreqInfo(word, 0);
                    wordInfo.updateFollows(wordArray[i+1].toLowerCase());
                    table.insert(word, wordInfo);
                }
            }
        }
        return table;
    }

    private int pickRand(int min, int max) {
        return 0;
    }
}
