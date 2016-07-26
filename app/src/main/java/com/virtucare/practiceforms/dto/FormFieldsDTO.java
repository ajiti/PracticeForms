package com.virtucare.practiceforms.dto;

public class FormFieldsDTO {

    /* common fields start */
    private String formName;
    private String displayFormName;
    /* common fields end */

    /* common fields setter and getter start */


    public FormFieldsDTO() {
    }

    public FormFieldsDTO(String formName, String displayFormName) {
        this.formName = formName;
        this.displayFormName = displayFormName;
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getDisplayFormName() {
        return displayFormName;
    }

    public void setDisplayFormName(String displayFormName) {
        this.displayFormName = displayFormName;
    }

    /* common fields setter and getter end */


}
