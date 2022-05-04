package inc.sanvic.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {
	private Double amount;
	private User paidBy;
}