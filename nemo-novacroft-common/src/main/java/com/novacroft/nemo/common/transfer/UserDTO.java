package com.novacroft.nemo.common.transfer;

/**
 * TfL application event transfer implementation
 */
public class UserDTO extends CommonUserDTO {

    public UserDTO() {
        super();
    }

    public UserDTO(String id) {
        super();
        this.id = id;
    }
}
