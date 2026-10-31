import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ExpenseManager {
    @SuppressWarnings("unchecked")
    private ArrayList<Expense> init(ArrayList<Expense> expenses, String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            // Читаем объект и приводим его к нужному типу
            expenses = (ArrayList<Expense>) ois.readObject();
            return expenses;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void save(ArrayList<Expense> expenses, String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public void addExpense(ArrayList<Expense> expenses, BigDecimal amount, LocalDate date, String description,
            String category, String fileName) {

        Expense expense = new Expense(amount, date, description, category);

        File f = new File(fileName);
        if (f.exists() && f.length() > 0) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                ArrayList<Expense> fromFile = (ArrayList<Expense>) ois.readObject();
                // Заменяем содержимое переданного списка содержимым файла
                expenses.clear();
                expenses.addAll(fromFile);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                return;
            }
        }
        expenses.add(expense);

        save(expenses, fileName);

    }

    public void showAll(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);

        if (expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                System.out.println("Id: " + i);
                System.out.println(expense.getCategory());
                System.out.println(expense.getDescription());
                System.out.println(expense.getAmount());
                System.out.println(expense.getDate());
                System.out.println();
            }
        }
    }

    public void printSum(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);

        BigDecimal sum = BigDecimal.ZERO;

        if (expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                sum = sum.add(expenses.get(i).getAmount());
            }
        }
        System.out.println(sum.toString());
    }

    public void showByCategory(String fileName, String category) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);

        if (expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                if (expense.getCategory().equalsIgnoreCase(category)) {
                    System.out.println("Id: " + i);
                    System.out.println(expense.getCategory());
                    System.out.println(expense.getDescription());
                    System.out.println(expense.getAmount());
                    System.out.println(expense.getDate());
                    System.out.println();
                }
            }
        }
    }

    public void deleteExpense(String fileName, int index) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);
        if (expenses == null) {
            System.out.println("NULL");
            return;
        }

        if (index < 0 || index >= expenses.size()) {
            System.out.println("error");
            return;
        }

        expenses.remove(index);
        save(expenses, fileName);

    }

    public void showStat(String fileName, String category) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("NULL");
            return;
        }

        BigDecimal sum = BigDecimal.ZERO;

        int quantity = 0;
        BigDecimal min = null;
        BigDecimal max = null;

        for (int i = 0; i < expenses.size(); i++) {
            BigDecimal currentAmount = expenses.get(i).getAmount();

            if (expenses.get(i).getCategory().equalsIgnoreCase(category)) {

                if (min == null) {
                    min = currentAmount;
                    max = currentAmount;
                }

                sum = sum.add(currentAmount);

                if (min.compareTo(currentAmount) > 0) {
                    min = currentAmount;
                }

                if (max.compareTo(currentAmount) < 0) {
                    max = currentAmount;
                }

                quantity++;

            }
        }

        if (min == null) {
            System.out.println("No expenses in this category");
            return;
        }

        System.out.println("Sum: " + sum);
        System.out.println("Quantity: " + quantity);
        System.out.println("Min: " + min);

        if (quantity > 0) {
            BigDecimal average = sum.divide(BigDecimal.valueOf(quantity), 2, java.math.RoundingMode.HALF_UP);
            System.out.println("Mid: " + average);
        }

        System.out.println("Max: " + max);
    }

    public void showStat(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("NULL");
            return;
        }

        BigDecimal sum = BigDecimal.ZERO;

        int quantity = 0;
        BigDecimal min = expenses.get(0).getAmount();
        BigDecimal max = expenses.get(0).getAmount();

        for (int i = 0; i < expenses.size(); i++) {
            BigDecimal currentAmount = expenses.get(i).getAmount();

            sum = sum.add(currentAmount);

            if (min.compareTo(currentAmount) > 0) {
                min = currentAmount;
            }

            if (max.compareTo(currentAmount) < 0) {
                max = currentAmount;
            }

            quantity++;

        }
        System.out.println("Sum: " + sum);
        System.out.println("Quantity: " + quantity);
        System.out.println("Min: " + min);

        if (quantity > 0) {
            BigDecimal average = sum.divide(BigDecimal.valueOf(quantity), 2, java.math.RoundingMode.HALF_UP);
            System.out.println("Mid: " + average);
        }

        System.out.println("Max: " + max);
    }

    public void editExpense(String fileName, int index, BigDecimal amount, LocalDate date, String category,
            String description) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);

        if (expenses == null || expenses.isEmpty()) {
            System.out.println("NULL");
            return;
        }

        if (index < 0 || index >= expenses.size()) {
            return;
        }

        Expense expense = expenses.get(index);

        expense.setAmount(amount);
        expense.setCategory(category);
        expense.setDate(date);
        expense.setDescription(description);

        save(expenses, fileName);
    }
    // showTopExpenses() — вывод пяти самых дорогих расходов.

    public void showByDateRange(String fileName, LocalDate firstDate, LocalDate secondDate) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);

        if (expenses == null || expenses.isEmpty()) {
            System.out.println("null");
            return;
        }
        if (firstDate == null || secondDate == null) {
            throw new IllegalArgumentException("NULL");
        }
        if (firstDate.isAfter(secondDate)) {
            return;
        }

        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);

            if (!expense.getDate().isBefore(firstDate) && !expense.getDate().isAfter(secondDate)) {
                System.out.println("Id: " + i);
                System.out.println(expense.getCategory());
                System.out.println(expense.getDescription());
                System.out.println(expense.getAmount());
                System.out.println(expense.getDate());
                System.out.println();
            }
        }
    }

    public void showByAmount(String fileName, BigDecimal amount, boolean greater) {
        ArrayList<Expense> expenses = new ArrayList<>();

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        expenses = init(expenses, fileName);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("NULL");
            return;
        }

        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);

            if (greater == true) {
                if (expense.getAmount().compareTo(amount) > 0) {
                    System.out.println("Id: " + i);
                    System.out.println(expense.getCategory());
                    System.out.println(expense.getDescription());
                    System.out.println(expense.getAmount());
                    System.out.println(expense.getDate());
                    System.out.println();
                }
            } else if (greater == false) {
                if (expense.getAmount().compareTo(amount) < 0) {
                    System.out.println("Id: " + i);
                    System.out.println(expense.getCategory());
                    System.out.println(expense.getDescription());
                    System.out.println(expense.getAmount());
                    System.out.println(expense.getDate());
                    System.out.println();
                }
            }
        }
    }

    public void showTopExpenses(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();

        expenses = init(expenses, fileName);
        if (expenses == null || expenses.isEmpty()) {
            System.out.println("NULL");
            return;
        }

        expenses.sort(Comparator.comparing(Expense::getAmount).reversed());

        for (int i = 0; i < 5 && i < expenses.size(); i++) {
            Expense expense = expenses.get(i);

            System.out.println(expense.getCategory());
            System.out.println(expense.getDescription());
            System.out.println(expense.getAmount());
            System.out.println(expense.getDate());
            System.out.println();
        }
    }

}