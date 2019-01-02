package com.fhh.technology.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.fhh.technology.base.Constant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * sharedPreference工具类
 * Created by fanhenghao
 */
public class SharedPreferenceUtils {

    private static SharedPreferenceUtils sharedPreferenceUtils;
    private final SharedPreferences mSp;
    private final SharedPreferences.Editor mEdit;
    private Context mContext;


    public static SharedPreferenceUtils getInstance(Context context) {
        if (sharedPreferenceUtils == null) {
            sharedPreferenceUtils = new SharedPreferenceUtils(context);
        }
        return sharedPreferenceUtils;
    }

    private SharedPreferenceUtils(Context context) {
        mContext = context;
        mSp = context.getSharedPreferences(Constant.SHAREPRE_NAME, Context.MODE_PRIVATE);
        mEdit = mSp.edit();
    }

    public void put(String key, Object objectValue) {
        if (objectValue instanceof String) {
            mEdit.putString(key, (String) objectValue);
        } else if (objectValue instanceof Integer) {
            mEdit.putInt(key, (Integer) objectValue);
        } else if (objectValue instanceof Boolean) {
            mEdit.putBoolean(key, (Boolean) objectValue);
        } else if (objectValue instanceof Float) {
            mEdit.putFloat(key, (Float) objectValue);
        } else if (objectValue instanceof Long) {
            mEdit.putLong(key, (Long) objectValue);
        }
        SharedPreferencesCompat.apply(mEdit);
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        mEdit.remove(key);
        SharedPreferencesCompat.apply(mEdit);
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mEdit.clear();
        SharedPreferencesCompat.apply(mEdit);
    }

    public long getLong(String key, long defValue) {
        return mSp.getLong(key, defValue);
    }

    public long getLong(String key) {
        return mSp.getLong(key, 0);
    }

    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    public String getString(String key) {
        return mSp.getString(key, "");

    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    public int getInt(String key) {
        return mSp.getInt(key, 0);
    }

    public float getFloat(String key, int defValue) {
        return mSp.getFloat(key, defValue);
    }

    public float getFloat(String key) {
        return mSp.getFloat(key, 0.0f);
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public boolean getBoolean(String key) {
        return mSp.getBoolean(key, false);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
