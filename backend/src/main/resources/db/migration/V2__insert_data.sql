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

INSERT INTO vote_options (caption, presentation_order, poll_id) VALUES (
         'Dog',
        1,
         'da3acfed-ef7b-477f-b1dc-76703a1dda2a');

INSERT INTO vote_options (caption, presentation_order, poll_id) VALUES (
       'Cat',
       2,
       'da3acfed-ef7b-477f-b1dc-76703a1dda2a');

