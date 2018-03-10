package kjd.gametheory.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * ClassCopier provides methods specific to copying Classes. 
 * 
 * @author kendavidson
 *
 */
public class ObjectCopier {
	
	/**
	 * Provides copy functionality to all Objects within the package.  The class
	 * being copied requires a copy constructor - a constructor which takes 
	 * the same type and creates new Object based on the one provided.
	 * 
	 * @param toCopy	the copied Object or null if Null was provided
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Object> T copyOf(T toCopy) {
		if (toCopy == null) {
			return null;
		}
		
		Class<?> clazz = toCopy.getClass();
        Constructor<?> copyConstructor;
        T copy = null;
		try {
			copyConstructor = clazz.getConstructor(clazz);
			copy = (T) copyConstructor.newInstance(toCopy);
		} catch (NoSuchMethodException 
				| SecurityException 
				| InstantiationException 
				| IllegalAccessException 
				| IllegalArgumentException 
				| InvocationTargetException e) {
			throw new RuntimeException(String.format("%s(%s toCopy) has not been implemented.", 
					clazz.getSimpleName(), clazz.getSimpleName()));
		}

        return copy;
	}
	
}
