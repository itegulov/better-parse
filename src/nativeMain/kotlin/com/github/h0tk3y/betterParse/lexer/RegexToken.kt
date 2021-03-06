package com.github.h0tk3y.betterParse.lexer

private class RelativeInput(val fromIndex: Int, val input: CharSequence) : CharSequence {
    override val length: Int get() = input.length - fromIndex
    override fun get(index: Int): Char = input[index + fromIndex]
    override fun subSequence(startIndex: Int, endIndex: Int) =
        input.subSequence(startIndex + fromIndex, endIndex + fromIndex)

    override fun toString(): String = error("unsupported operation")
}

public actual class RegexToken : Token {
    private val pattern: String
    private val regex: Regex

    public companion object {
        public const val inputStartPrefix: String = "\\A"
    }

    public actual constructor(name: String?, @Language("RegExp", "", "") patternString: String, ignored: Boolean)
            : super(name, ignored) {
        pattern = patternString
        regex = if (patternString.startsWith(inputStartPrefix))
            patternString.toRegex()
        else
            ("$inputStartPrefix(?:$patternString)").toRegex()
    }

    public actual constructor(name: String?, regex: Regex, ignored: Boolean)
            : super(name, ignored) {
        pattern = regex.pattern
        this.regex = regex
    }

    public override fun match(input: CharSequence, fromIndex: Int): Int {
        val relativeInput = RelativeInput(fromIndex, input)

        return regex.find(relativeInput)?.range?.let {
            val length = it.last - it.first + 1
            length
        } ?: 0
    }

    public override fun toString(): String = "${name ?: ""} [$pattern]" + if (ignored) " [ignorable]" else ""
}
