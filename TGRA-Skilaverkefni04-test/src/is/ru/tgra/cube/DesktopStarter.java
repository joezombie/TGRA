package is.ru.tgra.cube;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import is.ru.tgra.cube.engine.CubeEngine;

import javax.swing.*;
import java.awt.*;


/**
 * Created by Johannes Gunnar Heidarsson on 9.10.2014.
 */
public class DesktopStarter {

    public static void main(String[] args){
        new LwjglApplication(new CubeEngine(), "CUBE", 1024, 768, false);
    }

}
