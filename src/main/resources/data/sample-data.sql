--Authority
INSERT INTO authority (id, name) VALUES (1 ,'ROLE_USER');
INSERT INTO authority (id, name) VALUES (2 ,'ROLE_ADMIN');

--IssueType
INSERT INTO issue_type (id, name) VALUES (1, 'TASK');
INSERT INTO issue_type (id, name) VALUES (2, 'BUG');
INSERT INTO issue_type (id, name) VALUES (3, 'FEATURE');

--UserProfile
INSERT INTO user_profile (id, username, firstname, lastname, photo) VALUES (99998, 'testUser', 'testUser', 'testUser', NULL);
INSERT INTO user_profile (id, username, firstname, lastname, photo) VALUES (99999, 'testUser1', 'AAAAAA', 'AAAAAA', NULL);

-- --UserAccount
--decoded password= testUser
INSERT INTO user_account (id, enabled, password, username, user_profile_id) VALUES (99998, true, '$2a$10$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve', 'testUser', 99998);
INSERT INTO user_account (id, enabled, password, username, user_profile_id) VALUES (99999, true, '$2a$10$KG6KiB.Yx0IDwxRdYD9dku9DP3DspOQQ1lxs4o8WNrJs74GNFHtve', 'testUser1', 99999);

-- --UserAuthority
INSERT INTO user_authority (user_id, authority_id) VALUES (99998, 2);

--Project
INSERT INTO project (id, description, icon, name, owner_id, key) VALUES (99999, 'test description', '', 'project name', 99998, 'projkey');

--Project Issue Types
INSERT  INTO project_issue_types(project_id, issue_types_id) VALUES (99999, 1);
INSERT  INTO project_issue_types(project_id, issue_types_id) VALUES (99999, 2);
INSERT  INTO project_issue_types(project_id, issue_types_id) VALUES (99999, 3)