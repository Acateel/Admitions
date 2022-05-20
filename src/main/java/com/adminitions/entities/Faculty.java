package com.adminitions.entities;

public class Faculty extends Entity {
    private String name;
    private int budgetSeats;
    private int totalSeats;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBudgetSeats() {
        return budgetSeats;
    }

    public void setBudgetSeats(int budgetSeats) {
        this.budgetSeats = budgetSeats;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "Faculty{" +
                "id='" + getId() + '\'' +
                "name='" + name + '\'' +
                ", budgetSeats=" + budgetSeats +
                ", totalSeats=" + totalSeats +
                '}';
    }
}
