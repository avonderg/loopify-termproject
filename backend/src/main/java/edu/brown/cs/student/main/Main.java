package edu.brown.cs.student.main;

import edu.brown.cs.student.main.backendHandlers.DeleteHandler;
import edu.brown.cs.student.main.backendHandlers.InsertHandler;
import edu.brown.cs.student.main.backendHandlers.TableHandler;
import edu.brown.cs.student.main.backendHandlers.TableNameHandler;
import edu.brown.cs.student.main.backendHandlers.UpdateHandler;

import joptsimple.OptionParser;
import joptsimple.OptionSet;
import spark.Spark;

import java.io.IOException;

/**
 * The Main class of our project. This is where execution begins.
 */

public final class Main {

  private static final int DEFAULT_PORT = 4567;

  /**
   * The initial method called when execution begins.
   *
   * @param args An array of command line arguments
   */
  public static void main(String[] args) {
    new Main(args).run();
  }

  private String[] args;

  private Main(String[] args) {
    this.args = args;
  }

  private void run() {

    OptionParser parser = new OptionParser();
    parser.accepts("gui");
    parser.accepts("port").withRequiredArg().ofType(Integer.class).defaultsTo(DEFAULT_PORT);

    OptionSet options = parser.parse(args);

    if (options.has("gui")) {
      runSparkServer((int) options.valueOf("port"));
    }

    Repl myRepl = new Repl();
    try {
      myRepl.setUpRepl();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * This method runs the spark server to listen for requests to the endpoints specified.
   *
   * @param port to run server on
   */
  private void runSparkServer(int port) {
    Spark.port(port);
    Spark.externalStaticFileLocation("src/main/resources/static");

    Spark.options("/*", (request, response) -> {
      String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
      if (accessControlRequestHeaders != null) {
        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
      }

      String accessControlRequestMethod = request.headers("Access-Control-Request-Method");

      if (accessControlRequestMethod != null) {
        response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
      }

      return "OK";
    });

    Spark.before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

    // Put Routes Here
    // Spark.get("/table", new TableHandler());
    Spark.post("/table", new TableHandler());
    Spark.get("/tableNames", new TableNameHandler());


    Spark.post("/insert", new InsertHandler());
    Spark.post("/update", new UpdateHandler());
    Spark.post("/delete", new DeleteHandler());
    Spark.init();
  }
}
