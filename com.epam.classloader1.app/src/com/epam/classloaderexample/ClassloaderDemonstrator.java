package com.epam.classloaderexample;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import com.epam.classloaderexample.classloader.CustomClassloader;

public class ClassloaderDemonstrator {

	private static final String CLASS_NAME = "com.epam.mentoring.lessone.Semaphore";
	private static final String DENONSTRATION_MSG = "Custom Classloader demonstration";
	private static final String CASE1_MSG = "Case 1: Program loads really existing class from directory 1";
	private static final String CASE2_MSG = "Case 2: Program loads really existing class from directory 2(another version of class)";
	private static final String CASE3_MSG = "Case 3: Program tries to load class, but directory does not contain such class. ClassNotFound exception will occur.";
	private static final String EXECUTED_METHOD_NAME = "lever";
	private static final String CORRECT_URL_1 = "file:/C:/TMP/correctUrl1/";
	private static final String CORRECT_URL_2 = "file:/C:/TMP/correctUrl2/";
	private static final String INCORRECT_URL = "file:/C:/TMP/incorrectUrl/";

	/**
	 * 
	 * 1. Method demonstrate work of class loading and execution of methog from
	 * loaded class(Task 1)
	 * 
	 * 2. Reload class at runtime(Task 2). For convenience I provide at this
	 * method possible case for task 2: 
	 * 2.1 Program loads really existing class
	 * from directory 1; 
	 * 2.2 Program loads really existing class from directory
	 *  2(another version of class); 
	 * 2.3 Program tries to load class, but
	 * directory does not contain such class. ClassNotFound exception will
	 * occur.
	 * 
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static void demonstrateClassloaderWork() {
		System.out.println(DENONSTRATION_MSG);
		URL url;
		Class cls = null;
		CustomClassloader classLoader = null;
		try {
			// case 2.1
			url = new URL(CORRECT_URL_1);
			demonstrateClassLoading(url, classLoader, cls, CASE1_MSG);

			// case 2.1
			url = new URL(CORRECT_URL_2);
			demonstrateClassLoading(url, classLoader, cls, CASE2_MSG);

			// 2.3
			url = new URL(INCORRECT_URL);
			demonstrateClassLoading(url, classLoader, cls, CASE3_MSG);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Demonstrates class loading process and execution of method at loaded
	 * class.
	 * 
	 * @param url
	 * @param classLoader
	 */
	private static void demonstrateClassLoading(final URL url,
			CustomClassloader classLoader, Class cls, final String msg) {
		System.out.println('\n' + msg);
		cls = loadClass(url, classLoader);
		if (cls == null) {
			return;
		}
		executMethod(cls);
	}

	/**
	 * Method loads class by provided URL
	 * 
	 * @param url
	 * @param classLoader
	 * @return
	 */
	private static Class loadClass(final URL url, CustomClassloader classLoader) {
		final URL[] paramArrayOfURL = { url };
		classLoader = new CustomClassloader(paramArrayOfURL);
		Class cls;
		try {
			cls = classLoader.loadClass(CLASS_NAME);
			return cls;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method execute method from loaded class. Reflection is used here.
	 * 
	 * @param cls
	 */
	private static void executMethod(final Class cls) {

		Constructor<?> constructor;
		try {
			// Create a new instance from the loaded class
			constructor = cls.getConstructor();
			Object semaphore = constructor.newInstance();

			// Getting a method from the loaded class and invoke it
			Method method = cls.getMethod(EXECUTED_METHOD_NAME);
			method.invoke(semaphore);

		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
