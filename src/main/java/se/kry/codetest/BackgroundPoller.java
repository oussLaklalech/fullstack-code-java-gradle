package se.kry.codetest;

import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import se.kry.codetest.model.RequestResponse;
import se.kry.codetest.model.Service;
import se.kry.codetest.model.ServiceResponse;
import se.kry.codetest.service.DatabaseService;
import se.kry.codetest.util.Constants;

import java.util.List;

/**
 * Background poller.
 */
public class BackgroundPoller {

    private WebClient client;
    private DatabaseService databaseService;

    /**
     * Constructor.
     * @param databaseService
     */
    public BackgroundPoller(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    /**
     * Call each service and save the result in database.
     *
     * @param services
     * @param vertx
     */
    public void pollServices(List<Service> services, Vertx vertx) {
        System.out.println("BackgroundPoller Called");
        System.out.println("SIZE SERVICES : " + services.size());
        // Vertx vertx = Vertx.vertx();
        client = WebClient.create(vertx);

        services.forEach(service -> {
            client.get(Constants.HTTP_PORT, "localhost", service.getUrl())
                    .send(ar -> {
                        if (ar.succeeded()) {
                            HttpResponse<Buffer> response = ar.result();
                            System.out.println(response);
                            if (response.statusCode() == 200 && response.getHeader("content-type").equals("application/json")) {
                                System.out.println("RESPONSE OK");
                                // insert new line in request_response with result OK
                                databaseService.addNewRequestResponseToDatabase(new RequestResponse(ServiceResponse.OK, service.getUrl()));

                            } else {
                                System.out.println("Something went wrong " + response.statusCode());
                                // insert new line in request_response with result KO
                                databaseService.addNewRequestResponseToDatabase(new RequestResponse(ServiceResponse.FAIL, service.getUrl()));
                            }
                        } else {
                            System.out.println("Something went wrong " + ar.cause().getMessage());
                            // insert new line in request_response with result KO
                            databaseService.addNewRequestResponseToDatabase(new RequestResponse(ServiceResponse.FAIL, service.getUrl()));
                        }
                    });
        });

    }
}
