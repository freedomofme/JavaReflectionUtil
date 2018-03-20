# JavaReflectionUtil

## Core functions
* 简化反射代码,只需要输入参数的值即可，不必输入参数类型和考虑访问权限的问题
* 使用typeOf方法自动处理输入参数的基本类型的装箱和拆箱问题

	例如：

		假如输入参数foo为3，typeOf(foo)为int.class。
		假如输入参数foo为Integer(3), typeOf(foo)为Integer.class
	
* 假如当前类中没有指定参数的Method时，自动向父类中寻找Method
* 支持多参数、数组
	


## DEMO

--------
	Main.java

	// new Instance
	ReflectTest test = (ReflectTest) ReflectionUtil.newInstance(ReflectTest.class,
                    ReflectionUtil.typeOf(3));
                    
    // invoke mothod without regard to access right, auto boxing
	ReflectionUtil.invokeMethod(test, "setCount", ReflectionUtil.typeOf(Integer.valueOf(7)));
	
	// invoke mothod without regard to access right, no auton boxing
	ReflectionUtil.invokeMethod(test, "addCount", ReflectionUtil.typeOf(5));
	
	// invoke mothod which is in super class  
	ReflectionUtil.invokeMethod(test, "getBaseCount");
	
	// pass array as parameter
	int[] nums = new int[]{1, 2, 3};
	ReflectionUtil.invokeMethod(test, "printArray", ReflectionUtil.typeOf(nums));
	
--------
	ReflectTest.java
	
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

Contribution
------------

Want to contribute? Please, feel free to create a pull request! ;)

License
----------

[Apache Version 2.0][License]

[License]:          http://www.apache.org/licenses/LICENSE-2.0.html
	
		