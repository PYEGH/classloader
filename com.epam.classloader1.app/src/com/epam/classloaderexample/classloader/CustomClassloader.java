package com.epam.classloaderexample.classloader;

import java.net.URL;
import java.net.URLClassLoader;

/**
 * Custom class loader on base of URLClassloader
 * 
 * @author Pavel
 * 
 */
public class CustomClassloader extends URLClassLoader {

	private static URL[] paramArrayOfURL;

	public CustomClassloader(URL[] paramArrayOfURL) {
		super(paramArrayOfURL);
		this.paramArrayOfURL = paramArrayOfURL;
	}

	@Override
	protected synchronized Class<?> loadClass(String name, boolean resolve) {
		try {
			System.out.println("Status: Loading of class in progress... " + this.paramArrayOfURL[0]);
			Class<?> loaded = super.findLoadedClass(name);
			if (loaded != null) {
				System.out.println("Status: Success!");
				return loaded;
			}
			Class<?> founfClass = super.findClass(name);
			System.out.println("Status: Success!");
			return founfClass;
		} catch (ClassNotFoundException e) {
			try {
				Class<?> lodedClass = super.loadClass(name, resolve);
				System.out.println("Status: Success!");
				return lodedClass;
			} catch (ClassNotFoundException e1) {
				System.out.println("Status: Error!" + '\n'
						+ "Description of error: " + '\n' + e1.toString());
				return null;
			}
		}
	}
}
