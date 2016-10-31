package no.nb.depstalkrest.iface;

import no.nb.depstalkrest.model.DSUnit;

import java.util.List;

/**
 * Created by oddb on 28.09.16.
 */
public interface DSDatabaseRepository {
    DSUnit getUnitById(long id);
    DSUnit getUnitByName(String name);
    void saveNewUnit(DSUnit dsUnit);
    List<DSUnit> resolveDependenciesForId(long id);

    List<DSUnit> searchForUnitsMatchingName(String name);

    List<DSUnit> searchForUnitsMatchingDescription(String name);

    boolean addNewDependency(DSUnit parent, DSUnit child);

    void deleteDependencyById(long depId);
}
