package com.lin.spi.util;

import java.io.*;
import java.util.Properties;

/**
 * @author jianglinzou
 * @date 2019/3/8 下午6:44
 */
public class PropUtils {




    /**
     * 注意使用该方法会存在对流的缓存
     * 需要动态获取配置文件时，请不要使用此方法
     *
     * @param resource
     * @param encoding
     * @param
     * @return
     * @throws IOException
     */
    public static Properties getResourceAsProperties(String resource, String encoding) throws IOException {
        InputStream in = null;
        try {
            in = ResourceUtils.getResourceAsStream(resource);
        } catch (IOException e) {
            File file = new File(resource);
            if (!file.exists()) {
                throw e;
            }
            in = new FileInputStream(file);
        }
        Properties props = new Properties();

        props.load(in);
        in.close();
//        reader.close();

        return props;

    }




    /**
     * 每次都会获取文件的缓存流
     *
     * @param absolutePath
     * @return
     */
    public static Properties getLastestResourceProperties(String absolutePath) throws IOException {
        FileInputStream in = new FileInputStream(absolutePath);
        Properties props = new Properties();

        props.load(in);
        in.close();
        return props;
    }


    public static File getResourceAsFile(String resource) throws IOException {
        try {
            return new File(ResourceUtils.getResourceURL(resource).getFile());
        } catch (IOException e) {
            return new File(resource);
        }
    }

    public abstract static class Action {
        public abstract void process(String line);


        public boolean isBreak() {
            return false;
        }
    }


    public static void processEachLine(String string, Action action) throws IOException {
        BufferedReader br = new BufferedReader(new StringReader(string));
        try {
            String line;
            while ((line = br.readLine()) != null) {
                action.process(line);
                if (action.isBreak()) {
                    break;
                }
            }

        } finally {
            br.close();
            br = null;
        }
    }

}
