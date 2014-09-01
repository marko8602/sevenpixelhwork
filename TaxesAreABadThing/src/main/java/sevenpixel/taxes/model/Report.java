package sevenpixel.taxes.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Report {
	private BigDecimal salesTaxes;
	private BigDecimal totalAmount;
	private List<SoldItem> soldItems;

	public List<SoldItem> getSoldItems() {
		return soldItems;
	}

	public BigDecimal getSalesTaxes() {
		return salesTaxes;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setSoldItems(List<SoldItem> soldItems) {
		this.soldItems = soldItems;
	}

	public void setSalesTaxes(BigDecimal salesTaxes) {
		this.salesTaxes = salesTaxes.setScale(2, RoundingMode.CEILING);
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount.setScale(2, RoundingMode.UNNECESSARY);
	}
	
}
