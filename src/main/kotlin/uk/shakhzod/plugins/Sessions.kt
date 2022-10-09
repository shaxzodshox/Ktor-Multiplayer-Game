package uk.shakhzod.plugins

import io.ktor.application.*
import io.ktor.sessions.*
import io.ktor.util.*
import uk.shakhzod.session.DrawingSession

fun Application.configureSession(){
    install(Sessions){
        cookie<DrawingSession>("SESSION")
    }
    //The below method works whenever the client requests to the server
    intercept(ApplicationCallPipeline.Features){
        //If there is no any session for this requesting user, then we need to set a new session for him/her.
        if(call.sessions.get<DrawingSession>() == null){
            val clientId = call.parameters["client_id"] ?: ""
            //Session id should always be a unique value. Thus, generateNonce can generate random values every time for each user.
            call.sessions.set(DrawingSession(clientId, generateNonce())) //Instantiating a new session
        }
    }
}