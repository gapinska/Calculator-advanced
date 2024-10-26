package calculator;

public class Calculator {
    private double state = 0.0;
    private double memory = 0.0;
    private ErrorStatus errorStatus = new ErrorStatus();

    public double getState() {
        return state;
    }

    public void setState(double state) {
        this.state = state;
    }

    public void setState(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            this.state = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void add(double value) {
        state += value;
    }

    public void add(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            add(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void addWithOverflowCheck(double value) {
        try {
            state = safeAdd(state, value);
        } catch (ArithmeticException e) {
            errorStatus.setError(true, "Overflow");
        }
    }

    public void addWithOverflowCheck(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            addWithOverflowCheck(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void subtract(double value) {
        state -= value;
    }

    public void subtract(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            subtract(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void subtractWithOverflowCheck(double value) {
        try {
            state = safeSubtract(state, value);
        } catch (ArithmeticException e) {
            errorStatus.setError(true, "Overflow");
        }
    }

    public void subtractWithOverflowCheck(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            subtractWithOverflowCheck(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void multiply(double value) {
        state *= value;
    }

    public void multiply(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            multiply(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void multiplyWithOverflowCheck(double value) {
        try {
            state = safeMultiply(state, value);
        } catch (ArithmeticException e) {
            errorStatus.setError(true, "Overflow");
        }
    }

    public void multiplyWithOverflowCheck(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            multiplyWithOverflowCheck(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void divide(double value) {
        try {
            state = safeDivide(state, value);
        } catch (ArithmeticException e) {
            errorStatus.setError(true, e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public void divide(String value) {
        try {
            if (value == null) {
                throw new NumberFormatException("Null value provided");
            }
            double num = Double.parseDouble(value);
            divide(num);
        } catch (NumberFormatException e) {
            errorStatus.setError(true, "Invalid input: " + e.getMessage());
        }
    }

    public void reset() {
        state = 0.0;
        errorStatus.setError(false, "");
    }

    public double getMemory() {
        return memory;
    }

    public void storeInMemory() {
        if (!errorStatus.getIsError()) {
            memory = state;
        }
    }

    public void getValFromMemory() {
        if (!errorStatus.getIsError()) {
            state = memory;
        }
    }

    public void addMemoryToState() {
        state += memory;
    }

    public void subtractMemoryFromState() {
        state -= memory;
    }

    public void resetMemory() {
        memory = 0.0;
    }

    public boolean isError() {
        return errorStatus.getIsError();
    }

    // Metody pomocnicze do sprawdzania przepełnienia
    private double safeAdd(double a, double b) {
        double result = a + b;
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            throw new ArithmeticException("Overflow during addition");
        }
        return result;
    }

    private double safeSubtract(double a, double b) {
        double result = a - b;
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            throw new ArithmeticException("Overflow during subtraction");
        }
        return result;
    }

    private double safeMultiply(double a, double b) {
        double result = a * b;
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            throw new ArithmeticException("Overflow during multiplication");
        }
        return result;
    }

    private double safeDivide(double a, double b) {
        if (b == 0.0) {
            throw new ArithmeticException("Error: Division by zero is not allowed.");
        }
        double result = a / b;
        if (Double.isInfinite(result) || Double.isNaN(result)) {
            throw new ArithmeticException("Overflow during division");
        }
        return result;
    }
}