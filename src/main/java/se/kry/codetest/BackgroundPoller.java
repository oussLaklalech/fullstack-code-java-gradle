package se.kry.codetest;

import io.vertx.core.Future;
import se.kry.codetest.model.Service;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class BackgroundPoller {

  public Future<List<String>> pollServices(List<Service> services) {
    System.out.println("Poller log !!");
    //TODO
    return Future.failedFuture("TODO");
  }
}
