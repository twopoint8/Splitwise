package inc.sanvic.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import inc.sanvic.model.Expense;
import inc.sanvic.repository.ExpenseRepository;
import inc.sanvic.repository.IndexRepository;

@Service
public class IndexingService {

	IndexRepository indexRepository;
	ExpenseRepository expenseRepository;

	@Autowired
	public IndexingService(IndexRepository indexRepository, ExpenseRepository expenseRepository) {
		this.indexRepository = indexRepository;
		this.expenseRepository = expenseRepository;
	}

	public void setIndexes() {
		List<Expense> expenses = expenseRepository.getExpenses();
		Integer currentIndex = 0;
		for (Expense expense : expenses) {
			indexRepository.setUserMappingToIndex(expense.getPaidBy(), currentIndex);
			indexRepository.setIndexMappingToUser(currentIndex++, expense.getPaidBy());
		}
	}
}
