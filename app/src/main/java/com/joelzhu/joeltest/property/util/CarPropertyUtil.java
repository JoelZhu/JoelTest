package com.joelzhu.joeltest.property.util;

import android.car.VehiclePropertyType;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class CarPropertyUtil {
    public static String propertyValueToString(final int propertyId, final Object value) {
        return propertyValueToString(parseType(propertyId), value);
    }

    public static <Type> String propertyValueToString(final Class<Type> clazz, final Type value) {
        if (value == null) {
            return "";
        }

        if (clazz.isArray()) {
            return Arrays.toString((Object[]) value);
        } else {
            return value.toString();
        }
    }

    public static <Type> Class<Type> parseType(final int propertyId) {
        final Class type;
        switch (propertyId & VehiclePropertyType.MASK) {
            case VehiclePropertyType.STRING:
                type = String.class;
                break;
            case VehiclePropertyType.INT32:
                type = Integer.class;
                break;
            case VehiclePropertyType.INT32_VEC:
                type = Integer[].class;
                break;
            case VehiclePropertyType.FLOAT:
                type = Float.class;
                break;
            case VehiclePropertyType.FLOAT_VEC:
                type = Float[].class;
                break;
            case VehiclePropertyType.BOOLEAN:
                type = boolean.class;
                break;
            case VehiclePropertyType.MIXED:
                type = Object[].class;
                break;
            default:
                throw new IllegalArgumentException(
                        "Illegal property id: 0x" + Integer.toHexString(propertyId));
        }
        return type;
    }

    public static Object parseValue(final int property, final String valueString) {
        switch (property & VehiclePropertyType.MASK) {
            case VehiclePropertyType.STRING:
                return valueString;
            case VehiclePropertyType.INT32:
            case VehiclePropertyType.INT32_VEC:
                final ArrayList<Integer> integers = new ArrayList<>();
                final String[] splitIntegers = valueString.split(",");
                for (final String split : splitIntegers) {
                    integers.add(Integer.parseInt(split));
                }
                return integers.toArray(new Integer[0]);
            case VehiclePropertyType.FLOAT:
            case VehiclePropertyType.FLOAT_VEC:
                final ArrayList<Float> floats = new ArrayList<>();
                final String[] splitFloats = valueString.split(",");
                for (final String split : splitFloats) {
                    floats.add(Float.parseFloat(split));
                }
                return floats.toArray(new Float[0]);
            case VehiclePropertyType.BOOLEAN:
                return Boolean.valueOf(valueString);
            case VehiclePropertyType.MIXED:
                final ArrayList<Object> objects = new ArrayList<>();
                final String[] splitObjects = valueString.split(",");
                for (final String split : splitObjects) {
                    if (isInteger(split)) {
                        objects.add(Integer.parseInt(split));
                    } else if (isFloat(split)) {
                        objects.add(Float.parseFloat(split));
                    } else {
                        objects.add(split);
                    }
                }
                return objects.toArray();
            default:
                throw new IllegalArgumentException(
                        "Illegal value type of property: 0x" + Integer.toHexString(property));
        }
    }

    private static boolean isInteger(final String str) {
        if (str == null || str.equals("")) {
            return false;
        }

        final String regex = "^[-+]?[0-9]+$";
        return Pattern.matches(regex, str);
    }

    private static boolean isFloat(final String str) {
        if (str == null || str.equals("")) {
            return false;
        }

        final String regex =
                "^[-+]?([0-9]*\\.[0-9]+|[0-9]+\\.)([eE][-+]?[0-9]+)?$|^[-+]?[0-9]+[eE][-+]?[0-9]+$";
        return Pattern.matches(regex, str);
    }
}