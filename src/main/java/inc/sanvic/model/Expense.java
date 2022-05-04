package inc.sanvic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Expense {
	private Double amount;
	private User paidBy;
}
