package com.tgra;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class DesktopStarter
{
	public static void main(String[] args)
	{
		new LwjglApplication(new Assignment3Base(), "Assignment 3 Base", 640, 480, false);
	}
}
