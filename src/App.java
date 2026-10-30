import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        ArrayList<Expense> expenses = new ArrayList<>();

        LocalDate today = LocalDate.now();


        String fileName = "test.txt";

        expenseManager.addExpense(expenses, new BigDecimal("2000"), today, "hhh", "Toma", fileName);
        expenseManager.showAll(fileName);
        expenseManager.printSum(fileName);
        expenseManager.showByCategory(fileName, "Toma");
        //expenseManager.deleteExpense(fileName, 0);
    }
}