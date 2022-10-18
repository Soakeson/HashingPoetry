import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;


public class WritePoetry {
    /**
     * Generates a poem from text read in a text file.
     * @param fileName the path to the text file to be used to generate the poem.
     * @param seedWord the beginning word of the poem to generate from.
     * @param wordCount the amount of words generated in the poe including punctuation.
     * @param printHash print the has table that was generated or not.
     * @return a formated string poem.
     */
    public String WritePoem(String fileName, String seedWord, int wordCount, boolean printHash) {
        HashTable<String, WordFreqInfo> table = parseFile(fileName);
        if (table.find(seedWord) == null) return "Seed Word not found."; 
        StringBuilder sb = new StringBuilder();
        if (printHash) table.printTable();
        String previousWord = "";
        for (int i=0; i < wordCount; i++) {
            if (seedWord.contains("?") || seedWord.contains("!") || seedWord.contains(".")) {
                seedWord = seedWord + "\n";
            }
            else if (!seedWord.contains(",") && !previousWord.contains("\n") && i != 0)
                seedWord = " " + seedWord;
            previousWord = seedWord;
            sb.append(seedWord);
            seedWord = nextWord(table.find(seedWord.strip()));
        }
        return sb.toString();
    }

    /**
     * Parse a text file into a HashTable by entering each word as a WordFreqInfo to keep track of uses and following words. 
     * @param fileName the name of the text file to be parsed
     * @return a HashTable<String, WordFreqInfo>
     */
    private HashTable<String, WordFreqInfo> parseFile(String fileName) {
        try {
            Path filePath = Path.of(fileName);
            String poem = Files.readString(filePath);
            poem = poem.replaceAll("\\s+", " ");
            String[] wordArray = poem.split(" ");
            HashTable<String, WordFreqInfo> table = new HashTable<>();
            
            for (int i=0; i < wordArray.length-1; i++) {
                String word = wordArray[i].toLowerCase().strip();
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

    /***
     * Returns the next randomly selected word probabiticaly based on the amount of entries.
     * @param wordInfo the current wordInfo to decide what word will be returned
     * @return the next word wordInfo
     */
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
