package no.nb.depstalkrest.controller;

import no.nb.depstalkrest.model.DSUnit;
import no.nb.depstalkrest.repo.MysqlReverseDependencyRevolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by oddb on 26.10.16.
 */
@RestController
@CrossOrigin(origins = HomeController.CROSS_ORIGIN)
public class ReverseDependencyResolverController {

    @Autowired
    MysqlReverseDependencyRevolver reverseDependencyRevolver;

    @RequestMapping("/reverse")
    public List<DSUnit> test_1() {
        List<DSUnit> dsUnits = reverseDependencyRevolver.resolveDependenciesForId(21);
        return dsUnits;
    }

    @RequestMapping(value="/reverseResolveDepsForId/{id}")
    public List<DSUnit> resolveReverseDependencyById(@PathVariable long id) {
        List<DSUnit> dsUnits = reverseDependencyRevolver.resolveDependenciesForId(id);
        return dsUnits;
    }

    @RequestMapping("/oddis")
    public String test_2() {
        return "oddis";
    }
}
