package se.kry.codetest.model;

/**
 * Service Model.
 */
public class Service {

    private String name;
    private String status;

    /**
     * Constructor.
     * @param name
     * @param status
     */
    public Service(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
