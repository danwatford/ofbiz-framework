context.formParametersList = parameters.findAll { key, value ->
    def keyString = key.toString()

    keyString.startsWith("index") || keyString.startsWith("party") || keyString.startsWith("first") || keyString.startsWith("last") || keyString.startsWith("group")
}.collect { it.key + ": " + it.value }
