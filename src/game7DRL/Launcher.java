package game7DRL;

import org.lwjgl.LWJGLUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.*;

public class Launcher {
		
	public static void main(String[] args) throws SlickException, IOException{
		System.setProperty("org.lwjgl.librarypath", new File(new File(System.getProperty("lib"), "ShadowsOfHumanity_lib/native"), LWJGLUtil.getPlatformName()).getAbsolutePath());
		System.setProperty("net.java.games.input.librarypath", System.getProperty("org.lwjgl.librarypath"));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int) screenSize.getWidth();
		int height = (int) screenSize.getHeight();
		AppGameContainer app = 
				new AppGameContainer(new Game("Shadows of Humanity"));
		app.setDisplayMode(width, height, true);
		app.setAlwaysRender(false);
		app.setUpdateOnlyWhenVisible(true);
		app.setMaximumLogicUpdateInterval(20);
		app.setShowFPS(false);
		app.start();
	}
}	
