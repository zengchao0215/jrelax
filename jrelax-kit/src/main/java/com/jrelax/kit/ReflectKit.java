package com.jrelax.kit;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectKit {
    /**
     * 获取字段值
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        int idx = fieldName.indexOf(".");
        if (idx > 0) {//实体类型字段映射
            String f1 = fieldName.substring(0, idx);
            String f2 = fieldName.substring(idx + 1);

            Object entity = ReflectKit.getFieldValue(obj, f1);
            if (ObjectKit.isNull(entity)) {
                return null;
            }
            if (f2.indexOf(".") > 0)
                return getFieldValue(entity, f2);//递归
            else
                return getFieldValue(entity, f2);
        } else {
            Object value = null;
            try {
                Field field = obj.getClass().getDeclaredField(fieldName);
                if (field != null) {
                    field.setAccessible(true);
                    value = field.get(obj);
                }
            } catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
            return value;
        }
    }

    /**
     * 字段名不区分大小写
     * 性能比getFieldValue低
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValueNoCaseSensitive(Object obj, String fieldName) {
        Object value = null;
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            Field f = null;
            for (Field f1 : fields) {
                if (f1.getName().toLowerCase().equals(fieldName.toLowerCase())) {
                    f = f1;
                    break;
                }
            }
            if (f != null)
                value = getFieldValue(obj, f.getName());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 获取字段类型
     *
     * @param obj
     * @param fieldName
     * @return
     */
    public static Class getFieldType(Object obj, String fieldName) {
        Class clazz = null;
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            if (field != null) {
                field.setAccessible(true);
                clazz = field.getType();
            }
        } catch (SecurityException | NoSuchFieldException | IllegalArgumentException e) {
            e.printStackTrace();
        }
        return clazz;
    }


    /**
     * 设置字段值
     *
     * @param obj        实例对象
     * @param fieldName  对象属性，如属性中包含”.“ 表示这是一个循环嵌套属性， 会一直往下定位到最终对象的字段（支持无限级）
     * @param fieldValue 属性值
     */
    public static void setFieldValue(Object obj, String fieldName, Object fieldValue) throws IllegalAccessException {
        int idx = fieldName.indexOf(".");
        if (idx > 0) {//实体类型字段映射
            String f1 = fieldName.substring(0, idx);
            String f2 = fieldName.substring(idx + 1);

            Object entity = ReflectKit.getFieldValue(obj, f1);
            if (ObjectKit.isNull(entity)) {
                try {
                    Class clazz = ReflectKit.getFieldType(obj, f1);
                    entity = clazz.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (f2.indexOf(".") > 0)
                setFieldValue(entity, f2, fieldValue);//递归
            else
                setFieldValue2(entity, f2, fieldValue);
            setFieldValue(obj, f1, entity);
        } else {
            setFieldValue2(obj, fieldName, fieldValue);
        }
    }

    private static void setFieldValue2(Object obj, String fieldName, Object fieldValue) throws IllegalAccessException {
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            if (fields != null) {
                for (Field f : fields) {
                    f.setAccessible(true);
                    if (f.getName().toLowerCase().equals(fieldName.trim().toLowerCase())) {
                        f.set(obj, fieldValue);
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }

    }

    public static Class<?> forName(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * 将Hibernate持久化对象中设置为延迟加载的属性移除代理 还原为真实对象
     *
     * @param m
     * @param noIncludePropertys 不移除的延迟加载属性
     */
    public static <M> M removeLazyProperty(M m, String... noIncludePropertys) {
        if (m instanceof List<?>) {// 如果是List集合
            List<?> list = (List<?>) m;
            for (Object obj : list) {
                if (ObjectKit.isNull(obj))
                    continue;
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(false);
                    if (!StringKit.hasStr(noIncludePropertys, f.getName())) {
                        Annotation a = f.getAnnotation(ManyToOne.class);
                        if (a == null)
                            a = f.getAnnotation(OneToMany.class);
                        if (a == null)
                            a = f.getAnnotation(ManyToMany.class);
                        if (a != null) {
                            try {
                                Method method = a.getClass().getDeclaredMethod(
                                        "fetch");
                                FetchType fetchType = (FetchType) method
                                        .invoke(a);
                                if (fetchType == FetchType.LAZY) {
                                    String fType = f.getGenericType()
                                            .toString();
                                    if (fType.startsWith("class")) {
                                        fType = fType.replace("class ", "");
                                        setFieldValue(obj, f.getName(), Class
                                                .forName(fType).newInstance());
                                    } else {
                                        if (fType.contains("java.kit.List")
                                                || fType.contains("java.kit.ArrayList"))
                                            setFieldValue(obj, f.getName(),
                                                    ArrayList.class
                                                            .newInstance());
                                        else if (fType.contains("java.kit.Set")
                                                || fType.contains("java.kit.HashSet"))
                                            setFieldValue(obj, f.getName(),
                                                    HashSet.class.newInstance());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else if (m instanceof Set<?>) {// 如果是Set集合
            Set<?> list = (Set<?>) m;
            for (Object obj : list) {
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field f : fields) {
                    f.setAccessible(false);
                    if (!StringKit.hasStr(noIncludePropertys, f.getName())) {
                        Annotation a = f.getAnnotation(ManyToOne.class);
                        if (a == null)
                            a = f.getAnnotation(OneToMany.class);
                        if (a == null)
                            a = f.getAnnotation(ManyToMany.class);
                        if (a != null) {
                            try {
                                Method method = a.getClass().getDeclaredMethod(
                                        "fetch");
                                FetchType fetchType = (FetchType) method
                                        .invoke(a);
                                if (fetchType == FetchType.LAZY) {
                                    String fType = f.getGenericType()
                                            .toString();
                                    if (fType.startsWith("class")) {
                                        fType = fType.replace("class ", "");
                                        setFieldValue(obj, f.getName(), Class
                                                .forName(fType).newInstance());
                                    } else {
                                        if (fType.contains("java.kit.List")
                                                || fType.contains("java.kit.ArrayList"))
                                            setFieldValue(obj, f.getName(),
                                                    ArrayList.class
                                                            .newInstance());
                                        else if (fType.contains("java.kit.Set")
                                                || fType.contains("java.kit.HashSet"))
                                            setFieldValue(obj, f.getName(),
                                                    HashSet.class.newInstance());
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } else {
            if (ObjectKit.isNull(m))
                return null;
            Class<?> clazz = m.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(false);
                if (!StringKit.hasStr(noIncludePropertys, f.getName())) {

                    Annotation a = f.getAnnotation(ManyToOne.class);
                    if (a == null)
                        a = f.getAnnotation(OneToMany.class);
                    if (a == null)
                        a = f.getAnnotation(ManyToMany.class);
                    if (a != null) {
                        try {
                            Method method = a.getClass().getDeclaredMethod(
                                    "fetch");
                            FetchType fetchType = (FetchType) method.invoke(a);
                            if (fetchType == FetchType.LAZY) {
                                String fType = f.getGenericType().toString();
                                if (fType.startsWith("class")) {
                                    fType = fType.replace("class ", "");
                                    setFieldValue(m, f.getName(), Class
                                            .forName(fType).newInstance());
                                } else {
                                    if (fType.contains("java.kit.List")
                                            || fType.contains("java.kit.ArrayList"))
                                        setFieldValue(m, f.getName(),
                                                ArrayList.class.newInstance());
                                    else if (fType.contains("java.kit.Set")
                                            || fType.contains("java.kit.HashSet"))
                                        setFieldValue(m, f.getName(),
                                                HashSet.class.newInstance());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return m;
    }
}
