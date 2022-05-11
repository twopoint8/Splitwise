package inc.sanvic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;

@Service
public class ExpenseService {

	ExpenseRepository expenseRepository;
	UserRepository userRepository;

	@Autowired
	public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
		this.expenseRepository = expenseRepository;
		this.userRepository = userRepository;
	}

	public void createExpense(String paidBy, Double amount) {

		if (userRepository.isUserExists(paidBy)) {
			for (Expense expense : expenseRepository.getExpenses())
				if (expense.getPaidBy().getName().equals(paidBy))
					expense.setAmount(expense.getAmount() + amount);
		} else {
			User user = new User(paidBy);
			userRepository.addUser(user);
			expenseRepository.addExpense(new Expense(amount, user));
		}
	}
}
