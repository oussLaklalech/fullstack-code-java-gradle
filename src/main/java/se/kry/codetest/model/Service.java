package se.kry.codetest.model;

import java.util.Date;

/**
 * Service Model.
 * Corresponds to the database table : service.
 */
public class Service {

    private Integer rowid;
    private String name;
    private String url;
    private Date createAt;

    /**
     * Constructor.
     *
     * @param rowid
     * @param name
     * @param url
     * @param createAt
     */
    public Service(Integer rowid, String name, String url, Date createAt) {
        this.rowid = rowid;
        this.name = name;
        this.url = url;
        this.createAt = createAt;
    }

    /**
     * Constructor.
     * @param name
     * @param url
     */
    public Service(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
