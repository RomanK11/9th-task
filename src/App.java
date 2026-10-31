import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        ExpenseManager expenseManager = new ExpenseManager();
        ArrayList<Expense> expenses = new ArrayList<>();

        LocalDate today = LocalDate.now();


        String fileName = "test.txt";

        //expenseManager.addExpense(expenses, new BigDecimal("2000"), today, "hhh", "Toma", fileName);
        //expenseManager.showAll(fileName);
        //expenseManager.printSum(fileName);
        //expenseManager.showByCategory(fileName, "Toma");
        //expenseManager.deleteExpense(fileName, 0);
        //expenseManager.showStat(fileName);
        expenseManager.editExpense(fileName, 1, new BigDecimal("6000"), today, "TOMA", "fdfd");
        //expenseManager.showAll(fileName);
        expenseManager.showByDateRange(fileName, LocalDate.of(2008, 04, 11), today);
        expenseManager.showByAmount(fileName, new BigDecimal("3000"), false);
        expenseManager.showTopExpenses(fileName);
    }
}