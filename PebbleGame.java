import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.Random;
public class PebbleGame {
    private static int[] blackBagX = new int[0];
    private static int[] blackBagY = new int[0];
    private static int[] blackBagZ = new int[0];
    private static int[] whiteBagA = new int[0];
    private static int[] whiteBagB = new int[0];
    private static int[] whiteBagC = new int[0];

    public static void main(String[] args) {
        /* main function */
        Scanner userInput = new Scanner(System.in);
        System.out.println("Please enter the number of players: ");
        int playerCount = Integer.parseInt(userInput.next());

        System.out.println("Please enter the location of bag number 0 to load: ");
        String bag0File = userInput.next();
        try {
            blackBagX = readWeights(bag0File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter the location of bag number 1 to load: ");
        String bag1File = userInput.next();
        try {
            blackBagY = readWeights(bag1File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Please enter the location of bag number 2 to load: ");
        String bag2File = userInput.next();
        try {
            blackBagZ = readWeights(bag2File,playerCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        whiteBagA = new int[blackBagX.length];
        whiteBagB = new int[blackBagY.length];
        whiteBagC = new int[blackBagZ.length];

        for (int i = 0; i < playerCount; i++) {
            Thread newThread = new Thread(new Player());
            newThread.start();
        }
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

    static boolean checkBag(int[] blackBag) {
        boolean empty = true;
        for (int i : blackBag) {
            if (i != 0) {
                empty = false;
                break;
            }
        }
        return empty;
    }

    static void fillBag(int[] blackBag, int[] whiteBag) {
        for (int i = 0; i < whiteBag.length; i++) {
            blackBag[i] = whiteBag[i];
            whiteBag[i] = 0;
        }
    }

    static class Player implements Runnable {
        Random random = new Random();
        int[] hand = new int[10];
        private int bagNumber = 0;

        public Player() {

        }

        @Override
        public void run() {
            while(getHandValue() != 100) {
                //discard pebble from white bag matching previous black bag
                if (hand.length == 10) {
                    switch (bagNumber) {
                        case (0) -> discardPebble(whiteBagA);
                        case (1) -> discardPebble(whiteBagB);
                        case (2) -> discardPebble(whiteBagC);
                    }
                }
                //draw pebble from random black bag
                else {
                    bagNumber = random.nextInt(3);
                    switch (bagNumber) {
                        case (0) -> drawPebble(blackBagX);
                        case (1) -> drawPebble(blackBagY);
                        case (2) -> drawPebble(blackBagZ);
                    }
                    switch (bagNumber) {
                        case (0):
                            if (checkBag(blackBagX)) {
                                fillBag(blackBagX, whiteBagA);
                            }
                            break;
                        case (1):
                            if (checkBag(blackBagY)) {
                                fillBag(blackBagY, whiteBagB);
                            }
                            break;
                        case (2):
                            if (checkBag(blackBagZ)) {
                                fillBag(blackBagZ, whiteBagC);
                            }
                            break;
                    }
                }
            }
        }

        public int getHandValue() {
            int total = 0;
            for (int i : hand) {
                total += i;
            }
            return total;
        }

        public void drawPebble(int[] blackBag) {
            int randomPebbleIndex = random.nextInt(blackBag.length);
            int randomPebble = blackBag[randomPebbleIndex];
            blackBag[randomPebbleIndex] = 0;
            if (!(randomPebble == 0)) {
                for (int i = 0; i < hand.length; i++) {
                    if (hand[i] == 0) {
                        hand[i] = randomPebble;
                        break;
                    }
                }
            }
        }

        public void discardPebble(int[] whiteBag) {
            int chosenPebbleIndex = random.nextInt(10);
            int chosenPebbleValue = hand[chosenPebbleIndex];
            hand[chosenPebbleIndex] = 0;
            for (int i = 0; i < whiteBag.length; i++) {
                if (whiteBag[i] == 0) {
                    whiteBag[i] = chosenPebbleValue;
                    break;
                }
            }
        }
    }
}
