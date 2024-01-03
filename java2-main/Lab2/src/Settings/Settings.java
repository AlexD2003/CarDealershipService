package Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Settings {
    private Properties props;
    public Settings() throws IOException {
        props=new Properties();
        try {
            FileInputStream input = new FileInputStream("C:\\Users\\Alex\\Desktop\\JavaLab\\javalab5\\java2-main\\Lab2\\src\\Settings\\Settings.properties");
            props.load(input);
        }
        catch (IOException e){
            throw e;
        }
    }
    public void updateProps(String key, String field){
        Properties props2=new Properties();
        props2.setProperty(key,field);
        this.props=props2;

    }
    public String getProps(String key){
        return this.props.getProperty(key);
    }
}
