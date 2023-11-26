package by.melnikov.medicinesxml.entity;

import java.util.Objects;
import java.util.StringJoiner;

public class MedicinePackage {
    public enum Size {
        SMALL, MEDIUM, LARGE
    }
    public static final String DEFAULT_QUANTITY = "unknown";
    private int price;
    private Size size;
    private String quantity;

    public MedicinePackage() {
    }

    public MedicinePackage(int price, Size size, String quantity) {
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MedicinePackage that = (MedicinePackage) o;

        if (price != that.price) return false;
        if (size != that.size) return false;
        return Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        int result = price;
        result = 31 * result + (size != null ? size.hashCode() : 0);
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ","[", "]")
                .add("price=" + price)
                .add("size=" + size)
                .add("quantity='" + quantity + "'")
                .toString();
    }
}
