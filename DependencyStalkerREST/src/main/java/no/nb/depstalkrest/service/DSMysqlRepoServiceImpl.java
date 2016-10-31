package no.nb.depstalkrest.service;

import no.nb.depstalkrest.iface.DSDatabaseRepository;
import no.nb.depstalkrest.model.DSUnit;
import no.nb.depstalkrest.repo.MysqlRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by oddb on 28.09.16.
 */
@Service
public class DSMysqlRepoServiceImpl implements DSDatabaseRepository {

    MysqlRepositoryImpl repo;

    @Autowired
    public DSMysqlRepoServiceImpl(@SuppressWarnings("SpringJavaAutowiringInspection") MysqlRepositoryImpl repo) {
        this.repo = repo;
    }

    @Override
    public DSUnit getUnitById(long id) {

        DSUnit unitById = repo.getUnitById(id);
        return unitById;
    }

    @Override
    public DSUnit getUnitByName(String name) {
        return repo.getUnitByName(name);
    }

    @Override
    public void saveNewUnit(DSUnit dsUnit) {
        repo.saveNewUnit(dsUnit);
    }

    @Override
    public List<DSUnit> resolveDependenciesForId(long id) {
        DSUnit unitById = getUnitById(id);
        int nestingLevel = 0;
        List<DSUnit> dsUnits = repo.resolveDependenciesForId(unitById, null,id, nestingLevel);
        return dsUnits;
    }

    @Override
    public List<DSUnit> searchForUnitsMatchingName(String name) {
        return repo.searchForUnitsMatchingName(name);
    }
    @Override
    public List<DSUnit> searchForUnitsMatchingDescription(String description) {
        return repo.searchForUnitsMatchingDescription(description);
    }

    @Override
    public boolean addNewDependency(DSUnit parent, DSUnit child) {
        return repo.addNewDependency(parent,child);
    }

    @Override
    public void deleteDependencyById(long depId) {
        repo.deleteDependencyById(depId);
    }

    public void updateUnit(DSUnit dsUnit) {
        repo.updateUnit(dsUnit);
    }
}
