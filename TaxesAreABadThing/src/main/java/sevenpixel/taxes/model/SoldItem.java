package sevenpixel.taxes.model;

import java.math.BigDecimal;

public class SoldItem {
	private String type;
	private Integer quantity;
	private String name;
	private BigDecimal price;

	public String getName() {
		return this.name;
	}

	public String getType() {
		return this.type;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public BigDecimal getPrice() {
		return this.price;
	}
}
