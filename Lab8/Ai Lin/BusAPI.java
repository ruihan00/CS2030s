import java.io.IOException;
import java.lang.InterruptedException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.concurrent.CompletionException;
import java.util.concurrent.CompletableFuture;

/**
 * The BusAPI class interface with the Web API running at 
 * cs2030-bus-api.herokuapp.com to retrieve:
 * - bus services that serve a given bus stop 
 * - bus stop visited by a bus service.
 *
 * @author: Lim Ai Lin (Group 16F)
 * @version: CS2030 AY21/22 Semester 2, Lab 8
 **/
class BusAPI {
  /** 
   * URL to query for bus stops. 
   * An alternative is "https://www.comp.nus.edu.sg/~ooiwt/bus_services/"
   */
  private static final String BUS_SERVICE_API = 
      "https://cs2030-bus-api.herokuapp.com/bus_services/";


  /** 
   * URL to query for bus services. 
   * An alternative is "https://www.comp.nus.edu.sg/~ooiwt/bus_stops/"
   **/
  private static final String BUS_STOP_API = 
      "https://cs2030-bus-api.herokuapp.com/bus_stops/";

  /**
   * Given a URL, synchrnously obtained the HTTP response string
   * from the URL.  It returns an emptry string and prints an
   * error message if the URL is invalid (not the best behavior).
   * @param url The URL to query
   * @return The HTTP resposne body, or an empty string if the 
   *     query fails.
   */
  private static CompletableFuture<String> httpGet(String url) {
    HttpClient client = HttpClient.newBuilder()
        .build();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(url))
        .build();
    /*
    CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString()); // TODO

    return response.thenApply(x -> {
      System.out.println(x.body());
      return x.body();});
      */
    //System.out.println(request);
    return client.sendAsync(request, BodyHandlers.ofString()).thenApply(
        x -> {
          //System.out.println(x.body());
          return x.body();});
  }

  /**
   * Return the string from CS2030 BUS API given a bus service ID.
   * @param serviceId Bus service id
   * @return The string returned by the CS2030 BUS API service listing
   *     the bus stops that are serviced at this bus.  Return an empty
   *     string if something go wrong.
   */ 
  public static CompletableFuture<String> getBusStopsServedBy(String serviceId) {
    //System.out.print(1);
    return httpGet(BUS_SERVICE_API + serviceId);
  }

  /**
   * Return the string from CS2030 BUS API given a bus stop ID.
   * @param stopId Bus stop id
   * @return The string returned by the CS2030 BUS API service listing
   *     the bus services that stopped at this bus stop; an empty 
   *     string if the API query failed.
   */ 
  public static CompletableFuture<String> getBusServicesAt(String stopId) {
    //System.out.println(stopId);
    return httpGet(BUS_STOP_API + stopId);
  }
}
