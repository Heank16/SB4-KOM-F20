package dk.sdu.mmmi.cbse.common.util;

import java.util.*;

public class SPILocator {

    private static final Map<Class, ServiceLoader> loadermap = new HashMap<Class, ServiceLoader>();


    public static <T> List<T> locateAll(Class<T> service) {
        ServiceLoader<T> loader = loadermap.get(service);

        if (loader == null) {
            loader = ServiceLoader.load(service);
            loadermap.put(service, loader);
        }

        List<T> list = new ArrayList<T>();

        try {
            for (T instance : loader) {
                list.add(instance);
            }
        } catch (ServiceConfigurationError serviceError) {
            serviceError.printStackTrace();
        } catch (NullPointerException ignore) {
        }

        return list;
    }
}



