package com.huce.doan.mxh.error;

public class EmailAlreadyUsedException extends com.huce.doan.mxh.error.BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super(com.huce.doan.mxh.error.ErrorConstants.EMAIL_ALREADY_USED_TYPE, "Email is already in use!", "userManagement", "emailexists");
    }

}
