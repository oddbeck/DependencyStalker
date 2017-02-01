package no.nb.depstalkrest.controller;

import no.nb.depstalkrest.model.DSDependency;
import no.nb.depstalkrest.model.DSUnit;
import no.nb.depstalkrest.service.DSMysqlRepoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by oddb on 28.09.16.
 */
@RestController
@CrossOrigin(origins = HomeController.CROSS_ORIGIN)
public class HomeController {

    public static final String CROSS_ORIGIN = "http://localhost:4200";

    @Value("${server.context-path:test}")
    private String servletConf;

    @Autowired
    DSMysqlRepoServiceImpl repoService;

    @RequestMapping("/getDSUnit/{name}")
    public DSUnit getDsUnit(@PathVariable String name) {
        DSUnit unitList = repoService.getUnitByName(name);
        return unitList;
    }

    @RequestMapping("/searchForDSUnitByName/{name}")
    public List<DSUnit> searchForUnitByName(@PathVariable String name) {

        List<DSUnit> unitList = repoService.searchForUnitsMatchingName(name);
        return unitList;
    }

    @RequestMapping("/searchForDSUnitByDescription/{description}")
    public List<DSUnit> searchForUnitByDescription(@PathVariable String description) {

        List<DSUnit> unitList = repoService.searchForUnitsMatchingDescription(description);
        return unitList;
    }

    @RequestMapping("/getDSUnitById/{id}")
    public DSUnit getDsUnitByID(@PathVariable long id ) {
        DSUnit unit = null;
        try {
            unit = repoService.getUnitById(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return unit;
    }

    @RequestMapping(value="/saveUnit", method= RequestMethod.POST)
    public DSUnit saveNewUnit(@RequestBody DSUnit dsUnit) {
        repoService.saveNewUnit(dsUnit);
        dsUnit.setShortname(dsUnit.getShortname());
        return dsUnit;
    }

    @RequestMapping(value="/updateUnit", method= RequestMethod.PUT)
    public DSUnit updateUnit(@RequestBody DSUnit dsUnit) {
        repoService.updateUnit(dsUnit);
        return dsUnit;
    }

    @RequestMapping(value="/saveDependency", method= RequestMethod.POST)
    public boolean saveDependency(@RequestBody DSDependency dep) {
        repoService.addNewDependency(dep.getParent(),dep.getChild());
        return true;
    }

    @RequestMapping(value="/deleteDependencyById/{depId}", method= RequestMethod.DELETE)
    public boolean deleteDependency(@PathVariable long depId) {
        repoService.deleteDependencyById(depId);
        return true;
    }

    @RequestMapping(value="/resolveDepsForId/{id}")
    public List<DSUnit> resolveDependenciesForId(@PathVariable long id ) {
        try {
            List<DSUnit> dsUnits = repoService.resolveDependenciesForId(id);
            return dsUnits;

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
