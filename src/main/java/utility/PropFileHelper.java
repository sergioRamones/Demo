package utility;

import java.util.Properties;

import org.testng.Reporter;




public class PropFileHelper {	

	private static Properties systemProp = null;
	public static final String systemPropFileName = System.getProperty("user.dir")+"\\src\\main\\resources\\uiconfig.properties";
	
	/**
	 * @Description Constructor read system properties file 
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter N/A
	 * @return N/A
	 * @throws N/A 
	 */
	public PropFileHelper() {
		getSystemProp();
	}
	
	/**
	 * @Description load properties file stored in Constant class
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter N/A
	 * @return String
	 * @throws N/A 
	 */
	public Properties getSystemProp() {
		String filename = systemPropFileName;
		if(systemProp==null)
			systemProp = getPropertiesFromFileName(filename);
		
		loadSystemProp(filename, systemProp);
		
		return systemProp;
	}

	/**
	 * @Description return properties from file name
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String
	 * @return Properties
	 * @throws N/A 
	 */
	public Properties getPropertiesFromFileName(String filename) {
		
		Properties prop = null;
		
		try{
			PropertiesFile propFile = new PropertiesFile(filename);
			prop = propFile.getProperties();
		} catch (Exception e) {	
			Reporter.log("Unable to read properties file, name:[ " + filename + " ]error");
			System.exit(1);
		}
		
		return prop;
	}
	
	/**
	 * @Description load properties from file name and Properties.
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter String, Properties
	 * @return N/A
	 * @throws N/A 
	 */
	private void loadSystemProp(String filename, Properties prop) {
		
		if(System.getProperties().isEmpty())
			System.load(filename);
		else {
			System.getProperties().putAll(prop);
		}
	}


	/**
	 * @Description return specific property and Properties file.
	 * @author sramones
	 * @Date 01/03/2022
	 * @Parameter Properties, String
	 * @return String
	 * @throws N/A 
	 */
	public String getPropertyValue(Properties prop, String propName) {
		String retVal = null;
	
		try{
			retVal = prop.getProperty(propName).trim();
		} catch (Exception e) {
			System.out.println("Value can't be readed.");;
		}
		return retVal;
	}
	
 	
}
