INSERT INTO hackathon_status (code, name, description)
VALUES ('DRAFT', 'Draft', 'Hackathon in development'),
       ('REGISTRATION_OPEN', 'Registration open', 'Registration is open: members awaited'),
       ('REGISTRATION_CLOSED', 'Registration closed', 'Registration has been closed: you cannot enter this event'),
       ('IN_PROGRESS', 'In progress', 'Hackathon is live: teams are building their solutions'),
       ('JUDGING', 'Judging', 'Hackathon results are being evaluated by judges'),
       ('COMPLETED', 'Completed', 'Hackathon has been completed, winners have been selected'),
       ('CANCELLED', 'Cancelled', 'Hackathon has been cancelled');