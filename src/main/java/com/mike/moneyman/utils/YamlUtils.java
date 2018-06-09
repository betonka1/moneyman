package com.mike.moneyman.utils;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.yaml.YamlBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.*;


public class YamlUtils {

    public static YamlBean parse(File file) throws IOException {

        YamlReader reader = new YamlReader(new FileReader(file));
        return reader.read(YamlBean.class);
    }

    public static YamlBean write(Pipeline pipeline) throws IOException {
        File file = ResourceUtils.getFile("classpath:public/output.yaml");// this file is in target/classes/WEB-INF/public/output.yaml
        YamlWriter writer = new YamlWriter(new FileWriter(file));
        //writer.getConfig().setClassTag("contact", Contact.class);
        writer.write(pipeline);
        writer.close();
        return null;
    }
}
