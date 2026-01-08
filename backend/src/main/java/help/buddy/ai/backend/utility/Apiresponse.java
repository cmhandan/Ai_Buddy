package help.buddy.ai.backend.utility;

public class Apiresponse<T> {
    private T data;
    private Enum status;
    private String message;

    public Apiresponse(T data, Enum status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Enum getStatus() {
        return status;
    }

    public void setStatus(Enum status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Apiresponse{" +
                "data=" + data +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
