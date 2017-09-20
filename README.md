# Software Engineering Requirments
The project goal is to develop a secure messenger system, e.g. of the kind that might be used in a hospital. 
There are users (e.f. doctors, nurses, administrators) and the messenger system must support the ability of users to send a text messages. There are also groups and users are members of groups and when they send a message it is always to a group, with the privacy condition that only members of that group may read the message. Users may become members of any number of groups Users may send messages only to the members of a group they are registered in. Users may only access/read a message from a group they are registered in. Once a message is read by the recipient, its status changes from new to old. Users may delete their old messages.

The system provides the following queries: List of users (ID → name) and list of groups (ID → name), both sorted by name. Components of the abstract state are always sorted by ID, except for list of users, or lists of groups (as above). Lists of new and old messages for a user, are sorted by message ID.

Each user command shall be followed by the abstract state space. Queries shall display results only, not the abstract state.
