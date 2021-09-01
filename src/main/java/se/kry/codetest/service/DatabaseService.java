package se.kry.codetest.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.sql.ResultSet;
import se.kry.codetest.DBConnector;
import se.kry.codetest.model.RequestResponse;
import se.kry.codetest.model.Service;
import se.kry.codetest.model.ServiceResponse;
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
     * Insert new {@link Service} in database.
     *
     * @param service
     */
    public void addNewServiceToDatabase(Service service) {
        String insertQuery = "INSERT INTO service VALUES('" + service.getName() + "', '" + service.getUrl() + "', datetime('now'));";

        connector.query(insertQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[addNewServiceToDatabase] new item inserted in service table");
            } else {
                System.out.println("ERROR Occurred when trying to add new item in service table");
            }
        });
    }

    /**
     * Get all {@link Service} from database.
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
                    String createdAt = row.getString(3);

                    services.add(new Service(rowid, name, url, DateUtil.formatStringToDate(createdAt)));
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
     * Delete a {@link Service} from database using its ROWID.
     * @param rowId
     */
    public void deleteServiceToDatabase(Integer rowId) {
        String deleteQuery = "DELETE FROM service where ROWID =" + rowId;
        connector.query(deleteQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[addNewServiceToDatabase] new item inserted in service table");
            } else {
                System.out.println("ERROR Occurred when trying to add new item in service table");
            }
        });
    }

    /**
     * Update a {@link Service} in database using its ROWID.
     * @param service
     */
    public void updateServiceToDatabase(Service service) {
        String updateQuery = "UPDATE service\n" +
                "SET name = '" + service.getName() + "', url = '" + service.getUrl() + "'\n" +
                "WHERE ROWID = " + service.getRowid();

        connector.query(updateQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[updateServiceToDatabase] new item updated in service table");
            } else {
                System.out.println("ERROR Occurred when trying to update an item in service table : " + service.getRowid());
            }
        });
    }

    /**
     * Get all {@link RequestResponse} from database.
     * @return
     */
    public Future<List> getAllRequestResponse() {
        List<RequestResponse> services = new ArrayList<>();
        Future<List> ret = Future.future();

        connector.query("Select rowid,* From request_response").setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[getAllRequestResponse] OK");
                ResultSet resultSet = done.result();
                List<JsonArray> results = resultSet.getResults();
                for (JsonArray row : results) {
                    Integer rowid = row.getInteger(0);
                    String url = row.getString(1);
                    String status = row.getString(2);
                    String createdAt = row.getString(3);

                    services.add(new RequestResponse(rowid, ServiceResponse.valueOf(status), url, DateUtil.formatStringToDate(createdAt)));
                }

                ret.complete(services);
            } else {
                System.out.println("[getAllRequestResponse] ERROR Occurred when trying to get all RequestResponse");
                ret.fail(done.cause());
            }
        });

        return ret;
    }

    /**
     * Insert new {@link RequestResponse} in database.
     *
     * @param requestResponse
     */
    public void addNewRequestResponseToDatabase(RequestResponse requestResponse) {
        String insertQuery = "INSERT INTO request_response VALUES('" + requestResponse.getUrl() + "', '" + requestResponse.getStatus() + "', datetime('now'));";

        connector.query(insertQuery).setHandler(done -> {
            if (done.succeeded()) {
                System.out.println("[addNewRequestResponseToDatabase] new item inserted in service requestResponse");
            } else {
                System.out.println("ERROR Occurred when trying to add new item in requestResponse table");
                System.out.println(done);
            }
        });
    }
}
