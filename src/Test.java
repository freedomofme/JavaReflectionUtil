import sun.reflect.misc.ReflectUtil;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Test {
    public static void main(String[] args) {
        try {
            ReflectTest test = (ReflectTest) ReflectionUtil.newInstance(ReflectTest.class,
                    new ReflectionWrapper[]{ReflectionUtil.typeOf(3), ReflectionUtil.typeOf(100)});
            System.out.println("new instance success");

            int r = (int) ReflectionUtil.getField(test, "count");
            System.out.println("init count: " + r);

            r = (int) ReflectionUtil.invokeMethod(test, "setCount", ReflectionUtil.typeOf(Integer.valueOf(7)));
            System.out.println("invoke method setCount with parameter int 7 (unboxing), count: " + r);

            r = (int) ReflectionUtil.invokeMethod(test, "addCount", ReflectionUtil.typeOf(5));
            System.out.println("invoke method addCount with parameter Integer 5 (boxing), count: " + r);

            r = (int) ReflectionUtil.invokeMethod(test, "getBaseCount");
            System.out.println("invoke method getBaseCount from base class, count: " + r);

            int[] nums = new int[]{1, 2, 3};
            System.out.println("input array: 1 2 3");
            ReflectionUtil.invokeMethod(test, "printArray", ReflectionUtil.typeOf(nums));

        } catch (InstantiationException | NoSuchFieldException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}


class ReflectTest extends Base{
    private int count;
    public ReflectTest(int count, int foo) {
        this.count = count;
    }

    private int addCount(int adder) {
        return count += adder;
    }

    private int setCount(Integer adder) {
        return count = adder;
    }

    private void printArray(int[] nums) {
        System.out.print("invoke method printArray, print: ");
        for(int i: nums) {
            System.out.print(i + " ");
        }
    }
}

class Base {
    Base(){}
    private int baseCount = -1;
    private int getBaseCount() {
        return baseCount;
    }
}
