package edu.brown.cs.student.main;
// look into using these imports for your REPL!

//import edu.brown.cs.student.main.recommender.RecommenderSystem;
import edu.brown.cs.student.main.replcommands.ICommand;
import edu.brown.cs.student.main.replcommands.LoadDBBackend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class that represents the REPL and process user input.
 */
public class Repl {

  private final HashMap<String, ICommand> commandList;

  /**
   * Constructor calls method that sets up REPL and initializes starData variable.
   */
  public Repl() {
    this.commandList = new HashMap<>();
    this.initializeCommandList();
  }

  /**
   * Method that adds commands to the commandList. The REPL
   * will support functionality of all commands that are added to
   * the hashmap in this method.
   */
  public void initializeCommandList() {
    this.commandList.put("load_db_backend", new LoadDBBackend());
  }

  /**
   * Method that runs infinite while loop that sets up REPL and controls user input.
   *
   * @throws IOException Throws if there is invalid input.
   */
  public void setUpRepl() throws IOException {
//    RecommenderSystem recommender = new RecommenderSystem();
    BufferedReader reader =
        new BufferedReader(new InputStreamReader(System.in)); // Initialize new BufferedReader
    String input;
    // While loop reads from commandLine
    while ((input = reader.readLine()) != null) {
      try {
        List<String> buffer =
            new ArrayList<>(); // use ArrayList to represent Buffer that stores user input
        input = input.trim(); // Trim off whitespace from system input
        input = input + ' '; // Null terminate system input

        buffer =
            this.parseCommandLine(input, buffer); // Parse command line input with helper method

        if (buffer.size() == 0) {
          continue;
        }

        // If a valid command is input then execute the functionality
        if (this.commandList.containsKey(buffer.get(0))) {
          this.commandList.get(buffer.get(0)).handleCommand(buffer);
        } else {
          System.err.println("ERROR: " + buffer.get(0) + " is not a valid command");
        }

      } catch (NumberFormatException e) {
        e.printStackTrace();
      }
    }
  }


  /**
   * Uses Regex found from edPost to parse user input and return
   * an ArrayList that contains REPL commands.
   *
   * @param input  read in from command line.
   * @param buffer buffer ArrayList that stores parsed input.
   * @return ArrayList that contains parsed user input.
   */
  public List<String> parseCommandLine(String input, List<String> buffer) {
    Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
    Matcher regexMatcher = regex.matcher(input);
    while (regexMatcher.find()) {
      if (regexMatcher.group(1) != null) {
        // Add double-quoted string without the quotes
        buffer.add(regexMatcher.group(1));
      } else {
        // Add unquoted word
        buffer.add(regexMatcher.group());
      }
    }
    return buffer;
  }

}
