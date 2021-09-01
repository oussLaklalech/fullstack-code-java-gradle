package se.kry.codetest.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import se.kry.codetest.DBConnector;
import se.kry.codetest.model.Service;
import se.kry.codetest.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
        // String addQuery = "INSERT INTO service VALUES ('" + service.getName() + "', '" + service.getStatus() + "')";

        String insertQuery = "INSERT INTO service VALUES('" + service.getName() + "', '" + service.getUrl() + "', '" + service.getStatus() + "', datetime('now'));";

        connector.query(insertQuery).setHandler(done -> {
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
     *
     * @return
     */
    public Future<List> getAllServices() {
        List<Service> services = new ArrayList<>();
        Future<List> ret = Future.future();

        connector.query("Select rowid,* From service").setHandler(done -> {
            if (done.succeeded()) {

                System.out.println("[getAllServices] OK");
                ResultSet resultSet = done.result();
                List<JsonArray> results = resultSet.getResults();
                for (JsonArray row : results) {
                    Integer rowid = row.getInteger(0);
                    String name = row.getString(1);
                    String url = row.getString(2);
                    String status = row.getString(3);
                    String createdAt = row.getString(4);

                    services.add(new Service(rowid, name, url, status, DateUtil.formatStringToDate(createdAt)));
                }

                ret.complete(services);
            } else {
                System.out.println("[getAllServices] ERROR Occurred when trying to get all services");
                ret.fail(done.cause());
            }
        });

        return ret;
    }

    /**
     * Delete a service from database using its ROWID.
     * @param rowId
     */
    public void deleteServiceToDatabase(Integer rowId) {
        String deleteQuery = "DELETE FROM service where ROWID =" + rowId;
        connector.query(deleteQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[addNewServiceToDatabase] new item inserted in service table");
            } else {
                System.out.println("ERROR Occurred when trying to add new item in service table");
                System.out.println(done);
            }
        });
    }
}
