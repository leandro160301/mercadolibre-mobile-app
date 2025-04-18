package com.jws.jwsapi.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.BatteryManager;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Utils {

    @SuppressWarnings("all")
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    public static boolean isLong(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            long l = Long.parseLong(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static String format(String weight, int decimals) {
        String format = "0.";
        StringBuilder capacidadBuilder = new StringBuilder(format);
        for (int i = 0; i < decimals; i++) {
            capacidadBuilder.append("0");
        }
        format = capacidadBuilder.toString();
        DecimalFormat df = new DecimalFormat(format);

        double pesoNumero = Double.parseDouble(weight);
        return df.format(pesoNumero);
    }


    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap;
        if (drawable instanceof BitmapDrawable bitmapDrawable) {
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static int randomNumber() {
        int min = 1000;
        int max = 9999;
        return (int) Math.floor(Math.random() * (max - min + 1) + min);
    }

    @SuppressWarnings("unused")
    public static String pointDecimalFormat(String number, int decimal) {
        try {
            Double.parseDouble(number);
        } catch (NumberFormatException e) {
            return "0000";
        }
        StringBuilder format = new StringBuilder("0");
        if (decimal > 0) {
            format.append(".");
            for (int i = 0; i < decimal; i++) {
                format.append("0");
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat(format.toString());
        return decimalFormat.format(Double.parseDouble(number));
    }

    @SuppressWarnings("unused")
    public static int indexOfArray(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    @SuppressWarnings("unused")
    public static int calculatePercentage(int currentValue, int targetValue) {
        if (targetValue == 0) {
            return 0;
        }
        return (int) ((currentValue * 100.0) / targetValue);
    }


    /**
     * Calculates the result of a simple rule of three.
     * reference  ->  total
     * value      ->  result
     * Formula:
     * result = (value * total) / reference
     *
     * @param total     The known total value (top-right of the box).
     * @param reference The known reference value (top-left of the box).
     * @param value     The value to compare or scale (bottom-left of the box).
     * @return The result (bottom-right of the box).
     */
    @SuppressWarnings("unused")
    public static double ruleOfThree(double total, double reference, double value) {
        if (reference == 0) {
            return 0;
        }
        return (value * total) / reference;
    }

    @SuppressWarnings("all")
    public static int indexOf(String[] array, String target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(target)) {
                return i;
            }
        }
        return -1;
    }

    public static void getBatteryInfo(Context context) {
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);

        if (batteryStatus != null) {
            int voltage = batteryStatus.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1);
            int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            float batteryPct = level / (float) scale * 100;

            System.out.println("Voltaje: " + voltage + " mV");
            System.out.println("Nivel de batería: " + batteryPct + "%");

            Log.d("BatteryInfo", "Voltaje: " + voltage + " mV");
            Log.d("BatteryInfo", "Nivel de batería: " + batteryPct + "%");
        }
    }

    public static void getBatteryCurrent(Context context) {
        BatteryManager batteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);

        if (batteryManager != null) {
            int currentNow = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW);

            System.out.println("Corriente: " + currentNow + " µA");
            Log.d("BatteryCurrent", "Corriente: " + currentNow + " µA");
        } else {
            Log.d("BatteryCurrent", "BatteryManager no está disponible.");
        }
    }

    public static <E extends Enum<E>> List<E> convertToEnumList(List<String> stringList, Class<E> enumClass) {
        List<E> enumList = new ArrayList<>();
        for (String name : stringList) {
            try {
                E enumValue = Enum.valueOf(enumClass, name);
                enumList.add(enumValue);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
        return enumList;
    }

}