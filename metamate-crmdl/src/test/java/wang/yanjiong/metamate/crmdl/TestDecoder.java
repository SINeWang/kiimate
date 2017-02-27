package wang.yanjiong.metamate.crmdl;

import org.junit.Test;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by WangYanJiong on 2/27/17.
 */

public class TestDecoder {

    @Test
    public void hello(){
        File file = new File(getClass().getClassLoader().getResource("crmdl.yml").getFile());
        InputStream in = null;
        try {
            in = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Yaml yaml = new Yaml();

        Map<String, Object> map= (Map)yaml.load(in);
        System.out.println(map);

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
