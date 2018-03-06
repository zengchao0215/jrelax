package com.jrelax.kit;


import org.apache.commons.beanutils.BeanUtils;

import java.io.*;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * ObjectKit<br>
 * <ol>
 * <li>isEmpty(); isNotEmpty();
 * <li>toString();
 * <li>equals();
 * <li>clone();
 * </ol>
 *
 * @author xiaocao000
 *         Date：2009-3-14
 * @version 1.0
 */
public class ObjectKit {
    private static final String NULL = "null";
    private static final String EMPTY_STR = "";

    /**
     * 定义toString时忽略的属性名称字符串集合
     */
    private static final String[] IGNORE_FIELD_NAMES = {"log", "logger",
            "serialVersionUID", // 实现序列化接口时自动添加的字段
    };

    /**
     * 定义toString时忽略的属性类名字符串集合
     */
    private static final String[] IGNORE_FIELD_CLASSES = {
            "org.apache.log4j.Logger",
            "org.slf4j.Logger",
            "org.slf4j.LoggerFactory",
            "java.kit.logging.Logger", // 日志记录器声明的属性
            "java.lang.Object", "java.lang.Class",
            "java.lang.reflect.AccessibleObject", "java.lang.reflect.Field",
            "java.lang.reflect.Method", "java.lang.reflect.Constructor",};

    // =====================================================
    // isNull(); isNotNull();
    // isEmpty(); isNotEmpty();
    // =====================================================

    /**
     * 判断指定的对象是否为null。
     *
     * @param obj 要判断的对象实例
     * @return true：为null false：非null
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * 判断指定的对象是否不为null。
     *
     * @param obj 要判断的对象实例
     * @return true：非null false：为null
     */
    public static boolean isNotNull(Object obj) {
        return !isNull(obj);
    }

    /**
     * 判断指定的对象数组是否为空。
     *
     * @param objs 要判断的对象数组实例
     * @return true：对象数组为空 false：对象数组非空
     */
    public static boolean isEmpty(Object[] objs) {
        return isNull(objs) || objs.length == 0;
    }

    /**
     * 判断指定的对象数组是否非空。
     *
     * @param objs 要判断的对象数组实例
     * @return true：对象数组非空 false：对象数组为空
     */
    public static boolean isNotEmpty(Object[] objs) {
        return !isEmpty(objs);
    }

