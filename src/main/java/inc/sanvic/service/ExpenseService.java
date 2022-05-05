package inc.sanvic.service;

import org.springframework.stereotype.Service;

import inc.sanvic.model.Expense;
import inc.sanvic.model.User;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.UserRepository;


@Service
public class ExpenseService {
	
	private ExpenseRepository expenseRepository;
	private UserRepository userRepository;
	private UserService userService;
	public ExpenseService(ExpenseRepository expenseRepository, UserRepository userRepository) {
		this.expenseRepository = expenseRepository;
		this.userRepository = userRepository;
		userService = new UserService(userRepository);
	}
	public void createExpense(String paidBy, Double amount) {
		
		if(userRepository.isUserExists(paidBy)) {
			for(Expense expense:expenseRepository.getExpenses()) 
				if(expense.getPaidBy().getName().equals(paidBy))
					expense.setAmount(expense.getAmount()+amount);
		}
		else {
			User user = new User(paidBy);
			userService.addUser(user);
			expenseRepository.addExpense(new Expense(amount, user));
		}
		}
}
