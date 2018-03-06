package com.jrelax.kit;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author zengc
 * @version 1.0
 */
public class ClassKit {
    /**
     * 判断是否是Java的基础类
     *
     * @param clazz 需要判断的类
     * @return
     */
    public static boolean isJavaBasicType(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }
        return clazz.isPrimitive() || Number.class.isAssignableFrom(clazz)
                || Character.class.isAssignableFrom(clazz)
                || Boolean.class.isAssignableFrom(clazz)
                || CharSequence.class.isAssignableFrom(clazz)
                || Enum.class.isAssignableFrom(clazz)
                || Date.class.isAssignableFrom(clazz)
                || Calendar.class.isAssignableFrom(clazz);
    }

    /**
     * 判断是否是Java的基础类
     *
     * @param clazz
     * @return
     */
    public static boolean isPrimitive(Class<?> clazz) {
        return isJavaBasicType(clazz);
    }

    /**
     * 判断指定的类是否为Collection（或者其子类或者其子接口）。
     *
     * @param clazz 需要判断的类
     * @return true：是Collection false：非Collection
     */
    public static boolean isCollection(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return Collection.class.isAssignableFrom(clazz);
    }

    /**
     * 判断指定的类是否为Map（或者其子类或者其子接口)。
     *
     * @param clazz 需要判断的类
     * @return true：是Map false：非Map
     */
    public static boolean isMap(Class<?> clazz) {
        if (clazz == null) {
            return false;
        }

        return Map.class.isAssignableFrom(clazz);
    }

    /**
     * 判断指定的类是否为Java基本型别的数组。
     *
     * @param clazz 需要判断的类
     * @return true：是Java基本型别的数组 false：非Java基本型别的数组
     */
    public static boolean isPrimitiveArray(Class<?> clazz) {
        if (clazz == null)
            return false;

        if (clazz == byte[].class)
            return true;
        else if (clazz == short[].class)
            return true;
        else if (clazz == int[].class)
            return true;
        else if (clazz == long[].class)
            return true;
        else if (clazz == char[].class)
            return true;
        else if (clazz == float[].class)
            return true;
        else if (clazz == double[].class)
            return true;
        else // element is an array of object references
            return clazz == boolean[].class;
    }

    /**
     * 判断指定的类是否为Java基本型别的数组。
     *
     * @param clazz 需要判断的类
     * @return true：是Java基本型别的数组 false：非Java基本型别的数组
     */
    public static boolean isPrimitiveWrapperArray(Class<?> clazz) {
        if (clazz == null)
            return false;

        if (clazz == Byte[].class)
            return true;
        else if (clazz == Short[].class)
            return true;
        else if (clazz == Integer[].class)
            return true;
        else if (clazz == Long[].class)
            return true;
        else if (clazz == Character[].class)
            return true;
        else if (clazz == Float[].class)
            return true;
        else if (clazz == Double[].class)
            return true;
        else // element is an array of object references
            return clazz == Boolean[].class;
    }

    /**
     * 获取包括该类本身但不包含java.lang.Object的所有超类。
     *
     * @param clazz Class
     * @return 超类数组
     */
    public static Class<?>[] getAllClass(Class<?> clazz) {
        List<Class<?>> clazzList = new ArrayList<Class<?>>();
        getAllSupperClass0(clazzList, clazz);
        return clazzList.toArray(new Class<?>[]{});
    }

    private static void getAllSupperClass0(List<Class<?>> clazzList,
                                           Class<?> clazz) {
        if (clazz.equals(Object.class)) {
            return;
        }
        clazzList.add(clazz);
        getAllSupperClass0(clazzList, clazz.getSuperclass());
    }

    /**
     * 获取该类所有实现的接口数组。
     *
     * @param clazz Class
     * @return 该类所有实现的接口数组
     */
    public static Class<?>[] getAllInterface(Class<?> clazz) {
        List<Class<?>> clazzList = new ArrayList<Class<?>>();
        Class<?>[] interfaces = clazz.getInterfaces();

        for (Class<?> interfaceClazz : interfaces) {
            clazzList.add(interfaceClazz);
            getAllSupperInterface0(clazzList, interfaceClazz);
        }

        return clazzList.toArray(new Class<?>[]{});
    }

    private static void getAllSupperInterface0(List<Class<?>> clazzList,
                                               Class<?> clazz) {
        if (clazz.equals(Object.class)) {
            return;
        }

        Class<?>[] interfaces = clazz.getInterfaces();
        for (Class<?> interfaceClazz : interfaces) {
            clazzList.add(interfaceClazz);
            getAllSupperInterface0(clazzList, interfaceClazz);
        }
    }

    /**
     * 获取包括该类本身以及所有超类（不含java.lang.Object）和实现的接口中定义的属性。
     *
     * @param clazz Class
     * @return 属性数组
     */
    public static Field[] getAllField(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?>[] supperClazzs = ClassKit.getAllClass(clazz);
        for (Class<?> aClazz : supperClazzs) {
            ObjectKit.addAll(fieldList, aClazz.getDeclaredFields());
        }

        Class<?>[] supperInterfaces = ClassKit.getAllInterface(clazz);
        for (Class<?> aInterface : supperInterfaces) {
            ObjectKit.addAll(fieldList, aInterface.getDeclaredFields());
        }

        return fieldList.toArray(new Field[]{});
    }

    /**
     * 获取该类中所有字段
     *
     * @param clazz
     * @return
     */
    public static Field[] getFields(Class<?> clazz) {
        List<Field> fieldList = new ArrayList<Field>();
        ObjectKit.addAll(fieldList, clazz.getDeclaredFields());
        return fieldList.toArray(new Field[]{});
    }

    /**
     * 从包page中获取所有注解为clazz的Class
     *
     * @param pack
     * @param clazz
     * @return
     */
    public static Set<Class<?>> getClassesByAnnotation(String pack, Class<? extends Annotation> clazz) {
        Set<Class<?>> aClasses = new LinkedHashSet<Class<?>>();
        Set<Class<?>> classes = getClasses(pack);
        Iterator<Class<?>> iter = classes.iterator();
        while (iter.hasNext()) {
            Class<?> c = iter.next();
            if (c.isAnnotationPresent(clazz))
                aClasses.add(c);
        }
        return aClasses;
    }

    /**
     * 从包package中获取所有的Class
     *
     * @param pack
     * @return
     */
    public static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(
                    packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    getClassesByPackage(packageName, filePath, recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                // 如果是一个.class文件 而且不是目录
                                if (name.endsWith(".class")
                                        && !entry.isDirectory()) {
                                    // 去掉后面的".class" 获取真正的类名
                                    String className = name.substring(
                                            packageName.length() + 1, name
                                                    .length() - 6);
                                    try {
                                        // 添加到classes
                                        classes.add(Class
                                                .forName(packageName + '.'
                                                        + className));
                                    } catch (ClassNotFoundException e) {
                                        // log
                                        // .error("添加用户自定义视图类错误 找不到此类的.class文件");
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        // log.error("在扫描用户定义视图时从jar包获取文件出错");
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }

    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param classes
     */
    public static void getClassesByPackage(String packageName, String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            // log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        if (ObjectKit.isNull(dirfiles)) return;
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                getClassesByPackage(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    // classes.add(Class.forName(packageName + '.' +
                    // className));
                    // 经过回复同学的提醒，这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    // log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
}
