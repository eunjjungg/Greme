package com.shootit.greme.ui.view

interface LoginFinishInterface {
    fun errorCallback()
    fun openActivityCallback(isExistingUser: Boolean)
}