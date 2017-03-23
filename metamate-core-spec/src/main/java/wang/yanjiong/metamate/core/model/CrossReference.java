package wang.yanjiong.metamate.core.model;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public class CrossReference {

    private String id;

    private String excludeName;

    private String includeName;

    private String intensionId;

    private String referenceId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExcludeName() {
        return excludeName;
    }

    public void setExcludeName(String excludeName) {
        this.excludeName = excludeName;
    }

    public String getIncludeName() {
        return includeName;
    }

    public void setIncludeName(String includeName) {
        this.includeName = includeName;
    }

    public String getIntensionId() {
        return intensionId;
    }

    public void setIntensionId(String intensionId) {
        this.intensionId = intensionId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }
}
