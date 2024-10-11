package ru.shumskii.weather.ui

import divkit.dsl.Action
import divkit.dsl.Url
import divkit.dsl.action
import divkit.dsl.scope.DivScope
import ru.shumskii.weather.VARIABLE_HOST
import ru.shumskii.weather.VARIABLE_USER_ID

fun DivScope.navigationAction(
    page: String,
    queries: Map<String, Any> = emptyMap(),
    isEnabled: Boolean = true,
): Action {
    val route = makeRoute(page, queries)
    val url = makeUrl(route)
    return action(
        logId = "navigate_to_route",
        url = url,
        isEnabled = isEnabled,
    )
}

private fun makeUrl(
    route: String,
): Url {
    val actionUrl = "div-action://download?url=$route"
    return Url.create(actionUrl)
}

private fun makeRoute(
    page: String,
    queries: Map<String, Any> = emptyMap(),
): String {
    val extendedQueries = queries.toMutableMap().apply {
        put("email", "@{$VARIABLE_USER_ID}")
    }
    val formattedQueries = extendedQueries.map { entry ->
        entry.key + "%3D" + entry.value
    }.joinToString("%26")
    return "http://@{$VARIABLE_HOST}/$page/patch?$formattedQueries"
}