    /**
     * 判断指定的集合类是否为空。<BR>
     * 为空的含义是指：该集合为null，或者该集合不包含任何元素。
     *
     * @param coll 要判断的集合实例
     * @return true：集合为空 false：集合非空
     */
    public static boolean isEmpty(Collection<?> coll) {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * 判断指定的集合类是否非空。 <BR>
     * 非空的含义是指：该集合非null并且该集合包含元素。
     *
     * @param coll 要判断的集合实例
     * @return true：集合非空 false：集合为空
     */
    public static boolean isNotEmpty(Collection<?> coll) {
        return !isEmpty(coll);
    }

    /**
     * 判断指定的Map类是否为空。 <BR>
     * 为空的含义是指：该Map为null，或者该Map不包含任何元素。
     *
     * @param map 要判断的Map实例
     * @return true：Map为空 false：Map非空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    /**
     * 判断指定的Map类是否非空。<BR>
     * 非空的含义是指：该Map非null并且该Map包含元素。
     *
     * @param map 要判断的Map实例
     * @return true：Map非空 false：Map为空
     */
    public static boolean isNotEmpty(Map<?, ?> map) {
        return !isEmpty(map);
    }

    /**
     * 判断指定的CharSequence类是否为空。 <BR>
     * 为空的含义是指：该CharSequence为null，或者该CharSequence长度为0。<BR>
     * 对String类型，虽然也属于CharSequence，<BR>
     * 但因为通常字符串都有特殊的处理要求，所以使用方法isEmpty(String str);
     *
     * @param charSeq 要判断的CharSequence实例
     * @return true：CharSequence为空 false：CharSequence非空
     */
    public static boolean isEmpty(CharSequence charSeq) {
        return isNull(charSeq) || charSeq.length() == 0;
    }

    /**
     * 判断指定的CharSequence类是否非空。 <BR>
     * 非空的含义是指：该CharSequence非null并且该CharSequence包含元素。<BR>
     * 对String类型，虽然也属于CharSequence， <BR>
     * 但因为通常字符串都有特殊的处理要求，所以使用方法isNotEmpty(String str);
     *
     * @param charSeq 要判断的CharSequence实例
     * @return true：CharSequence非空 false：CharSequence为空
     */
    public static boolean isNotEmpty(CharSequence charSeq) {
        return !isEmpty(charSeq);
    }

    /**
     * 判断指定的String类是否为空。 <BR>
     * 为空的含义是指：该String为null，或者该String为空串""。
     *
     * @param str 要判断的String实例
     * @return true：String为空 false：String非空
     */
    public static boolean isEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str);
    }

    /**
     * 判断指定的String类是否非空。 <BR>
     * 非空的含义是指：该String非null并且该String不为空串。
     *
     * @param str 要判断的String实例
     * @return true：String非空 false：String为空
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 判断指定的String类是否为空。<BR>
     * 为空的含义是指：该String为null，或者该String为空串""。
     *
     * @param str 要判断的String实例
     * @return true：String为空 false：String非空
     */
    public static boolean isTrimEmpty(String str) {
        return isNull(str) || EMPTY_STR.equals(str.trim());
    }

    /**
     * 判断指定的String类是否非空。 <BR>
     * 非空的含义是指：该String非null并且该String不为空串。
     *
     * @param str 要判断的String实例
     * @return true：String非空 false：String为空
     */
    public static boolean isTrimNotEmpty(String str) {
        return !isTrimEmpty(str);
    }

    // =============================================================
    // toString()
    // 1. Java 基本型别，包括基本对象型别 直接调用对象本身的toString()
    // 2. 简单的Bean类型 采用反射机制取得每个属性的值
    // 3. Java集合类型 如：Collection，Map以及对象数组
    // 4. 非Java基本型别的对象类型作为属性
    // 5. enum处理：能正确调用object.toString()，归类为Java基本类型
    // 6. 继承关系处理：先取得所有父类，再取得所有属性 递归toString
    // 7. 内部类处理：内部类包含了一个对外部类的默认引用，其名称会包含"this$",忽略即可
    // =============================================================

    /**
     * 通用的toString方法。 <BR>
     * 1. 以对象形式存在的基本型别数组，如：byte[],long[]需要特殊处理。<BR>
     *
     * @param obj 要转化为字符串的对象
     * @return 转化后的字符串
     */
    public static String toString(Object obj) {
        if (isNull(obj)) {
            return NULL;
        }

        Class<?> clazz = obj.getClass();
        if (clazz.equals(Object.class) || clazz.equals(Class.class)) {
            return obj.toString();
        }

        // 如果是Java基本型别 可以直接调用toString得到正确的信息
        if (ClassKit.isJavaBasicType(clazz)) {
            return obj.toString();
        }

        StringBuilder sb = new StringBuilder();

        // 如果是数组类
        if (clazz.isArray()) {
            // Java基本型别的数组，可以利用Arrays.toString()完成转化，
            // 这里转为对象数组再用deepToString()，只是为了添加附加信息和减少代码
            if (ClassKit.isPrimitiveArray(clazz)) {
                Object[] objs = new Object[]{obj};
                sb.append(clazz.getSimpleName()).append("=");
                String objsStr = Arrays.deepToString(objs);
                sb.append(objsStr.substring(1, objsStr.length() - 1));
                return sb.toString();
            }

            // 基本类型的数组已经处理，这里只可能是对象数组。
            // 将基本型别数组与对象数字分开处理的原因是：
            // 基本型别数组可以利用Arrays.deepToString(Object[] objs)转为String
            // 但对象数组中的对象没有声明toSring 方法时也需要deepToString，
            // Arrays.deepToString方法就无能为力了
            return toString((Object[]) obj);
        }

        // 如果是集合类Collection
        if (Collection.class.isAssignableFrom(clazz)) {
            return toString((Collection<?>) obj);
        }

        // 如果是映射类Map
        if (Map.class.isAssignableFrom(clazz)) {
            return toString((Map<?, ?>) obj);
        }

        // 到这里，是Bean类型的对象，反射每个字段toString
        sb.append(clazz.getSimpleName()).append("{");
        Field[] fields = ClassKit.getAllField(clazz);

        if (fields.length > 0) {
            boolean isAppend = false;
            AccessibleObject.setAccessible(fields, true);
            for (Field field : fields) {
                // 需要忽略的属性
                if (isIgnoreField(field)) {
                    continue;
                }
                isAppend = true;
                sb.append(field.getName()).append("=");
                try {
                    // 这里如果对每一个属性都调用ObjectUtil.toString()
                    // 对内部类，会造成堆栈溢出，需要解决
                    sb.append(toString(field.get(obj))).append(",");
                } catch (Exception e) {
                    sb.append("???,");
                }
            }
            if (isAppend) {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        sb.append("}");
        return sb.toString();
    }

    public static <T> void addAll(List<T> list, T[] array) {
        if (isNull(list) || isNull(array)) {
            return;
        }

        for (T t : array) {
            list.add(t);
        }
    }

    /**
     * 判断属性字段是否为toString时忽略的字段属性。
     *
     * @param field 需要判断是否忽略属性的字段。
     * @return true：为忽略属性 false：非忽略属性
     */
    public static boolean isIgnoreField(Field field) {
        if (isNull(field)) {
            return false;
        }

        // 根据名称判断总归比根据类名判断要快
        return isIgnoreFieldByName(field) || isIgnoreFieldByClass(field);
    }

    /**
     * 根据字段的Name判断是否该字段为已定义的忽略属性
     *
     * @param field 需要判断是否忽略属性的字段。
     * @return true：为忽略属性 false：非忽略属性
     */
    private static boolean isIgnoreFieldByName(Field field) {
        // 如果是已定义的需要忽略的属性
        for (String fieldName : IGNORE_FIELD_NAMES) {
            if (fieldName.equals(field.getName())) {
                return true;
            }
        }

        // 如果是需要模糊匹配需要忽略的属性
        // 说明：如果一个类定义了内部类，内部类保留一个对外部类的默认引用
        // 这会导致递归toString时堆栈溢出，而这个默认引用又不需要toString，所以忽略
        return field.getName().indexOf("this$") != -1;

    }

    /**
     * 根据字段的Class判断是否该字段为已定义的忽略属性
     *
     * @param field 需要判断是否忽略属性的字段。
     * @return true：为忽略属性 false：非忽略属性
     */
    private static boolean isIgnoreFieldByClass(Field field) {
        Class<?> clazz = null;
        for (String className : IGNORE_FIELD_CLASSES) {
            clazz = ReflectKit.forName(className);
            if (clazz != null && clazz.isAssignableFrom(field.getType())) {
                return true;
            }
        }

        return false;
    }

    /**
     * 将对象数组转换为String。
     *
     * @param objs 对象数组
     * @return 转换后的字符串
     */
    public static String toString(Object[] objs) {
        if (isNull(objs)) {
            return NULL;
        }
        if (objs.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(objs.getClass().getSimpleName()).append("(length=").append(
                objs.length).append(")=[");
        for (Object obj : objs) {
            sb.append(toString(obj)).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(']');

        return sb.toString();
    }

    /**
     * 将Collection转换为String。
     *
     * @param coll Collection
     * @return 转换后的字符串
     */
    public static String toString(Collection<?> coll) {
        if (isNull(coll)) {
            return NULL;
        }
        if (coll.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(coll.getClass().getSimpleName()).append("(size=").append(
                coll.size()).append(")={");
        for (Object obj : coll) {
            sb.append(toString(obj)).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');

        return sb.toString();
    }

    /**
     * 将Map转换为String。
     *
     * @param map Map
     * @return 转换后的字符串
     */
    public static String toString(Map<?, ?> map) {
        if (isNull(map)) {
            return NULL;
        }
        if (map.isEmpty()) {
            return "{}";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(map.getClass().getSimpleName());
        sb.append("(size=").append(map.size()).append(")={");
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            sb.append(toString(entry.getKey())).append('=');
            sb.append(toString(entry.getValue())).append(',');
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append('}');

        return sb.toString();
    }
    // =============================================================
    // equals()
    // 1. Java 基本型别，包括基本对象型别 直接调用对象本身的equals()
    // 2. 简单的Bean类型 采用反射机制比较每个属性的equals
    // 3. Java集合类型 如：Collection，Map以及对象数组
    // 4. 非Java基本型别的对象类型作为属性如何处理
    // 5. enum类型
    // 6. 内部类如何处理
    // =============================================================

    /**
     * 通用的equals 方法
     *
     * @param a 对象a
     * @param b 对象b
     * @return true 两个对象值相等 false 两个对象值不相等
     */
    public static boolean equals(Object a, Object b) {
        // 如果任何一个对象为null，认为值不等。
        if (isNull(a) || isNull(b)) {
            return false;
        }

        // 如果是同一个对象，认为值相等。
        if (a == b) {
            return true;
        }

        Class<?> aClazz = a.getClass();

        // 如果两个对象不是同一型别，认为值不等。
        if (!aClazz.equals(b.getClass())) {
            return false;
        }

        // 以下开始，认为两个对象是相同型别的对象
        // 是Java基本类型的，可以直接equals
        if (ClassKit.isJavaBasicType(aClazz)) {
            return a.equals(b);
        }

        // 如果是数组类
        if (aClazz.isArray()) {
            // Java基本型别的数组，可以利用Arrays.toString()完成转化，
            // 这里转为对象数组再用deepToString()，只是为了添加附加信息和减少代码
            if (ClassKit.isPrimitiveArray(aClazz)
                    || ClassKit.isPrimitiveWrapperArray(aClazz)) {
                return Arrays.deepEquals(new Object[]{a}, new Object[]{b});
            }

            // 基本类型的数组已经处理，这里只可能是对象数组。
            // 将基本型别数组与对象数字分开处理的原因是：
            // 基本型别数组可以利用Arrays.deepEquals()
            // 但对象数组中的对象没有声明equals 方法时也需要deepEquals，
            // Arrays.deepEquals方法就无能为力了
            return equals((Object[]) a, (Object[]) b);
        }

        // 如果是集合类
        if (ClassKit.isCollection(aClazz)) {
            return equals((Collection<?>) a, (Collection<?>) b);
        }

        // 如果是映射类
        if (ClassKit.isMap(aClazz)) {
            return equals((Map<?, ?>) a, (Map<?, ?>) b);
        }

        // 到这里认为是基本Bean类型，比较每一个属性。
        try {
            Field[] fields = aClazz.getDeclaredFields();

            // 没有声明属性
            if (isEmpty(fields)) {
                return true;
            }

            AccessibleObject.setAccessible(fields, true);
            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];

                if (!equals(field.get(a), field.get(b))) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * 通用的equals 方法
     *
     * @param a 对象数组a
     * @param b 对象数组b
     * @return true 两个对象数组值相等 false 两个对象数组值不相等
     */
    public static boolean equals(Object[] a, Object[] b) {
        if (isNull(a) || isNull(b)) {
            return false;
        }

        // 去掉这段代码，不区分对象数组具体类型，只比较值
        if (!a.getClass().equals(b.getClass())) {
            return false;
        }

        if (a.length != b.length) {
            return false;
        }

        for (int i = 0; i < a.length; i++) {
            if (!equals(a[i], b[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * 通用的equals 方法
     *
     * @param a Collection a
     * @param b Collection b
     * @return true 两个Collection值相等 false 两个Collection值不相等
     */
    public static boolean equals(Collection<?> a, Collection<?> b) {
        if (isNull(a) || isNull(b)) {
            return false;
        }

        // 去掉这段代码，不区分集合具体子类型，只比较值
        if (!a.getClass().equals(b.getClass())) {
            return false;
        }

        if (a.size() != b.size()) {
            return false;
        }

        return equals(a.toArray(), b.toArray());
    }

    /**
     * 通用的equals 方法
     *
     * @param a Map a
     * @param b Map b
     * @return true 两个Map值相等 false 两个Map值不相等
     */
    public static boolean equals(Map<?, ?> a, Map<?, ?> b) {
        if (isNull(a) || isNull(b)) {
            return false;
        }

        // 去掉这段代码，不区分映射具体子类型，只比较值
        if (!a.getClass().equals(b.getClass())) {
            return false;
        }

        if (a.size() != b.size()) {
            return false;
        }

        for (Map.Entry<?, ?> entry : a.entrySet()) {
            if (!b.containsKey(entry.getKey())
                    || !equals(entry.getValue(), b.get(entry.getKey()))) {
                return false;
            }
        }

        return true;
    }

    /**
     * clone方法。需要克隆的对象必须实现Serializable接口
     *
     * @param obj 需要克隆的对象。
     * @return 克隆的对象。
     */
    public static <T> T clone(T obj) {
        if (isNull(obj)) {
            return null;
        }

        T cloneObj = null;
        if (obj instanceof Serializable) {
            ByteArrayOutputStream baos = null;
            ObjectOutputStream oos = null;
            ObjectInputStream ois = null;

            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(obj);
                ois = new ObjectInputStream(new ByteArrayInputStream(baos
                        .toByteArray()));

                cloneObj = (T) ois.readObject();
            } catch (Exception e) {
                return cloneObj;
            } finally {
                try {
                    if (baos != null)
                        baos.close();
                    if (oos != null)
                        oos.close();
                    if (ois != null)
                        ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return cloneObj;
    }

    /**
     * 对比两个对象中的一些字段值是否相同
     * 字段值完全相同返回True
     * 有一个或者多个字段值不相同返回false
     *
     * @param obj1
     * @param obj2
     * @param properties
     * @return
     */
    public static boolean equalProperty(Object obj1, Object obj2, String[] properties) {
        int equalCount = 0;
        if (properties != null) {
            for (String prop : properties) {
                Object val1 = ReflectKit.getFieldValue(obj1, prop);
                Object val2 = ReflectKit.getFieldValue(obj2, prop);
                if (val1 != null && val2 != null) {
                    if (val1 instanceof Enum) {//枚举类型用等号判断
                        if (val1 == val2) {
                            equalCount++;
                        }
                    } else if (val1.equals(val2)) {
                        equalCount++;
                    }
                } else if (val1 == null && val2 == null) {
                    equalCount++;
                }
            }
            if (equalCount == properties.length) {
                return true;
            }
        }
        return false;
    }

    /**
     * Map转换成Object
     *
     * @param clazz
     * @param map
     */
    public static <M> M mapToObject(Class<M> clazz, Map<String, Object> map) {
        try {
            M m = clazz.newInstance();
            BeanUtils.populate(m, map);

            return m;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转换成对象集合
     *
     * @param clazz
     * @param listMap
     * @return
     */
    public static <M> List<M> mapToList(Class<M> clazz, List<Map<String, Object>> listMap) {
        List<M> list = new ArrayList<>();

        for (Map<String, Object> map : listMap) {
            list.add(mapToObject(clazz, map));
        }

        return list;
    }
}
