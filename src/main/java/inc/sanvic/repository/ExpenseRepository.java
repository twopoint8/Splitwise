 package inc.sanvic.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import inc.sanvic.model.Expense;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Repository
public class ExpenseRepository {

	private List<Expense> expenses;

	public ExpenseRepository() {
		expenses = new ArrayList<>();
	}

	public void addExpense(Expense expense) {
		expenses.add(expense);
	}

}