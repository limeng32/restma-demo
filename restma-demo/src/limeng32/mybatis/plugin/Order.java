package limeng32.mybatis.plugin;

public class Order {

	public Order(String field, String sequence) {
		this.field = field;
		this.sequence = sequence;
	}

	private String field;

	private String sequence;

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String toSql() {
		return new StringBuffer().append(" ").append(field).append(" ")
				.append(sequence).append(",").toString();
	}
}
