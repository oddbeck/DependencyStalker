package no.nb.depstalkrest.model;

/**
 * Created by oddb on 28.09.16.
 */
public class DSUnit {

    private String shortname;
    private String description;
    private long id;
    private boolean directDependency = false;

    public DSUnit() {
    }

    public boolean isDirectDependency() {
        return directDependency;
    }

    public void setDirectDependency(boolean directDependency) {
        this.directDependency = directDependency;
    }

    public long getDependencyId() {
        return dependencyId;
    }

    public void setDependencyId(long dependencyId) {
        this.dependencyId = dependencyId;
    }

    private long dependencyId;

    public DSUnit(String shortname, String description, long id) {
        this.shortname = shortname;
        this.description = description;
        this.id = id;
    }

    public DSUnit(String shortname, long id) {
        this.shortname = shortname;
        this.id = id;
        this.description = shortname;
    }


    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return shortname + " " + id;
    }


}
