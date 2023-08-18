package charlie.domain;

import java.util.Optional;

public class Result<T> {

    private Optional<T> value;
    private Optional<String> error;

    private Result(T value, String error) {
        this.value = Optional.ofNullable(value);
        this.error = Optional.ofNullable(error);
    }

    public static <U> Result<U> ok(U value) {
        return new Result<>(value, null);
    }

    public static <U> Result<U> error(String error) {
        return new Result<>(null, error);
    }

    public boolean isError() {
        return error.isPresent();
    }

    public T getValue() {
        if (!value.isPresent()) {
            return null;
        }

        return value.get();
    }

    public String getError() {
        if (!error.isPresent()) {
            return null;
        }

        return error.get();
    }

    @Override
    public String toString() {
        return "Result{" + "value=" + value + ", error=" + error + '}';
    }
   
}
