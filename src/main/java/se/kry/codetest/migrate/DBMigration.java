package se.kry.codetest.migrate;

import io.vertx.core.Vertx;
import se.kry.codetest.DBConnector;

public class DBMigration {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    DBConnector connector = new DBConnector(vertx);

    String creationQuery = "create table service\n" +
            "(\n" +
            "    name varchar(40) NOT NULL,\n" +
            "    url varchar(128) NOT NULL,\n" +
            "    status varchar(20),\n" +
            "    createdAt DATE\n" +
            ")";
    connector.query(creationQuery).setHandler(done -> {
      if(done.succeeded()){
        System.out.println("completed db migrations");
      } else {
        done.cause().printStackTrace();
      }
      vertx.close(shutdown -> {
        System.exit(0);
      });
    });
  }
}
