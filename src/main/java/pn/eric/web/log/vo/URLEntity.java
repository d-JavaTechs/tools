package pn.eric.web.log.vo;

/**
 * @author Shadow
 * @date
 */
public class URLEntity{
    public String url;
    public long maxTime;
    public long minTime;
    public int invokeTimes;
    public long averageTime;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
