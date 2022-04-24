package edu.brown.cs.student.main.replcommands;


import java.util.List;

/**
 * Interface that represents a command.
 */
public interface ICommand {

  void handleCommand(List<String> buffer);
}
