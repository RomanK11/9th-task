import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
            }
        }

        expenses.add(expense);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(expenses);
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @SuppressWarnings("unchecked")
    public void printSum(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();

        init(expenses, fileName);

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

        init(expenses, fileName);

        if (expenses != null) {
            for (int i = 0; i < expenses.size(); i++) {
                Expense expense = expenses.get(i);
                if (expense.getCategory().contains(category)) {
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

    @SuppressWarnings("unchecked")
    public void deleteExpense(String fileName, int index) {
        ArrayList<Expense> expenses = new ArrayList<>();

        init(expenses, fileName);

        if (index < 0 && index >= expenses.size()) {
            System.out.println("error");
            return ;
        }

        if (expenses.size() != 0 && expenses != null) {
            expenses.remove(index);
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                oos.writeObject(expenses);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void showStat(String fileName, String category) {

    }
}