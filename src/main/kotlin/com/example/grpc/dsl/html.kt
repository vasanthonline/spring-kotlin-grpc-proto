package com.example.grpc.dsl

interface Element {
    fun render(builder: StringBuilder, indent: String)
}

class TextElement(val text: String) : Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}

abstract class Tag(val name: String): Element {
    val children = arrayListOf<Element>()
    val attributes = hashMapOf<String, String>()
    protected fun <T : Element> initTag(tag: T, init: T.() -> Unit): T {
        tag.init()
        children.add(tag)
        return tag
    }

    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent<$name${renderAttributes()}>\n")
        for( child in children) {
            child.render(builder, indent)
        }
        builder.append("$indent</$name>\n")
    }

    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for(attribute in attributes) {
            builder.append(" ${attribute.key}=\"${attribute.value}\"")
        }
        return builder.toString()
    }

    override fun toString(): String {
        val builder = StringBuilder()
        render(builder, "")
        return builder.toString()
    }
}

abstract class TagWithText(name: String): Tag(name) {
    operator fun String.unaryPlus() {
        children.add(TextElement(this))
    }
}

class HTML: TagWithText("html") {
    fun head(init: Head.() -> Unit) = initTag(Head(), init)
    fun body(init: Body.() -> Unit) = initTag(Body(), init)
}
class Head: TagWithText("head") {
    fun title(init: Title.() -> Unit) = initTag(Title(), init)
}

class Title: TagWithText("title")

class Body: TagWithText("body") {
    fun p(init: P.() -> Unit) = initTag(P(), init)
    fun a(href: String, init: A.() -> Unit) {
        val a = initTag(A(), init)
        a.href = href
    }
}

class P: TagWithText("p")

class A: TagWithText("a") {
    var href: String
        get() = attributes["href"]!!
        set(value) {
            attributes["href"] = value
        }
}

fun html(init: HTML.() -> Unit): HTML {
    val html = HTML()
    html.init()
    return html
}

object DSL {
    @JvmStatic
    fun main(args: Array<String>) {
        println(init())
    }
}

fun init(): String {
    return html {
        head {
            title {
                +"title of the page"
            }
        }
        body {
            p {
                +"This is a paragraph"
            }
            a(href = "https://kotlinlang.org") {
                +"Kotlin"
            }
        }
    }.toString()
}