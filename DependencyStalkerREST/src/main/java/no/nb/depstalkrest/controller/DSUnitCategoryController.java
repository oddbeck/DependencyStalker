package no.nb.depstalkrest.controller;

import no.nb.depstalkrest.model.DSUnitCategory;
import no.nb.depstalkrest.repo.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by oddb on 12.10.16.
 */
@RestController
@CrossOrigin(origins = HomeController.CROSS_ORIGIN)
public class DSUnitCategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    @RequestMapping(value = "/saveCategory",method = RequestMethod.POST)
    public void saveNewDSUnitCategory(@RequestBody DSUnitCategory cat) {
        categoryRepository.saveCategoryItem(cat);
    }

    @RequestMapping(value = "/getCategoryListByName/{name}")
    public List<DSUnitCategory> getCategoryListByName(@PathVariable String name) {
        return categoryRepository.getCategoryListByName(name);
    }

    @RequestMapping(value = "/putNewCategoryOnItem/{unitId}/{categoriId}", method = RequestMethod.PUT)
    public void getCategoryListByName(@PathVariable long unitId, @PathVariable long categoriId) {
        categoryRepository.saveNewCategoryForUnit(unitId,categoriId);
    }

    @RequestMapping(value = "/getCategoryForUnit/{unitId}")
    public DSUnitCategory getCategoryForUnit(@PathVariable long unitId) {
        DSUnitCategory categoryForUnit = categoryRepository.getCategoryForUnit(unitId);
//        if (categoryForUnit == null) {
//            DSUnitCategory dsUnitCategory = new DSUnitCategory();
//            dsUnitCategory.setShortname("not found");
//            dsUnitCategory.setDescription("not found");
//            return dsUnitCategory;
//        }
        return categoryForUnit;
    }

    @RequestMapping(value = "/getAllCategories")
    public List<DSUnitCategory> getAllCategories() {
        return categoryRepository.getAllCategories();
    }

    @RequestMapping(value = "/deleteCategory/{id}",method = RequestMethod.DELETE)
    public String deleteCategory(@PathVariable long id) {
        return categoryRepository.deleteCategoryById(id);
    }
}
