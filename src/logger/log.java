package logger;

import java.io.IOException;
import java.util.logging.*;

public class log {

    private static Logger logr = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

 /*   public static void test() throws IOException {

        LogManager.getLogManager().reset();
        logr.setLevel(Level.ALL);

        try {
            FileHandler fh = new FileHandler("bapLog.txt",true);
            fh.setLevel(Level.ALL);
            fh.setFormatter(new SimpleFormatter());
            logr.addHandler(fh);
            logr.info("Inserted data to table");
        }catch (IOException e){\
            logr.log(Level.SEVERE, "File Logger failed" ,e);
        }*/
    public static void insert(String dataInserted) {

     LogManager.getLogManager().reset();
     logr.setLevel(Level.ALL);

     try {
         FileHandler fh = new FileHandler("bapLog.txt",true);
         fh.setLevel(Level.ALL);
         fh.setFormatter(new SimpleFormatter());
         logr.addHandler(fh);
         logr.info(dataInserted);
     }catch (IOException e){
         logr.log(Level.SEVERE, "File Logger failed" ,e);
        }
     }
}
