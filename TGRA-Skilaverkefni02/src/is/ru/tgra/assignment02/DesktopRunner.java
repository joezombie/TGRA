package is.ru.tgra.assignment02;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

/**
 * Created by Johannes Gunnar Heidarsson on 14.9.2014.
 */
public class DesktopRunner {
    public static void main (String[] args){
        new LwjglApplication(new CannonEngine(), "Cannon", 800, 600, false);
    }

}
