import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
public class PebbleGame {
    public static void main(String[] args) {
        /* main function */
        Scanner userInput = new Scanner(System.in);
        int[] blackBag0 = new int[0];
        int[] blackBag1 = new int[0];
        int[] blackBag2 = new int[0];

        System.out.println("Please enter the number of players: ");
        int playerCount = Integer.parseInt(userInput.next());

        System.out.println("Please enter the location of bag number 0 to load: ");
        String bag0File = userInput.next();
        try {
            blackBag0 = readWeights(bag0File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter the location of bag number 1 to load: ");
        String bag1File = userInput.next();
        try {
            blackBag1 = readWeights(bag1File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter the location of bag number 2 to load: ");
        String bag2File = userInput.next();
        try {
            blackBag2 = readWeights(bag2File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int[] whiteBag0 = new int[blackBag0.length];
        int[] whiteBag1 = new int[blackBag1.length];
        int[] whiteBag2 = new int[blackBag2.length];

    }

    public static int[] readWeights(String fileName, int players) throws IOException {
        // Reading in length of user entered file
        int[] bag;
        int fileLength = 0;
        File file = new File(fileName);
        try {
            Scanner fileLengthScanner = new Scanner(file);
            fileLengthScanner.useDelimiter(",");
            while(fileLengthScanner.hasNext()) {
                fileLengthScanner.next();
                fileLength++;
            }
            fileLengthScanner.close();
            //change these to throws instead of printstacktrace
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        // Reading in range of values from user file
        if ((players * 11) > fileLength){
            throw new IOException("File has to have over 11 values");
        }
        bag = new int[fileLength];
        try {
            Scanner fileScanner = new Scanner(file);
            String[] stringWeights = fileScanner.nextLine().split(",");
            for (int i = 0; i < stringWeights.length; i++) {
                if (Integer.parseInt(stringWeights[i]) < 1) {
                    throw new IOException("File values must be strictly positive");
                }
                bag[i] = Integer.parseInt(stringWeights[i]);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return bag;
    }

    class Player implements Runnable {
        Random random = new Random();
        int[] hand = new int[10];
        @Override
        public void run() {

        }
        public void drawPebble(int[] prevWhiteBag, int[] prevBlackBag) {
            if (hand.length == 10) {
                int chosenPebbleIndex = random.nextInt(10);
                int chosenPebbleValue = hand[chosenPebbleIndex];
                hand[chosenPebbleIndex] = Integer.parseInt(null);
                for (int i = 0; i < prevWhiteBag.length; i++) {
                    if (prevWhiteBag[i] == Integer.parseInt(null)) {
                        prevWhiteBag[i] = chosenPebbleValue;
                        break;
                    }
                }
            }
            else {
                int randomPebbleIndex = random.nextInt(prevBlackBag.length);
                int randomPebble = prevBlackBag[randomPebbleIndex];
                prevBlackBag[randomPebbleIndex] = Integer.parseInt(null);
                if (!(randomPebble == Integer.parseInt(null))) {
                    for (int i = 0; i < hand.length; i++) {
                        if (hand[i] == Integer.parseInt(null)) {
                            hand[i] = randomPebble;
                        }
                    }
                }
            }
        }
    }
}
