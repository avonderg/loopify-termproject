package edu.brown.cs.student.main.routefindermaps;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class used for the generating a distance matrix of distances between each of the 25 start and end nodes
 * created for the use of our route finder algorithm.
 */
public class DistanceMatrixGenerator {
  private GeoApiContext context = new GeoApiContext.Builder()
          .apiKey("AIzaSyAbGfdrfwUDK_1YXGP8b7NQZbNh3AKRH7o")
          .build();
  // origins and destinations
  private String[] origins;
  private String[] destinations;

  /**
   * Constructor for the DistanceMatrixGenerator class
   * @param origins - String array of origin addresses
   * @param destinations - String array of destination addresses
   */
  public DistanceMatrixGenerator(String[] origins, String[] destinations) {
    this.origins = origins;
    this.destinations = destinations;
  }

  /**
   * Since the distance matrix can only take in 10 origins and destinations, this method
   * calls the API multiple times in order to generate the desired 625 distances.
   * @return - list of lists containing distances.
   * @throws IOException
   * @throws InterruptedException
   * @throws ApiException
   */
  public List<List<Double>> generateDistances25() throws IOException, InterruptedException, ApiException {
    assert origins.length == 25;
    assert destinations.length == 25;
    Double[][] distMat = new Double[25][25];
    List<String> locations = Arrays.asList(origins);
    String[] locations1 = locations.subList(0, 10).toArray(String[]::new);
    String[] locations2 = locations.subList(10, 20).toArray(String[]::new);
    String[] locations3 = locations.subList(20, 25).toArray(String[]::new);
    List<String[]> locsList = new ArrayList<>();
    locsList.add(locations1);
    locsList.add(locations2);
    locsList.add(locations3);
    List<Integer> lengths = new ArrayList<>();
    lengths.add(10);
    lengths.add(10);
    lengths.add(5);
    for (int i = 0; i < 3; i ++){
      for (int j = 0; j < 3; j ++){
        List<List<Double>> subDistMat = generateDistances(locsList.get(i), locsList.get(j));
        for (int i2 = 0; i2 < lengths.get(i); i2++){
          for (int j2 = 0; j2 < lengths.get(j); j2++){
            distMat[10*i + i2][10*j + j2] = subDistMat.get(i2).get(j2);
          }
        }
      }
    }
    // converts array to list of lists
    return Arrays.stream(distMat)
            .map(Arrays::asList)
            .collect(Collectors.toList());
  }
  /**
   * Makes a call to the DistanceMatrixApi, given 25 origin and destination addresses, and retrieves a response
   * consisting of a distance matrix. This response is then parsed, and the distances are extracted and stored
   * in a list of lists.
   * @return - list of lists of doubles, of the distances between nodes
   * @throws IOException
   * @throws InterruptedException
   * @throws ApiException
   */
  public List<List<Double>> generateDistances(String[] origins, String[] destinations) throws IOException, InterruptedException, ApiException {
    List<List<Double>> nodeDistances;

    DistanceMatrixApiRequest distanceMatReq = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations);
    // get response
    DistanceMatrix matrix = distanceMatReq.await();
    DistanceMatrixRow[] rows = matrix.rows; // gets rows from distance matrix
    Double[][] distMat = new Double[10][10]; // 10 by 10 matrix -> max # we can request from the API

    for (int i = 0; i < rows.length; i++) { // loops through the rows
      DistanceMatrixElement[] elements = rows[i].elements; // gets elements array
      for (int j = 0; j < elements.length; j++) { // loops through the elements
        if (elements[j].status.name() == "OK") { // Error checking to prevent program from crashing
          distMat[i][j] = Double.valueOf(elements[j].distance.inMeters) / 1609.344; // gets distance in miles

          ; // gets distance in meters
        }
        else { // if an element was unable to be grabbed -- given distance of -1
          distMat[i][j] = -1.0;
        }
      }
    }

    // converts array to list of lists
    nodeDistances = Arrays.stream(distMat)
            .map(Arrays::asList)
            .collect(Collectors.toList());

    return nodeDistances;
  }

}
