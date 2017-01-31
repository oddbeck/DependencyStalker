package no.nb.depstalkrest.repo;

import no.nb.depstalkrest.model.DSUnitCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by oddb on 12.10.16.
 */
@Service
public class CategoryRepository {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public DSUnitCategory getCategoryByName(String name) {
        try {
            DSUnitCategory dsUnitCategory = jdbcTemplate.queryForObject("SELECT id, shortname,description from DSUnitCategory where name = ?",
                    new BeanPropertyRowMapper<>(DSUnitCategory.class), name);
            return dsUnitCategory;
        } catch (DataAccessException e) {
            return null;
        }
    }

    public DSUnitCategory getCategoryById(long id) {
        try {
            return jdbcTemplate.queryForObject("SELECT id, shortname,description from DSUnitCategory where id = ?",
                    new BeanPropertyRowMapper<>(DSUnitCategory.class), id);
        } catch (DataAccessException e) {
            return null;
        }
    }

    public DSUnitCategory getCategoryForDsUnitItemById(long itemId) {

        String sql = "select * from DSUnitCategory c inner join DSUnitAndCategory a on c.id = a.category_id where a.unit_id = ?";
        //jdbcTemplate.queryForObject(sql,);
        return null;
    }

    public void saveNewCategoryForUnit(long unitId, long categoryId) {
        DSUnitCategory categoryForUnit = getCategoryForUnit(unitId);
        if (categoryForUnit == null) {
            String insSql = "INSERT INTO DSUnitAndCategory (unit_id,category_id) values (?,?)";
            jdbcTemplate.update(insSql,unitId,categoryId);
        } else {
            String insSql = "UPDATE DSUnitAndCategory set category_id = ? where unit_id = ?";
            jdbcTemplate.update(insSql,categoryId,unitId);
        }
    }

    public void saveCategoryItem(DSUnitCategory category) {
        DSUnitCategory existingCategory = getCategoryById(category.getId());

        if (existingCategory == null) {
            String insSql = "INSERT INTO DSUnitCategory (shortname,description) values (?,?)";
            jdbcTemplate.update(insSql,category.getShortname(),category.getDescription());
        } else {
            String insSql = "UPDATE DSUnitCategory set shortname = ?, description = ? where id = ?";
            jdbcTemplate.update(insSql,category.getShortname(),category.getDescription(),existingCategory.getId());
        }
    }

    public List<DSUnitCategory> getAllCategories() {
        try {
            List<DSUnitCategory> dsUnitCategories =
                    jdbcTemplate.query("SELECT id, shortname,description from DSUnitCategory order by shortname",
                            new BeanPropertyRowMapper<>(DSUnitCategory.class));
            return dsUnitCategories;
        } catch (DataAccessException e) {
            return null;
        }
    }


    public List<DSUnitCategory> getCategoryListByName(String name) {

        name = name.replace("*","%");
        List<DSUnitCategory> query = null;
        try {
            query = jdbcTemplate.query("SELECT id,shortname,description from DSUnitCategory where shortname like ? limit 0,25",
                    new BeanPropertyRowMapper<>(DSUnitCategory.class), name);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return query;
    }

    public DSUnitCategory getCategoryForUnit(long unitId) {
        String sql = "select c.* from DSUnitCategory c inner join DSUnitAndCategory ac on c.id = ac.category_id where ac.unit_id = ?";
        try {
            DSUnitCategory dsUnitCategory = jdbcTemplate.queryForObject(sql,
                    new BeanPropertyRowMapper<>(DSUnitCategory.class), unitId);
            return dsUnitCategory;
        } catch (DataAccessException e) {
            return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public String deleteCategoryById(long id) {
        try {
            String sql = "DELETE from DSUnitCategory where id = ?";
            jdbcTemplate.update(sql,id);
            return "OK";
        } catch (DataAccessException e) {
            return "Unable to delete category, the category is in use";
        }
    }

}
