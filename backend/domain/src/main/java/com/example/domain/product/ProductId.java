package com.example.domain.product;

import java.util.Objects;
import java.util.UUID;

public final class ProductId {
    private final UUID value;

    private ProductId(UUID value) {
        this.value = Objects.requireNonNull(value, "id");
    }

    public static ProductId of(UUID value) { return new ProductId(value); }
    public static ProductId of(String value) { return new ProductId(UUID.fromString(value)); }
    public static ProductId of(Long value) { return new ProductId(new UUID(0L, value)); }   

    public static ProductId newId() { return new ProductId(UUID.randomUUID()); }

    public UUID value() { return value; }
    
    public Long toLong() { return value.getLeastSignificantBits(); }

    @Override public String toString() { return value.toString(); }
    @Override public boolean equals(Object o) {
        return (this == o) || (o instanceof ProductId other && value.equals(other.value));
    }
    @Override public int hashCode() { return value.hashCode(); }
}
