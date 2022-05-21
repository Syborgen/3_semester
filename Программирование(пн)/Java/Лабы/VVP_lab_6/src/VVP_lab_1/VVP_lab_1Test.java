package VVP_lab_1;

import org.junit.Assert;
import org.junit.Test;

public class VVP_lab_1Test {
    //1
    @Test
    public void operator1() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 0; i < 3; i++) {
            actual = VVP_lab_1.operator1((short) i, (short) (i * 2));
            expected = i - i * 2;
            Assert.assertEquals(expected, actual);
        }
    }

    //2
    @Test
    public void operator2() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 2; i < 5; i++) {
            actual = VVP_lab_1.operator2((short) i, (short) (i-1));
            expected = i%(i-1);
            Assert.assertEquals(expected, actual);
        }
    }

    //3
    @Test
    public void operator3() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.operator3((short) i, (short) (i+3));
            expected = i*(i+3);
            Assert.assertEquals(expected, actual);
        }
    }

    //4
    @Test
    public void operator4() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.operator4((short) i, (short) (1));
            expected = i>>1;
            Assert.assertEquals(expected, actual);
        }
    }

    //5
    @Test
    public void operator5() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.operator5((short) i, (short) (2));
            expected = i<<2;
            Assert.assertEquals(expected, actual);
        }
    }

    //6
    @Test
    public void operator6() {
        VVP_lab_1 a = new VVP_lab_1();
        boolean actual;
        boolean expected = true;
        actual = VVP_lab_1.operator6((short) 1, (short) 1);
        Assert.assertEquals(expected, actual);
        actual = VVP_lab_1.operator6((short) 1, (short) 43);
        expected=true;
        Assert.assertEquals(expected, actual);
        actual = VVP_lab_1.operator6((short) 66, (short) 43);
        expected=false;
        Assert.assertEquals(expected, actual);
    }

    //7
    @Test
    public void prioritet3() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.prioritet3((short)i,(short)(i*3));
            expected = ((i*3)*i+i);
            Assert.assertEquals(expected, actual);
        }
    }

    //8
    @Test
    public  void operator9() {
        VVP_lab_1 a = new VVP_lab_1();
        boolean actual ;
        boolean expected ;

            actual = VVP_lab_1.operator9((short) 1, (short) 1);
            expected = true;
        Assert.assertEquals(expected, actual);
        actual = VVP_lab_1.operator9((short) 5, (short) 1);
        expected =false;
        Assert.assertEquals(expected, actual);
        actual = VVP_lab_1.operator9((short) -23, (short) 1);
        expected = false;
        Assert.assertEquals(expected, actual);

    }

    //9
    @Test
    public void prioritet1() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.prioritet1((short) i, (short) (i-1));
            expected = i-(i-1+i-1);
            Assert.assertEquals(expected, actual);
        }
    }

    //10
    @Test
    public void prioritet2() {
        VVP_lab_1 a = new VVP_lab_1();
        int actual = 0;
        int expected = 0;
        for (int i = 1; i < 4; i++) {
            actual = VVP_lab_1.prioritet2((short) i, (short) (i-1));
            expected = (i-1)*(i+i);
            Assert.assertEquals(expected, actual);
        }
    }


}