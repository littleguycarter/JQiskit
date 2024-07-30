package com.cf.jqiskit.util.general;

import java.io.*;
import java.net.URL;
import java.util.function.Consumer;
import java.util.regex.Pattern;

public final class Reflection {
    private static final Pattern SYNTHETIC_PATTERN = Pattern.compile("\\$\\d+?\\.class$");

    public static void consumeClasses(String pkgLoc, ClassLoader loader, Consumer<Class<?>> classConsumer) {
        URL url = loader.getResource(pkgLoc.replace(".", "/"));

        if (url == null) {
            throw new IllegalArgumentException("Please provide a non-null pkgLoc!");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()))) {
            while (reader.ready()) {
                String className = reader.readLine();

                if (SYNTHETIC_PATTERN.matcher(className).find()) {
                    continue;
                }

                Class<?> clazz;

                try {
                    clazz = Class.forName(pkgLoc + "." + className.substring(0, className.length() - 6));
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    continue;
                }

                classConsumer.accept(clazz);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
