package com.uottawahack.chefswipe;

public class RecipeInfo<T> {
    private RecipeInfo() {}

    public static final class Success<T> extends RecipeInfo<T> {
        public T data;

        public Success(T data) {
            this.data = data;
        }
    }

    public static final class Error<T> extends RecipeInfo<T> {
        public Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }
    }
}
