package wang.yanjiong.metamate.core.model;

import java.sql.Timestamp;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public class Instance {

    private String id;

    private Timestamp currentTimestamp;

    private String currentValue;

    private String currentReferenceId;

    private String intensionId;

    private String currentCommit;

    private String previousCommit;

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

    public String getCurrentReferenceId() {
        return currentReferenceId;
    }

    public void setCurrentReferenceId(String currentReferenceId) {
        this.currentReferenceId = currentReferenceId;
    }

    public String getIntensionId() {
        return intensionId;
    }

    public void setIntensionId(String intensionId) {
        this.intensionId = intensionId;
    }

    public String getCurrentCommit() {
        return currentCommit;
    }

    public void setCurrentCommit(String currentCommit) {
        this.currentCommit = currentCommit;
    }

    public String getPreviousCommit() {
        return previousCommit;
    }

    public void setPreviousCommit(String previousCommit) {
        this.previousCommit = previousCommit;
    }

    public Timestamp getPreviousTimestamp() {
        return previousTimestamp;
    }

    public void setPreviousTimestamp(Timestamp previousTimestamp) {
        this.previousTimestamp = previousTimestamp;
    }
}
