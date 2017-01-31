package no.nb.depstalkrest.repo;

import no.nb.depstalkrest.Util;
import no.nb.depstalkrest.model.DSUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by oddb on 26.10.16.
 */
@Repository
public class MysqlReverseDependencyRevolver {


    private JdbcTemplate jt;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public MysqlReverseDependencyRevolver(JdbcTemplate jt) {
        this.jt = jt;
    }

    private final String DIRECT_DEPENDENCY_AS_TRUE = " 'true' as 'directDependency', id, shortname, description ";
    private final int MAX_SEARCH_LIKE_LIMIT = 25;



    private  void addIfDoesntExistInList(List<DSUnit> dlist, DSUnit unit, long parentId) {
        // check if we're about to add ourself.
        if (unit.getId() == parentId) {
            return;
        }
        for (DSUnit ds:dlist) {
            if (ds.getId() == unit.getId()) {
                return;
            }
        }
        dlist.add(unit);
    }
    public List<DSUnit> resolveReverseDependenciesForId(DSUnit dsu, List<DSUnit> allSubItems, long parentId, long nestingLevel) {
        if (Util.listContainsUnit(allSubItems,dsu.getId())) {
            return null;
        }

        List<DSUnit> subItems = getSubItems(dsu.getId());
        if (nestingLevel == 0) {
            for (DSUnit d: subItems) {
                d.setDirectDependency(true);
            }
        }

        if (allSubItems == null) {
            allSubItems = new ArrayList<>();
        } else {
            addIfDoesntExistInList(allSubItems,dsu,parentId);
        }

        for (DSUnit subDSU: subItems) {
            List<DSUnit> dsUnits = resolveReverseDependenciesForId(subDSU, allSubItems,parentId,++nestingLevel);
            if (dsUnits != null) {
                for (DSUnit d:dsUnits) {
                    addIfDoesntExistInList(allSubItems,d,parentId);
                }
            }
        }
        return allSubItems;

    }

    private List<DSUnit> getSubItems(long id) {
        String q = "select dp.id as dependencyId, d.* from DSUnit d inner join DSUnitDependencies dp on d.id = dp.unitId  where dp.dependencyId= ?";
        List<DSUnit> subItems = null;
        try {
            subItems = jt.query(q, new BeanPropertyRowMapper<>(DSUnit.class), id);
        } catch (DataAccessException e) {

        }
        return subItems;
    }

    public List<DSUnit> resolveDependenciesForId(long id) {
        DSUnit unitById = getUnitById(id);
        int nestingLevel = 0;
        List<DSUnit> dsUnits = resolveReverseDependenciesForId(unitById, null,id, nestingLevel);
        return dsUnits;
    }

    public DSUnit getUnitById(long id) {
        DSUnit dsUnit = null;
        try {
            dsUnit = jt.queryForObject(
                    "SELECT "  + DIRECT_DEPENDENCY_AS_TRUE + "from DSUnit where id = ?",
                    new BeanPropertyRowMapper<>(DSUnit.class), id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return dsUnit;
    }


}
