package pn.eric.web.log.vo;

/**
 * @author Shadow
 * @date
 */
public class ServiceEntity{
    public String seiveName;
    public String methodName;
    public long maxTime;
    public long minTime;
    public int invokeTimes;
    public long averageTime;


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

    public long getMaxTime() {
        return maxTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

    public long getMinTime() {
        return minTime;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public int getInvokeTimes() {
        return invokeTimes;
    }

    public void setInvokeTimes(int invokeTimes) {
        this.invokeTimes = invokeTimes;
    }

    public long getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(long averageTime) {
        this.averageTime = averageTime;
    }
}
