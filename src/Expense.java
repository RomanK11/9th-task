import java.math.BigDecimal;
import java.time.LocalDate;
import java.io.Serializable;

public class Expense implements Serializable {
    private static final long serialVersionUID = 1L;

    private BigDecimal amount;
    private LocalDate date;
    private String description;
    private String category;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Expense(BigDecimal amount, LocalDate date, String description, String category) {
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.category = category;
    }
}
