package se.kry.codetest;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;
import se.kry.codetest.model.Service;
import se.kry.codetest.service.DatabaseService;

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
    private BackgroundPoller poller = new BackgroundPoller();

    @Override
    public void start(Future<Void> startFuture) {
        connector = new DBConnector(vertx);
        databaseService = new DatabaseService(connector);

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        vertx.setPeriodic(1000 * 60, timerId -> poller.pollServices(services));
        setRoutes(router);

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8000, result -> {
                    if (result.succeeded()) {
                        System.out.println("KRY code test service started");
                        startFuture.complete();
                    } else {
                        startFuture.fail(result.cause());
                    }
                });
    }

    private void setRoutes(Router router) {
        router.route("/*").handler(StaticHandler.create());

        /*
         * [GET] get all services
         */
        router.get("/service").handler(req -> {
            databaseService.getAllServices().setHandler(res -> {
                List<Service> listServices = res.result();
                System.out.println("getAllServices");
                List<JsonObject> jsonServices = listServices
                        .stream()
                        .map(service ->
                                new JsonObject()
                                        .put("rowid", service.getRowid())
                                        .put("name", service.getName())
                                        .put("url", service.getUrl())
                                        .put("createdAt", service.getCreateAt())
                                        .put("status", service.getStatus()))

                        .collect(Collectors.toList());
                req.response()
                        .putHeader("content-type", "application/json")
                        .end(new JsonArray(jsonServices).encode());
            });
        });

        /*
         * [POST] add new service
         */
        router.post("/service").handler(req -> {
            JsonObject jsonBody = req.getBodyAsJson();
            // services.add(new Service(jsonBody.getString("url"), "UNKNOWN"));
            databaseService.addNewServiceToDatabase(new Service(jsonBody.getString("url"), "UNKNOWN"));
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
    }

}



