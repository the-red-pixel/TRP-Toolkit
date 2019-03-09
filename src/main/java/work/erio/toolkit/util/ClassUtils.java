package work.erio.toolkit.util;

import work.erio.toolkit.Toolkit;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;

public class ClassUtils {
    public static List<Class> findAllClasses(String basePackage) throws URISyntaxException, IOException, ClassNotFoundException {
        List<Class> classes = new LinkedList<>();
        ClassLoader classLoader = Toolkit.class.getClassLoader();
        URL baseUrl = classLoader.getResource(basePackage.replace(".", "/"));
        if (baseUrl.getProtocol() == "file") {
            classes = findAllClassesInFile(basePackage, baseUrl);
        } else if (baseUrl.getProtocol() == "jar") {
            classes = findAllClassesInJar(basePackage, baseUrl);
        }
        return classes;
    }

    public static List<Class> findAllClassesInJar(String basePackage, URL baseUrl) throws IOException {
        List<Class> classes = new LinkedList<>();
        JarFile jarFile = ((JarURLConnection) baseUrl.openConnection()).getJarFile();
        jarFile.stream().map(e -> e.getName().replace("/", "."))
                .filter(e -> e.startsWith(basePackage) && e.endsWith(".class") && !e.contains("$"))
                .map(e -> e.replace(".class", ""))
                .forEach(e -> {
                    try {
                        classes.add(Class.forName(e));
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                    }
                });
        return classes;
    }

    public static List<Class> findAllClassesInFile(String basePackage, URL baseUrl) throws URISyntaxException, ClassNotFoundException {
        List<Class> classes = new LinkedList<>();
        File packageDir = new File(baseUrl.toURI());
        for (File f : Objects.requireNonNull(packageDir.listFiles())) {
            String name = f.getName();
            if (name.endsWith(".class") && !name.contains("$")) {
                String classPath = String.format("%s.%s", basePackage, name.replace(".class", ""));
                classes.add(Class.forName(classPath));
            }
        }
        return classes;
    }
}
