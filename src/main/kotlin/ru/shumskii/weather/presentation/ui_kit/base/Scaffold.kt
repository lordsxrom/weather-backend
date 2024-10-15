package ru.shumskii.weather.presentation.ui_kit.base

import divkit.dsl.*
import divkit.dsl.scope.DivScope

fun DivScope.renderScaffold(
    appBar: Div? = null,
    body: Div? = null,
    bottomNavigationBar: Div? = null,
    backgroundColor: List<Background>? = null,
    floatingActionButton: Div? = null,
): Div {
    return stack(
        id = ROOT_SCAFFOLD_ID,
        width = fixedSize(400),
        height = fixedSize(800),
        items = listOfNotNull(
            column(
                width = matchParentSize(),
                height = matchParentSize(),
                items = listOfNotNull(
                    appBar?.let { renderAppBarContainer(it) },
                    body?.let { renderBodyContainer(it, floatingActionButton) },
                    bottomNavigationBar?.let { renderBottomNavigationBarContainer(bottomNavigationBar) },
                )
            ),
            renderDialog(),
            renderBottomSheet(),
        ),
        background = backgroundColor
    )
}

private fun DivScope.renderAppBarContainer(
    div: Div,
): Div {
    return container(
        width = matchParentSize(),
        height = wrapContentSize(),
        items = listOf(div),
        alignmentVertical = top
    )
}

private fun DivScope.renderBodyContainer(
    body: Div,
    fab: Div? = null,
): Div {
    return stack(
        width = matchParentSize(),
        height = matchParentSize(),
        items = listOfNotNull(
            body,
            fab
        ),
    )
}

private fun DivScope.renderBottomNavigationBarContainer(
    div: Div,
): Div {
    return container(
        width = matchParentSize(),
        height = wrapContentSize(),
        items = listOf(div),
        alignmentVertical = bottom
    )
}

const val ROOT_SCAFFOLD_ID = "scaffold"