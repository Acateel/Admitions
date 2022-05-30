package com.adminitions.entities;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Faculty faculty = (Faculty) o;
        return budgetSeats == faculty.budgetSeats && totalSeats == faculty.totalSeats && Objects.equals(name, faculty.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, budgetSeats, totalSeats);
    }
}
