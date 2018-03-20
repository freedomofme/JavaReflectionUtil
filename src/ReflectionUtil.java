import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


class ReflectionWrapper{
    Object obj;
    Class cls;
    public ReflectionWrapper(Class cls, Object obj) {
        this.cls = cls;
        this.obj = obj;
    }
}

public class ReflectionUtil {
    // 获得指定变量的值
    public static Object getField(Object instance, String fieldName)
            throws IllegalAccessException, NoSuchFieldException {

        Field field = getField(instance.getClass(), fieldName);
        // 参数值为true，禁用访问控制检查
        field.setAccessible(true);
        return field.get(instance);
    }

    //该方法实现根据变量名获得该变量的值
    private static Field getField(Class thisClass, String fieldName)
            throws NoSuchFieldException {

        if (thisClass == null) {
            throw new NoSuchFieldException("Error field !");
        }
        return thisClass.getDeclaredField(fieldName);
    }


    public static Method getMethod(Object instance, String methodName, Class[] classTypes)
            throws  NoSuchMethodException {

        Method accessMethod = getMethod(instance.getClass(), methodName, classTypes);
        //参数值为true，禁用访问控制检查
        accessMethod.setAccessible(true);

        return accessMethod;
    }

    private static Method getMethod(Class<?> thisClass, String methodName, Class[] classTypes)
            throws NoSuchMethodException {
        if (thisClass == null) {
            throw new NoSuchMethodException("Cannot find the corresponding method, check the input parameters");
        }
        try {
            return thisClass.getDeclaredMethod(methodName, classTypes);
        } catch (NoSuchMethodException e) {
            return getMethod(thisClass.getSuperclass(), methodName, classTypes);
        }
    }


//    public static

    //调用含null
    public static Object invokeMethod(Object instance, String methodName)
            throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        return getMethod(instance, methodName, null).invoke(instance);
    }

    //调用含单个参数的方法
    public static Object invokeMethod(Object instance, String methodName, Object arg)
            throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Object[] args = new Object[1];
        args[0] = arg;
        return invokeMethod(instance, methodName, args);
    }

    //调用含多个参数的方法
    public static Object invokeMethod(Object instance, String methodName, Object[] args)
            throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Class[] classTypes = null;
        if (args != null) {
            classTypes = new Class[args.length];
            for (int i = 0; i < args.length; i++) {
                if (args[i] != null) {
                    classTypes[i] = args[i].getClass();
                }
            }
        }
        return getMethod(instance, methodName, new Class[]{int.class}).invoke(instance, args);
    }

    // 使用typeOf封装, 初始化构造函数为单个参数的对象
    public static Object newInstance(Class<?> cls, ReflectionWrapper arg) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        ReflectionWrapper[] args = new ReflectionWrapper[1];
        args[0] = arg;
        return newInstance(cls, args);
    }

    // 使用typeOf封装, 初始化构造函数为多个参数的对象
    public static Object newInstance(Class<?> cls, ReflectionWrapper[] args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class[] classTypes = null;
        Object[] realObjects = null;
        if (args != null) {
            classTypes = new Class[args.length];
            realObjects = new Object[args.length];
            fillReflectionWrapper(classTypes, realObjects, args);
        }
        return cls.getConstructor(classTypes).newInstance(realObjects);
    }

    // 使用typeOf封装, 调用含单个参数的方法
    public static Object invokeMethod(Object instance, String methodName, ReflectionWrapper arg)
            throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        ReflectionWrapper[] args = new ReflectionWrapper[1];
        args[0] = arg;
        return invokeMethod(instance, methodName, args);
    }

    // 使用typeOf封装, 调用含多个参数的方法
    public static Object invokeMethod(Object instance, String methodName, ReflectionWrapper[] args)
            throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        Class[] classTypes = null;
        Object[] realObjects = null;
        if (args != null) {
            classTypes = new Class[args.length];
            realObjects = new Object[args.length];
            fillReflectionWrapper(classTypes, realObjects, args);
        }
        return getMethod(instance, methodName, classTypes).invoke(instance, realObjects);
    }

    // 将ReflectionWrapper中的参数分别拆分开，填充了Class数组和Object数据
    private static void fillReflectionWrapper(Class[] classTypes, Object[] realObjects, ReflectionWrapper[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null) {
                classTypes[i] = args[i].cls;
                realObjects[i] = args[i].obj;
            }
        }
    }



    public static ReflectionWrapper typeOf(Integer obj) {
        return new ReflectionWrapper(Integer.class, obj);
    }

    public static ReflectionWrapper typeOf(int obj) {
        return new ReflectionWrapper(Integer.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Double obj) {
        return new ReflectionWrapper(Double.class, obj);
    }

    public static ReflectionWrapper typeOf(double obj) {
        return new ReflectionWrapper(Double.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Character obj) {
        return new ReflectionWrapper(Character.class, obj);
    }

    public static ReflectionWrapper typeOf(char obj) {
        return new ReflectionWrapper(Character.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Boolean obj) {
        return new ReflectionWrapper(Boolean.class, obj);
    }

    public static ReflectionWrapper typeOf(boolean obj) {
        return new ReflectionWrapper(Boolean.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Byte obj) {
        return new ReflectionWrapper(Byte.class, obj);
    }

    public static ReflectionWrapper typeOf(byte obj) {
        return new ReflectionWrapper(Byte.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Short obj) {
        return new ReflectionWrapper(Short.class, obj);
    }

    public static ReflectionWrapper typeOf(short obj) {
        return new ReflectionWrapper(Short.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Long obj) {
        return new ReflectionWrapper(Long.class, obj);
    }

    public static ReflectionWrapper typeOf(long obj) {
        return new ReflectionWrapper(Long.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Float obj) {
        return new ReflectionWrapper(Float.class, obj);
    }

    public static ReflectionWrapper typeOf(float obj) {
        return new ReflectionWrapper(Float.TYPE, obj);
    }

    public static ReflectionWrapper typeOf(Void obj) {
        return new ReflectionWrapper(Void.class, obj);
    }

    public static ReflectionWrapper typeOf(Object obj) {
        return new ReflectionWrapper(obj.getClass(), obj);
    }

//    public static ReflectionWrapper[] typeOf(Object[] objs) {
//        ReflectionWrapper[] wrappers = new ReflectionWrapper[objs.length];
//        Class cls = objs.getClass();
//        for (int i = 0; i < objs.length; i++) {
//           wrappers[i] = new ReflectionWrapper(cls, objs);
//        }
//        return wrappers;
//    }

}






