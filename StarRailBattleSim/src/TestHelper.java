import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class TestHelper {

    public static List<Class<?>> getStaticClassesExtendingA(Class<?> outerClass, Class<?> A) {
        List<Class<?>> staticClasses = new ArrayList<>();

        Class<?>[] innerClasses = outerClass.getDeclaredClasses();

        for (Class<?> innerClass : innerClasses) {
            if (Modifier.isStatic(innerClass.getModifiers()) && A.isAssignableFrom(innerClass)) {
                staticClasses.add(innerClass);
            }
        }

        return staticClasses;
    }

    public static Object callMethodOnClasses(Class<?> clazz, String methodName) {
        try {
            Method method = clazz.getMethod(methodName);

            if (Modifier.isStatic(method.getModifiers())) {
                method.invoke(null);
            } else {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                return method.invoke(instance);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

}
