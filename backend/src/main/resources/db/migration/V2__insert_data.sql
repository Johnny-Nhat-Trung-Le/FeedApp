INSERT INTO users (id, username, email, password, role) VALUES (
'feffcbac-5713-41ef-9fca-1d5f2d0eb672',
  'Bobby',
  'bobby@email.com',
  'bobby123',
  'ADMIN'
);

INSERT INTO users (id, username, email, password, role) VALUES (
    'dc9e4e99-b2e9-4ef3-9239-4180439dfa93',
    'Billy',
    'billy@email.com',
    '$2a$10$b2OwERbM8P8H.ssuPSCbhOy2gH4H6DA5vSWGfY8en5L/TvXc4rXWy',
    'USER'
);
INSERT INTO users (id, username, email, password, role) VALUES (
    'fd686afd-3f77-4f8a-abbf-7637729e1979',
    'admin',
    'admin@email.com',
    '$2a$10$/dnkf4iI5WUBgDZnLRdY.OloPQpyzIKrgLp4xVud1/YV4QGhAeX7q',
    'ADMIN'
);
INSERT INTO polls (id, question, valid_until, creator) VALUES (
     'da3acfed-ef7b-477f-b1dc-76703a1dda2a',
    'What animal?',
     '2025-10-31T10:00:00Z',
     'dc9e4e99-b2e9-4ef3-9239-4180439dfa93'
);

INSERT INTO vote_options (id, caption, presentation_order, poll_id) VALUES (
        'd90e0895-e651-4431-b768-fe30dcefa8cc',
         'Dog',
        1,
         'da3acfed-ef7b-477f-b1dc-76703a1dda2a');

INSERT INTO vote_options (id, caption, presentation_order, poll_id) VALUES (
        '562a6a39-dbc9-4648-bcaa-2c6ad46e3300',
       'Cat',
       2,
       'da3acfed-ef7b-477f-b1dc-76703a1dda2a');

INSERT INTO votes (id,vote_option_id, user_id) VALUES (
       'acf8bdaa-f52c-4008-9514-940aa0889580',
       '562a6a39-dbc9-4648-bcaa-2c6ad46e3300',
       'dc9e4e99-b2e9-4ef3-9239-4180439dfa93'
                                                    );

