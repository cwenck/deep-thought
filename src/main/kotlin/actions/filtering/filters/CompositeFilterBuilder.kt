package actions.filtering.filters

class CompositeFilterBuilder<T>() {

    private val filters: MutableList<Filter<T>> = mutableListOf()

    fun addFilter(filter: Filter<T>) : CompositeFilterBuilder<T> {
        filters.add(filter)
        return this
    }

    fun apply(value: T): Boolean {
        for (filter in filters) {
            if (!filter.accept(value)) {
                return false
            }
        }

        return true;
    }
}
