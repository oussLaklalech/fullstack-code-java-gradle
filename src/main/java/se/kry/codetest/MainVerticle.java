package se.kry.codetest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import se.kry.codetest.model.RequestResponse;
import se.kry.codetest.model.Service;
import se.kry.codetest.service.DatabaseService;
import se.kry.codetest.util.Constants;
import se.kry.codetest.util.DateUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main Verticle.
 */
public class MainVerticle extends AbstractVerticle {

    private List<Service> services = new ArrayList<>();
    private DBConnector connector;
    DatabaseService databaseService;
    private BackgroundPoller poller;

    /**
     * Start.
     * @param startFuture
     */
    @Override
    public void start(Future<Void> startFuture) {
        connector = new DBConnector(vertx);
        databaseService = new DatabaseService(connector);
        poller = new BackgroundPoller(databaseService);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        databaseService.getAllServices().setHandler(res -> {
            vertx.setPeriodic(1000 * 60, timerId -> poller.pollServices(res.result(), vertx));
        });

        setRoutes(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(Constants.HTTP_PORT, result -> {
                    if (result.succeeded()) {
                        System.out.println("KRY code test service started");
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });
    }

    /**
     * Set routes.
     * Contains the services: GET POST DELETE and PUT.
     *
     * @param router
     */
    private void setRoutes(Router router) {
        router.route("/*").handler(StaticHandler.create());

        /*
         * [GET] get all services
         */
        router.get("/service").handler(req -> {
            databaseService.getAllServices().setHandler(res -> {
                List<Service> listServices = res.result();
                System.out.println("[GET] /service -> getAllServices");


                List<JsonObject> jsonServices = listServices
                        .stream()
                        .map(service ->
                                new JsonObject()
                                        .put("rowid", service.getRowid())
                                        .put("name", service.getName())
                                        .put("url", service.getUrl())
                                        .put("createdAt", DateUtil.formatDateToString(service.getCreateAt())))

                        .collect(Collectors.toList());
                req.response()
                        .putHeader("content-type", "application/json")
                        .end(new JsonArray(jsonServices).encode());
            });
        });

        /*
         * [GET] get all RequestResponse
         */
        router.get("/request-response").handler(req -> {
            databaseService.getAllRequestResponse().setHandler(res -> {
                List<RequestResponse> listRequestResponse = res.result();
                System.out.println("[GET] /service -> getAllServices");


                List<JsonObject> jsonRequestResponses = listRequestResponse
                        .stream()
                        .map(service ->
                                new JsonObject()
                                        .put("rowid", service.getRowid())
                                        .put("status", service.getStatus())
                                        .put("url", service.getUrl())
                                        .put("createdAt", DateUtil.formatDateToString(service.getCreateAt())))

                        .collect(Collectors.toList());
                req.response()
                        .putHeader("content-type", "application/json")
                        .end(new JsonArray(jsonRequestResponses).encode());
            });
        });


        /*
         * [POST] add new service
         */
        router.post("/service").handler(req -> {
            JsonObject jsonBody = req.getBodyAsJson();
            databaseService.addNewServiceToDatabase(new Service(jsonBody.getString("name"), jsonBody.getString("url")));
            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("OK");
        });

        /*
         * [DELETE] delete a service
         */
        router.delete("/service").handler(req -> {
            JsonObject jsonBody = req.getBodyAsJson();
            databaseService.deleteServiceToDatabase(jsonBody.getInteger("rowid"));

            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("OK");
        });

        /*
         * [PUT] update a service
         */
        router.put("/service").handler(req -> {
            JsonObject jsonBody = req.getBodyAsJson();
            databaseService.updateServiceToDatabase(
                    new Service(jsonBody.getInteger("rowid"),
                            jsonBody.getString("name"),
                            jsonBody.getString("url"),
                            null));

            req.response()
                    .putHeader("content-type", "text/plain")
                    .end("OK");
        });
    }
}



