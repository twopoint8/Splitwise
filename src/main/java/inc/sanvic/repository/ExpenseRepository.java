package inc.sanvic.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inc.sanvic.helper.Utility;
import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;
import lombok.Getter;

@Getter
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