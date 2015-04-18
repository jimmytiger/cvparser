package cvParser;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

public class CV
{
    public final static List<String> TITLE_DELIMITERS = Arrays.asList("-", "+");
    public final static List<String> PATH_DELIMITERS = Arrays.asList("/", "\\");
    public final static String PATH_SUFFIX = ".";
    public final static String REGX_SEX = "ÄÐ|Å®";
    public final static String REGX_PHONE = "((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))[\\d\\s\\-]{8,15}";
    public final static String REGX_EMAIL = "([a-z_0-9A-Z]+[-|\\.]?)+[a-z_0-9A-Z]@([a-z_0-9A-Z]+(-[a-z_0-9A-Z]+)?\\.)+[a-zA-Z]{2,}";
    private String title;
    private String name;
    private List<String> tags;
    private String textContent;
    private String sex;
    private String email;
    private String phone;
    

    public CV(String title, String textContent)
    {
    	Validate.notNull(title);
    	Validate.notNull(textContent);
        this.title = title;
        this.textContent = textContent;
        
        wrapUp();
    }
    
    private void wrapUp()
    {
        name    =  getNameFromTitle(title);
        tags    =  getTagsFromTitle(title);
        sex     =  getSexFromContent(textContent);
        email   =  getEmailFromContent(textContent);
        phone   =  getPhoneFromContent(textContent);
        
    }
    
    
    /**
	 * get the matching string
	 * 
	 * @param con
	 * @param reg
	 * @return
	 */
	private String getValue(String reg, String con){
		Pattern p = Pattern.compile(reg);
		Matcher m = p.matcher(con);
		String res = "";
		while (m.find()) {
			res = m.group(0);
		}
		return res;
	}

	/**
	 * get Sex
	 * @param content
	 * @return
	 */
    private String getSexFromContent(String content)
    {
        return getValue(REGX_SEX, content);
    }
    
    /**
     * get Email
     * @param content
     * @return
     */
    private String getEmailFromContent(String content)
    {
        return getValue(REGX_EMAIL, content);
    }
    
    /**
     * get Phone
     * @param content
     * @return
     */
    private String getPhoneFromContent(String content)
    {
        return getValue(REGX_PHONE, content);
    }
    
    /**
     * Get Name from title
     * @param title
     * @return
     */
    private String getNameFromTitle(String title)
    {
         if(StringUtils.isNotBlank(title))
         {
             for(String delimiter : TITLE_DELIMITERS)
             {
                 if(title.contains(delimiter))
                 {
                     String[] parts = title.split(delimiter);
                     return parts[0];
                 }
             }
         }
        return null;
    }
    
    /**
     * get other tags from the Title, like school, job position
     * @param title
     * @return
     */
    private List<String> getTagsFromTitle(String title)
    {
         if(StringUtils.isNotBlank(title))
         {
             for(String delimiter : TITLE_DELIMITERS)
             {
                 if(title.contains(delimiter))
                 {
                     List<String> parts = Arrays.asList(title.split(delimiter));
                     return parts.subList(1, parts.size());
                 }
             }
         }
        return null;
    }
    
    public String toString()
    {
    	return "Name:" + name + "\n"
            +  "Sex:" + sex + "\n"
            +  "Email:" + email + "\n"
            +  "Phone:" + phone + "\n"
    	    +  "Tags:" + tags + "\n";
    }
}
