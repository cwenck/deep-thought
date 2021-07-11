package actions.filtering.filters

fun interface Filter<T> {
    fun accept(value: T): Boolean
}
