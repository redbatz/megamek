/*
 * MegaMek - Copyright (C) 2005 Ben Mazur (bmazur@sev.org)
 * 
 *  This program is free software; you can redistribute it and/or modify it 
 *  under the terms of the GNU General Public License as published by the Free 
 *  Software Foundation; either version 2 of the License, or (at your option) 
 *  any later version.
 * 
 *  This program is distributed in the hope that it will be useful, but 
 *  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 *  or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License 
 *  for more details.
 */

package org.redbat.roguetech.megamek.common;

import org.redbat.roguetech.megamek.common.preference.PreferenceManager;
import org.redbat.roguetech.megamek.common.util.EncodeControl;

import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {

    private static final String BUNDLE_NAME = "megamek.common.messages";//$NON-NLS-1$

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME, PreferenceManager.getClientPreferences()
                    .getLocale(), new EncodeControl());

    private Messages() {
    }

    /**
     * Returns the translated value given the key for that value in the
     * common Resource Bundle directory.
     *
     * @param key
     * @return
     */
    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
    /**
     * Returns true if the resource bundle has a translated value for the given key.
     *
     * @param key
     * @return
     */
    public static boolean hasString(String key) {
        return RESOURCE_BUNDLE.containsKey(key);
    }

    /**
     * Returns the formatted message for the given key in the resource bundle.
     * 
     * @param key the resource name
     * @param args the message arguments
     * @return the string
     */
    public static String getString(String key, Object[] args) {
        return MessageFormat.format(getString(key), args);
    }

}