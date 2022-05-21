package VVP_lab_3;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class VVP_lab_3Test {

    @Test
    public void variant() {
        List<Integer> expectedList = new ArrayList<>();
        List<Integer> actualList = new ArrayList<>();
        expectedList.add(1);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(3);
        expectedList.add(4);
        expectedList.add(4);
        expectedList.add(4);
        expectedList.add(5);
        actualList.add(1);
        actualList.add(2);
        actualList.add(3);
        actualList.add(4);
        actualList.add(5);
        actualList = VVP_lab_3.variant((ArrayList<Integer>) actualList);
        Assert.assertEquals(expectedList, actualList);


        expectedList = new ArrayList<>();
        actualList = new ArrayList<>();
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(2);
        expectedList.add(3);
        expectedList.add(4);
        expectedList.add(4);
        expectedList.add(4);
        expectedList.add(6);
        expectedList.add(6);
        expectedList.add(6);
        actualList.add(2);
        actualList.add(2);
        actualList.add(3);
        actualList.add(4);
        actualList.add(6);
        actualList = VVP_lab_3.variant((ArrayList<Integer>) actualList);
        Assert.assertEquals(expectedList, actualList);
    }
}
