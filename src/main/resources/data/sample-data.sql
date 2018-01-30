--Authority
INSERT INTO authority (id, name) VALUES (1 ,'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2 ,'ROLE_ADMIN');

--IssueType
INSERT INTO issue_type (id, name) VALUES (1, 'TASK');
INSERT INTO issue_type (id, name) VALUES (2, 'BUG');
INSERT INTO issue_type (id, name) VALUES (3, 'FEATURE');

--UserProfile
INSERT INTO user_profile (id, firstname, lastname, photo) VALUES (-1, 'testUser', 'testUser', NULL);

-- --UserAccount
--decoded password= testUser
INSERT INTO user_account (id, enabled, password, username, user_profile_id) VALUES (-1, true, '$2a$10$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve', 'testUser', -1);

-- --UserAuthority
INSERT INTO user_authority (user_id, authority_id) VALUES (-1, 2);

--Project
INSERT INTO project (id, description, icon, name, owner_id) VALUES (-1, 'test description', NULL, 'project name', -1);