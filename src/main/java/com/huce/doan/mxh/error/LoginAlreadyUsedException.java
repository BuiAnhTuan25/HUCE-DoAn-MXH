package com.huce.doan.mxh.error;

public class LoginAlreadyUsedException extends com.huce.doan.mxh.error.BadRequestAlertException {

    private static final long serialVersionUID = 1L;

    public LoginAlreadyUsedException() {
        super(com.huce.doan.mxh.error.ErrorConstants.LOGIN_ALREADY_USED_TYPE, "Login name already used!", "userManagement", "userexists");
    }
}
