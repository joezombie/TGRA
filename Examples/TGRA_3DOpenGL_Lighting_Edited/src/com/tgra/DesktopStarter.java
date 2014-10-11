package com.tgra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter
{
	public static void main(String[] args)
	{
		new LwjglApplication(new First3D_Core(), "First 3D Program", 1024, 768, false);
	}
}
