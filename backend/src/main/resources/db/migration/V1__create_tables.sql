create TABLE IF NOT EXISTS users (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY ,
    username VARCHAR (255) NOT NULL,
    email VARCHAR (255) NOT NULL,
    password VARCHAR (255) NOT NULL,
    role VARCHAR(255)
);

create TABLE IF NOT EXISTS polls (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY ,
    question VARCHAR (255) NOT NULL,
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    valid_until TIMESTAMP,
    creator UUID NOT NULL
);

create TABLE IF NOT EXISTS votes (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    vote_option_id UUID NOT NULL,
    published_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id UUID NOT NULL
);

create TABLE IF NOT EXISTS vote_options (
    id UUID DEFAULT RANDOM_UUID() PRIMARY KEY,
    caption VARCHAR (255) NOT NULL,
    presentation_order INT NOT NULL,
    poll_id UUID NOT NULL
);