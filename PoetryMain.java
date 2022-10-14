
public class PoetryMain {
    public static void main(String[] args) {

        WritePoetry poem = new WritePoetry();
        // System.out.println(poem.WritePoem("Green.txt", "sam", 20, true));
        // System.out.println(poem.WritePoem("Lester.txt", "lester", 30, true));
        // System.out.println(poem.WritePoem("HowMany.txt", "how", 30, false));
        // System.out.println(poem.WritePoem("Zebra.txt", "are", 50, true));

        poem.WritePoem("reen.txt", "sam", 20, true);
        System.out.println();
        poem.WritePoem("Lester.txt", "lester", 30, true);
    }

}