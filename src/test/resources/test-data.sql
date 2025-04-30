BEGIN TRANSACTION;

-- Users
INSERT INTO users (username, password_hash, role) VALUES ('user1','user1','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user2','user2','ROLE_USER');
INSERT INTO users (username, password_hash, role) VALUES ('user3','user3','ROLE_USER');


INSERT INTO users (username, password_hash, role) VALUES
('mackerdoodle', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
('holytrin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_USER'),
('admin', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_ADMIN'),
('drsmith', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_PROFESSIONAL'),
('notsomidanna', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_PROFESSIONAL'),
('emmap', '$2a$10$tmxuYYg1f5T0eXsTPlq/V.DJUKmRHyFbJ.o.liI1T35TFbjs2xiem', 'ROLE_PROFESSIONAL');


-- User IDs:
-- 1: Mackinzie Hansen (pregnant)
-- 2: Trinity Newport (pregnant)
-- 3: Admin User (admin)
-- 4: Dr. Smith (professional)
-- 5: Midwife Anna (professional)
-- 6: Doula Emma (professional)

-- Insert sample profiles for non-admin users
INSERT INTO profiles (user_id, bio, location, experience, specialties, birth_preferences)
VALUES
    (1, 'Expecting first child and looking for personalized care.', 'Loveland, CO', NULL, NULL, 'Home birth preferred'),
    (2, 'Seeking natural childbirth options.', 'Boulder, CO', NULL, NULL, 'Water birth'),
    (4, 'Experienced OB/GYN with 15 years in practice.', 'Denver, CO', '15 years', 'Obstetrics', NULL),
    (5, 'Certified midwife specializing in natural births.', 'Boulder, CO', '10 years', 'Midwifery', NULL),
    (6, 'Passionate doula offering continuous support.', 'Denver, CO', '8 years', 'Doula services', NULL);

-- Insert sample appointments (matches between pregnant users and professionals)
-- Mackinzie Hansen (user_id 1) books an appointment with Dr. Smith (user_id 4)
INSERT INTO appointments (pregnant_user_id, professional_id, appointment_time, status)
VALUES
    (1, 4, '2025-03-01 14:00:00', 'PENDING'),
    -- Trinity Newport (user_id 2) books an appointment with Midwife Anna (user_id 5)
    (2, 5, '2025-03-02 09:00:00', 'CONFIRMED');

-- Insert sample friend requests (or friend relationships)
-- Friend requests are only between users in the same category.
-- For pregnant users (ROLE_USER): Mackinzie Hansen (1) sends a friend request to Trinity Newport (2) which gets accepted.
INSERT INTO friends (requester_id, addressee_id, status)
VALUES
    (1, 2, 'ACCEPTED');

-- For professionals (ROLE_PROFESSIONAL): A friend request between Dr. Smith (4) and Midwife Anna (5) is pending,
-- and a friend request between Midwife Anna (5) and Doula Emma (6) is pending.
INSERT INTO friends (requester_id, addressee_id, status)

VALUES
    (4, 5, 'PENDING'),
    (5, 6, 'PENDING');

-- Insert sample matches (only between pregnant users and professionals)
-- Mackinzie Hansen (pregnant, user_id 1) is matched with Dr. Smith (professional, user_id 4)
INSERT INTO matches (user1_id, user2_id, status)
VALUES
    (1, 4, 'MATCHED'),
    -- Trinity Newport (pregnant, user_id 2) is matched with Midwife Anna (professional, user_id 5)
    (2, 5, 'MATCHED');

-- Insert sample messages between users
-- Mackinzie Hansen (user_id 1) sends a message to Dr. Smith (user_id 4)
INSERT INTO messages (sender_id, receiver_id, message_text)
VALUES
    (1, 4, 'Hi Dr. Smith! I am excited for our first appointment!'),
    -- Dr. Smith replies to Mackinzie Hansen
    (4, 1, 'Hi Mackinzie, I look forward to discussing your birth plan in detail.');

COMMIT TRANSACTION;
