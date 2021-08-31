package se.kry.codetest.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import se.kry.codetest.DBConnector;
import se.kry.codetest.model.Service;
import java.util.ArrayList;
import java.util.List;

public class DatabaseService {

    private DBConnector connector;

    public DatabaseService(DBConnector connector) {
        this.connector = connector;
    }

    /**
     * Insert new service in database.
     *
     * @param service
     */
    public void addNewServiceToDatabase(Service service) {
        String addQuery = "INSERT INTO service VALUES ('" + service.getName() + "', '" + service.getStatus() + "')";
        connector.query(addQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[addNewServiceToDatabase] new item inserted in service table");
            } else {
                System.out.println("ERROR Occurred when trying to add new item in service table");
                System.out.println(done);
            }
        });
    }

    /**
     * Get all services from database.
     * @return
     */
    public Future<List> getAllServices() {
        List<Service> services = new ArrayList<>();
        Future<List> ret = Future.future();

        connector.query("Select * From service").setHandler(done -> {
            if (done.succeeded()) {

                System.out.println("[getAllServices] OK");
                ResultSet resultSet = done.result();
                List<JsonArray> results = resultSet.getResults();
                for (JsonArray row : results) {
                    String name = row.getString(0);
                    String status = row.getString(1);
                    services.add(new Service(name, status));
                }

                ret.complete(services);
            } else {
                System.out.println("[getAllServices] ERROR Occurred when trying to get all services");
                ret.fail(done.cause());
            }
        });

        return ret;
    }
}
