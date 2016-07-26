package com.virtucare.practiceforms.dto;

import java.util.List;

/**
 * Created by AJITI on 16-Jul-16.
 */
public class SharedIdDTO {

    private int sharedId;
    private List<SharedToMemberDetailsDTO> sharedMembers;

    public void setMembersList(List<SharedToMemberDetailsDTO> sharedMembers) {
        this.sharedMembers = sharedMembers;
    }

    public int getSharedId() {
        return sharedId;
    }

    public void setSharedId(int sharedId) {
        this.sharedId = sharedId;
    }

    public List<SharedToMemberDetailsDTO> getSharedMembers() {
        return sharedMembers;
    }

    public void setSharedMembers(List<SharedToMemberDetailsDTO> sharedMembers) {
        this.sharedMembers = sharedMembers;
    }
}
