package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        int exitCode = new CommandLine(new App()).execute(args);
//        System.exit(exitCode);
    }

    @Override
    public Integer call(){
        return null;
    }
}
