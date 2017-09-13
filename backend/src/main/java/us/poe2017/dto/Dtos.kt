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

data class CompletedResponse(val groupName: String, val email: String, val dietaryRestrictions: String, val comments: String,
                    val guestResponses: List<CompletedGuestResponse>)

data class CompletedGuestResponse(val name: String?, val attending: Boolean, val plusOneName: String?)

data class GroupAdmin(val code: String, val groupName: String, val guests: List<GuestAdmin>, val responses: List<GroupResponseAdmin>)

data class GuestAdmin(val name: String?, val kid: Boolean, val under21: Boolean, val plusOne: Boolean, val responses: List<GuestResponseAdmin>)

data class GroupResponseAdmin(val email: String, val dietaryRestrictions: String, val comments: String)

data class GuestResponseAdmin(val attending: Boolean, val plusOneName: String?)