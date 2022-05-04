package NodeTests;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import edu.brown.cs.student.main.routefindermaps.NodeGrid;
import org.junit.Test;

import java.io.IOException;

public class NodeGridTest {

  @Test
  public void testCalculateDistances() throws IOException, InterruptedException, ApiException {
    GeoApiContext context = new GeoApiContext.Builder()
            .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
            .build();
    NodeGrid grid = new NodeGrid(0.0,0.0,0,0);
    grid.calculateDistances();
  }
}
