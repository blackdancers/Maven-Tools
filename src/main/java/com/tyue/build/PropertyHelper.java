package com.tyue.build;

import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyHelper {

	private ResourceBundle propBundle;

	public PropertyHelper(String bundle) {
		propBundle = PropertyResourceBundle.getBundle(bundle,new Locale("zh","CN"));
	}

	public String getValue(String key) {
		return this.propBundle.getString(key);
	}

}
