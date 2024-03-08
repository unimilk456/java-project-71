package hexlet.code;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @Option(names = {"-f", "--format"}, paramLabel = "format", defaultValue = "stylish",
            description = "output format [default: ${DEFAULT-VALUE}]")
    String format;

    @Parameters(index = "0", paramLabel = "filepath1", description = "path to first file")
    private String filePath1;

    @Parameters(index = "1", paramLabel = "filepath2", description = "path to second file")
    private String filePath2;

    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.out.println("Please provide two file paths as arguments");
            return;
        }
        Path path1 = Paths.get(args[0]);
        Path path2 = Paths.get(args[1]);

        String result = Differ.generate(path1, path2);

        System.out.println(result);
    }

    @Override
    public Integer call() {
        return null;
    }

}
