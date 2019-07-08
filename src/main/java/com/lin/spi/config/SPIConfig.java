package com.lin.spi.config;

import com.lin.spi.util.ClassLoaderUtils;
import com.lin.spi.util.PropUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.ConcurrentReferenceHashMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author jianglinzou
 * @date 2019/7/5 下午1:05
 */
public class SPIConfig {

    private static final Map<ClassLoader, MultiValueMap<String, String>> cache = new ConcurrentReferenceHashMap<>();


    public static String separator = ",";

    public static String SPIPathKey = "SPIPath";


    public static Logger logger = LoggerFactory.getLogger(SPIConfig.class);

    /**
     * The location to look for factories.
     * <p>Can be present in multiple JAR files.
     */
    public static final String SPT_PATH = "spi/spi.properties";

    public static ConcurrentHashMap<String, String> iniMap = new ConcurrentHashMap();

    static {
        ClassLoader classLoader = SPIConfig.class.getClassLoader();
        loadSPIPath(classLoader);
    }


    public static String getInitValue(String key, String defaultValue) {
        String value = iniMap.get(key);
        if (Strings.isBlank(value)) {
            return defaultValue;
        }
        return value;
    }


    public static List<String> getInitSetValue(String key, List<String> defaultList) {
        ClassLoader classLoader = SPIConfig.class.getClassLoader();
        return getInitSetValue(key, defaultList, classLoader);
    }

    protected static List<String> getInitSetValue(String key, List<String> defaultList, ClassLoader classLoader) {

        return cache.get(classLoader).get(key);

    }


    private static Map<String, List<String>> loadSPIPath(@Nullable ClassLoader classLoader) {
        MultiValueMap<String, String> result = cache.get(classLoader);
        if (result != null) {
            return result;
        }
        try {
            Enumeration<URL> urls = (classLoader != null ?    //获取所有spi/path文件
                    classLoader.getResources(SPT_PATH) :
                    ClassLoaderUtils.getCurrentClassLoader().getResources(SPT_PATH));
            result = new LinkedMultiValueMap<>();
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                UrlResource resource = new UrlResource(url);
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                for (Map.Entry<?, ?> entry : properties.entrySet()) {
                    List<String> factoryClassNames = Arrays.asList(
                            StringUtils.commaDelimitedListToStringArray((String) entry.getValue()));
                    result.addAll((String) entry.getKey(), factoryClassNames);
                }
            }
            cache.put(classLoader, result);
            return result;
        } catch (IOException ex) {
            throw new IllegalArgumentException("Unable to load factories from location [" +
                    SPT_PATH + "]", ex);
        }
    }


}
