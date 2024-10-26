package calculator;

public class ErrorStatus {
    private boolean isError;
    private String errorMessage;

    public ErrorStatus() {
        this.isError = false;
        this.errorMessage = "";
    }

    public void setError(boolean error, String errorMessage) {
        this.isError = error;
        this.errorMessage = errorMessage;
    }

    public boolean getIsError(){
        return isError;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void resetError(){
        this.isError = false;
        this.errorMessage = "";
    }
}
