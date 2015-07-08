package limeng32.mybatis.plugin.mapper.builder;

/**
 * 对sql中被查询内容进行转义处理，以解决单引号（'）、下横线（_）、和百分号（%）的问题
 * 
 * @author limeng32
 * 
 */
public class SqlEscaper {

	public static String escape(Object value) {
		/* 之后有更好的方法进行处理 */
		if (((String) value).indexOf("\\") == -1) {
			value = ((String) value).replaceAll("\\%", "\\\\%");
			value = ((String) value).replaceAll("\\_", "\\\\_");
			value = ((String) value).replaceAll("'", "''");
		}
		return (String) value;
	}

}
