package com.virtucare.practiceforms.dto;

/**
 * Created by AJITI on 12-Jul-16.
 */
public class FileAttachmentDTO {

    private String id;
    private String fullimagepath;
    private String orgfilename;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullimagepath() {
        return fullimagepath;
    }

    public void setFullimagepath(String fullimagepath) {
        this.fullimagepath = fullimagepath;
    }

    public String getOrgfilename() {
        return orgfilename;
    }

    public void setOrgfilename(String orgfilename) {
        this.orgfilename = orgfilename;
    }
}
