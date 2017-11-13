# chat
## Chatting API 
- chat in group
- chat between both single-user  
             
## *Handler*:
### *Send message*
- Send messages:  <br/>*+send normal text <br/>
                 + send Emoji<br/>
                 + send URL*<br/>
          - Attachment<br/>
                 *+ send image*<br/>
                 *+ send file*<br/>
                 
- sendTypingIndicator: 
Sends a "USERNAME is typing" indicator to other members of the thread indicated by threadID. This indication will disappear            after 30 second or when the end function is called. The end function is returned by api.sendTypingIndicator.

- setMessageReaction: 

### *Receive messages*
- getEmojiURL

- getThreadHistory

- getThreadImages

- getTheadFile

- listen : listen to events: <br/>
-*messageEvent*: a message was sent to a thread (Note: unread or not) <br/>
-*typeEvent*: an user in a thread is typing<br/>
-*readEvent*: the current user has read the message<br/>
-*read_receipt*: user within a thread has seen the messg<br/>
-*presence*: user online or not<br/>

### *Manage*
Participants
- removeUserFromGroup: just group
    
- addUserToGroup: both single-user chat and group chat

- blockUser

- getUserList

- searchUserByName : by nickname or username

Messages
- editMessage: just text

- deleteMessage: all text, file and image and emoji

- forwardMessage: forward message to an other user - do with all file, image and text

Threads
- deleteThread

- setTitleThread: just group chat

Security
- login
- logout

        
