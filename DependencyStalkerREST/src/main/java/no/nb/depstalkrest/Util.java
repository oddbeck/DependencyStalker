package no.nb.depstalkrest;

import no.nb.depstalkrest.model.DSUnit;

import java.util.List;

/**
 * Created by oddb on 26.10.16.
 */
public class Util {

    static public boolean listContainsUnit(List<DSUnit> items, long id) {
        if (items == null) {
            return false;
        }
        try {
            for (DSUnit dsu :items) {
                if (dsu.getId() == id) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
