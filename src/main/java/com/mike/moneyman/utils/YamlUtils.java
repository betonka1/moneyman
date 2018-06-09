package com.mike.moneyman.utils;

import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.mike.moneyman.domain.Pipeline;
import com.mike.moneyman.yaml.YamlBean;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class YamlUtils {

    public static YamlBean parse(File file) throws IOException {

        YamlReader reader = new YamlReader(new FileReader(file));
        return reader.read(YamlBean.class);
    }


    public static YamlBean write(Pipeline pipeline) throws IOException {

        YamlWriter writer = new YamlWriter(new FileWriter("/Users/mishakonopelko/Desktop/MoneyMan/src/main/resources/static/output.yaml"));
        //writer.getConfig().setClassTag("contact", Contact.class);
        writer.write(pipeline);
        writer.close();
        return null;
    }
}
