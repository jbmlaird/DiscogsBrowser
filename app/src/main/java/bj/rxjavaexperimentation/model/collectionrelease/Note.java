
package bj.rxjavaexperimentation.model.collectionrelease;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Note {

    @SerializedName("field_id")
    @Expose
    private Integer fieldId;
    @SerializedName("value")
    @Expose
    private String value;

    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
