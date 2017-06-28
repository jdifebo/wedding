package us.poe2017.dto

/**
 * Kotlin data classes are a much less verbose version of Java POJOs.  And it has the added bonus of having multiple
 * per file!
 *
 * Created by jdifebo on 3/14/2017.
 */
data class Group(val code: String, val groupName: String, val guests: List<Guest>)

data class Guest(val id: Long, val name: String?, val isPlusOne: Boolean)

data class Response(val code: String="", val email: String="", val dietaryRestrictions: String="", val comments: String="",
                val guestResponses: Map<Long, GuestResponse> = hashMapOf())

data class GuestResponse(val attending: Boolean=false, val plusOneName: String?=null)

data class Success(val success: Boolean)