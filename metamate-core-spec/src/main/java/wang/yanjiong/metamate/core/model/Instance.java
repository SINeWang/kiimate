package wang.yanjiong.metamate.core.model;

import java.sql.Timestamp;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public class Instance {

    private String id;

    private Timestamp currentTimestamp;

    private String currentValue;

    private String commitId;

    private String intensionId;

    private String previousCommitId;

    private Timestamp previousTimestamp;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Timestamp getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(Timestamp currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }

    public String getIntensionId() {
        return intensionId;
    }

    public void setIntensionId(String intensionId) {
        this.intensionId = intensionId;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getPreviousCommitId() {
        return previousCommitId;
    }

    public void setPreviousCommitId(String previousCommitId) {
        this.previousCommitId = previousCommitId;
    }

    public Timestamp getPreviousTimestamp() {
        return previousTimestamp;
    }

    public void setPreviousTimestamp(Timestamp previousTimestamp) {
        this.previousTimestamp = previousTimestamp;
    }
}
