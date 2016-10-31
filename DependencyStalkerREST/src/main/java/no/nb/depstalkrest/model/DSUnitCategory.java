package no.nb.depstalkrest.model;

/**
 * Created by oddb on 12.10.16.
 */
public class DSUnitCategory {
    private long id;
    private String description;
    private String shortname;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }
}
