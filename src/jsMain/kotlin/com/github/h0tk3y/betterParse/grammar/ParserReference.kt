package com.github.h0tk3y.betterParse.grammar

import com.github.h0tk3y.betterParse.lexer.*
import com.github.h0tk3y.betterParse.parser.ParseResult
import com.github.h0tk3y.betterParse.parser.Parser

public actual class ParserReference<out T> internal actual constructor(parserProvider: () -> Parser<T>) : Parser<T> {
    public actual val parser: Parser<T> by lazy(parserProvider)

    override fun tryParse(tokens: TokenMatchesSequence, fromPosition: Int): ParseResult<T> =
        parser.tryParse(tokens, fromPosition)
}
