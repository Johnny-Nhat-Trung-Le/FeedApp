INSERT INTO users (id, username, email, password) VALUES (
'feffcbac-5713-41ef-9fca-1d5f2d0eb672',
  'Bobby',
  'bobby@email.com',
  'bobby123'
);

INSERT INTO users (id, username, email, password) VALUES (
    'dc9e4e99-b2e9-4ef3-9239-4180439dfa93',
    'Billy',
    'billy@email.com',
    'billy123'
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

