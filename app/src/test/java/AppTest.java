import hexlet.code.App;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AppTest {

    /**
     * In this test class, we are going to test the main() method of the App class.
     * It should compare two configuration files and generate the difference.
     */

//    @Test
//    public void testMain() throws Exception {
//        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
//        final PrintStream originalOut = System.out;
//
//        try {
//            System.setOut(new PrintStream(outContent)); // To capture the output from main method
//            Path path1 = Paths.get("src/test/resources/file1.json").toAbsolutePath();
//            Path path2 = Paths.get("src/test/resources/file2.json").toAbsolutePath();
//
//            String expectedOutput = """
//                    {
//                     - follow:false
//                       host:hexlet.io
//                     - proxy:123.234.53.22
//                     - timeout:50
//                     + timeout:20
//                     + verbose:true
//                    }""";
//
//            App.main(new String[]{path1.toString(), path2.toString()});
//            assertEquals(expectedOutput, outContent.toString().trim());
//        } finally {
//            System.setOut(originalOut); // Resetting the System.out to avoid affecting other tests
//        }
//    }

    @Test
    public void testMainWithInsufficientArguments() throws Exception {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        final PrintStream originalOut = System.out;

        try {
            System.setOut(new PrintStream(outContent));
            App.main(new String[]{}); // No arguments provided

            assertEquals("Please provide two file paths as arguments", outContent.toString().trim());
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    public void testCallMethod() {
        App app = new App();
        assertNull(app.call());
    }
}
