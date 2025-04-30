-- database m2_final_project
BEGIN TRANSACTION;

-- *************************************************************************************************
-- Drop all db objects in the proper order
-- *************************************************************************************************
DROP TABLE IF EXISTS users;

-- *************************************************************************************************
-- Create the tables and constraints
-- *************************************************************************************************

--users (name is pluralized because 'user' is a SQL keyword)
CREATE TABLE users (
	user_id SERIAL,
	username varchar(50) NOT NULL UNIQUE,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

-- This table stores details about each user.
CREATE TABLE profiles (
    profile_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL UNIQUE,
    bio TEXT,
    location VARCHAR(100),
    experience TEXT,         -- For professionals
    specialties VARCHAR(255),-- For professionals
    birth_preferences TEXT,  -- For pregnant users
    latitude double precision,
    longitude double precision,
    CONSTRAINT fk_profiles_users FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- This table records match attempts (like swipes) between two users.
CREATE TABLE matches (
    match_id SERIAL PRIMARY KEY,
    user1_id INTEGER NOT NULL,
    user2_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'MATCHED', 'DECLINED')),
    CONSTRAINT fk_match_user1 FOREIGN KEY (user1_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_match_user2 FOREIGN KEY (user2_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- This table stores messages sent between users.
CREATE TABLE messages (
    message_id SERIAL PRIMARY KEY,
    sender_id INTEGER NOT NULL,
    receiver_id INTEGER NOT NULL,
    message_text TEXT NOT NULL,
    timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_messages_receiver FOREIGN KEY (receiver_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- This table handles appointments between a pregnant user and a professional.
CREATE TABLE appointments (
    appointment_id SERIAL PRIMARY KEY,
    pregnant_user_id INTEGER NOT NULL,
    professional_id INTEGER NOT NULL,
    appointment_time TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'CONFIRMED', 'CANCELED')),
    CONSTRAINT fk_appointment_pregnant FOREIGN KEY (pregnant_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_appointment_professional FOREIGN KEY (professional_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- This table stores friend relationships (or friend requests) between users.
-- Each record shows who sent the friend request, who received it, and the current status.
CREATE TABLE friends (
    friend_id SERIAL PRIMARY KEY,
    requester_id INTEGER NOT NULL,
    addressee_id INTEGER NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('PENDING', 'ACCEPTED', 'DECLINED')),
    CONSTRAINT fk_friends_requester FOREIGN KEY (requester_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT fk_friends_addressee FOREIGN KEY (addressee_id) REFERENCES users(user_id) ON DELETE CASCADE,
    CONSTRAINT unique_friend_request UNIQUE (requester_id, addressee_id)
);

COMMIT TRANSACTION;
