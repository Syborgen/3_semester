package VVP_lab_5;

import org.junit.Assert;

import org.junit.Test;
class VVP_lab_5Test {

    @Test
    void findCount() {
        VVP_lab_5 a = new VVP_lab_5();
        int actual,expected;
        actual=a.findCount("asd,gfds.asdf.,qwer,ttt.");
        expected = 5;
        Assert.assertEquals(expected,actual);

        actual=a.findCount("qwerqw.......,,,,rewq,,,,,,,rewq.,,,,,,,.");
        expected = 3;
        Assert.assertEquals(expected,actual);

        actual = a.findCount("itawet`kfasdfqwe.wqerwqef,sadfawe    a fff ff ff f.");
        expected = 9;
        Assert.assertEquals(expected,actual);
    }
}