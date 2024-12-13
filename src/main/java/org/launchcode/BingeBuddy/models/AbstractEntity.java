package org.launchcode.BingeBuddy.models;


import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @GeneratedValue
    private Long id;

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractEntity entity = (AbstractEntity) o;
        return Objects.equals(id, entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }


    public void setReviewId(Long reviewId) {
    }

    public void setId(Long id) {

    }

    protected void remove(Review review) {
    }

    protected void add(Review review) {
    }
}

