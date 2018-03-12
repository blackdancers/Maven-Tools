package com.github.fishbaby.maven;

import java.io.UnsupportedEncodingException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class PropertyHelper {

	private ResourceBundle propBundle;

	public PropertyHelper(String bundle) {
		propBundle = PropertyResourceBundle.getBundle(bundle);
	}

	public String getValue(String key) {
		return this.get(key);
	}
	public String get(String key) {
        String str = "";
        try {
            str = new String(this.propBundle.getString(key).getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

}
