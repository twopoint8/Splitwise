package inc.sanvic.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import inc.sanvic.model.Expense;
import inc.sanvic.model.Friend;

@Repository
public class ExpenseRepository {

	private List<Expense> expenses;
	@Autowired
	private FriendRepository friendRepository;
	
	public ExpenseRepository() {
		expenses = Collections.synchronizedList(new ArrayList<>());
	}

	
	public List<Expense> getExpenses() {
		return expenses;
	}

	public void addExpense(BigDecimal amount,String paidBy) {
		if (friendRepository.isFriendExistsWithName(paidBy)) {
			appendAmountForExistingFriend(amount, paidBy);
		}
		else {
			addExpenseForNewFriend(amount, paidBy);
		}		
	}

	private void addExpenseForNewFriend(BigDecimal amount, String paidBy) {
		Friend friend = Friend.createFriendInstance(paidBy);
		friendRepository.addFriend(friend);
		
		expenses.add(Expense.createExpense(amount, friend));
	}

	private void appendAmountForExistingFriend(BigDecimal amount, String paidBy) {
		Optional<Expense> targetExpense = expenses.stream().filter(expense -> expense.getPaidBy().getName().equals(paidBy)).findFirst();
				targetExpense.ifPresent(expense -> {expense.setAmount(expense.getAmount().add(amount));});
	}
}