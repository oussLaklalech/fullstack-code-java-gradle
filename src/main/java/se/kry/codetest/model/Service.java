package se.kry.codetest.model;

import java.util.Date;

/**
 * Service Model.
 */
public class Service {

    private Integer rowid;
    private String name;
    private String url;
    private String status;
    private Date createAt;

    /**
     * Constructor.
     *
     * @param rowid
     * @param name
     * @param url
     * @param status
     * @param createAt
     */
    public Service(Integer rowid, String name, String url, String status, Date createAt) {
        this.rowid = rowid;
        this.name = name;
        this.url = url;
        this.status = status;
        this.createAt = createAt;
    }

    /**
     * Constructor.
     * @param name
     * @param status
     * @param url
     */
    public Service(String name, String status, String url) {
        this.name = name;
        this.status = status;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }
}
