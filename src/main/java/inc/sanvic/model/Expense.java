package inc.sanvic.model;

import java.math.BigDecimal;

public class Expense {
	private BigDecimal amount;
	private Friend paidBy;
	
	private Expense(BigDecimal amount, Friend paidBy) {
		this.amount = amount;
		this.paidBy = paidBy;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Friend getPaidBy() {
		return paidBy;
	}

	public static Expense createExpense(BigDecimal amount, Friend paidBy) {
		return new Expense(amount, paidBy);
	}	
}