package auto.mapping.exercise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;


@Component
public class CommandRunner implements CommandLineRunner {

    private final BufferedReader bufferedReader;

    public CommandRunner() {
        this.bufferedReader =  new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void run(String... args) throws Exception {

        while (true) {
            System.out.println("Enter your commands:");
            String[] commands = bufferedReader.readLine().split("\\|");

            switch (commands[0]){
            }
        }
    }
}
