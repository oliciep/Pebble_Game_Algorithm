import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class PebbleGameTest {

    @Test
    public void testReadWeightsLength() throws IOException {
        assert(PebbleGame.readWeights("example_file_1.csv",2).length == 100);
    }

    @Test
    public void testReadWeights() throws IOException {
        int[] testData = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62,63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,90,91,92,93,94,95,96,97,98,99,100};
        assert(Arrays.equals(PebbleGame.readWeights("example_file_1.csv",2), testData));
    }

    @Test
    public void testReadWeightsMinLength() throws IOException {
        try {
            PebbleGame.readWeights("example_file_1.csv",10);
            fail("Test passed");
        }
        catch(IOException e) {
            assertEquals(e.getMessage(),"File has to have over 11 values");
        }
    }

    @Test
    public void testReadWeightsPositive() throws IOException {
        try {
            PebbleGame.readWeights("example_file_2.csv",4);
            fail("Test passed");
        }
        catch(IOException e) {
            assertEquals(e.getMessage(),"File values must be strictly positive");
        }
    }
}
