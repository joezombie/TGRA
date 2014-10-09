package is.ru.tgra.cube.helpers;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

/**
 * Created by Johannes Gunnar Heidarsson on 9.10.2014.
 */
public class CubeLogger {
    private static CubeLogger instance = new CubeLogger();
    protected final String fileName = "./cube.log";
    protected final DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    protected boolean debug = false;
    Writer writer;

    protected CubeLogger(){
        try{
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "utf-8"));
        } catch (IOException e){
            System.out.println("CubeLogger: could not open file");
        }
    }


    public static CubeLogger getInstance(){
        return instance;
    }

    public void log(String prefix, String message){
        try{
            writer.write(dateFormat.format(new Date()) + " - " + prefix + ": " + message + "\n");
            writer.flush();
        } catch (IOException e){
            System.out.println("CubeLogger: could not write to file");
        }
    }

    public void setDebug(boolean debug){
        this.debug = debug;
    }

    public void info(String message){
        log("INFO", message);
    }

    public void error(String message){
        log("ERROR", message);
    }

    public void debug(String message){
        if(debug){
            log("DEBUG", message);
        }
    }
}
