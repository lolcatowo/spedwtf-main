// 
// Decompiled by Procyon v0.5.36
// 

package me.alpha432.oyvey.settings;

import me.alpha432.oyvey.util.EnumConverter;
import java.util.function.Predicate;
import me.alpha432.oyvey.features.modules.Module;

public class Setting<T>
{
    public String name;
    public String[] alias;
    public String desc;
    public Module module;
    private Predicate<T> shown;
    private String type;
    public T defaultValue;
    public T value;
    public T min;
    public T max;
    
    public void setValue(final T value) {
        this.value = value;
    }
    
    public Setting(final String name, final String[] alias, final String desc) {
        this.name = name;
        this.alias = alias;
        this.desc = desc;
    }
    
    public Setting(final String name, final String[] alias, final String desc, final T value) {
        this(name, alias, desc);
        this.value = value;
        this.defaultValue = value;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final String[] alias, final String desc, final T value, final T min, final T max) {
        this(name, alias, desc, value);
        this.min = min;
        this.max = max;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final String desc, final T value) {
        this(name, new String[0], desc);
        this.value = value;
        this.defaultValue = value;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final String desc, final T value, final T min, final T max) {
        this(name, new String[0], desc, value);
        this.min = min;
        this.max = max;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final T value) {
        this(name, new String[0], "");
        this.value = value;
        this.defaultValue = value;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final T value, final T min, final T max) {
        this(name, new String[0], "", value);
        this.min = min;
        this.max = max;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final T value, final T min, final T max, final Predicate<T> shown) {
        this(name, new String[0], "", value);
        this.min = min;
        this.max = max;
        this.shown = shown;
        this.type = value.getClass().toString();
    }
    
    public Setting(final String name, final T value, final Predicate<T> shown) {
        this(name, new String[0], "", value);
        this.shown = shown;
        this.type = value.getClass().toString();
    }
    
    public <T> T clamp(final T value, final T min, final T max) {
        return (((Comparable)value).compareTo(min) < 0) ? min : ((((Comparable)value).compareTo(max) > 0) ? max : value);
    }
    
    public T getValue() {
        return this.value;
    }
    
    public String getType() {
        return this.type;
    }
    
    public String getMeta() {
        return (this.value == null) ? "NULL" : this.value.toString();
    }
    
    public boolean getShown() {
        return this.shown != null && !this.shown.test(this.getValue());
    }
    
    public Object parse(final String string) {
        if (this.value instanceof Number && !(this.value instanceof Enum)) {
            if (this.value instanceof Integer) {
                return Integer.parseInt(string);
            }
            if (this.value instanceof Float) {
                return Float.parseFloat(string);
            }
            if (this.value instanceof Double) {
                return Double.parseDouble(string);
            }
            if (this.value instanceof Long) {
                return Long.parseLong(string);
            }
        }
        else {
            if (this.value instanceof Boolean) {
                return Boolean.parseBoolean(string);
            }
            if (this.value instanceof Enum) {
                final EnumConverter converter = new EnumConverter(((Enum)this.value).getClass());
                return converter.doBackward(string);
            }
            if (this.value instanceof String) {
                return this.value;
            }
        }
        return null;
    }
}
