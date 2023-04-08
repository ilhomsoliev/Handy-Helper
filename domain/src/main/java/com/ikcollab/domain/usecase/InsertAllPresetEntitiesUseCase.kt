package com.ikcollab.domain.usecase

import com.ikcollab.model.local.budget.BudgetCategoryEntity
import com.ikcollab.model.local.budget.EXPENSES_TYPE
import com.ikcollab.model.local.budget.INCOME_TYPE
import com.ikcollab.model.local.note.FolderEntity
import com.ikcollab.repository.budget.BudgetRepository
import com.ikcollab.repository.notes.NotesRepository
import javax.inject.Inject

class InsertAllPresetEntitiesUseCase@Inject constructor(
    private val budgetRepository: BudgetRepository,
    private val notesRepository: NotesRepository

) {
    suspend operator fun invoke() {
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 1, name = "Products", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 2, name = "Education", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 3, name = "Transport", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 4, name = "Health", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 5, name = "Entertainment", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 6, name = "Restaurant", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 7, name = "Shopping", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 8, name = "Fitness", dateCreated = System.currentTimeMillis(), type = EXPENSES_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 9, name = "Wage", dateCreated = System.currentTimeMillis(), type = INCOME_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 10, name = "Scholarship, allowances", dateCreated = System.currentTimeMillis(), type = INCOME_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 11, name = "Business", dateCreated = System.currentTimeMillis(), type = INCOME_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 12, name = "Interest on deposits", dateCreated = System.currentTimeMillis(), type = INCOME_TYPE))
        budgetRepository.insertBudgetCategory(BudgetCategoryEntity(id = 13, name = "Other income", dateCreated = System.currentTimeMillis(), type = INCOME_TYPE))
        notesRepository.insertFolder(FolderEntity(id = 1,name = "Diary", dateCreated = System.currentTimeMillis()))
        notesRepository.insertFolder(FolderEntity(id = 2,name = "Success journal", dateCreated = System.currentTimeMillis()))
        notesRepository.insertFolder(FolderEntity(id = 3,name = "Gratefulness", dateCreated = System.currentTimeMillis()))
        notesRepository.insertFolder(FolderEntity(id = 4,name = "My mistakes", dateCreated = System.currentTimeMillis()))
        notesRepository.insertFolder(FolderEntity(id = 5,name = "Happiness journal", dateCreated = System.currentTimeMillis()))
    }
}