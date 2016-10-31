package no.nb.depstalkrest.repo;

import no.nb.depstalkrest.model.DSUnit;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by oddb on 30.09.16.
 */
public class MysqlRepoTest {
    @Test
    public void test1() throws Exception {

        List<DSUnit> first = new ArrayList<>();
        first.add(new DSUnit("en","en",1));
        first.add(new DSUnit("to","to",2));
        first.add(new DSUnit("tre","tre",3));

        List<DSUnit> second = new ArrayList<>();
        second.add(new DSUnit("en",1));
        second.add(new DSUnit("to",2));
        second.add(new DSUnit("tre",3));

        List<DSUnit> third= new ArrayList<>();
        third.add(new DSUnit("en","en",1));
        third.add(new DSUnit("en","en",2));
        third.add(new DSUnit("en","en",3));



    }

}