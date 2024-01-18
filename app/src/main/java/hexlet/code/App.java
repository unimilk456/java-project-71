package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format"}, paramLabel = "format", defaultValue = "stylish", description = "output format [default: ${DEFAULT-VALUE}]")
    String format = "stylish";

    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filePath2;
    public static void main(String[] args) {
        System.out.println("Hello World!");
        int exitCode = new CommandLine(new App()).execute(args);
//        System.exit(exitCode);
    }

    @Override
    public Integer call(){
        return null;
    }
    // Custom parameter renderer to exclude angle brackets
}
