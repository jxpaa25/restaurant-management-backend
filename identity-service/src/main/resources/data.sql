-- Insert 3 primary roles
INSERT INTO roles (name) VALUES ('ROLE_ADMIN') ON CONFLICT DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_MANAGER') ON CONFLICT DO NOTHING;
INSERT INTO roles (name) VALUES ('ROLE_WAITER') ON CONFLICT DO NOTHING;

-- Insert initial user
INSERT INTO users (username, password, is_first_login) 
VALUES ('admin', '$2a$12$OdtE3Mo.X9pmmuYx0ETCKO.EqRJkn1jUzEN1WUgtNonria6f8SMqi', false) 
ON CONFLICT DO NOTHING;

-- Give admin to role to initial user
INSERT INTO user_roles (user_id, role_id)
VALUES (
    (SELECT id FROM users WHERE username = 'admin'),
    (SELECT id FROM roles WHERE name = 'ROLE_ADMIN')
)
ON CONFLICT DO NOTHING;