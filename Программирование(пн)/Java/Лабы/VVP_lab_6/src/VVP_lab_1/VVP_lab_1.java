package VVP_lab_1;

import java.util.Scanner;
import VVP_lab_6.exceptions;

public class VVP_lab_1 {
    public static void main(String[] args) throws exceptions {
        System.out.println("ЛАБОРАТОРНАЯ РАБОТА 1");
        System.out.print("Введите первое число (диапозон от -32678 до 32678) :> \na = ");
        short a = proverka();
        System.out.print("Введите второе число (диапозон от -32678 до 32678) :> \nb = ");
        short b = proverka();
        System.out.println("Девять методов с операторами по варианту идут далее :>");
        operator1(a,b);
        operator2(a,b);
        operator3(a,b);
        operator4(a,b);
        operator5(a,b);
        operator6(a,b);
        operator7(a,b);
        operator8();
        operator9(a,b);
        System.out.println("__________________________________________________________");
        System.out.println(" a = " + a + ",  b = " + b + ";");
        System.out.println("Далее 5 методов для проверки приоритетов :>");
        prioritet1(a,b);
        prioritet2(a,b);
        prioritet3(a,b);
        prioritet4(a,b);
        prioritet5(a,b);
        System.out.println("__________________________________________________________");
        System.out.println(" a = " + a + ",  b = " + b + ";");
        System.out.println("Далее 5 методов для проверки ассоциативности :>");
        System.out.print("Введите третье число (диапозон от -32678 до 32678) :> \nс = ");
        short c = proverka();
        assoc1(a,b,c);
        assoc2(a,b,c);
        assoc3(a,b,c);
        assoc4(a,b,c);
        assoc5(a,b,c);



    }
    public static short proverka()throws exceptions{
        Scanner scr = new Scanner(System.in);
        short temp;
        while (true){
            try{
                if (!scr.hasNextShort()) throw new exceptions("Введите число от -32678 до 32678 :> ");
                else {
                    temp = scr.nextShort();
                    if (temp == (short) 0)   throw new exceptions("Значение не может быть равно нулю! :> ");
                    else return temp;
                }
            }
            catch (exceptions e){
                System.err.print(e.text);
                scr.next();
            }
        }
    }

    //odin operator
//1
    public static int operator1(short a,short b){
        System.out.println("Первый оператор (-) : a - b = " + (a - b));
        return a-b;
    }
//2
public static int operator2(short a,short b){
        System.out.println("Второй оператор (%) : a % b = " + (a%b));
        return a%b;
    }
//3
public  static int operator3(short a,short b){
        System.out.println("Третий оператор (*=) : a *= b = " + a * b);
        return a*b;
    }
//4
public static int operator4(short a, short b){
        System.out.println("Четвертый оператор (>>) : a >> b = " + (short)(a >> b));
        return (a >> b);
    }
//5
public static int operator5(short a,short b){
        System.out.println("Пятый оператор (<<=) : a <<= b = " + (short)(a << b));
        return (a << b);
    }
//6
public static boolean operator6(short a, short b) {
    if (a <= b) {
        System.out.println("Шестой оператор (<=) сработал : a <= b;");
        return true;
    } else {
        System.out.println("Шестой оператор (<=) не сработал, так как a > b;");
        return false;
    }
}
//7
    public static void operator7(short a,short b){
        System.out.println("Седьмой оператор (^) : a ^ b = " + (a ^ b));

    }
//8
    public static boolean operator8() {
    Scanner scr = new Scanner(System.in);
    System.out.println("Введите True или False :>");
    while(!scr.hasNextBoolean()) {
        try {   // новая обработка исключений
            if (!scr.hasNextBoolean()) throw new exceptions("Ошибка, введите True или False :>");
        } catch (exceptions e) {
            System.err.println(e.text);
            scr.next();
        }
    }
    boolean temp = scr.nextBoolean();
    System.out.println("Восьмой оператор (!) : " + temp + " -> ! -> " + !temp);
    return !temp;
}
//9
    public static boolean operator9(short a,short b) {
        if (a == b) {
            System.out.println("Девятый оператор (==) сработал, числа равны;");
            return true;
        } else {
            System.out.println("Девятый оператор (==) не сработал, числа не равны;");
            return false;
        }
    }


    //proverka prioritetov(2 operatora)
//1
    public static  int prioritet1(short a,short b){
        System.out.println("Первая проверка приоритетности :\na - (b + b) (вначале (), и только потом -) = " + (a -(b+b)));
        return (a -(b+b));
    }
//2
public static  int prioritet2(short a,short b){
        System.out.println("Вторая проверка приоритетности :\nb * (a + a) (вначале (), и только потом *) = " + b * (a + a));
        return b * (a + a);
    }
//3
    public static  int prioritet3(short a,short b){
        System.out.println("Третья проверка приоритетности :\nb * a + a (вначале *, и только потом +) = " + (b * a + a));
        return b*a+a;
    }
//4
public static  void prioritet4(short a,short b){
        System.out.println("Четвертая проверка приоритетности :\na * a < b ? a : b (вначале ?:, и только потом *) = " + (a * (a < b ? a : b)));
    }
//5
public  static void prioritet5(short a,short b){
        System.out.println("Пятая проверка приоритетности :\na += a > b ? a : b (вначале ?:, и только потом +=) = " + (a + (a > b ? a : b)));
    }

    //udvoenniye operatory
    public static int[] assoc1(short a,short b,short c) {
        System.out.println("Первая проверка ассоциативности : a - b - c И c - a - b >");
        System.out.println("a - b - c = " + (a - b - c));
        System.out.println("c - a - b = " + (c - a - b));
        int[] temp = {a - b - c, c - a - b};
        return temp;
    }

    public static float[] assoc2(short a,short b,short c) {
        System.out.println("Вторая проверка ассоциативности : a / b / c И c / a / b >");
        System.out.println("a / b / c = " + (float)a / (float)b / (float)c);
        System.out.println("c / a / b = " + (float)c / (float)a / (float)b);
        float[] temp = {(float)a / (float)b / (float)c, (float)c / (float)a / (float)b};
        return temp;
    }

    public static float[] assoc3(short a,short b,short c) {
        System.out.println("Третья проверка ассоциативности : a * b / c И c * a / b >");
        System.out.println("a * b / c = " + (float)a * (float)b / (float)c);
        System.out.println("c * a / b = " + (float)c * (float)a / (float)b);
        float[] temp = {(float)a * (float)b / (float)c , (float)c * (float)a / (float)b};
        return temp;
    }

    public static void assoc4(short a,short b,short c) {
        System.out.println("Четвертая проверка ассоциативности : a + b / c И c + a / b >");
        System.out.println("a + b / c = " + ((float)a + (float)(b / c)));
        System.out.println("c + a / b = " + ((float)c + (float)(a / b)));
    }

    public static float[] assoc5(short a,short b,short c) {
        System.out.println("Пятая проверка ассоциативности : a / b - c И c / a - b >");
        System.out.println("a / b - c = " + ((float)a / (float)b - (float)c));
        System.out.println("c / a - b = " + ((float)c / (float)a - (float)b));
        float[] temp = {((float)a / (float)b - (float)c), ((float)c / (float)a - (float)b)};
        return temp;
    }
    //text methods
//1
//2
//3
//4
//5
//6
//7
//8
//9
//10

}//main
