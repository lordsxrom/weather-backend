package ru.shumskii.weather.ui.resources

object Strings {
    const val NAVIGATION_ITEM_TEXT_MAIN = "Main"
    const val NAVIGATION_ITEM_TEXT_SETTINGS = "Settings"

    const val AUTH_TITLE = "Login into your account"
    const val AUTH_INPUT_TITLE_EMAIL = "Email address"
    const val AUTH_INPUT_HINT_EMAIL = "alex@email.com"
    const val AUTH_INPUT_TITLE_PASSWORD = "Password"
    const val AUTH_INPUT_HINT_PASSWORD = "Enter your password"
    const val AUTH_BUTTON_TEXT_SIGN_IN = "Login now"
    const val AUTH_DIVIDER_TEXT = "OR"
    const val AUTH_BUTTON_TEXT_SIGN_UP = "Signup now"

    const val AUTH_DIALOG_BUTTON_TEXT = "OK"
    const val AUTH_DIALOG_ERROR_TEXT_NOT_FOUND = "User not found"
    const val AUTH_DIALOG_ERROR_TEXT_INCORRECT = "Email or password is incorrect"
    fun AUTH_DIALOG_ERROR_TEXT_ALREADY_EXIST(email: String) = "User $email is already exist"
    fun AUTH_DIALOG_SUCCESS_TEXT_CREATED(email: String) = "User $email was created"
    fun AUTH_DIALOG_SUCCESS_TEXT_WELLCOME(email: String) = "Welcome back $email"

    const val ADD_CITY_BOTTOM_SHEET_TITLE_CITY = "Add a city to your favourite list"
    const val ADD_CITY_BOTTOM_SHEET_HINT_CITY = "Enter city name"
    const val ADD_CITY_BOTTOM_SHEET_BUTTON_TEXT = "Add"

    const val MAIN_APP_BAR_TEXT = "Main"
}