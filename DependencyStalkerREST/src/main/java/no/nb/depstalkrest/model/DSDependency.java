package no.nb.depstalkrest.model;

/**
 * Created by oddb on 03.10.16.
 */
public class DSDependency {

    private DSUnit parent;
    private DSUnit child;
    private boolean isDirectDependency;

    public boolean isDirectDependency() {
        return isDirectDependency;
    }

    public void setDirectDependency(boolean directDependency) {
        isDirectDependency = directDependency;
    }

    public DSUnit getParent() {
        return parent;
    }

    public void setParent(DSUnit parent) {
        this.parent = parent;
    }

    public DSUnit getChild() {
        return child;
    }

    public void setChild(DSUnit child) {
        this.child = child;
    }
}
