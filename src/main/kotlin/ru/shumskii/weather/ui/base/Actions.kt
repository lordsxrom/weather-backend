package ru.shumskii.weather.ui.base

import divkit.dsl.Action
import divkit.dsl.Url
import divkit.dsl.action
import divkit.dsl.core.ExpressionProperty
import divkit.dsl.evaluate
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.VARIABLE_HOST
import ru.shumskii.weather.VARIABLE_USER_ID
import ru.shumskii.weather.utils.UPDATE
import ru.shumskii.weather.utils.HIDE
import ru.shumskii.weather.utils.SHOW
import ru.shumskii.weather.utils.USER_ID

fun DivScope.navigationAction(
    page: String,
    queries: Map<String, Any> = emptyMap(),
    isEnabledExpression: ExpressionProperty<Boolean>? = null,
): Action {
    val route = makeRoute(page, queries)
    val url = makeUrl(route)
    return action(
        logId = "navigate_to_page_$page",
        url = url,
    ).evaluate(
        isEnabled = isEnabledExpression,
    )
}

fun DivScope.showDialogAction(
    dialog: String,
    queries: Map<String, Any> = emptyMap(),
    isEnabledExpression: ExpressionProperty<Boolean>? = null,
): Action {
    val dialogPath = "$dialog/$SHOW"
    val route = makeRoute(dialogPath, queries)
    val url = makeUrl(route)
    return action(
        logId = "show_dialog_$dialog",
        url = url
    ).evaluate(
        isEnabled = isEnabledExpression,
    )
}

fun DivScope.hideDialogAction(
    dialog: String,
    queries: Map<String, Any> = emptyMap(),
    isEnabledExpression: ExpressionProperty<Boolean>? = null,
): Action {
    val dialogPath = "$dialog/$HIDE"
    val route = makeRoute(dialogPath, queries)
    val url = makeUrl(route)
    return action(
        logId = "hide_dialog_$dialog",
        url = url,
    ).evaluate(
        isEnabled = isEnabledExpression,
    )
}

fun DivScope.updateDialogAction(
    dialog: String,
    queries: Map<String, Any> = emptyMap(),
    isEnabledExpression: ExpressionProperty<Boolean>? = null,
): Action {
    val dialogPath = "$dialog/$UPDATE"
    val route = makeRoute(dialogPath, queries)
    val url = makeUrl(route)
    return action(
        logId = "action_dialog_$dialog",
        url = url,
    ).evaluate(
        isEnabled = isEnabledExpression,
    )
}

private fun makeUrl(
    route: String,
): Url {
    val actionUrl = "div-action://download?url=$route"
    return Url.create(actionUrl)
}

private fun makeRoute(
    path: String,
    queries: Map<String, Any> = emptyMap(),
): String {
    val extendedQueries = queries.toMutableMap().apply {
        if (!contains(USER_ID)) {
            put(USER_ID, "@{$VARIABLE_USER_ID}")
        }
    }
    val formattedQueries = extendedQueries.map { entry ->
        entry.key + "%3D" + entry.value
    }.joinToString("%26")
    return "http://@{$VARIABLE_HOST}/$path?$formattedQueries"
}

fun DivScope.setVariableAction(
    name: String,
    value: String
): Action {
    return action(
        logId = "set_variable_${name}_value_${value}",
        url = Url.create("div-action://set_variable?name=$name&value=$value")
    )
}