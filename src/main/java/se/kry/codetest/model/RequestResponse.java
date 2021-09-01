package se.kry.codetest.model;

import java.util.Date;

/**
 * RequestResponse Model.
 * Corresponds to the database table : request_response.
 */
public class RequestResponse {
    private Integer rowid;
    private ServiceResponse status;
    private String url;
    private Date createAt;

    public RequestResponse(Integer rowid, ServiceResponse status, String url, Date createAt) {
        this.rowid = rowid;
        this.status = status;
        this.url = url;
        this.createAt = createAt;
    }

    public RequestResponse(ServiceResponse status, String url) {
        this.status = status;
        this.url = url;
    }

    public Integer getRowid() {
        return rowid;
    }

    public void setRowid(Integer rowid) {
        this.rowid = rowid;
    }

    public ServiceResponse getStatus() {
        return status;
    }

    public void setStatus(ServiceResponse status) {
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
}
