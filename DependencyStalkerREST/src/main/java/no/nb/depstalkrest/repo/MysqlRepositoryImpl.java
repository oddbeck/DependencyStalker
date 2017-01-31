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
 * Created by oddb on 28.09.16.
 */
@Repository
public class MysqlRepositoryImpl {

    private final String DIRECT_DEPENDENCY_AS_TRUE = " 'true' as 'directDependency', id, shortname, description ";
    private JdbcTemplate jt;
    private final int MAX_SEARCH_LIKE_LIMIT = 25;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public MysqlRepositoryImpl(JdbcTemplate jt) {
        this.jt = jt;
    }

    public DSUnit getUnitByName(String name) {

        DSUnit dsUnit = null;
            BeanPropertyRowMapper<DSUnit> dsUnitBeanPropertyRowMapper = new BeanPropertyRowMapper<>(DSUnit.class);
        try {
            dsUnit = jt.queryForObject("SELECT" +
                    DIRECT_DEPENDENCY_AS_TRUE +
                    "from DSUnit where shortname = ? limit 0,1", new Object[] {name}, dsUnitBeanPropertyRowMapper);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return dsUnit;
    }

    public List<DSUnit> searchForUnitsMatchingName(String name) {
        List<DSUnit> dsUnit = null;
        try {
            BeanPropertyRowMapper<DSUnit> dsUnitBeanPropertyRowMapper = new BeanPropertyRowMapper<>(DSUnit.class);
            name = name.replace("*","%");
            dsUnit = jt.query("SELECT " + DIRECT_DEPENDENCY_AS_TRUE +
                    "from DSUnit where shortname like ? limit 0,"
                    + MAX_SEARCH_LIKE_LIMIT, new Object[] {name}, dsUnitBeanPropertyRowMapper);
        } catch (Exception e) {
        }
        return dsUnit;
    }

    public List<DSUnit> searchForUnitsMatchingDescription(String name) {
        List<DSUnit> dsUnit = null;
        try {
            BeanPropertyRowMapper<DSUnit> dsUnitBeanPropertyRowMapper = new BeanPropertyRowMapper<>(DSUnit.class);
            name = name.replace("*","%");
            dsUnit = jt.query(
                    "SELECT " + DIRECT_DEPENDENCY_AS_TRUE + "from DSUnit where description like ? limit 0,"
                            + MAX_SEARCH_LIKE_LIMIT, new Object[] {name}, dsUnitBeanPropertyRowMapper);
        } catch (Exception e) {
        }
        return dsUnit;
    }


    public DSUnit getUnitById(long id) {
        DSUnit dsUnit = null;
        try {
            dsUnit = jt.queryForObject(
                    "SELECT "  + DIRECT_DEPENDENCY_AS_TRUE + "from DSUnit where id = ?",
                    new BeanPropertyRowMapper<>(DSUnit.class), id);
        } catch (DataAccessException e) {

            System.out.println(e.getMessage());
        }
        return dsUnit;
    }

    public void saveNewUnit(DSUnit unit) {
        String sql =
                "INSERT INTO DSUnit (shortname,description) values (?,?)";
        if (unit.getShortname() != null && unit.getShortname().length() > 0) {
            jt.update(sql,unit.getShortname(),unit.getDescription());
        }

    }

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
    public List<DSUnit> resolveDependenciesForId(DSUnit dsu, List<DSUnit> allSubItems, long parentId, long nestingLevel) {
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
            List<DSUnit> dsUnits = resolveDependenciesForId(subDSU, allSubItems,parentId,++nestingLevel);
            if (dsUnits != null) {
                for (DSUnit d:dsUnits) {
                    addIfDoesntExistInList(allSubItems,d,parentId);
                }
            }
        }
        return allSubItems;

    }

    public boolean dependencyExists(long unitId, long dependencyId) {
        try {
            Long aLong = jt.queryForObject("SELECT ID from DSUnitDependencies where unitId=? AND dependencyId=?", long.class, unitId, dependencyId);
            if (aLong > 0) {
                return true;
            }
        } catch (DataAccessException e) {
            return false;
        }
        return false;
    }

    public boolean addNewDependency(DSUnit parent, DSUnit child) {

        if (parent.getId() == child.getId()) {
            return false;
        }
        if (dependencyExists(parent.getId(),child.getId())) {
            return true;
        }
        DSUnit foundParent = getUnitById(parent.getId());
        if (foundParent == null) {
            return false;
        }

        DSUnit foundChild;
        if (child.getId() != 0) {
            foundChild = getUnitById(child.getId());
            if (foundChild == null) {
                saveNewUnit(child);
                foundChild = getUnitByName(child.getShortname());
            }
        } else {
            foundChild = getUnitByName(child.getShortname());
            if (foundChild == null) {
                saveNewUnit(child);
            }
            foundChild = getUnitByName(child.getShortname());
        }
        System.out.println(foundChild.getShortname());
        try {
            jt.update("INSERT INTO DSUnitDependencies (unitId,dependencyId) values (?,?)",parent.getId(),foundChild.getId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return true;

    }

    private List<DSUnit> getSubItems(long id) {
        String q = "select dp.id as dependencyId, d.* from DSUnit d inner join DSUnitDependencies dp on d.id = dp.dependencyId  where dp.unitId= ?";
        List<DSUnit> subItems = jt.query(q, new BeanPropertyRowMapper<>(DSUnit.class), id);
        return subItems;
    }

    public void updateUnit(DSUnit unit) {
        String sql = "UPDATE DSUnit set description=?, shortname = ? where id = ?";
        jt.update(sql,unit.getDescription(),unit.getShortname(),unit.getId());
    }
    public void deleteDependencyById(long depId) {
        jt.update("DELETE from DSUnitDependencies where id = ?",depId);
    }
}
