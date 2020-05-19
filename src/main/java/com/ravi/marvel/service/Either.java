package com.ravi.marvel.service;

import java.util.Optional;

public class Either<Right> {
    protected Optional<RuntimeException> left;
    protected Optional<Right> right;
    private Either(RuntimeException left, Right right) {
        this.left = Optional.ofNullable(left);
        this.right = Optional.ofNullable(right);
    }
    public boolean noException() {
        return !left.isPresent();
    }
    public boolean hasException() {
        return left.isPresent();
    }
    public RuntimeException exception() {
        return left.get();
    }
    public Right rightValue() {
        return this.right.get();
    }
    public static Either toLeft(RuntimeException left) {
        return new Either(left, null);
    }
    public static <Right> Either<Right> toRight(Right right) {
        return new Either(null, right);
    }
}
