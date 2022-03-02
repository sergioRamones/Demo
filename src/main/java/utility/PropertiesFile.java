package utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFile {

	private Properties m_properties = new Properties();
	
	/**
	 * @throws IOException
	 * @Description  Loads a Properties file
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String 
	 * @return N/A
	 */
	public PropertiesFile(String fileName) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(fileName));
			m_properties.load(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}
	
	/**
	 * @throws N/A
	 * @Description  Returns the properties loaded.
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String 
	 * @return Properties
	 */
	public Properties getProperties() {
		return m_properties;

	}
}
