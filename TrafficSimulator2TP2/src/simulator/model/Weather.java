package simulator.model;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public enum Weather {
	SUNNY, CLOUDY, RAINY, WINDY, STORM;
	
	public Image getImage() 
	{ 
		String path = "resources/icons/" + this.toString().toLowerCase() + ".png";
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
