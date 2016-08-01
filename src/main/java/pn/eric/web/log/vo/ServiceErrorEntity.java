package pn.eric.web.log.vo;

/**
 * @author Shadow
 * @date
 */
public class ServiceErrorEntity {
    public String seiveName;
    public String methodName;
    public int invokeTimes;
    public String errorMessage;

    public String getSeiveName() {
        return seiveName;
    }

    public void setSeiveName(String seiveName) {
        this.seiveName = seiveName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getInvokeTimes() {
        return invokeTimes;
    }

    public void setInvokeTimes(int invokeTimes) {
        this.invokeTimes = invokeTimes;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
