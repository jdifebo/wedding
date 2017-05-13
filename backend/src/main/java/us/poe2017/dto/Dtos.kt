package us.poe2017.dto

/**
 * Kotlin data classes are a much less verbose version of Java POJOs.  And it has the added bonus of having multiple
 * per file!
 *
 * Created by jdifebo on 3/14/2017.
 */
data class Group(val code: String, val groupName: String, val names: List<String>)

data class Response(val code: String="", val email: String="", val dietaryRestrictions: String="", val comments: String="",
                val attending: Map<String, Boolean> = hashMapOf())