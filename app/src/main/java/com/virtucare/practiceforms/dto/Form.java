package com.virtucare.practiceforms.dto;

/**
 * Created by AJITI on 6/8/2016.
 */
public class Form {
    private String formId;
    private String displayFormName;
    private String formName;
    private Object formData;
    private boolean isSelected;

    public Form(String formId, String displayFormName, String formName, Object formData) {
        this.formId = formId;
        this.displayFormName = displayFormName;
        this.formName = formName;
        this.formData = formData;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getDisplayFormName() {
        return displayFormName;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public Object getFormData() {
        return formData;
    }

    public void setFormData(Object formData) {
        this.formData = formData;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}
