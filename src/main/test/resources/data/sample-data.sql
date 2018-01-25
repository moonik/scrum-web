--Authority
INSERT INTO authority(id, name) VALUES (1 ,'ROLE_USER');
INSERT INTO authority(id, name) VALUES (2 ,'ROLE_ADMIN');

--UserProfile
INSERT INTO user_profile(id, firstname, lastname, photo) VALUES (-1, 'testUser', 'testUser', '');

-- --UserAccount
--decoded password= testUser
INSERT INTO user_account(id, enabled, password, username, user_profile_id) VALUES (-1, true, '$2a$10$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve', 'Mr Smith', -1);

-- --UserAuthority
INSERT INTO user_authority(user_id, authority_id) VALUES (-1, 2);